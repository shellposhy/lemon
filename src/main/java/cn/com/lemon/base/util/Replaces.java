package cn.com.lemon.base.util;

import static cn.com.lemon.base.Preasserts.checkNotNull;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

import static cn.com.lemon.base.Files.encode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Static utility methods pertaining to {@code Replaces} primitives.
 * <p>
 * Replace the file content or content
 * <p>
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Replaces {

	private Replaces() {
	}

	public static void file(String path, String source, String target) {
		file(path, path.length(), source, target);
	}

	/**
	 * Replace the content in the file for the new content and re-write it in
	 * the current file.
	 * 
	 * @param path
	 *            {@code String} the file directory or the file path
	 * @param length
	 *            {@code int} the path length,when used
	 * @param source
	 *            {@code String} the old file content
	 * @param target
	 *            {@code String} the new file content
	 * @return
	 */
	public static void file(String path, int length, String source, String target) {
		checkNotNull(path, "The file path or directory is not null");
		if (isNullOrEmpty(source) || isNullOrEmpty(target))
			return;
		File fileOrDirectory = new File(path);
		if (fileOrDirectory.isFile() || fileOrDirectory.isDirectory()) {
			File[] files = fileOrDirectory.listFiles();
			if (null == files)
				return;
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					file(files[i].getAbsolutePath(), length, source, target);
				} else {
					String fileFullName = files[i].getAbsolutePath().toLowerCase();
					String suffix = fileFullName.substring(fileFullName.lastIndexOf(".") + 1, fileFullName.length());
					if (suffix.equals("js") || suffix.equals("html") || suffix.equals("jsp") || suffix.equals("css")) {
						String encode = encode(files[i].getAbsolutePath());
						if ("void".equals(encode)) {
							encode = "UTF-8";
						}
						if ("windows-1252".equals(encode)) {
							encode = "GBK";
						}
						FileInputStream fis = null;
						InputStreamReader in = null;
						try {
							fis = new FileInputStream(files[i].getAbsolutePath());
							in = new InputStreamReader(fis, encode);
							BufferedReader reader = new BufferedReader(in);
							StringBuffer strBuffer = new StringBuffer();
							String line = null;
							while ((line = reader.readLine()) != null) {
								if (line.contains(source)) {
									line = line.replaceAll(source, target);
								}
								strBuffer.append(line).append(System.getProperty("line.separator"));
							}
							reader.close();
							in.close();
							fis.close();
							// write new files
							PrintWriter writer = new PrintWriter(files[i]);
							writer.write(strBuffer.toString().toCharArray());
							writer.flush();
							writer.close();
						} catch (IOException e) {
						}
					} else {
						continue;
					}
				}
			}
		} else {
			checkNotNull(null, "The given file path is not directory or real file path!");
		}
	}
}
