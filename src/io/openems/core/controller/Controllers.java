package io.openems.core.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Controllers {
	private Map<String, Controller> controllers;
	
	public Controllers() {
		this.controllers = new HashMap<>();
	}
	
	public Controllers(Map<String, Controller> controllers) {
		this.controllers = controllers;
	}
	
	public Controller get(String controllerId) {
		return controllers.get(controllerId);
	}
	
	public Collection<Controller> getAll() {
		return controllers.values();
	}
	
	public void put(Controller controller) {
		controllers.put(controller.getName(), controller);
	}
}
