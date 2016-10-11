package io.openems.core.datamanager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.openems.api.general.Thing;
import io.openems.api.general.data.Value;
import io.openems.api.general.data.element.Element;
import io.openems.api.general.data.element.ElementChangedListener;
import io.openems.api.general.dataaccess.IsItem;

public class DataManager {

	private final Map<ChannelId, Channel> channels;
	private final Map<String, Thing> things;
	private final Map<String, List<String>> typeIdMapping;

	public DataManager() {
		channels = new HashMap<>();
		things = new HashMap<>();
		typeIdMapping = new HashMap<>();
	}

	public Channel getChannel(ChannelId id) throws ChannelNotFoundException {
		Channel c = channels.get(id);
		if (c == null) {
			throw new ChannelNotFoundException(id);
		}
		return c;
	}

	public void addThing(Thing d) {
		if (!things.containsKey(d.getName())) {
			things.put(d.getName(), d);
			registerChannels(d);
		}
	}

	private void registerChannels(Thing thing) {
		Class<?> thingClass = thing.getClass();
		List<Method> methods = getMatchingFields(thing, IsItem.class);
		for (Method m : methods) {
			try {
				Method writeMethod = null;
				try {
					writeMethod = thingClass.getMethod("write" + m.getName().substring(3), Value.class);
				} catch (NoSuchMethodException e) {

				}
				if (m.getReturnType().equals(Element.class)) {
					try {
						Element e = (Element) m.invoke(thing);
						if (e != null) {
							Channel c = new Channel(m.getName().substring(3).toLowerCase(), thing, writeMethod);
							ChannelUpdateElementChangeListener listener = new ChannelUpdateElementChangeListener(c);
							e.addListener(listener);
							channels.put(c.getId(), c);
						}
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private List<Method> getMatchingFields(Object obj, Class<? extends Annotation> annotation) {
		Set<Class<?>> clazzes = getAllSuperclasses(obj.getClass());
		ArrayList<Method> methods = new ArrayList<>();
		for (Class<?> clazz : clazzes) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.isAnnotationPresent(annotation)) {
					methods.add(method);
				}
			}
		}
		return methods;
	}

	private Set<Class<?>> getAllSuperclasses(Class<?> clazz) {
		// Source:
		// http://stackoverflow.com/questions/22031207/find-all-classes-and-interfaces-a-class-extends-or-implements-recursively
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

			// Interfaces does not have java,lang.Object as superclass, they
			// have null, so break the cycle and return
			if (superClass == null) {
				break;
			}

			// Now inspect the superclass
			clazz = superClass;
		} while (!"java.lang.Object".equals(clazz.getCanonicalName()));
		return new HashSet<Class<?>>(res);
	}
}
