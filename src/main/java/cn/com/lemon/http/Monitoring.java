package cn.com.lemon.http;

import org.apache.log4j.Logger;

/**
 * The <code>Monitoring</code> class is used to monitor the big file upload and
 * download status.
 * 
 * @see Thread
 * @author shellpo shih
 * @version 1.0
 */
public class Monitoring {
	private static Logger LOG = Logger.getLogger(Monitoring.class.getName());

	public Monitoring() {
	}

	public static void sleep(int second) {
		try {
			Thread.sleep(second);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void log(String msg) {
		LOG.debug(msg);
		System.err.println(msg);
	}

	public static void log(int msg) {
		LOG.debug(msg);
		System.err.println(msg);
	}
}
