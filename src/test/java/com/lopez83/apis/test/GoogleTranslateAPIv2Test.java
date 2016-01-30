package com.lopez83.apis.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lopez83.apis.googletranslate.GoogleTranslateAPIv2;

public class GoogleTranslateAPIv2Test {

	private static final Logger logger = LoggerFactory.getLogger(GoogleTranslateAPIv2Test.class);

	public GoogleTranslateAPIv2Test() {

	}

	@Test
	public void testDetectLanguageAPI() {
		try {

			String textToDetect = "hola que tal";
			GoogleTranslateAPIv2 gTranslate = new GoogleTranslateAPIv2(Constants.GOOGLE_API_KEY);

			String language = gTranslate.detectLanguage(textToDetect);

			Assert.assertFalse(gTranslate.hasErrors());
			Assert.assertNotNull(language);
			Assert.assertEquals(language, "es");

			logger.debug("detected language is " + language);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
