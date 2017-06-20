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
		boolean success = true;
		File fileDirectory = new File(folder);
		if (!fileDirectory.exists()) {
			success = fileDirectory.mkdirs();
		}
		return success;
	}

	public static boolean deleteFolder(String folder) {
		boolean success = true;
		File fileDirectory = new File(folder);
		if (fileDirectory.exists()) {
			success = fileDirectory.delete();
		}
		return success;
	}

	/* ==============copy file=============== */
	public static File createFile(String filePath, String fileName, boolean replace) {
		return createFile(filePath, fileName, null, replace);
	}

	public static File createFile(String filePath, String fileName, String fileContent, boolean replace) {
		if ((filePath == null) || (filePath.equals("")) || (fileName == null) || (fileName.equals(""))) {
			return null;
		}
		File desFile = null;
		try {
			desFile = new File(filePath + (filePath.endsWith("/") ? "" : "/") + fileName);
			if (desFile.exists()) {
				if (replace) {
					desFile.delete();
				} else {
					while (desFile.exists()) {
						fileName = getNextFile(fileName);
						desFile = new File(filePath + (filePath.endsWith("/") ? "" : "/") + fileName);
					}
				}
			}
			createFolder(filePath);
			desFile.createNewFile();
			if (fileContent != null) {
				FileWriter resultFile = new FileWriter(desFile);
				PrintWriter myFile = new PrintWriter(resultFile);
				String strContent = fileContent;
				myFile.println(strContent);
				resultFile.close();
			}
		} catch (Exception e) {
			LOG.error("新建目录操作出错:" + filePath);
			e.printStackTrace();
			return null;
		}
		return desFile;
	}

	public static boolean deleteFile(String fileName) {
		return deleteFile(new File(fileName));
	}

	public static boolean deleteFile(File file) {
		boolean success = true;
		if ((file != null) && (file.exists())) {
			success = file.delete();
		}
		if (!success) {
			LOG.error("无法删除文件：" + file.getAbsolutePath() + File.separator + file.getName());
		}
		return success;
	}

	public static boolean deleteAllFile(String path) {
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
		return true;
	}

	public static String copyFile(String srcFileFullName, String desFolder, String desFileName, boolean replace) {
		return copyFile(new File(srcFileFullName), desFolder, desFileName, replace);
	}

	public static String copyFile(File srcFile, String desFolder, String desFileName, boolean replace) {
		createFolder(desFolder);
		String fileName = desFileName;
		if ((fileName == null) || (fileName.equals(""))) {
			fileName = srcFile.getName();
		}
		StringBuffer desFileFullName = new StringBuffer();
		desFileFullName.append(desFolder);
		if (!desFolder.endsWith("/")) {
			desFileFullName.append("/");
		}
		desFileFullName.append(fileName);

		File desFile = new File(desFileFullName.toString());
		if (replace) {
			desFile.delete();
		} else {
			while (desFile.exists()) {
				fileName = getNextFile(fileName);
				desFileFullName = new StringBuffer();
				desFileFullName.append(desFolder);
				if (!desFolder.endsWith("/")) {
					desFileFullName.append("/");
				}
				desFileFullName.append(fileName);
				desFile = new File(desFileFullName.toString());
			}
		}
		InputStream inStream = null;
		try {
			int byteread = 0;
			inStream = new FileInputStream(srcFile);
			FileOutputStream fs = new FileOutputStream(desFile);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			fs.close();
		} catch (Exception e) {
			LOG.error("复制单个文件操作出错:" + srcFile.getAbsolutePath() + File.separator + srcFile.getName());
			e.printStackTrace();
			return "";
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					LOG.error("复制单个文件Close_InStream操作出错:" + srcFile.getAbsolutePath() + File.separator
							+ srcFile.getName());
					e.printStackTrace();
				}
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("复制文件成功,目标：" + desFileFullName.toString());
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
				if (oldPath.endsWith("/")) {
					srcFile = new File(oldPath + file[i]);
				} else {
					srcFile = new File(oldPath + "/" + file[i]);
				}
				if (srcFile.isFile()) {
					copyFile(srcFile, newPath, srcFile.getName(), replace);
				}
				if (srcFile.isDirectory()) {
					copyFolder(oldPath + (oldPath.endsWith("/") ? "" : "/") + file[i],
							newPath + (newPath.endsWith("/") ? "" : "/") + file[i], replace);
				}
			}
		} catch (Exception e) {
			LOG.error("复制整个文件夹内容操作出错:" + oldPath + " to " + newPath);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String moveFile(String srcFileFullName, String desFolder, String desFileName, boolean replace)
			throws Exception {
		return moveFile(new File(srcFileFullName), desFolder, desFileName, replace);
	}

	public static String moveFile(File srcFile, String desFolder, String desFileName, boolean replace)
			throws Exception {
		String fileName = copyFile(srcFile, desFolder, desFileName, replace);
		if ((fileName != null) && (!fileName.equals(""))) {
			deleteFile(srcFile);
		}
		return fileName;
	}

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

	public static void moveFile(String sFilePathName, String toPath) throws Exception {
		File sFile = new File(sFilePathName);
		if (!sFile.exists()) {
			throw new Exception("源文件[" + sFile.getPath() + "]不存在。");
		}
		File tFile = new File(toPath);
		if (!tFile.exists()) {
			tFile.mkdirs();
		}
		tFile = new File(toPath + "/" + sFile.getName());
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(tFile);
			fis = new FileInputStream(sFile);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} finally {
			fos.close();
			fis.close();
		}
		sFile.delete();
	}

	public static Zip zip(String zipFilePathName, String inputFileName) throws Exception {
		ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(new FileOutputStream(zipFilePathName), BUFFER_SIZE));
		Zipper zipper = new Zipper();
		Zip zro = new Zip();
		File zipFile = new File(inputFileName);
		try {
			zipper.zip(out, zipFile, "");
		} finally {
			out.close();
		}
		zro.setContentSize(Long.valueOf(zipper.getContentSize()));
		zipFile = new File(zipFilePathName);
		zro.setZipFileSize(Long.valueOf(zipFile.length()));
		return zro;
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
}
