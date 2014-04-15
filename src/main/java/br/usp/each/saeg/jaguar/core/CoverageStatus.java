package br.usp.each.saeg.jaguar.core;

public enum CoverageStatus {

	EMPTY(0),
	NOT_COVERED(1),
	FULLY_COVERED(2),
	PARTLY_COVERED(3),
	UNKNOWN(4);

	private Integer value;

	private CoverageStatus(int value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static CoverageStatus as(Integer value) {
		switch (value) {
		case 0:
			return EMPTY;
		case 1:
			return NOT_COVERED;
		case 2:
			return FULLY_COVERED;
		case 3:
			return PARTLY_COVERED;
		default:
			return UNKNOWN;
		}
	}
	
}
