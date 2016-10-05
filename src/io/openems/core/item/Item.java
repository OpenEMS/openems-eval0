package io.openems.core.item;

import io.openems.core.value.Value;

public abstract class Item extends ReadonlyItem {
	
	public Item(String name, Value value) {
		super(name, value);
	}

	public void setValue(Value value) {
		this.value = value;
	}
}
