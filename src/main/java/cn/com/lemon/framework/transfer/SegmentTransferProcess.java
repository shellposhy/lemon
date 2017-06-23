package cn.com.lemon.framework.transfer;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SegmentTransferProcess extends Thread {
	// File URL
	String url;
	// File Snippet Start Position
	long startPos;
	// File Snippet End Position
	long endPos;
	// Thread's ID
	int threadID;
	// Downing is over
	boolean status = false;
	// Stop identical
	boolean stop = false;
	// File Access interface
	TransferStorage storage = null;

	public SegmentTransferProcess(String url, String fileName, long startPos, long endPos, int id) {
		this.url = url;
		this.startPos = startPos;
		this.endPos = endPos;
		this.threadID = id;
		storage = new TransferStorage(fileName, startPos);
	}

	public void run() {
		while (startPos < endPos && !stop) {
			try {
				URL httpUrl = new URL(url);
				HttpURLConnection httpConnection = (HttpURLConnection) httpUrl.openConnection();
				httpConnection.setRequestProperty("User-Agent", "NetFox");
				String sProperty = "bytes=" + startPos + "-";
				httpConnection.setRequestProperty("RANGE", sProperty);
				InputStream input = httpConnection.getInputStream();
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
