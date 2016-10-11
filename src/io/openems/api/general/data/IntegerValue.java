package io.openems.api.general.data;

import java.nio.ByteBuffer;

public class IntegerValue implements Value {

	private final int value;

	public IntegerValue(int value) {
		this.value = value;
	}

	@Override
	public double asDouble() {
		return value;
	}

	@Override
	public float asFloat() {
		return value;
	}

	@Override
	public long asLong() {
		return value;
	}

	@Override
	public int asInt() {
		return value;
	}

	@Override
	public short asShort() {
		return (short) value;
	}

	@Override
	public byte asByte() {
		return (byte) value;
	}

	@Override
	public boolean asBoolean() {
		return (value != 0);
	}

	@Override
	public byte[] asByteArray() {
		byte[] bytes = new byte[4];
		ByteBuffer.wrap(bytes).putInt(value);
		return bytes;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	@Override
	public String asString() {
		return toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegerValue other = (IntegerValue) obj;
		if (value != other.value)
			return false;
		return true;
	}
}
