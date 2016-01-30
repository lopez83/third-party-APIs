package com.lopez83.apis;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseApi {

	protected boolean errors = false;
	protected JSONObject response = null;
	protected String apiKey = null;
	
	public BaseApi(){		
	}
	
	public BaseApi(String apiKey) {
		super();
		this.apiKey = apiKey;
	}

	protected boolean checkErrors(JSONObject rsp) {
		try {
			if (rsp.has("error") && rsp.get("error") != null) {
				errors = true;
				return errors;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		errors = false;
		return errors;
	}
	
	public boolean hasErrors() {
		return errors;
	}
	
}
