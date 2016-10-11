package io.openems.api.general.dataaccess;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.openems.api.general.Thing;

@Retention(RetentionPolicy.RUNTIME)
public @interface IsRequired {
	Class<? extends Thing> type();
	String itemId();
	Permission permission();
}
