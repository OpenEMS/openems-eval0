package io.openems.core.datamanager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.openems.api.general.Thing;
import io.openems.api.general.data.Value;

public class Channel {

	private final String name;

	private final ChannelId id;

	private Record latestRecord;

	protected final Thing thing;

	private final Method writeMethod;

	public Channel(String id, Thing t, Method writeMethod) {
		this.name = id;
		thing = t;
		this.id = new ChannelId(t.getName(), id);
		this.writeMethod = writeMethod;
	}

	public String getName() {
		return name;
	}

	public ChannelId getId() {
		return this.id;
	}

	public Record getLatestRecord() {
		return latestRecord;
	}

	public void addRecord(Record r) {
		if (r != null && (latestRecord == null || r.getTimestamp() > latestRecord.getTimestamp())) {
			latestRecord = r;
		}
	}

	public Thing getThing() {
		return thing;
	}

	public void write(Value v) throws ChannelWriteException {
		if (writeMethod != null) {
			try {
				writeMethod.invoke(thing, v);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new ChannelWriteException("failed to write value to thing.",e);
			}
		} else {
			throw new ChannelWriteException("Can't find write method för attribute " + id.getChannelName()
					+ " in class " + thing.getClass().getName());
		}
	}
}
