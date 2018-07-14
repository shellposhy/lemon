package cn.com.lemon.http.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cn.com.lemon.base.util.Jsons;

public class Httpclients {

	private PoolingHttpClientConnectionManager connectionManager = null;
	private HttpClientBuilder httpBuilder = null;
	private RequestConfig requestConfig = null;
	private static int MAXCONNECTION = 10;
	private static int DEFAULTMAXCONNECTION = 5;

	public static Httpclients init(String domain, int port) {
		return new Httpclients(domain, port);
	}

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

	public CloseableHttpClient connection() {
		CloseableHttpClient httpClient = httpBuilder.build();
		return httpClient;
	}

	public HttpUriRequest request(Map<String, String> parameter, String url, EHttpMethod method) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> e : parameter.entrySet()) {
			NameValuePair pair = new BasicNameValuePair(e.getKey(), e.getValue());
			params.add(pair);
		}
		NameValuePair[] valuePairs = params.toArray(new BasicNameValuePair[params.size()]);
		HttpUriRequest request = null;
		switch (method) {
		case GET:
			request = RequestBuilder.get().setUri(url).addParameters(valuePairs).setConfig(requestConfig).build();
			break;
		case POST:
			request = RequestBuilder.post().setUri(url).addParameters(valuePairs).setConfig(requestConfig).build();
			break;
		case DELETE:
			request = RequestBuilder.delete().setUri(url).addParameters(valuePairs).setConfig(requestConfig).build();
			break;
		case HEAD:
			request = RequestBuilder.head().setUri(url).addParameters(valuePairs).setConfig(requestConfig).build();
			break;
		case OPTIONS:
			request = RequestBuilder.options().setUri(url).addParameters(valuePairs).setConfig(requestConfig).build();
			break;
		case PUT:
			request = RequestBuilder.put().setUri(url).addParameters(valuePairs).setConfig(requestConfig).build();
			break;
		case TRACE:
			request = RequestBuilder.trace().setUri(url).addParameters(valuePairs).setConfig(requestConfig).build();
			break;
		default:
			request = RequestBuilder.get().setUri(url).addParameters(valuePairs).setConfig(requestConfig).build();
			break;
		}
		return request;
	}

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Httpclients httpclients = Httpclients.init("baidu.com", 80);
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "朱天庆");
		map.put("password", "921024cxq");
		map.put("token", "1f1176b726665bf87c9e0dd9600ef1a2");
		map.put("account_type", "1");
		
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("header", Jsons.json(map));
		
		HttpClient client = httpclients.connection();
		
		HttpUriRequest post =httpclients.request(map1, "https://api.baidu.com/json/tongji/v1/ReportService/getData", EHttpMethod.POST);
		HttpResponse response=client.execute(post);
		HttpEntity entity = response.getEntity();  
        String message = EntityUtils.toString(entity, "utf-8");  
        System.out.println(message);  
	}

}
