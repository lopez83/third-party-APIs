package com.lopez83.apis.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lopez83.apis.serverlocator.ServerLocator;

public class ServerLocatorTest {

	private static final Logger logger = LoggerFactory
			.getLogger(ServerLocatorTest.class);

	public ServerLocatorTest() {
	}

	@Test
	public void testGetCountryServer() {
		try {

			String ip = "193.110.128.109";
			ServerLocator locator = new ServerLocator();
			String country = locator.getServerLocation(ip);

			Assert.assertNotNull(country);
			Assert.assertEquals(country, "es");

			logger.debug("Server Location for IP " + ip + " is " + country);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
