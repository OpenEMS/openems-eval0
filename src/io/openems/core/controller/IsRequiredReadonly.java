package io.openems.core.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.openems.core.Thing;

@Retention(RetentionPolicy.RUNTIME)
public @interface IsRequiredReadonly {
	Class<? extends Thing> clazz();
	String itemId();
}
