package io.openems.core.value;

public abstract class NumberValue extends Value {
	private Number value;
	
	public NumberValue(Number value) {
		this.value = value;
	}
	
	public Number getValue() {
		return value;
	}
}
