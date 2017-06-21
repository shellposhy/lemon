package cn.com.lemon.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * The <code>FileUtil</code> class,the basic file and folder handler
 * 
 * @see Ant
 * @see Logger
 * @see File
 * @author shellpo shih
 * @version 1.0
 */
public class FileUtil {
	private static final Logger LOG = Logger.getLogger(FileUtil.class);
	private static int BUFFER_SIZE = 1048576;

	/* ==============folder handler=============== */
	public static boolean createFolder(String folder) {
		LOG.debug("=====create folder=====");
		boolean success = true;
		File fileDirectory = new File(folder);
		if (!fileDirectory.exists()) {
			success = fileDirectory.mkdirs();
		}
		return success;
	}

	public static boolean deleteFolder(String folder) {
		LOG.debug("=====delete folder=====");
		boolean success = true;
		File fileDirectory = new File(folder);
		if (fileDirectory.exists()) {
			success = fileDirectory.delete();
		}
		return success;
	}

	/* ==============copy file=============== */
	public static File createFile(String filePath, String fileName, boolean replace) {
		LOG.debug("=====create file=====");
		return createFile(filePath, fileName, null, replace);
	}

	public static File createFile(String path, String name, String content, boolean replace) {
		LOG.debug("=====create file=====");
		if ((path == null) || (path.equals("")) || (name == null) || (name.equals(""))) {
			return null;
		}
		try {
			File desFile = new File(path + (path.endsWith("/") ? "" : "/") + name);
			if (desFile.exists()) {
				if (replace) {
					desFile.delete();
				} else {
					if (desFile.exists()) {
						name = getNextFile(name);
						desFile = new File(path + (path.endsWith("/") ? "" : "/") + name);
					}
				}
			}
			createFolder(path);
			desFile.createNewFile();
			if (content != null) {
				FileWriter resultFile = new FileWriter(desFile);
				PrintWriter myFile = new PrintWriter(resultFile);
				String strContent = content;
				myFile.println(strContent);
				myFile.close();
				resultFile.close();
			}
			return desFile;
		} catch (Exception e) {
			LOG.error(path + name + ",create file error!");
			e.printStackTrace();
			return null;
		}
	}

	/* ==============delete file=============== */
	public static boolean deleteFile(String fileName) {
		return deleteFile(new File(fileName));
	}

	public static boolean deleteFile(File file) {
		LOG.debug("=====delete file=====");
		boolean success = true;
		if ((file != null) && (file.exists())) {
			success = file.delete();
		}
		return success;
	}

