package io.openems.core.scheduler;

import java.lang.reflect.Field;

import io.openems.api.controller.DataMap;
import io.openems.api.controller.Range;
import io.openems.api.general.data.IntegerValue;
import io.openems.api.general.data.Value;
import io.openems.core.datamanager.Channel;

public class ChannelMapping {
	private final Channel channel;
	private final Field field;
	private final DataMap dataMap;
	private Range setRange;
	
	public ChannelMapping(Channel channel, Field field, DataMap dataMap){
		this.channel = channel;
		this.field = field;
		this.dataMap = dataMap;
	}
	
	public void updateField() throws IllegalArgumentException, IllegalAccessException{
		Range r = new Range(channel.getLatestRecord(),new IntegerValue(Integer.MIN_VALUE), new IntegerValue(Integer.MAX_VALUE));
		field.set(dataMap, r);
		setRange = r;
	}
	
	public Range getWriteRange() throws IllegalArgumentException, IllegalAccessException, RangeModificationException{
		Range r =(Range) field.get(dataMap); 
		if(r == setRange){
			return r; 
		}else{
			throw new RangeModificationException("Range at field "+ field.getName() + " has been changed by the controller!");
		}
	}

	public Channel getChannel() {
		return channel;
	}

	public void updateField(Range r) throws IllegalArgumentException, IllegalAccessException {
		field.set(dataMap, r);
		setRange = r;
	}
	
}
