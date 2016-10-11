package io.openems.core.scheduler;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.openems.api.controller.Controller;
import io.openems.core.datamanager.DataManager;

public class Scheduler extends Thread{
	
	private final DataManager dm;
	private final List<Controller> controller;
	private long lastExecution = 0;
	private final int wait = 1000; 
	
	public Scheduler(DataManager dm,List<Controller> controller){
		this.dm = dm;
		this.controller = controller;
	}
	
	public void activate() {
		System.out.println("Activate");
		Collections.sort(controller, (Controller c1, Controller c2) ->  c1.priority() - c2.priority());
		this.setName("Scheduler");
		this.start();
	}
	
	@Override
	public void run() {
		while(!this.isInterrupted()){
			lastExecution = System.currentTimeMillis();
			for(Controller c : controller){
				//TODO Set values to Fields
				//TODO Timeout
				c.run();
			}
			try {
				Thread.sleep(wait - (System.currentTimeMillis()-lastExecution));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		super.run();
	}
	
//	private Set<Class<?>> getAllSuperclasses(Class<?> clazz) {
//		// Source: http://stackoverflow.com/questions/22031207/find-all-classes-and-interfaces-a-class-extends-or-implements-recursively
//		List<Class<?>> res = new ArrayList<>();
//		do {
//	        res.add(clazz);
//
//	        // First, add all the interfaces implemented by this class
//	        Class<?>[] interfaces = clazz.getInterfaces();
//	        if (interfaces.length > 0) {
//	            res.addAll(Arrays.asList(interfaces));
//
//	            for (Class<?> interfaze : interfaces) {
//	                res.addAll(getAllSuperclasses(interfaze));
//	            }
//	        }
//
//	        // Add the super class
//	        Class<?> superClass = clazz.getSuperclass();
//
//	        // Interfaces does not have java,lang.Object as superclass, they have null, so break the cycle and return
//	        if (superClass == null) {
//	            break;
//	        }
//
//	        // Now inspect the superclass 
//	        clazz = superClass;
//	    } while (!"java.lang.Object".equals(clazz.getCanonicalName()));
//		return new HashSet<Class<?>>(res);
//	}
//	
//	private List<Field> getMatchingFields(Object obj, Class<? extends Annotation> annotation) {
//		Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
//		ArrayList<Field> fields = new ArrayList<>();
//		for(Class<?> clazz : clazzes) {
//			for (Field field : clazz.getDeclaredFields()) {
//				if (field.isAnnotationPresent(annotation)) {
//					fields.add(field);
//				}
//			}
//		}
//		return fields;
//	}
//	
//	private List<Method> getMatchingMethods(Object obj, Class<? extends Annotation> annotation) {
//		Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
//		ArrayList<Method> methods = new ArrayList<>();
//		for(Class<?> clazz : clazzes) {
//			for (Method method : clazz.getDeclaredMethods()) {
//				if (method.isAnnotationPresent(annotation)) {
//					methods.add(method);
//				}
//			}
//		}
//		return methods;
//	}
//	
//	// Obsolete
//	private List<Member> getMatchingMembers(Object obj, Class<? extends Annotation> annotation) {
//		Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
//		ArrayList<Member> members = new ArrayList<>();
//		for(Class<?> clazz : clazzes) {
//			for (Field field : clazz.getDeclaredFields()) {
//				if (field.isAnnotationPresent(annotation)) {
//					members.add(field);
//				}
//			}
//			for (Method method : clazz.getDeclaredMethods()) {
//				if (method.isAnnotationPresent(annotation)) {
//					members.add(method);
//				}
//			}
//		}
//		return members;
//	}
//	
//	private List<Member> getItemMembers(Thing thing) {
//		return getMatchingMembers(thing, IsItem.class);
//	}
//
//	private List<Field> getRequiredReadonlyFields(Controller controller) {
//		return getMatchingFields(controller, IsRequired.class);
//	}
//
//	private List<Member> getRequiredReadonlyMembers(Controller controller) {
//		return getMatchingMembers(controller, IsRequired.class);
//	}
//	
//	private Controller getHighestPriorityController(Integer startFromPriority) {
//		Controller returnController = null;
//		for(Controller controller : controllers.values()) {
//			if(controller.getPriority() < startFromPriority) {
//				if(returnController == null) {
//					returnController = controller;
//				} else {
//					if(returnController.getPriority() < controller.getPriority()) {
//						returnController = controller;
//					}
//				}
//			}
//		}
//		return returnController;
//	}
}
