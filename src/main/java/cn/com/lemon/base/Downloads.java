package cn.com.lemon.base;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Static utility methods pertaining to {@code Downloads} primitives.
 * <p>
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Downloads {

	private Downloads() {
	}

	/**
	 * Set the ({@code HttpServletResponse} header,when cross-domain request api
	 * base on https/http protocol
	 * 
	 * @param response
	 * @return
	 */
	public static void response(HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	/**
	 * In order to solve the problem of Chinese language,
	 * <p>
	 * caused by the problem.The file content of the header is preferred for
	 * filename setting.
	 * <li>Safari:{@code ISO}</li>
	 * <li>Other:{@code URLEncoder}</li>
	 * 
	 * @param fileName
	 *            the file name,or the file name contains Chinese character
	 * @param request
	 * @return {@code String} the {@code HttpServletResponse}}
	 */
	public static String contentDisposition(String fileName, HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		try {
			if (userAgent.contains("Safari")) {
				byte[] bytes = fileName.getBytes("UTF-8");
				fileName = new String(bytes, "ISO-8859-1");
				return String.format("attachment; filename=\"%s\"", fileName);
			} else {
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
				return "attachment;filename=" + fileName;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "attachment;filename=" + fileName;
		}
	}
}
