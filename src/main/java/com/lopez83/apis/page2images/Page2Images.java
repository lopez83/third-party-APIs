package com.lopez83.apis.page2images;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.lopez83.apis.BaseApi;
import com.lopez83.apis.page2images.model.BrowserScreenShot;
import com.lopez83.apis.utils.HTTPUtils;

public class Page2Images extends BaseApi {

	private static final Logger logger = LoggerFactory
			.getLogger(Page2Images.class);

	private final String endpoint = "http://api.page2images.com/restfullink";

	private String url;
	private ScheduledExecutorService scheduledExecutorService = Executors
			.newScheduledThreadPool(2);

	public static final String DEVICE_IPAD = "4";
	public static final String DEVICE_DESKTOP = "6";
	public static final String DEVICE_ANDROID = "2";
	public static final String DEVICE_IPHONE5 = "1";

	private boolean retry = true;

	public Page2Images(String apiKey, String url) {
		super(apiKey);
		this.url = url;
	}

	public BrowserScreenShot getImageURL(String device) {
		BrowserScreenShot screenShotObj = null;
		screenShotObj = this.waitForImageURL(device);
		return screenShotObj;
	}
	
	private URL buildURL(String device) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("p2i_key", apiKey);
		params.put("p2i_url", url);
		params.put("p2i_device", device);

		URI uri = HTTPUtils.createURI(endpoint, params);
		try {
			return new URL(uri.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Make request to retrieve URL image, wait if image is being processed
	 * @param device
	 * @return
	 */
	private BrowserScreenShot waitForImageURL(String device) {
		URL url = buildURL(device);
		BrowserScreenShot screenShotObj = null;
		response = HTTPUtils.getJSONResponse(url);
		if (!checkErrors(response)) {
			try {
				String status = (String) response.getString("status");
				Gson gson = new Gson();
				if (status.equalsIgnoreCase(BrowserScreenShot.STATUS_FINISHED)) {
					//image has been generated
					screenShotObj = gson.fromJson(response.toString(),
							BrowserScreenShot.class);
					logger.debug("got the image, not waiting for it");
				} else if (status
						.equalsIgnoreCase(BrowserScreenShot.STATUS_PROCESSING)
						&& retry) {
					//image is being processed
					retry = false;
					screenShotObj = gson.fromJson(response.toString(),
							BrowserScreenShot.class);
					logger.debug("calling to executing scheduled task after "
							+ screenShotObj.getEstimated_need_time()
							+ " seconds");
					return this.scheduleTask(
							screenShotObj.getEstimated_need_time(), device);
				} else {
					logger.debug("couldn't retrieve image");
				}

			} catch (Exception e) {
				errors = true;
				e.printStackTrace();
				logger.error("error retrieving image");
			}
		}
		scheduledExecutorService.shutdown();
		return screenShotObj;
	}
	
	/**
	 * Schedule task to request for the image url after 'timer' seconds  
	 * @param timer
	 * @param device
	 * @return
	 */
	private synchronized BrowserScreenShot scheduleTask(int timer,
			final String device) {

		ScheduledFuture<BrowserScreenShot> scheduledFuture = scheduledExecutorService
				.schedule(new Callable<BrowserScreenShot>() {
					public BrowserScreenShot call() throws Exception {
						logger.debug("Executed!");
						return waitForImageURL(device);
					}
				}, timer, TimeUnit.SECONDS);

		try {
			logger.debug("result in scheduleTask = " + scheduledFuture.get());
			return (BrowserScreenShot) scheduledFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}