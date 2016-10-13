package io.openems.core.datamanager;

public class ChannelId {
	private final String thingName;
	
	private final String channelName;
	

	public ChannelId(String thingName, String channelName) {
		super();
		this.thingName = thingName;
		this.channelName = channelName;
	}

	public String getThingName() {
		return thingName;
	}

	public String getChannelName() {
		return channelName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channelName == null) ? 0 : channelName.hashCode());
		result = prime * result + ((thingName == null) ? 0 : thingName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChannelId other = (ChannelId) obj;
		if (channelName == null) {
			if (other.channelName != null)
				return false;
		} else if (!channelName.equals(other.channelName))
			return false;
		if (thingName == null) {
			if (other.thingName != null)
				return false;
		} else if (!thingName.equals(other.thingName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return thingName + ":"+ channelName;
	}
	
}
