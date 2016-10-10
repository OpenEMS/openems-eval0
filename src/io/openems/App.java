package io.openems;


import io.openems.api.device.Device;
import io.openems.core.datamanager.Channel;
import io.openems.core.datamanager.ChannelNotFoundException;
import io.openems.core.datamanager.DataManager;
import io.openems.core.scheduler.Scheduler;
import io.openems.impl.device.ess.Commercial;

public class App {

	public static void main(String[] args) {
//		Scheduler scheduler = new Scheduler();
//		scheduler.activate();
		
		DataManager dm = new DataManager();
		Device commercial = new Commercial("ess0");
		dm.addThing(commercial);
		Channel c;
		try {
			c = dm.getChannel("ess0.soc");
			System.out.println(c.getLatestRecord().getValue().asLong());
		} catch (ChannelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scheduler s = new Scheduler(dm);
		s.activate();
	}

}
