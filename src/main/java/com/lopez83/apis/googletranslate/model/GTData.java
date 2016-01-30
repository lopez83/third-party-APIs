package com.lopez83.apis.googletranslate.model;

import java.util.ArrayList;

public class GTData {
	private ArrayList<ArrayList<Detection>> detections;

	public ArrayList<ArrayList<Detection>> getDetections() {
		return detections;
	}

	public void setDetections(ArrayList<ArrayList<Detection>> detections) {
		this.detections = detections;
	}
	
	public Detection getFirstDetection(){
		Detection detection = null;
		if (detections!=null && detections.size()>0 && detections.get(0)!=null){
			if (detections.get(0).size()>0 && detections.get(0).get(0)!=null){
				detection = detections.get(0).get(0);
			}
		}
		return detection;
	}

	@Override
	public String toString() {
		return "GTData [detections=" + detections + "]";
	}
}