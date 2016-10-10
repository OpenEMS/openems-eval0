package io.openems.impl.controller;

import io.openems.api.controller.Controller;

public class AvoidTotalDischargeController extends Controller {
	
	public AvoidTotalDischargeController(String name) {
		super(name);
	}
	
	@Override
	public int priority() {
		return 100;
	}

	@Override
	public void run() {
		System.out.println("Run: " + toString());
	}
}
