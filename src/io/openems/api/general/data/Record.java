package io.openems.api.general.data;

public class Record {
	
	private final Long timestamp;
	private final Flag flag;
	private final Value value;

	public Record(Value value, Long timestamp, Flag flag) {
		this.value = value;
		this.timestamp = timestamp;
		if (value == null && flag.equals(Flag.VALID)) {
			throw new IllegalStateException("If a record's flag is set valid the value may not be NULL.");
		}
		this.flag = flag;
	}

	/**
	 * Creates a valid record.
	 * 
	 * @param value
	 *            the value of the record
	 * @param timestamp
	 *            the timestamp of the record
	 */
	public Record(Value value, Long timestamp) {
		this(value, timestamp, Flag.VALID);
	}

	/**
	 * Creates an invalid record with the given flag. The flag may not indicate valid.
	 * 
	 * @param flag
	 *            the flag of the invalid record.
	 */
	public Record(Flag flag) {
		this(null, null, flag);
		if (flag == Flag.VALID) {
			throw new IllegalArgumentException("flag must indicate an error");
		}
	}

	public Value getValue() {
		return value;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public Flag getFlag() {
		return flag;
	}

	@Override
	public String toString() {
		return "value: " + value + "; timestamp: " + timestamp + "; flag: " + flag;
	}

}
