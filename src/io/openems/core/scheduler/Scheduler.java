package io.openems.core.scheduler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.openems.api.controller.Controller;
import io.openems.api.controller.DataMap;
import io.openems.api.controller.Mapping;
import io.openems.api.controller.Range;
import io.openems.api.general.Thing;
import io.openems.api.general.data.Record;
import io.openems.api.general.data.Value;
import io.openems.api.general.dataaccess.IsRequired;
import io.openems.api.general.dataaccess.Permission;
import io.openems.core.datamanager.Channel;
import io.openems.core.datamanager.ChannelWriteException;
import io.openems.core.datamanager.DataManager;

public class Scheduler extends Thread {

	private final DataManager dm;
	private final List<Controller> controller;
	private long lastExecution = 0;
	private final int wait = 1000;
	private final Map<Controller, List<ChannelMapping>> readFieldMapping;
	private final Map<Controller, List<ChannelMapping>> writeFieldMapping;
	private final List<Channel> writtenChannels;
	private final Map<Channel, Range> rangeCache;

	public Scheduler(DataManager dm, List<Controller> controller) {
		this.dm = dm;
		this.controller = controller;
		this.readFieldMapping = new HashMap<>();
		this.writeFieldMapping = new HashMap<>();
		writtenChannels = new ArrayList<>();
		rangeCache = new HashMap<>();
	}

	public void activate() throws Exception {
		System.out.println("Activate");
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
			Collections.sort(controller, (Controller c1, Controller c2) -> c1.priority() - c2.priority());
			lastExecution = System.currentTimeMillis();
			writtenChannels.clear();
			rangeCache.clear();
			for (Controller c : controller) {
				try {
					if (isExecutionAllowed(c)) {
						updateReadMappings(c);
						// TODO Timeout
						c.run();

						checkWriteMappings(c);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ChannelWriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RangeModificationException e) {
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
			Range r = rangeCache.get(cm.getChannel());
			if (r != null) {
				cm.updateField(r);
			} else {
				cm.updateField();
			}
		}
	}

	private void checkWriteMappings(Controller c)
			throws IllegalArgumentException, IllegalAccessException, ChannelWriteException, RangeModificationException {
		HashMap<Channel, Range> writtenRanges = new HashMap<>();
		HashMap<Channel, Range> ranges = new HashMap<>();
		for (ChannelMapping cm : writeFieldMapping.get(c)) {
			Range r = cm.getWriteRange();
			if (r != null) {
				ranges.put(cm.getChannel(), r);
				if (r.getWriteValue() != null) {
					writtenRanges.put(cm.getChannel(), r);
				}
			}
		}
		for (Entry<Channel, Range> r : ranges.entrySet()) {
			rangeCache.put(r.getKey(), r.getValue());
		}
		for (Entry<Channel, Range> wr : writtenRanges.entrySet()) {
			System.out.println("Controller " + c.getName() + " has written Value " + wr.getValue().getWriteValue().asString()
					+ " on Field " + wr.getKey().getId());
			wr.getKey().write(wr.getValue().getWriteValue());
			writtenChannels.add(wr.getKey());
		}
	}

	private boolean isExecutionAllowed(Controller c) {
		List<ChannelMapping> mappings = writeFieldMapping.get(c);
		for (ChannelMapping mapping : mappings) {
			if (writtenChannels.contains(mapping.getChannel())) {
				return false;
			}
		}
		return true;
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
									if (mappingField.getType().equals(Range.class)) {
										Channel channel = dm.getChannelFromThing(thing, r.itemId());
										if (channel != null) {
											ChannelMapping cm = new ChannelMapping(channel, mappingField, dataMap);
											readChannelMap.add(cm);
											if (r.permission() == Permission.WRITE) {
												writeChannelMap.add(cm);
											}
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
}
