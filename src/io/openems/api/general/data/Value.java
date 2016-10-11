package io.openems.api.general.data;

public interface Value {

	/**
	 * Returns the value as a double.
	 * 
	 * @return the value as a double
	 * @throws TypeConversionException
	 *             if the stored value cannot be converted to a double
	 */
	public double asDouble();

	/**
	 * Returns the value as a float.
	 * 
	 * @return the value as a float
	 * @throws TypeConversionException
	 *             if the stored value cannot be converted to a float
	 */
	public float asFloat();

	/**
	 * Returns the value as a long.
	 * 
	 * @return the value as a long
	 * @throws TypeConversionException
	 *             if the stored value cannot be converted to a long
	 */
	public long asLong();

	/**
	 * Returns the value as an integer.
	 * 
	 * @return the value as an integer
	 * @throws TypeConversionException
	 *             if the stored value cannot be converted to an integer
	 */
	public int asInt();

	/**
	 * Returns the value as a short.
	 * 
	 * @return the value as a short
	 * @throws TypeConversionException
	 *             if the stored value cannot be converted to a short
	 */
	public short asShort();

	/**
	 * Returns the value as a byte.
	 * 
	 * @return the value as a byte
	 * @throws TypeConversionException
	 *             if the stored value cannot be converted to a byte
	 */
	public byte asByte();

	/**
	 * Returns the value as a boolean.
	 * 
	 * @return the value as a boolean
	 * @throws TypeConversionException
	 *             if the stored value cannot be converted to a boolean
	 */
	public boolean asBoolean();

	/**
	 * Returns the value as a byte array.
	 * 
	 * @return the value as a byte array
	 */
	public byte[] asByteArray();

	/**
	 * Returns the value as a string.
	 * 
	 * @return the value as a string
	 */
	public String asString();
}
