package io.openems.api.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.openems.core.datamanager.Channel;
import io.openems.core.datamanager.ChannelId;

public class WriteableChannels {
	private final Map<ChannelId,Channel> channels;
	
	public WriteableChannels(Map<ChannelId,Channel> channels){
		this.channels = channels;
	}
	
	private List<ChannelId> getAllIds(){
		return new LinkedList<>(channels.keySet());
	}
	
}
