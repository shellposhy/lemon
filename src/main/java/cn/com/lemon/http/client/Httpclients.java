package cn.com.lemon.http.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Static utility methods pertaining to {@code Httpclients} primitives, base on
 * {@link HttpClient} tools
 * <p>
 * {@code Httpclients} provide the base {@link CloseableHttpClient},the use for
 * {@link HttpConnection},also privide the {@link HttpResponse} data processing.
 *
 * @see HttpClient-tools-4.5.6
 * @author shellpo shih
 * @version 1.0
 */
public class Httpclients {

	private static final String APPLICATION_JSON = "application/json";
	private static final String TEXT_JSON = "text/json";

	private PoolingHttpClientConnectionManager connectionManager = null;
	private HttpClientBuilder httpBuilder = null;
	private RequestConfig requestConfig = null;
	private static int MAXCONNECTION = 10;
	private static int DEFAULTMAXCONNECTION = 5;

	/**
	 * Static the initialize method
	 * <p>
	 * the initialization parameter
	 * 
	 * @param domain
	 *            ip address or domain name
	 * @param port
	 *            the {@code int} the web service port number
	 * @return {@link Httpclients}
	 */
	public static Httpclients init(String domain, int port) {
		return new Httpclients(domain, port);
	}

	/**
	 * private constructor
	 * 
	 * @param domain
	 *            ip address or domain name
	 * @param port
	 *            the {@code int} the web service port number
	 */
	private Httpclients(String domain, int port) {
		requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000).build();
		HttpHost target = new HttpHost(domain, port);
		connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(MAXCONNECTION);
		connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
		connectionManager.setMaxPerRoute(new HttpRoute(target), 20);
		httpBuilder = HttpClients.custom();
		httpBuilder.setConnectionManager(connectionManager);
	}

	/**
	 * Base implementation of {@link HttpClient} that also implements
	 * {@link Closeable}
	 * 
	 * @since {@link HttpClient} 4.3
	 */
	public CloseableHttpClient connection() {
		CloseableHttpClient httpClient = httpBuilder.build();
		return httpClient;
	}

	/**
	 * Submit requests based on post
	 * 
	 * @param param
	 *            the base parameter
	 * @param url
	 *            the request interface
	 * @return {@link HttpUriRequest}
	 */
	public HttpUriRequest post(Map<String, String> param, String url) {
		if (null != param && param.size() > 0)
			return RequestBuilder.post().setUri(url).addParameters(param(param)).setConfig(requestConfig).build();
		return RequestBuilder.post().setUri(url).setConfig(requestConfig).build();
	}

	/**
	 * Submit requests based on get
	 * 
	 * @param param
	 *            the base parameter
	 * @param url
	 *            the request interface
	 * @return {@link HttpUriRequest}
	 */
	public HttpUriRequest get(Map<String, String> param, String url) {
		if (null != param && param.size() > 0)
			return RequestBuilder.get().setUri(url).addParameters(param(param)).setConfig(requestConfig).build();
		return RequestBuilder.get().setUri(url).setConfig(requestConfig).build();
	}

	/**
	 * Submit {@code Json} requests based on post
	 * 
	 * @param json
	 *            the json {@code String}
	 * @param url
	 *            the request interface
	 * @return {@code String}
	 */
	public String post(String url, String json) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
		try {
			// JSON is encoded in utf-8 to transfer Chinese
			json = URLEncoder.encode(json, StandardCharsets.UTF_8.name());
			StringEntity stringEntity = new StringEntity(json);
			stringEntity.setContentType(TEXT_JSON);
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = connection().execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Execute the {@code HttpUriRequest} and return {@code HttpResponse}
	 * 
	 * @param request
	 *            the {@code HttpUriRequest}
	 * @return {@code HttpResponse}
	 */
	public HttpResponse execute(HttpUriRequest request) {
		try {
			return httpBuilder.build().execute(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* ==================private tools========================== */
	private NameValuePair[] param(Map<String, String> param) {
		if (null != param && param.size() > 0) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> e : param.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(e.getKey(), e.getValue());
				params.add(pair);
			}
			return params.toArray(new BasicNameValuePair[params.size()]);
		}
		return null;
	}
}
