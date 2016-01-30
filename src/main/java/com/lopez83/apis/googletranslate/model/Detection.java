package com.lopez83.apis.googletranslate.model;

public class Detection {

	private String language;
	private boolean idReliable;
	private double confidence;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isIdReliable() {
		return idReliable;
	}

	public void setIdReliable(boolean idReliable) {
		this.idReliable = idReliable;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	@Override
	public String toString() {
		return "Detection [language=" + language + ", idReliable=" + idReliable
				+ ", confidence=" + confidence + "]";
	}
}
