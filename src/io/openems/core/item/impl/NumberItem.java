package io.openems.core.item.impl;

import io.openems.core.item.Item;
import io.openems.core.value.NumberValue;

public abstract class NumberItem extends Item {

	public NumberItem(String name, NumberValue value) {
		super(name, value);
	}
}
