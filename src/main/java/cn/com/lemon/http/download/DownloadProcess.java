package cn.com.lemon.http.download;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.lemon.http.Transfer;

/**
 * Download large files process main class,provider download.
 * 
 * @see Thread
 * @see SegmentProcess
 * @see DataOutputStream
 * @see DataInputStream
 * @see File
 * @author shellpo shih
 * @version 1.0
 */
public class DownloadProcess extends Thread {
	Transfer transfer = null;
	long[] startPos;
	long[] endPos;
	SegmentProcess[] segments;
	long length;
	boolean first = true;
	boolean stop = false;
	File tmpFile;
	DataOutputStream output;

	public DownloadProcess(Transfer transfer) {
		this.transfer = transfer;
		tmpFile = new File(transfer.getFilePath() + File.separator + transfer.getFileName() + ".tmp");
		if (tmpFile.exists()) {
			first = false;
			readPos();
		} else {
			startPos = new long[transfer.getSegment()];
			endPos = new long[transfer.getSegment()];
		}
	}

	public void run() {
		try {
			if (first) {
				length = getFileSize();
				if (length == -1) {
					System.err.println("File Length is not known!");
				} else if (length == -2) {
					System.err.println("File is not access!");
				} else {
					for (int i = 0; i < startPos.length; i++) {
						startPos[i] = (long) (i * (length / startPos.length));
					}
					for (int i = 0; i < endPos.length - 1; i++) {
						endPos[i] = startPos[i + 1];
					}
					endPos[endPos.length - 1] = length;
				}
			}
			segments = new SegmentProcess[startPos.length];
			for (int i = 0; i < startPos.length; i++) {
				segments[i] = new SegmentProcess(transfer.getUrl(),
						transfer.getFilePath() + File.separator + transfer.getFileName(), startPos[i], endPos[i], i);
				// Monitoring.log("Thread[" + i + "] , StartPos[" + startPos[i]
				// + "] EndPos[" + endPos[i] + "]");
				segments[i].start();
			}
			boolean flag = false;
			while (!stop) {
				writePos();
				// Monitoring.sleep(500);
				flag = true;
				for (int i = 0; i < startPos.length; i++) {
					if (!segments[i].status) {
						flag = false;
						break;
					}
				}
				if (flag)
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			int count = 0;
			for (int i = 0; i < startPos.length; i++) {
				if (segments[i].status) {
					count += 1;
				}
			}
			if (count == segments.length) {
				stopTransfer();
				tmpFile.delete();
			}
			// Monitoring.log("The Stop Thread Number[" + count + "]");
		}
	}

	public void stopTransfer() {
		stop = true;
		for (int i = 0; i < startPos.length; i++)
			segments[i].processStop();
	}

	public long getFileSize() {
		int fileLength = -1;
		try {
			URL url = new URL(transfer.getUrl());
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("User-Agent", "NetFox");
			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				return -2;
			}
			String sHeader;
			for (int i = 1;; i++) {
				sHeader = httpConnection.getHeaderFieldKey(i);
				if (sHeader != null) {
					if (sHeader.equals("Content-Length")) {
						fileLength = Integer.parseInt(httpConnection.getHeaderField(sHeader));
						break;
					}
				} else
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileLength;
	}

	private void writePos() {
		try {
			output = new DataOutputStream(new FileOutputStream(tmpFile));
			output.writeInt(startPos.length);
			// Monitoring.log("\n");
			for (int i = 0; i < startPos.length; i++) {
				output.writeLong(segments[i].startPos);
				output.writeLong(segments[i].endPos);
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readPos() {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(tmpFile));
			int count = input.readInt();
			startPos = new long[count];
			endPos = new long[count];
			for (int i = 0; i < startPos.length; i++) {
				startPos[i] = input.readLong();
				endPos[i] = input.readLong();
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}