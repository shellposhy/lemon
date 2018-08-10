package cn.com.lemon.test;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import cn.com.lemon.http.client.Httpclients;

public class Test {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		Httpclients httpclients = Httpclients.init("www.zgshfp.com.cn", 80);
		HttpUriRequest httpGet = new HttpGet("https://www.zgshfp.com.cn/bi/api/count");
		CloseableHttpResponse response = httpclients.connection().execute(httpGet);
		HttpEntity entity = response.getEntity();
		String  entityStr= EntityUtils.toString(entity,"utf-8");
        System.out.println(entityStr);
	}
}
