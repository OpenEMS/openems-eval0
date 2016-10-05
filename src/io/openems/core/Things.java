package io.openems.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Things {
	private Map<String, Thing> things;
	
	public Things() {
		this.things = new HashMap<>();
	}
	
	public Things(Map<String, Thing> things) {
		this.things = things;
	}
	
	public Thing get(String thingId) {
		return things.get(thingId);
	}
	
	public Collection<Thing> getAll() {
		return things.values();
	}
	
	public void put(Thing thing) {
		things.put(thing.getName(), thing);
	}
	
	public void putAll(Collection<? extends Thing> things) {
		for(Thing thing : things) {
			this.put(thing);
		}
	}
}
