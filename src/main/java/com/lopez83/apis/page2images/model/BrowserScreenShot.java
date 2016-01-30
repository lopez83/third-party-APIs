package com.lopez83.apis.page2images.model;

public class BrowserScreenShot {

	public static final String STATUS_PROCESSING = "processing";
	public static final String STATUS_FINISHED = "finished";

	private String status;// processing and finished
	private String image_url;
	private int estimated_need_time;
	private String ori_url;
	private String mobileok;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public int getEstimated_need_time() {
		return estimated_need_time;
	}

	public void setEstimated_need_time(int estimated_need_time) {
		this.estimated_need_time = estimated_need_time;
	}

	public String getOri_url() {
		return ori_url;
	}

	public void setOri_url(String ori_url) {
		this.ori_url = ori_url;
	}

	public String getMobileok() {
		return mobileok;
	}

	public void setMobileok(String mobileok) {
		this.mobileok = mobileok;
	}

	@Override
	public String toString() {
		return "BrowserScreenShot [status=" + status + ", image_url="
				+ image_url + ", estimated_need_time=" + estimated_need_time
				+ ", ori_url=" + ori_url + ", mobileok=" + mobileok + "]";
	}

}
