package com.lopez83.apis.test;


import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lopez83.apis.page2images.Page2Images;
import com.lopez83.apis.page2images.model.BrowserScreenShot;

public class Page2ImagesTest {

	private static final Logger logger = LoggerFactory.getLogger(Page2ImagesTest.class);

	private String url = Constants.TEST_URL;
	public Page2ImagesTest() {
		
	}

	@Test
	public void testGetBrowserScreenShotAndroid() {
		try {			
			Page2Images page2Images = new Page2Images(Constants.PAGE2IMAGES_API_KEY, url);

			BrowserScreenShot screenShot = page2Images.getImageURL(Page2Images.DEVICE_ANDROID);
			Assert.assertNotNull(screenShot);
			Assert.assertNotNull(screenShot.getImage_url());
			logger.debug("DEVICE_ANDROID Screenshot result is " + screenShot.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetBrowserScreenShotDesktop() {
	
		Page2Images page2Images = new Page2Images(Constants.PAGE2IMAGES_API_KEY, url);

		BrowserScreenShot screenShot = page2Images.getImageURL(Page2Images.DEVICE_IPAD);
		Assert.assertNotNull(screenShot);
		Assert.assertNotNull(screenShot.getImage_url());
		logger.debug("DEVICE_DESKTOP Screenshot result is " + screenShot.toString());

	}

	@Test
	public void testGetBrowserScreenShotIphone5() {
		
		Page2Images page2Images = new Page2Images(Constants.PAGE2IMAGES_API_KEY, url);

		BrowserScreenShot screenShot = page2Images.getImageURL(Page2Images.DEVICE_IPHONE5);
		Assert.assertNotNull(screenShot);
		Assert.assertNotNull(screenShot.getImage_url());
		logger.debug("DEVICE_IPHONE5 Screenshot result is " + screenShot.toString());
	}
}