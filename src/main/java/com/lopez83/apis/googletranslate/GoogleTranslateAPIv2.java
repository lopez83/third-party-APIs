package com.lopez83.apis.googletranslate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.Translate.Builder;
import com.google.api.services.translate.TranslateRequestInitializer;
import com.google.api.services.translate.model.DetectionsListResponse;
import com.lopez83.apis.BaseApi;

public class GoogleTranslateAPIv2 extends BaseApi{

	public GoogleTranslateAPIv2(String apiKey) {
		super(apiKey);
	}

	public String detectLanguage(String textToDetect) {
		String language = "";
		try {
			List<String> text = new ArrayList<String>();
			text.add(textToDetect);

			TranslateRequestInitializer trans = new TranslateRequestInitializer(
					apiKey);
			HttpTransport httpTransport = new NetHttpTransport();
			JacksonFactory jsonFactory = new JacksonFactory();

			Builder builder = new Translate.Builder(httpTransport, jsonFactory,
					null);
			builder.setGoogleClientRequestInitializer(trans);

			com.google.api.services.translate.Translate.Detections.List detections;

			detections = builder.setApplicationName("apiv2_translate").build().detections().list(text);

			DetectionsListResponse response = detections.execute();
			language = response.getDetections().get(0).get(0).getLanguage();

		} catch (IOException e) {
			errors = true;
		}
		return language;
	}
}