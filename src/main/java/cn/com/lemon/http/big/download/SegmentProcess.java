package cn.com.lemon.http.big.download;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.lemon.http.big.TransferStorage;

/**
 * Download large files process main class,provider threads download.
 * 
 * @see Thread
 * @see TransferStorage
 * @see URL
 * @see HttpURLConnection
 * @see InputStream
 * @author shellpo shih
 * @version 1.0
 */
public class SegmentProcess extends Thread {
	String url;
	long startPos;
	long endPos;
	int threadID;
	boolean status = false;
	boolean stop = false;
	TransferStorage storage = null;

	public SegmentProcess(String url, String fileName, long startPos, long endPos, int id) {
		this.url = url;
		this.startPos = startPos;
		this.endPos = endPos;
		this.threadID = id;
		this.storage = new TransferStorage(fileName, startPos);
	}

	public void run() {
		while (startPos < endPos && !stop) {
			try {
				URL httpUrl = new URL(url);
				HttpURLConnection connect = (HttpURLConnection) httpUrl.openConnection();
				connect.setRequestProperty("User-Agent", "NetFox");
				String sProperty = "bytes=" + startPos + "-";
				connect.setRequestProperty("RANGE", sProperty);
				InputStream input = connect.getInputStream();
				byte[] b = new byte[1024];
				int nRead;
				while ((nRead = input.read(b, 0, 1024)) > 0 && startPos < endPos && !stop) {
					startPos += storage.write(b, 0, nRead);
				}
				status = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void processStop() {
		stop = true;
	}
}