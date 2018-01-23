package cn.com.lemon.base.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.com.lemon.base.Preasserts.checkArgument;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * Static utility methods pertaining to {@code Zip} primitives.
 * <p>
 * The base utility contain basic operate by file zip and dir zips
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Zips {
	private static final int BUFSIZE = 8192;
	private static final String SUFFIX = ".zip";
	private static Logger LOG = LoggerFactory.getLogger(Zips.class.getName());

	private Zips() {
	}

	/**
	 * Uncompress file from {@code srcPath} to {@code dest}
	 * 
	 * @param srcPath
	 *            {@code String} the source file path
	 * @param dest
	 *            {@code String} the destination file path,the value allow to
	 *            null
	 * @return
	 */
	public static void unzip(String srcPath, String dest) {
		File file = new File(srcPath);
		checkArgument(file.exists(), "The " + file + " is not exist!");
		if (isNullOrEmpty(dest)) {
			dest = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."));
		}
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			ZipFile zipFile = new ZipFile(file);
			Enumeration<ZipEntry> entries = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (entries.hasMoreElements()) {
				zipEntry = entries.nextElement();
				if (zipEntry.isDirectory()) {
					String dirPath = dest + File.separator + zipEntry.getName();
					File dir = new File(dirPath);
					dir.mkdirs();
				} else {
					File newFile = new File(dest + File.separator + zipEntry.getName());
					if (!newFile.exists()) {
						String dirs = newFile.getParent();
						File parentDir = new File(dirs);
						parentDir.mkdirs();
					}
					newFile.createNewFile();
					is = zipFile.getInputStream(zipEntry);
					fos = new FileOutputStream(newFile);
					int count;
					byte[] buf = new byte[BUFSIZE];
					while ((count = is.read(buf)) != -1) {
						fos.write(buf, 0, count);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Compress file from {@code srcFilePath} to {@code destFilePath}
	 * 
	 * @param srcFilePath
	 *            {@code String} the source file path
	 * @param destFilePath
	 *            {@code String} the destination file path
	 * @return
	 */
	public static void zip(String srcFilePath, String destFilePath) {
		File srcFile = new File(srcFilePath);
		checkArgument(srcFile.exists(), "The " + srcFilePath + " is not exist!");
		File destFile = null;
		if (isNullOrEmpty(destFilePath)) {
			destFilePath = srcFile.getAbsolutePath();
			if (srcFile.isDirectory()) {
				destFile = new File(destFilePath + SUFFIX);
			} else {
				destFile = new File(destFilePath.substring(0, destFilePath.lastIndexOf(".")) + SUFFIX);
			}
		} else {
			destFile = new File(destFilePath);
		}
		FileOutputStream fos = null;
		CheckedOutputStream cos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(destFile);
			cos = new CheckedOutputStream(fos, new CRC32());
			zos = new ZipOutputStream(cos);
			type(srcFile, zos, "");
		} catch (FileNotFoundException e) {
			LOG.error("The " + destFilePath + " is not exist!");
		} finally {
			try {
				zos.close();
				cos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/* ====================================== */
	/* ===========common private utilities========= */
	/* ====================================== */
	private static void type(File srcFile, ZipOutputStream zipOutputStream, String dir) {
		if (!srcFile.exists())
			return;
		if (srcFile.isFile()) {
			file(srcFile, zipOutputStream, dir);
		} else if (srcFile.isDirectory()) {
			dir(srcFile, zipOutputStream, dir);
		}
	}

	private static void file(File file, ZipOutputStream zipOutputStream, String dir) {
		if (!file.exists())
			return;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(dir + file.getName());
			zipOutputStream.putNextEntry(entry);
			int count;
			byte[] buf = new byte[BUFSIZE];
			while ((count = bis.read(buf)) != -1) {
				zipOutputStream.write(buf, 0, count);
			}
		} catch (FileNotFoundException e) {
			LOG.error("The " + file.getAbsolutePath() + " is not exist!");
		} catch (IOException e) {
			LOG.error("The " + file.getAbsolutePath() + " is zip error!");
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				LOG.error("The " + file.getAbsolutePath() + " is zip error!");
			}
		}
	}

	private static void dir(File dirFile, ZipOutputStream zipOutputStream, String dir) {
		if (!dirFile.exists())
			return;
		File[] files = dirFile.listFiles();
		if (files.length == 0) {
			try {
				zipOutputStream.putNextEntry(new ZipEntry(dir + dirFile.getName() + File.separator));
			} catch (IOException e) {
				LOG.error("The " + dirFile.getAbsolutePath() + " is zip error!");
			}
		}
		for (File file : files) {
			type(file, zipOutputStream, dir + dirFile.getName() + File.separator);
		}
	}
}
