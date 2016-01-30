package com.lopez83.apis.test;


import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lopez83.apis.googletranslate.GoogleTranslate;
import com.lopez83.apis.googletranslate.model.Detection;

public class GoogleTranslateTest {

	private static final Logger logger = LoggerFactory.getLogger(GoogleTranslateTest.class);

	public GoogleTranslateTest() {
	}

	@Test
	public void testDetectLanguage() {
		try {

			String textToDetect = "hola prueba de texto a detectar";
			GoogleTranslate gTranslate = new GoogleTranslate(
					Constants.GOOGLE_API_KEY);

			ArrayList<Detection> detections = gTranslate
					.detectLanguage(textToDetect);
			
			Assert.assertFalse(gTranslate.hasErrors());
			Assert.assertNotNull(detections);
			if (detections != null && detections.size() > 0) {
				Assert.assertNotNull(detections.get(0));
				Assert.assertNotNull(detections.get(0).getLanguage());
				Assert.assertEquals(detections.get(0).getLanguage(), "es");
			}

			for (Detection detection : detections) {
				logger.debug("detected language is "
						+ detection.getLanguage());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
