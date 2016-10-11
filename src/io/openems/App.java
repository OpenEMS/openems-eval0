package io.openems;


import java.util.LinkedList;
import java.util.List;

import io.openems.api.controller.Controller;
import io.openems.api.device.Device;
import io.openems.api.general.data.IntegerValue;
import io.openems.core.datamanager.Channel;
import io.openems.core.datamanager.ChannelId;
import io.openems.core.datamanager.ChannelNotFoundException;
import io.openems.core.datamanager.ChannelWriteException;
import io.openems.core.datamanager.DataManager;
import io.openems.core.scheduler.Scheduler;
import io.openems.impl.controller.AvoidTotalDischargeController;
import io.openems.impl.device.ess.Commercial;

public class App {

	public static void main(String[] args) {
//		Scheduler scheduler = new Scheduler();
//		scheduler.activate();
		
		DataManager dm = new DataManager();
		Commercial commercial = new Commercial("ess0");
		dm.addThing(commercial);
		Channel c;
		try {
			c = dm.getChannel(new ChannelId("ess0", "soc"));
			try {
				c.write(new IntegerValue(566));
			} catch (ChannelWriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(c.getLatestRecord().getValue().asLong());
		} catch (ChannelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Controller> controller = new LinkedList<>();
		controller.add(new AvoidTotalDischargeController("controller1"));
		Scheduler s = new Scheduler(dm,controller);
		s.activate();
	}

}
