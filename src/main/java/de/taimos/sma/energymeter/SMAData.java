package de.taimos.sma.energymeter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains a single data snapshot<br>
 * <br>
 * 
 * Copyright 2014 Taimos GmbH<br>
 * <br>
 * 
 * @author Thorsten Hoeger
 * 
 */
public final class SMAData {
	
	private final String serial;
	
	private final Map<String, BigInteger> map = new HashMap<>();
	
	
	SMAData(String serial) {
		this.serial = serial;
	}
	
	/**
	 * Add field
	 * 
	 * @param key the field name
	 * @param value the value
	 */
	void add(String key, BigInteger value) {
		this.map.put(key, value);
	}
	
	/**
	 * @param field the field to read
	 * @return the value of the given field as {@link BigDecimal}
	 */
	public BigDecimal getValue(SMAField field) {
		if (this.map.containsKey(field.getAddress())) {
			return new BigDecimal(this.map.get(field.getAddress())).divide(new BigDecimal(field.getDivisor()), field.getScale(), RoundingMode.HALF_UP);
		}
		return null;
	}
	
	/**
	 * @param field the field to read
	 * @return the value of the given field including the unit as {@link String}
	 */
	public String getValueString(SMAField field) {
		BigDecimal value = this.getValue(field);
		if (value != null) {
			return value.toPlainString() + " " + field.getUnit();
		}
		return null;
	}
	
	/**
	 * @return the serial number of the sending energy meter
	 */
	public String getSerial() {
		return this.serial;
	}
	
	@Override
	public String toString() {
		return this.map.toString();
	}
	
}
