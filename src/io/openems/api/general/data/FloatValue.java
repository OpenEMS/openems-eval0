package io.openems.api.general.data;

import java.nio.ByteBuffer;

public class FloatValue implements Value {

	private final float value;

	public FloatValue(float value) {
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
		return (long) value;
	}

	@Override
	public int asInt() {
		return (int) value;
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
		return (value != 0.0);
	}

	@Override
	public byte[] asByteArray() {
		byte[] bytes = new byte[4];
		ByteBuffer.wrap(bytes).putFloat(value);
		return bytes;
	}

	@Override
	public String toString() {
		return Float.toString(value);
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
		FloatValue other = (FloatValue) obj;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}
	
	
}
