package io.openems.core.item;

import java.util.Map;

public class Items {
	private Map<String, Item> items;
	
	public Items(Map<String, Item> items) {
		this.items = items;
	}
	
	public Item get(String thingId) {
		return items.get(thingId);
	}
	
	public void put(String thingId, Item item) {
		items.put(thingId, item);
	}
}
