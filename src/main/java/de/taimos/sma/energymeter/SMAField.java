package de.taimos.sma.energymeter;

/**
 * This enum lists all possible fields provided by the SMA Energymeter<br>
 * <br>
 *
 * Copyright 2014 Taimos GmbH<br>
 * <br>
 *
 * @author Thorsten Hoeger
 *
 */
public enum SMAField {

	/** Total Ingress Power */
	TOTAL_P_IN("014", 10, 1, "W"),
	/** Total Ingress Power Sum */
	TOTAL_P_IN_SUM("018", 3600 * 1000, 3, "kWh"),
	/** Total Egress Power */
	TOTAL_P_OUT("024", 10, 1, "W"),
	/** Total Egress Power Sum */
	TOTAL_P_OUT_SUM("028", 3600 * 1000, 3, "kWh"),
	/**  */
	TOTAL_Q_OUT("034", 10, 1, "var"),
	/**  */
	TOTAL_Q_OUT_SUM("038", 3600 * 1000, 3, "kvarh"),
	/**  */
	TOTAL_Q_IN("044", 10, 1, "var"),
	/**  */
	TOTAL_Q_IN_SUM("048", 3600 * 1000, 3, "kvarh"),
	/**  */
	TOTAL_S_IN("094", 10, 1, "VA"),
	/**  */
	TOTAL_S_IN_SUM("098", 3600 * 1000, 3, "kVAh"),
	/**  */
	TOTAL_S_OUT("0A4", 10, 1, "VA"),
	/**  */
	TOTAL_S_OUT_SUM("0A8", 3600 * 1000, 3, "kVAh"),
	/**  */
	TOTAL_COS_PHI("0D4", 1000, 3, ""),

	/** Phase 1 Ingress Power */
	L1_P_IN("154", 10, 1, "W"),
	/** Phase 1 Ingress Power Sum */
	L1_P_IN_SUM("158", 3600 * 1000, 3, "kWh"),
	/** Phase 1 Egress Power */
	L1_P_OUT("164", 10, 1, "W"),
	/** Phase 1 Egress Power Sum */
	L1_P_OUT_SUM("168", 3600 * 1000, 3, "kWh"),
	/**  */
	L1_Q_OUT("174", 10, 1, "var"),
	/**  */
	L1_Q_OUT_SUM("178", 3600 * 1000, 3, "kvarh"),
	/**  */
	L1_Q_IN("184", 10, 1, "var"),
	/**  */
	L1_Q_IN_SUM("188", 3600 * 1000, 3, "kvarh"),
	/**  */
	L1_S_IN("1D4", 10, 1, "VA"),
	/**  */
	L1_S_IN_SUM("1D8", 3600 * 1000, 3, "kVAh"),
	/**  */
	L1_S_OUT("1E4", 10, 1, "VA"),
	/**  */
	L1_S_OUT_SUM("1E8", 3600 * 1000, 3, "kVAh"),
	/**  */
	L1_THD("1F4", 1000, 3, "%"),
	/**  */
	L1_VOLTAGE("204", 1000, 3, "V"),
	/**  */
	L1_COS_PHI("214", 1000, 3, ""),

	/**  */
	L2_P_IN("294", 10, 1, "W"),
	/**  */
	L2_P_IN_SUM("298", 3600 * 1000, 3, "kWh"),
	/**  */
	L2_P_OUT("2A4", 10, 1, "W"),
	/**  */
	L2_P_OUT_SUM("2A8", 3600 * 1000, 3, "kWh"),
	/**  */
	L2_Q_OUT("2B4", 10, 1, "var"),
	/**  */
	L2_Q_OUT_SUM("2B8", 3600 * 1000, 3, "kvarh"),
	/**  */
	L2_Q_IN("2C4", 10, 1, "var"),
	/**  */
	L2_Q_IN_SUM("2C8", 3600 * 1000, 3, "kvarh"),
	/**  */
	L2_S_IN("314", 10, 1, "VA"),
	/**  */
	L2_S_IN_SUM("318", 3600 * 1000, 3, "kVAh"),
	/**  */
	L2_S_OUT("324", 10, 1, "VA"),
	/**  */
	L2_S_OUT_SUM("328", 3600 * 1000, 3, "kVAh"),
	/**  */
	L2_THD("334", 1000, 3, "%"),
	/**  */
	L2_VOLTAGE("344", 1000, 3, "V"),
	/**  */
	L2_COS_PHI("354", 1000, 3, ""),

	/**  */
	L3_P_IN("3D4", 10, 1, "W"),
	/**  */
	L3_P_IN_SUM("3D8", 3600 * 1000, 3, "kWh"),
	/**  */
	L3_P_OUT("3E4", 10, 1, "W"),
	/**  */
	L3_P_OUT_SUM("3E8", 3600 * 1000, 3, "kWh"),
	/**  */
	L3_Q_OUT("3F4", 10, 1, "var"),
	/**  */
	L3_Q_OUT_SUM("3F8", 3600 * 1000, 3, "kvarh"),
	/**  */
	L3_Q_IN("404", 10, 1, "var"),
	/**  */
	L3_Q_IN_SUM("408", 3600 * 1000, 3, "kvarh"),
	/**  */
	L3_S_IN("454", 10, 1, "VA"),
	/**  */
	L3_S_IN_SUM("458", 3600 * 1000, 3, "kVAh"),
	/**  */
	L3_S_OUT("464", 10, 1, "VA"),
	/**  */
	L3_S_OUT_SUM("468", 3600 * 1000, 3, "kVAh"),
	/**  */
	L3_THD("474", 1000, 3, "%"),
	/**  */
	L3_VOLTAGE("484", 1000, 3, "V"),
	/**  */
	L3_COS_PHI("494", 1000, 3, "");

	private String address;
	private int divisor;
	private String unit;
	private int scale;


	private SMAField(String address, int divisor, int scale, String unit) {
		this.address = address;
		this.divisor = divisor;
		this.scale = scale;
		this.unit = unit;
	}

	public String getAddress() {
		return this.address;
	}

	public int getDivisor() {
		return this.divisor;
	}

	public String getUnit() {
		return this.unit;
	}

	public int getScale() {
		return this.scale;
	}

}
