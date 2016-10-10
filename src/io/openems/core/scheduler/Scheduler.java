package io.openems.core.scheduler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.openems.api.controller.Controller;
import io.openems.api.general.Thing;
import io.openems.api.general.dataaccess.IsItem;
import io.openems.api.general.dataaccess.IsRequired;
import io.openems.core.datamanager.DataManager;

public class Scheduler {
	
	private final DataManager dm;
	
	public Scheduler(DataManager dm){
		this.dm = dm;
	}
	
	public void activate() {
		System.out.println("Activate");
		
		
	}
	
	private Set<Class<?>> getAllSuperclasses(Class<?> clazz) {
		// Source: http://stackoverflow.com/questions/22031207/find-all-classes-and-interfaces-a-class-extends-or-implements-recursively
		List<Class<?>> res = new ArrayList<>();
		do {
	        res.add(clazz);

	        // First, add all the interfaces implemented by this class
	        Class<?>[] interfaces = clazz.getInterfaces();
	        if (interfaces.length > 0) {
	            res.addAll(Arrays.asList(interfaces));

	            for (Class<?> interfaze : interfaces) {
	                res.addAll(getAllSuperclasses(interfaze));
	            }
	        }

	        // Add the super class
	        Class<?> superClass = clazz.getSuperclass();

	        // Interfaces does not have java,lang.Object as superclass, they have null, so break the cycle and return
	        if (superClass == null) {
	            break;
	        }

	        // Now inspect the superclass 
	        clazz = superClass;
	    } while (!"java.lang.Object".equals(clazz.getCanonicalName()));
		return new HashSet<Class<?>>(res);
	}
	
	private List<Field> getMatchingFields(Object obj, Class<? extends Annotation> annotation) {
		Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
		ArrayList<Field> fields = new ArrayList<>();
		for(Class<?> clazz : clazzes) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(annotation)) {
					fields.add(field);
				}
			}
		}
		return fields;
	}
	
	private List<Method> getMatchingMethods(Object obj, Class<? extends Annotation> annotation) {
		Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
		ArrayList<Method> methods = new ArrayList<>();
		for(Class<?> clazz : clazzes) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.isAnnotationPresent(annotation)) {
					methods.add(method);
				}
			}
		}
		return methods;
	}
	
	// Obsolete
	private List<Member> getMatchingMembers(Object obj, Class<? extends Annotation> annotation) {
		Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
		ArrayList<Member> members = new ArrayList<>();
		for(Class<?> clazz : clazzes) {
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(annotation)) {
					members.add(field);
				}
			}
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.isAnnotationPresent(annotation)) {
					members.add(method);
				}
			}
		}
		return members;
	}
	
	private List<Member> getItemMembers(Thing thing) {
		return getMatchingMembers(thing, IsItem.class);
	}

	private List<Field> getRequiredReadonlyFields(Controller controller) {
		return getMatchingFields(controller, IsRequired.class);
	}

	private List<Member> getRequiredReadonlyMembers(Controller controller) {
		return getMatchingMembers(controller, IsRequired.class);
	}
	
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
