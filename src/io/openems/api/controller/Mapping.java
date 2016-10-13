package io.openems.api.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.openems.api.general.Thing;

@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
	Class<? extends Thing> type();
}
