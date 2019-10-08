package cn.com.lemon.base.zip;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.com.lemon.base.Strings;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Static utility for file compression and decompression based on zip4j tool.
 * 
 * @author shih shaobo
 * @version 1.0
 */
public final class Zip4Js {
	private Zip4Js() {
	}

	public static File[] unzip(String zip, String dest, String passwd) throws ZipException {
		File zipFile = new File(zip);
		return unzip(zipFile, dest, passwd);
	}

	public static File[] unzip(String zip, String passwd) throws ZipException {
		File zipFile = new File(zip);
		File parentDir = zipFile.getParentFile();
		return unzip(zipFile, parentDir.getAbsolutePath(), passwd);
	}

	/**
	 * Unzip the specified ZIP file to the specified directory with the given
	 * password.
	 * <p>
	 * If the specified directory does not exist, it can be automatically
	 * created, and an invalid path will cause an exception to be thrown
	 * 
	 * @param zipFile
	 * @param dest
	 * @param passwd
	 * @return File array
	 * @throws ZipException
	 */
	@SuppressWarnings("unchecked")
	public static File[] unzip(File zipFile, String dest, String passwd) throws ZipException {
		ZipFile zFile = new ZipFile(zipFile);
		zFile.setFileNameCharset("UTF-8");
		if (!zFile.isValidZipFile()) {
			throw new ZipException("Compressed files are illegal and may be corrupted.");
		}
		File destDir = new File(dest);
		if (destDir.isDirectory() && !destDir.exists()) {
			destDir.mkdir();
		}
		if (zFile.isEncrypted()) {
			zFile.setPassword(passwd.toCharArray());
		}
		zFile.extractAll(dest);
		List<FileHeader> headerList = zFile.getFileHeaders();
		List<File> extractedFileList = new ArrayList<File>();
		for (FileHeader fileHeader : headerList) {
			if (!fileHeader.isDirectory()) {
				extractedFileList.add(new File(destDir, fileHeader.getFileName()));
			}
		}
		File[] extractedFiles = new File[extractedFileList.size()];
		extractedFileList.toArray(extractedFiles);
		return extractedFiles;
	}

	public static String zip(String src) {
		return zip(src, null);
	}

	public static String zip(String src, String passwd) {
		return zip(src, null, passwd);
	}

	public static String zip(String src, String dest, String passwd) {
		return zip(src, dest, true, passwd);
	}

	/**
	 * Compresses the specified file or folder to the specified location with
	 * the given password.
	 * <p>
	 * Dest can be the absolute path where the final compressed file resides,
	 * the directory where it resides, null or .<br/>
	 * If null or "" will be stored in the current directory, that is, the same
	 * directory as the source file, compressed file name to take the source
	 * file name, suffix. Zip; <br/>
	 * If it ends in (File. Separator), it is considered a directory. Compress
	 * the filename to get the source filename, suffix. Zip, otherwise it is
	 * considered a filename.
	 * 
	 * @param src
	 * @param dest
	 * @param isCreateDir
	 * @param passwd
	 * @return
	 */
	public static String zip(String src, String dest, boolean isCreateDir, String passwd) {
		File srcFile = new File(src);
		dest = buildDestinationZipFilePath(srcFile, dest);
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		if (!Strings.isNullOrEmpty(passwd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(passwd.toCharArray());
		}
		try {
			ZipFile zipFile = new ZipFile(dest);
			if (srcFile.isDirectory()) {
				if (!isCreateDir) {
					File[] subFiles = srcFile.listFiles();
					ArrayList<File> temp = new ArrayList<File>();
					Collections.addAll(temp, subFiles);
					zipFile.addFiles(temp, parameters);
					return dest;
				}
				zipFile.addFolder(srcFile, parameters);
			} else {
				zipFile.addFile(srcFile, parameters);
			}
			return dest;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Build a zip file path that, if it does not exist, will create a file name
	 * or directory that may or may not be passed in.
	 * 
	 * @param srcFile
	 * @param destParam
	 * @return {@link String}
	 */
	private static String buildDestinationZipFilePath(File srcFile, String destParam) {
		if (Strings.isNullOrEmpty(destParam)) {
			if (srcFile.isDirectory()) {
				destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
			} else {
				String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				destParam = srcFile.getParent() + File.separator + fileName + ".zip";
			}
		} else {
			createDestDirectoryIfNecessary(destParam);
			if (destParam.endsWith(File.separator)) {
				String fileName = "";
				if (srcFile.isDirectory()) {
					fileName = srcFile.getName();
				} else {
					fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				}
				destParam += fileName + ".zip";
			}
		}
		return destParam;
	}

	/**
	 * Create a zip file directory if necessary, such as if the specified
	 * directory was not created.
	 * 
	 * @param destParam
	 */
	private static void createDestDirectoryIfNecessary(String destParam) {
		File destDir = null;
		if (destParam.endsWith(File.separator)) {
			destDir = new File(destParam);
		} else {
			destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}
}
