package io.openems.core.item;

import io.openems.core.value.Value;

public abstract class ReadonlyItem {
	protected String itemId;
	protected Value value;
	
	public ReadonlyItem(String itemId, Value value) {
		this.itemId = itemId;
		this.value = value;
	}
	
	public final Value getValue() {
		return value;
	}
	
	public final String getItemId() {
		return itemId;
	}
}
