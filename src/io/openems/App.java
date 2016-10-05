package io.openems;

import io.openems.impl.scheduler.Scheduler;

public class App {

	public static void main(String[] args) {
		Scheduler scheduler = new Scheduler();
		scheduler.activate();
	}

}
