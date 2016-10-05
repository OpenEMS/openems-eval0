package io.openems.core.value;

import java.math.BigInteger;

public class IntegerValue extends NumberValue {

	public IntegerValue(BigInteger value) {
		super(value);
	}

	public IntegerValue(int value) {
		super(BigInteger.valueOf(value));
	}
}
