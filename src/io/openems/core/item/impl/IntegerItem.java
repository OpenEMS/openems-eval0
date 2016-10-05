package io.openems.core.item.impl;

import java.math.BigInteger;

import io.openems.core.value.IntegerValue;

public class IntegerItem extends NumberItem {

	public IntegerItem(String name, IntegerValue value) {
		super(name, value);
	}

	public IntegerItem(String name, BigInteger value) {
		super(name, new IntegerValue(value));
	}
	
	public IntegerItem(String name, int value) {
		super(name, new IntegerValue(value));
	}
}
