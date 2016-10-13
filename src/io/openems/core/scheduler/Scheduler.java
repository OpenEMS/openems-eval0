package io.openems.core.scheduler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.openems.api.controller.Controller;
import io.openems.api.controller.DataMap;
import io.openems.api.controller.Mapping;
import io.openems.api.general.Thing;
import io.openems.api.general.data.Record;
import io.openems.api.general.data.Value;
import io.openems.api.general.dataaccess.IsRequired;
import io.openems.api.general.dataaccess.Permission;
import io.openems.core.datamanager.Channel;
import io.openems.core.datamanager.ChannelMapping;
import io.openems.core.datamanager.DataManager;

public class Scheduler extends Thread {

	private final DataManager dm;
	private final List<Controller> controller;
	private long lastExecution = 0;
	private final int wait = 1000;
	private final Map<Controller, List<ChannelMapping>> readFieldMapping;
	private final Map<Controller, List<ChannelMapping>> writeFieldMapping;

	public Scheduler(DataManager dm, List<Controller> controller) {
		this.dm = dm;
		this.controller = controller;
		this.readFieldMapping = new HashMap<>();
		this.writeFieldMapping = new HashMap<>();
	}

	public void activate() throws Exception {
		System.out.println("Activate");
		Collections.sort(controller, (Controller c1, Controller c2) -> c1.priority() - c2.priority());
		try {
			generateMappings();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new Exception("Failed to generate FieldChannelMapping", e);
		}
		this.setName("Scheduler");
		this.start();
	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {
			lastExecution = System.currentTimeMillis();
			for (Controller c : controller) {
				// TODO Set values to Fields
				try {
					updateReadMappings(c);
					// TODO Timeout
					c.run();
					
					checkWriteMappings(c);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				if (wait - (System.currentTimeMillis() - lastExecution) > 0) {
					Thread.sleep(wait - (System.currentTimeMillis() - lastExecution));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();
	}

	private void updateReadMappings(Controller c) throws IllegalArgumentException, IllegalAccessException {
		for (ChannelMapping cm : readFieldMapping.get(c)) {
			cm.updateField();
		}
	}
	
	private void checkWriteMappings(Controller c) throws IllegalArgumentException, IllegalAccessException{
		for(ChannelMapping cm : writeFieldMapping.get(c)){
			Value v = cm.getWrittenValue();
			if(v != null){
				System.out.println("Controller "+ c.getName() + " has written Value " + v.asString() + " on Field " + cm.getChannel().getId());
			}
		}
	}

	private void generateMappings() throws InstantiationException, IllegalAccessException {
		for (Controller controller : controller) {
			List<ChannelMapping> readChannelMap = new LinkedList<>();
			readFieldMapping.put(controller, readChannelMap);
			List<ChannelMapping> writeChannelMap = new LinkedList<>();
			writeFieldMapping.put(controller, writeChannelMap);
			// Search Fields with @Mapping Annotation
			for (Field f : controller.getClass().getDeclaredFields()) {
				if (f.isAnnotationPresent(Mapping.class)) {
					Mapping m = f.getAnnotation(Mapping.class);
					Class<?> mappingClass = (Class<?>) ((ParameterizedType) f.getGenericType())
							.getActualTypeArguments()[0];
					// Check if the field has the right type
					if (DataMap.class.isAssignableFrom(mappingClass)) {
						// generate List of mappings which are set to the Field
						// of the Controller
						List<DataMap> dataMaps = new ArrayList<>();
						// Search all things of the specified type of the
						// mapping annotation
						List<Thing> things = dm.getThingsByClass(m.type());
						for (Thing thing : things) {
							DataMap dataMap = (DataMap) mappingClass.newInstance();
							dataMaps.add(dataMap);
							// Search fields, which are marked with the
							// IsRequired annotation
							for (Field mappingField : mappingClass.getDeclaredFields()) {
								if (mappingField.isAnnotationPresent(IsRequired.class)) {
									IsRequired r = mappingField.getAnnotation(IsRequired.class);
									// Search Channel by id and generate
									// channelmapping
									if (r.permission() == Permission.READ && mappingField.getType().equals(Record.class)) {
										Channel channel = dm.getChannelFromThing(thing, r.itemId());
										if (channel != null) {
											readChannelMap.add(new ChannelMapping(channel, mappingField, dataMap));
										}
									}else if(r.permission() == Permission.WRITE && Value.class.isAssignableFrom(mappingField.getType())){
										Channel channel = dm.getChannelFromThing(thing, r.itemId());
										if (channel != null) {
											writeChannelMap.add(new ChannelMapping(channel, mappingField, dataMap));
										}
									}
								}
							}
						}
						f.set(controller, dataMaps);
					}
				}
			}
		}
	}

	// private Set<Class<?>> getAllSuperclasses(Class<?> clazz) {
	// // Source:
	// http://stackoverflow.com/questions/22031207/find-all-classes-and-interfaces-a-class-extends-or-implements-recursively
	// List<Class<?>> res = new ArrayList<>();
	// do {
	// res.add(clazz);
	//
	// // First, add all the interfaces implemented by this class
	// Class<?>[] interfaces = clazz.getInterfaces();
	// if (interfaces.length > 0) {
	// res.addAll(Arrays.asList(interfaces));
	//
	// for (Class<?> interfaze : interfaces) {
	// res.addAll(getAllSuperclasses(interfaze));
	// }
	// }
	//
	// // Add the super class
	// Class<?> superClass = clazz.getSuperclass();
	//
	// // Interfaces does not have java,lang.Object as superclass, they have
	// null, so break the cycle and return
	// if (superClass == null) {
	// break;
	// }
	//
	// // Now inspect the superclass
	// clazz = superClass;
	// } while (!"java.lang.Object".equals(clazz.getCanonicalName()));
	// return new HashSet<Class<?>>(res);
	// }
	//
	// private List<Field> getMatchingFields(Object obj, Class<? extends
	// Annotation> annotation) {
	// Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
	// ArrayList<Field> fields = new ArrayList<>();
	// for(Class<?> clazz : clazzes) {
	// for (Field field : clazz.getDeclaredFields()) {
	// if (field.isAnnotationPresent(annotation)) {
	// fields.add(field);
	// }
	// }
	// }
	// return fields;
	// }
	//
	// private List<Method> getMatchingMethods(Object obj, Class<? extends
	// Annotation> annotation) {
	// Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
	// ArrayList<Method> methods = new ArrayList<>();
	// for(Class<?> clazz : clazzes) {
	// for (Method method : clazz.getDeclaredMethods()) {
	// if (method.isAnnotationPresent(annotation)) {
	// methods.add(method);
	// }
	// }
	// }
	// return methods;
	// }
	//
	// // Obsolete
	// private List<Member> getMatchingMembers(Object obj, Class<? extends
	// Annotation> annotation) {
	// Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
	// ArrayList<Member> members = new ArrayList<>();
	// for(Class<?> clazz : clazzes) {
	// for (Field field : clazz.getDeclaredFields()) {
	// if (field.isAnnotationPresent(annotation)) {
	// members.add(field);
	// }
	// }
	// for (Method method : clazz.getDeclaredMethods()) {
	// if (method.isAnnotationPresent(annotation)) {
	// members.add(method);
	// }
	// }
	// }
	// return members;
	// }
	//
	// private List<Member> getItemMembers(Thing thing) {
	// return getMatchingMembers(thing, IsItem.class);
	// }
	//
	// private List<Field> getRequiredReadonlyFields(Controller controller) {
	// return getMatchingFields(controller, IsRequired.class);
	// }
	//
	// private List<Member> getRequiredReadonlyMembers(Controller controller) {
	// return getMatchingMembers(controller, IsRequired.class);
	// }
	//
	// private Controller getHighestPriorityController(Integer
	// startFromPriority) {
	// Controller returnController = null;
	// for(Controller controller : controllers.values()) {
	// if(controller.getPriority() < startFromPriority) {
	// if(returnController == null) {
	// returnController = controller;
	// } else {
	// if(returnController.getPriority() < controller.getPriority()) {
	// returnController = controller;
	// }
	// }
	// }
	// }
	// return returnController;
	// }
}
