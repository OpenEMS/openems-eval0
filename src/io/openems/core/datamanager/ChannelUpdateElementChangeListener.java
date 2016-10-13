package io.openems.core.datamanager;

import io.openems.api.general.data.Record;
import io.openems.api.general.data.Value;
import io.openems.api.general.data.element.Element;
import io.openems.api.general.data.element.ElementChangedListener;

public class ChannelUpdateElementChangeListener implements ElementChangedListener{
	private final Channel channel;
	
	public ChannelUpdateElementChangeListener(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void notify(Element sender,Value oldValue, Value newValue) {
		channel.addRecord(new Record(newValue,sender.getTimestamp()));
	}
}
