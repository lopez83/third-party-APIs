package com.lopez83.apis.googletranslate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.lopez83.apis.BaseApi;
import com.lopez83.apis.googletranslate.model.Detection;
import com.lopez83.apis.googletranslate.model.Error;
import com.lopez83.apis.googletranslate.model.GTData;
import com.lopez83.apis.utils.HTTPUtils;

public class GoogleTranslate extends BaseApi{

	private final String detectEndpoint = "https://www.googleapis.com/language/translate/v2/detect";
	private ArrayList<Detection> detections = new ArrayList<Detection>();
	private Error googleErrorInfo;
	
	public GoogleTranslate(String apiKey) {
		super(apiKey);
	}
	
	private URL getDetectURL(String textToDetect) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("key", apiKey);
		params.put("q", textToDetect);

		URI uri;

		uri = HTTPUtils.createURI(detectEndpoint, params);
		try {
			return new URL(uri.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Request to detect language of textToDetec
	 * @param textToDetect
	 * @return
	 */
	public ArrayList<Detection> detectLanguage(String textToDetect) {
		URL url = getDetectURL(textToDetect);
		ArrayList<Detection> detections = new ArrayList<Detection>();
		response = HTTPUtils.getJSONResponse(url);
		if (!checkErrors(response)) {
			detections = getDetectionsFromResponse(response);
		}
		return detections;
	}

	/**
	 * Get only the first detected language from the list of possible languages
	 * @param textToDetect
	 * @return
	 */
	public String getFirstDetectedLanguage(String textToDetect) {

		ArrayList<Detection> detections = this.detectLanguage(textToDetect);
		if (detections != null && detections.size() > 0) {
			return detections.get(0).getLanguage();
		}
		return null;
	}

	private ArrayList<Detection> getDetectionsFromResponse(JSONObject response) {

		Gson gson = new Gson();
		try {
			JSONObject data = (JSONObject) response.get("data");
			GTData dataObj = gson.fromJson(data.toString(), GTData.class);
			// retrieving only the first language detected
			this.detections.add(dataObj.getFirstDetection());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this.detections;
	}

	@Override
	protected boolean checkErrors(JSONObject rsp) {
		try {
			if (rsp.has("error") && rsp.get("error") != null) {
				Gson gson = new Gson();
				googleErrorInfo = gson.fromJson(rsp.get("error").toString(), Error.class);
				errors = true;
				return errors;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		errors = false;
		return errors;
	}

	public ArrayList<Detection> getDetections() {
		return detections;
	}

	public Error getGoogleErrorInfo() {
		return googleErrorInfo;
	}
}