package io.openems.core.datamanager;

import java.lang.reflect.Field;

import io.openems.api.controller.DataMap;
import io.openems.api.general.data.Value;

public class ChannelMapping {
	private final Channel channel;
	private final Field field;
	private final DataMap dataMap;
	
	public ChannelMapping(Channel channel, Field field, DataMap dataMap){
		this.channel = channel;
		this.field = field;
		this.dataMap = dataMap;
	}
	
	public void updateField() throws IllegalArgumentException, IllegalAccessException{
		field.set(dataMap, channel.getLatestRecord());
	}
	
	public Value getWrittenValue() throws IllegalArgumentException, IllegalAccessException{
		return (Value) field.get(dataMap);
	}

	public Channel getChannel() {
		return channel;
	}
	
}