	public static boolean deleteAllFile(String path) {
		LOG.debug("=====delete all file=====");
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		if (!file.isDirectory()) {
			return false;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				deleteAllFile(path + "/" + tempList[i]);
				deleteFolder(path + "/" + tempList[i]);
			}
		}
		deleteFolder(path);
		return true;
	}

	/* ==============copy file=============== */
	public static String copyFile(String fileFullName, String folder, String name, boolean replace) {
		return copyFile(new File(fileFullName), folder, name, replace);
	}

	public static String copyFile(File sourceFile, String folder, String name, boolean replace) {
		createFolder(folder);
		String fileName = name;
		if ((fileName == null) || (fileName.equals(""))) {
			fileName = sourceFile.getName();
		}
		File desFile = new File(folder + (folder.endsWith("/") ? "" : "/") + fileName);
		if (replace) {
			desFile.delete();
		} else {
			while (desFile.exists()) {
				fileName = getNextFile(fileName);
				desFile = new File(folder + (folder.endsWith("/") ? "" : "/") + fileName);
			}
		}
		InputStream inStream = null;
		try {
			int byteread = 0;
			inStream = new FileInputStream(sourceFile);
			FileOutputStream fs = new FileOutputStream(desFile);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			fs.close();
		} catch (Exception e) {
			LOG.error("copy file error:" + sourceFile.getAbsolutePath() + File.separator + sourceFile.getName());
			e.printStackTrace();
			return "";
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					LOG.error("file output stream close error:" + sourceFile.getAbsolutePath() + File.separator
							+ sourceFile.getName());
					e.printStackTrace();
				}
			}
		}
		return fileName;
	}

	public static boolean copyFolder(String oldPath, String newPath, boolean replace) {
		try {
			File a = new File(oldPath);
			if (!a.exists()) {
				return true;
			}
			createFolder(newPath);
			String[] file = a.list();
			File srcFile = null;
			for (int i = 0; i < file.length; i++) {
				srcFile = new File(oldPath + (oldPath.endsWith("/") ? "" : "/") + file[i]);
				if (srcFile.isFile()) {
					copyFile(srcFile, newPath, srcFile.getName(), replace);
				}
				if (srcFile.isDirectory()) {
					copyFolder(oldPath + (oldPath.endsWith("/") ? "" : "/") + file[i],
							newPath + (newPath.endsWith("/") ? "" : "/") + file[i], replace);
				}
			}
		} catch (Exception e) {
			LOG.error("copy file folder error:" + oldPath + " to " + newPath);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* ==============move file=============== */
	public static String moveFile(String fileFullName, String folder, String fileName, boolean replace) {
		try {
			return moveFile(new File(fileFullName), folder, fileName, replace);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("=====move file error=====");
			return null;
		}
	}

	public static String moveFile(File sourceFile, String folder, String name, boolean replace) throws Exception {
		String fileName = copyFile(sourceFile, folder, name, replace);
		if ((fileName != null) && (!fileName.equals(""))) {
			deleteFile(sourceFile);
		}
		return fileName;
	}

	public static boolean moveFolder(String oldPath, String newPath) {
		if (copyFolder(oldPath, newPath, true)) {
			deleteAllFile(oldPath);
			return true;
		} else {
			LOG.error("=====move file folder error=====");
			return false;
		}
	}

	/* ==============lock file=============== */
	public static void simpleLockFile(String fileName) {
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			int length = fis.available();

			int[] temp = new int[length];
			for (int i = 0; i < length; i++) {
				temp[i] = (fis.read() + 100);
			}
			fis.close();

			FileOutputStream fos = new FileOutputStream(file);
			for (int i = 0; i < length; i++) {
				fos.write(temp[i]);
			}
			fos.close();
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	public static void simpleUnLockFile(String fileName) {
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			int length = fis.available();

			int[] temp = new int[length];
			for (int i = 0; i < length; i++) {
				temp[i] = (fis.read() - 100);
			}
			fis.close();

			FileOutputStream fos = new FileOutputStream(file);
			for (int i = 0; i < length; i++) {
				fos.write(temp[i]);
			}
			fos.close();
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	public static byte[] readSimpleUnLockFile(String fileName) {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			int length = fis.available();
			for (int i = 0; i < length; i++) {
				result.write(fis.read() - 100);
			}
			fis.close();
		} catch (Exception e) {
			LOG.error(e);
		}
		return result.toByteArray();
	}

	/* ==============zip/unzip file=============== */
	public static Zip zip(String folder, String zipName) {
		try {
			ZipOutputStream out = new ZipOutputStream(
					new BufferedOutputStream(new FileOutputStream(zipName), BUFFER_SIZE));
			Zipper zipper = new Zipper();
			Zip zro = new Zip();
			File zipFile = new File(folder);
			try {
				if (!zipFile.exists()) {
					zipFile.createNewFile();
				}
				zipper.zip(out, zipFile, "");
			} finally {
				out.close();
			}
			zro.setContentSize(Long.valueOf(zipper.getContentSize()));
			zro.setZipFileSize(Long.valueOf(zipFile.length()));
			return zro;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static class Zipper {
		private long contentSize;

		public void zip(ZipOutputStream out, File f, String base) throws Exception {
			if (f.isDirectory()) {
				File[] fl = f.listFiles();
				out.putNextEntry(new ZipEntry(base + "/"));
				base = base + "/";
				for (int i = 0; i < fl.length; i++) {
					zip(out, fl[i], base + fl[i].getName());
				}
			} else {
				ZipEntry ze = new ZipEntry(base);
				this.contentSize += ze.getSize();
				out.putNextEntry(ze);
				FileInputStream in = new FileInputStream(f);
				BufferedInputStream bIn = new BufferedInputStream(in, FileUtil.BUFFER_SIZE);

				byte[] data = new byte[FileUtil.BUFFER_SIZE];
				try {
					int b;
					while ((b = bIn.read(data, 0, FileUtil.BUFFER_SIZE)) != -1) {
						out.write(data, 0, b);
					}
				} finally {
					bIn.close();
					in.close();
				}
			}
		}

		public long getContentSize() {
			return contentSize;
		}

	}

	public static void unzip(String zipFilePathName, String outputDirectory) throws Exception {
		Unzipper unzipper = new Unzipper();
		unzipper.unzip(zipFilePathName, outputDirectory);
	}

	private static class Unzipper {
		public void unzip(String zipFileName, String outputDirectory) throws Exception {
			ZipFile zipFile = new ZipFile(zipFileName);
			try {
				Enumeration<ZipEntry> e = zipFile.getEntries();
				ZipEntry zipEntry = null;
				List<ZipEntry> list = new LinkedList<ZipEntry>();
				while (e.hasMoreElements()) {
					zipEntry = (ZipEntry) e.nextElement();
					if (zipEntry.isDirectory()) {
						String name = zipEntry.getName();
						name = name.substring(0, name.length() - 1);
						File f = new File(outputDirectory + File.separator + name);
						f.mkdirs();
					} else {
						list.add(zipEntry);
					}
				}
				File dir = new File(outputDirectory);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				for (ZipEntry ze : list) {
					File f = new File(outputDirectory + File.separator + ze.getName());
					f.createNewFile();
					BufferedInputStream bIn = new BufferedInputStream(zipFile.getInputStream(ze), FileUtil.BUFFER_SIZE);
					BufferedOutputStream bOut = new BufferedOutputStream(new FileOutputStream(f), FileUtil.BUFFER_SIZE);
					try {
						byte[] data = new byte[FileUtil.BUFFER_SIZE];
						int c;
						while ((c = bIn.read(data, 0, FileUtil.BUFFER_SIZE)) != -1) {
							bOut.write(data, 0, c);
						}
					} finally {
						bOut.close();
						bIn.close();
					}
				}
			} finally {
				zipFile.close();
			}
		}
	}

	/* =============================== */
	/* ============utilities============= */
	/* =============================== */
	public static String getFileExt(String fileName) {
		if (fileName == null) {
			return null;
		}
		int pos = fileName.lastIndexOf('.');
		if (pos > 0) {
			return fileName.substring(pos + 1, fileName.length());
		}
		return "";
	}

	public static String getFileShortName(String fileName) {
		if (fileName == null) {
			return null;
		}
		int pos = fileName.lastIndexOf('.');
		if (pos > 0) {
			return fileName.substring(0, pos);
		}
		return fileName;
	}

	public static String getNextFile(String fileName) {
		StringBuffer sb = new StringBuffer();
		String ext = getFileExt(fileName);
		String shortName = getFileShortName(fileName);

		int pos = shortName.lastIndexOf('[');
		if ((shortName.endsWith("]")) && (pos > 0)) {
			String sureName = shortName.substring(0, pos);
			int index = Integer.valueOf(shortName.substring(pos + 1, shortName.length() - 1)).intValue();
			sb.append(sureName).append("[").append(index + 1).append("]");
		} else {
			sb.append(shortName).append("[1]");
		}
		if ((ext != null) && (!ext.equals(""))) {
			sb.append(".").append(ext);
		}
		return sb.toString();
	}

	public static String getFileSize(long fileSize) {
		DecimalFormat df = new DecimalFormat("###,##0.00");
		double s = 1.0D * fileSize;
		if (s > 1073741824.0D) {
			return df.format(s / 1024.0D / 1024.0D / 1024.0D) + "G";
		}
		if (s > 1048576.0D) {
			return df.format(s / 1024.0D / 1024.0D) + "M";
		}
		return df.format(s / 1024.0D) + "K";
	}

	public static List<String> findAllFiles(String path) {
		List<String> result = new ArrayList<String>();
		File file = new File(path);
		return listFile(file, result);
	}

	private static List<String> listFile(File file, List<String> result) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				listFile(files[i], result);
			}
		} else {
			String filePath = file.getAbsolutePath();
			result.add(filePath);
		}
		return result;
	}
}
