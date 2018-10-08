package cn.com.lemon.base.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;

import static cn.com.lemon.base.Preasserts.checkArgument;
import static cn.com.lemon.base.Strings.isNullOrEmpty;
import static cn.com.lemon.base.DateUtil.format;
import static cn.com.lemon.util.resource.FileUtil.createFolder;

/**
 * In multi-thread environment, read and write files frequently.
 * 
 * @author shellpo shih
 * @version 1.0
 */
public final class MultiWriteFile {

	private static final String DEFAULT_SUFFIX = ".txt";

	private MultiWriteFile() {
	}

	/**
	 * write file
	 * 
	 * @param path
	 *            the file path
	 * @param name
	 *            the file name
	 * @param content
	 *            the line content
	 * @return
	 */
	public static void write(String path, String name, String content) {
		checkArgument(!isNullOrEmpty(path));
		// create folder
		if (createFolder(path)) {
			String fileName = isNullOrEmpty(name) ? path + "/" + format(new Date(), "yyyyMMdd") + DEFAULT_SUFFIX
					: path + "/" + name + "_" + format(new Date(), "yyyyMMdd") + DEFAULT_SUFFIX;
			// create file
			File file = new File(fileName);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}

			// multiple write
			if (file.exists()) {
				RandomAccessFile fout = null;
				FileChannel fcout = null;
				try {
					fout = new RandomAccessFile(file, "rw");
					long filelength = fout.length();
					fcout = fout.getChannel();

					// Get file lock
					FileLock flout = null;
					while (true) {
						try {
							flout = fcout.tryLock();
							break;
						} catch (Exception e) {
							Thread.sleep(1000);
						}
					}

					// Get the file tail pointer
					fout.seek(filelength);
					content += "\n";
					fout.write(content.getBytes());

					// release
					flout.release();
					fcout.close();
					fout.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if (fcout != null) {
						try {
							fcout.close();
						} catch (IOException e) {
							e.printStackTrace();
							fcout = null;
						}
					}
					if (fout != null) {
						try {
							fout.close();
						} catch (IOException e) {
							e.printStackTrace();
							fout = null;
						}
					}
				}
			}
		}
	}
}
