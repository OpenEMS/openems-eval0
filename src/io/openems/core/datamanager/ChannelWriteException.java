package io.openems.core.datamanager;

public class ChannelWriteException extends Exception {
	
	public ChannelWriteException(String message) {
		super(message);
	}
	
	public ChannelWriteException(String message, Throwable e){
		super(message,e);
	}
}
