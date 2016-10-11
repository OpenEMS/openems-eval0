package io.openems.api.general.data;

import java.nio.ByteBuffer;

public class ShortValue implements Value {

	private final short value;

	public ShortValue(short value) {
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
		return value;
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
		byte[] bytes = new byte[2];
		ByteBuffer.wrap(bytes).putShort(value);
		return bytes;
	}

	@Override
	public String toString() {
		return Short.toString(value);
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
		ShortValue other = (ShortValue) obj;
		if (value != other.value)
			return false;
		return true;
	}
}
