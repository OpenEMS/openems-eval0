package io.openems.core.item;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReadonlyItems {
	private final Map<String, ReadonlyItem> items;
	
	public ReadonlyItems(Set<ReadonlyItem> items) {
		Map<String, ReadonlyItem> newItems = new HashMap<>();
		for(ReadonlyItem item: items) {
			newItems.put(item.getItemId(), item);
		}
		this.items = Collections.unmodifiableMap(newItems);
	}
	
	public ReadonlyItem get(String itemId) {
		return items.get(itemId);
	}
}
