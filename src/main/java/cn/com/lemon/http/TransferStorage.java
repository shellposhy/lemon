package cn.com.lemon.http;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 * Instances of this class support both reading and writing to a random access
 * file.<em>for large files</em>,this class provide the safety writing the
 * content what by <code>Bytes</code>
 * 
 * @see RandomAccessFile
 * @see Serializable
 * @author shellpo shih
 * @version 1.0
 */
public class TransferStorage implements Serializable {
	private static final long serialVersionUID = 1L;
	RandomAccessFile storage;
	long pos;

	public TransferStorage() {
		this("", 0);
	}

	public TransferStorage(String fileName, long pos) {
		try {
			storage = new RandomAccessFile(fileName, "rw");
			this.pos = pos;
			storage.seek(pos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized int write(byte[] b, int start, int length) {
		int n = -1;
		try {
			storage.write(b, start, length);
			n = length;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}
}
