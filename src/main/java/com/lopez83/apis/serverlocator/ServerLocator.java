package com.lopez83.apis.serverlocator;

import com.google.gson.Gson;
import com.lopez83.apis.BaseApi;
import com.lopez83.apis.serverlocator.model.IpInfoData;
import com.lopez83.apis.utils.HTTPUtils;

/**
 * Get Server Location based on IP address
 */
public class ServerLocator extends BaseApi{

	public String getServerLocation(String ip) {
		if (ip != null && !ip.isEmpty()) {
			IpInfoData ipInfoData = this.getIpInfoData(ip);
			if (ipInfoData != null) {
				return ipInfoData.getCountry().toLowerCase();
			}
		}
		return null;
	}

	private IpInfoData getIpInfoData(String ip) {

		String url = "http://ipinfo.io/" + ip + "/json";
		response = HTTPUtils.getJSONResponse(url);
		IpInfoData ipInfoData = null;

		if (response != null && !checkErrors(response)) {
			try {
				Gson gson = new Gson();
				ipInfoData = gson.fromJson(response.toString(),
						IpInfoData.class);

			} catch (Exception e) {				
				errors = true;
				e.printStackTrace();				
			}
		}
		return ipInfoData;
	}
}
