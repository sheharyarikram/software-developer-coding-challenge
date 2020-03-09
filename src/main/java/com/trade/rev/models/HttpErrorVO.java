package com.trade.rev.models;

public class HttpErrorVO {
	
	public String requestId;
	public int status; //httpStatusCode
	public String title; //httpStatusTitle
	public String detail;
	public int internalCode; //codes from ErrorCodesEnum
	
	public HttpErrorVO(String requestId, int httpStatusCode, String httpStatusTitle, String detail, int internalCode) {
		this.requestId = requestId;
		this.status = httpStatusCode;
		this.title = httpStatusTitle;
		this.detail = detail;
		this.internalCode = internalCode;
	}

	public String getRequestId() {
		return requestId;
	}
	
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public int getInternalCode() {
		return internalCode;
	}
	
	public void setInternalCode(int internalCode) {
		this.internalCode = internalCode;
	}

}
