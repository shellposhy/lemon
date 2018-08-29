package cn.com.lemon.test.http;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import cn.com.lemon.base.util.Jsons;
import cn.com.lemon.http.client.Httpclients;

public class HttpclientsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExecute() throws ParseException, IOException, JSONException {

		Httpclients httpClients = Httpclients.init("www.shfp1017.org.cn", 80);

		org.codehaus.jettison.json.JSONObject body = new JSONObject();
		body.put("ywNo", 4);
		body.put("phone", "13910735841");

		//System.out.println(doPost("https://www.shfp1017.org.cn/mts/sms/sendSmsCode", body.toString(), "utf-8"));

		System.out.println(httpClients.post("https://www.shfp1017.org.cn/mts/sms/sendSmsCode", Jsons.json(body)));

	}

	public static String doPost(String url, String content, String charset) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(content, ContentType.APPLICATION_JSON);
		httpPost.setEntity(entity);
		try {
			response = httpClient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), charset);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
