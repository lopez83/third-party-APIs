package com.lopez83.apis.googletranslate.model;

public class DetailedError {

	private String message;
	private String reason;
	private String domain;
	private String extendedHelp;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getExtendedHelp() {
		return extendedHelp;
	}

	public void setExtendedHelp(String extendedHelp) {
		this.extendedHelp = extendedHelp;
	}

	@Override
	public String toString() {
		return "DetailedError [message=" + message + ", reason=" + reason
				+ ", domain=" + domain + ", extendedHelp=" + extendedHelp + "]";
	}

}
