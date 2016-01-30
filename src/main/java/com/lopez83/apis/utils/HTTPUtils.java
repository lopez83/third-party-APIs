package com.lopez83.apis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPUtils {
	private static final Logger logger = LoggerFactory.getLogger(HTTPUtils.class);
	
	public static final int SECONDS_TIMEOUT = 7;

	protected PoolingHttpClientConnectionManager connectionManager;
	protected CloseableHttpClient httpClient;

	
	public static String getBody(URL url) throws IOException {
		return getBody(url.toExternalForm());
	}

	public static boolean isValidStatusCode(int statusCode) {
		return statusCode == HttpStatus.SC_OK;
	}
	
	public static String getBody(String url) throws IOException {

		try {

			HttpResponse response = fetch(url,null);
			// Get http entity
			HttpEntity entity = response.getEntity();

			final String encoding = entity.getContentEncoding() != null ? entity.getContentEncoding().getValue() : "UTF-8";

			final String body = (readBody(entity, encoding));

			EntityUtils.consume(entity);

			return body;

		} catch (Exception e) {
			return "";
		}
	}
	
	private static HttpResponse fetch(String url, String userAgent) throws IOException {
		RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
		connRegistryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);
		
		try {
			SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(final X509Certificate[] chain, String authType) {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			connRegistryBuilder.register("https", sslsf);
		} catch (Exception e) {
			logger.warn("Exception thrown while trying to register https");
			logger.debug("Stacktrace", e);
		}

		Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(connRegistry);
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(100);

		RequestConfig requestConfig = RequestConfig.custom().setExpectContinueEnabled(false).setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).setRedirectsEnabled(false).setSocketTimeout(SECONDS_TIMEOUT*1000).setConnectTimeout(SECONDS_TIMEOUT*1000).build();

		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.setDefaultRequestConfig(requestConfig);
		clientBuilder.setConnectionManager(connectionManager);
		if(userAgent!=null)
			clientBuilder.setUserAgent(userAgent);

		CloseableHttpClient httpClient = clientBuilder.build();

		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(url);

			HttpResponse response = httpClient.execute(httpGet);
			return response;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		} finally {
			// httpGet.releaseConnection();
		}
	}
	
	private static String readBody(HttpEntity entity, String enc) throws IOException {
		final StringBuilder body = new StringBuilder();
		String buffer = "";
		if (entity != null) {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), enc));
			while ((buffer = reader.readLine()) != null) {
				body.append(buffer).append(System.getProperty("line.separator"));
			}
			reader.close();
		}
		return body.toString();
	}
	
	public static JSONObject getJSONResponse(String urlStr) {
		URL url;
		JSONObject json = null;

		try {
			url = new URL(urlStr);
			json = getJSONResponse(url);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		}
		return json;
	}

	public static JSONObject getJSONResponse(URL url) {
		String body;
		JSONObject json = null;
		try {
			body = HTTPUtils.getBody(url.toString());
			json = new JSONObject(body);
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		return json;
	}

	public static URL createURL(String url) {
		try {

			if (!url.matches("http*://.*")) {
				url = "http://" + url;
			}

			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Invalid URL " + url, e);
		}
	}

	public static URI createURI(String url, Map<String, String> params) {
		URIBuilder uriB;
		URI uri = null;
		try {
			uriB = new URIBuilder(url);

			Iterator<Entry<String, String>> it = params.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();

				uriB.addParameter(pairs.getKey(), URLEncoder.encode(pairs.getValue(), "UTF-8")).build();
			}
			uri = uriB.build();
			return uri;
		} catch (UnsupportedEncodingException | URISyntaxException e) {
			logger.error(e.getMessage());
		}
		return uri;
	}

	public static String addParameters(String url, Map<String, String> params) {
		try {
			return createURI(url, params).toURL().toExternalForm();
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public static String addParametersStringBuilder(String url, Map<String, String> params) {
		StringBuilder builder = new StringBuilder(url);
		String delimiter = "?";
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.append(delimiter);
			builder.append(entry.getKey() + "=" + entry.getValue());
			delimiter = "&";
		}
		return builder.toString();
	}

	public static String addParameters(URL url, Map<String, String> params) {
		return addParameters(url.toExternalForm(), params);
	}

	public static String addParameters(URI uri, Map<String, String> params) {
		return addParameters(uri.toString(), params);
	}
	
	public static long getLastModified (URL url){
		long date = 0;
		try{
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setConnectTimeout(SECONDS_TIMEOUT * 1000);
			httpCon.setReadTimeout(SECONDS_TIMEOUT * 1000);
			date = httpCon.getLastModified();
	    }catch (Exception e){
	    	logger.error(e.getMessage());
	    }
		return date;
	}

}
