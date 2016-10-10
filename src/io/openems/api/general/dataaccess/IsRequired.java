package io.openems.api.general.dataaccess;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.openems.api.general.Thing;
import io.openems.api.general.data.Permission;

@Retention(RetentionPolicy.RUNTIME)
public @interface IsRequired {
	Class<? extends Thing> clazz();
	String itemId();
	Permission permission();
}
