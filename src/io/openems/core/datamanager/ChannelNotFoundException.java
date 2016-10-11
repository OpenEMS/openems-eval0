package io.openems.core.datamanager;

public class ChannelNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9015291887911357471L;
	
	private final ChannelId id;
	
	public ChannelNotFoundException(ChannelId id){
		this.id = id;
	}
	
	public ChannelId getId(){
		return this.id;
	}

}
