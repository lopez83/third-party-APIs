package com.lopez83.apis.googletranslate.model;

import java.util.ArrayList;

public class Error {

	private String message;
	private ArrayList<DetailedError> errors;
	private String code;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<DetailedError> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<DetailedError> errors) {
		this.errors = errors;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Error [message=" + message + ", errors=" + errors + ", code="
				+ code + "]";
	}

}
