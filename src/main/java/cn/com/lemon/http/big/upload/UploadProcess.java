package cn.com.lemon.http.big.upload;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import cn.com.lemon.http.big.Transfer;

public class UploadProcess extends Thread {
	private static Logger LOG = Logger.getLogger(UploadProcess.class.getName());
	Transfer transfer = null;
	long[] startPos;
	long[] endPos;
	UploadSegmentProcess[] segments;
	long length;
	boolean first = true;
	boolean stop = false;
	File tmpFile;
	DataOutputStream output;
	File uploadFile = null;

	public void init() {
		tmpFile = new File(transfer.getFilePath() + File.separator + transfer.getFileName() + ".tmp");
		if (tmpFile.exists()) {
			first = false;
			readPos();
		} else {
			startPos = new long[transfer.getSegment()];
			endPos = new long[transfer.getSegment()];
		}
	}

	public UploadProcess(Transfer transfer, File uploadFile) {
		this.transfer = transfer;
		if (uploadFile.exists() && uploadFile.isFile())
			this.uploadFile = uploadFile;
		init();
	}

	public UploadProcess(Transfer transfer, String uploadFileFullName) {
		this.transfer = transfer;
		File file = new File(uploadFileFullName);
		if (file.exists() && file.isFile())
			this.uploadFile = file;
		init();
	}

	private void readPos() {
		LOG.debug("=====Read the upload temp property file =====");
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
