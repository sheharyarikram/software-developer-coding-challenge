package com.trade.rev.enums;

public enum ErrorCodesEnum {	
	USER_NOT_FOUND(1000),
	CAR_NOT_FOUND(1001),
	BIDDING_NOT_FOUND(1002),
		
	USER_HAS_BIDS(1003),
	CAR_HAS_BIDS(1004),
	BID_VALUE_INVALID(1005),
	
	USER_EXISTS(1006),
	CAR_EXISTS(1007),
	BIDDING_EXISTS(1008),
	
    FIELD_MISSING(1010),
    BAD_FORMAT(1011),
    SERVER_ERROR(1012);
 
    public final int code;
 
    private ErrorCodesEnum(int code) {
        this.code = code;
    }
    
}
