package vn.vdc.nab.alice.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Provide methods for HTTP request
 */
@Component
public class Request {

	private static final String CONTENT_TYPE = "Content-type";
	private static final String CHARSET = "UTF-8";

	/**
	 * Create a post request with provided URL and with data as JSON format
	 * 
	 * @param url  refers to the calling url
	 * @param json contains entry of parameter which to be used into a request Form
	 * @throws IOException
	 */
	public HttpResponse postAsJson(String url, String json) throws IOException {
		HttpClient client = HttpClients.custom().useSystemProperties().build();
		HttpPost post = new HttpPost(url);
		// add header
		post.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		post.setEntity(new StringEntity(json, CHARSET));
		return client.execute(post);
	}

	/**
	 * Create a post request with provided URL and with data as JSON format, having
	 * timeout in milliseconds
	 * 
	 * @param url           refers to the calling url
	 * @param json          contains entry of parameter which to be used into a
	 *                      request Form
	 * @param timeoutMillis timeout in milliseconds
	 * @throws IOException
	 */
	public HttpResponse postAsJson(String url, String json, int timeoutMillis) throws IOException {
		HttpClient client = HttpClients.custom().useSystemProperties().build();
		HttpPost post = new HttpPost(url);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeoutMillis)
				.setConnectTimeout(timeoutMillis).setConnectionRequestTimeout(timeoutMillis).build();

		post.setConfig(requestConfig);
		// add header
		post.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return client.execute(post);
	}
}
