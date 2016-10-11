package io.openems.api.general.data.element;

import io.openems.api.general.data.Value;

public interface ElementChangedListener {
	
	public void notify(Element sender, Value oldValue, Value newValue);
}
