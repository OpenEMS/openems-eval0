package io.openems.api.general.data.element;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.openems.api.general.data.Value;

public class Element {
	
	private Value value;
	
	private Long timestamp = 0l;
	
	private final Set<ElementChangedListener> changedListeners;
	
	public Element(){
		this.changedListeners = ConcurrentHashMap.newKeySet();
	}
	
	public void addListener(ElementChangedListener listener){
		changedListeners.add(listener);
	}
	
	public void removeListener(ElementChangedListener listener){
		changedListeners.remove(listener);
	}
	
	private void notifyChangedListeners(Value oldValue, Value newValue){
		for(ElementChangedListener listener: changedListeners){
			listener.notify(this,oldValue, newValue);
		}
	}
	
	public void setValue(Value value){
		synchronized (value) {
			if(this.value == null || !this.value.equals(value)){
				Value oldValue = this.value;
				this.value = value;
				timestamp = System.currentTimeMillis();
				notifyChangedListeners(oldValue, this.value);
			}
		}
	}
	
	public Value getValue(){
		synchronized (value) {
			return this.value;
		}
	}
	
	public Long getTimestamp(){
		return this.timestamp;
	}
	
}
