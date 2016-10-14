package io.openems.api.controller;

import io.openems.api.general.data.Record;
import io.openems.api.general.data.Value;

public class Range {
	private final Record readValue;
	private Value minWriteValue;
	private Value maxWriteValue;
	private Value writeValue;
	
	public Range(Record readValue, Value minWriteValue, Value maxWriteValue){
		this.readValue = readValue;
		this.minWriteValue = minWriteValue;
		this.maxWriteValue = maxWriteValue;
	}
	
	public void setRange(Value minWriteValue, Value maxWriteValue){
		if(minWriteValue.asFloat() >= this.minWriteValue.asFloat() && maxWriteValue.asFloat() <= this.maxWriteValue.asFloat() && this.writeValue == null){
			this.minWriteValue = minWriteValue;
			this.maxWriteValue = maxWriteValue;
		}
	}
	
	public void setWriteValue(Value writeValue){
		if(writeValue.asFloat() >= minWriteValue.asFloat() && writeValue.asFloat() <= maxWriteValue.asFloat()){
			this.writeValue = writeValue;
		}
	}
	
	public Record getReadValue(){
		return readValue;
	}
	
	public Value getMinWriteValue(){
		return minWriteValue;
	}
	
	public Value getMaxWriteValue(){
		return maxWriteValue;
	}
	
	public Value getWriteValue(){
		return writeValue;
	}
}
