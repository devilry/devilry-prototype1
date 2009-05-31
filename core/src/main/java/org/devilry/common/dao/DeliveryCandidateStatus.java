package org.devilry.common.dao;

public enum DeliveryCandidateStatus {
		
	NOT_DELIVERED, NOT_APPROVED, APPROVED;
	
	static final int valueCount = 3;
	
	public static boolean isValidValue(int statusValue) {
		return statusValue >= 0 && statusValue < valueCount;
	}
	
	public static int getValue(DeliveryCandidateStatus status) {

		switch(status) {
		case NOT_DELIVERED: return 0;
		case NOT_APPROVED: return 1;
		case APPROVED: return 2;
		}

		return -1;
	}
	
}
