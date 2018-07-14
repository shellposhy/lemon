package cn.com.lemon.http.client;

/**
 * Base on HttpClient Connect Method
 * 
 * @author shellpo shih
 * @version 1.0
 */
public enum EHttpMethod {
	GET("get"), POST("post"), PUT("put"), DELETE("delete"), HEAD("head"), OPTIONS("options"), TRACE("trace");

	private String method;

	private EHttpMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

}
