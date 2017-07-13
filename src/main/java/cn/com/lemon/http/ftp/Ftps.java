package cn.com.lemon.http.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

/**
 * Static utility methods pertaining to {@code FTPClient} primitives.
 * <p>
 * The basic operate contains {@code upload} {@code download} {@code rename}
 * {@code remove} {@code makeDirecotory} {@code renameDirecotory}
 * {@code removeDirecotory}{@code list}
 * 
 * @author shellpo shih
 * @version 1.0
 */
public class Ftps {
	private static final Logger LOG = Logger.getLogger(Ftps.class.getName());

	/**
	 * Return {@code true} ,if the file is uploaded success
	 * 
	 * @param hostName
	 *            The ftp server hostname
	 * @param port
	 *            The ftp server port,default 21
	 * @param account
	 *            The ftp server account name
	 * @param password
	 *            The ftp server account password
	 * @param directory
	 *            The ftp work directory
	 * @param fileFullName
	 *            The local file full name
	 * @param name
	 *            The new upload file name
	 * @return {@code true}if the file is uploaded success
	 */
	public static boolean upload(String hostName, int port, String account, String password, String directory,
			String fileFullName, String name) {
		FTPClient client = new FTPClient();
		FileInputStream fis = null;
		boolean result = false;
		try {
			client.connect(hostName, port);
			client.login(account, password);
			client.enterLocalPassiveMode();
			File srcFile = new File(fileFullName);
			fis = new FileInputStream(srcFile);
			client.changeWorkingDirectory(directory);
			client.setBufferSize(1024);
			client.setControlEncoding("utf-8");
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			result = client.storeFile(name, fis);
			return result;
		} catch (SocketException e) {
			LOG.debug("Ftp connection refused");
			return false;
		} catch (FileNotFoundException e) {
			LOG.debug("Uploading file is not find");
			return false;
		} catch (IOException e) {
			LOG.debug("Uploading file  failed");
			return false;
		} finally {
			IOUtils.closeQuietly(fis);
			try {
				client.disconnect();
			} catch (IOException e) {
				LOG.debug("Ftp connection closed error");
			}
		}
	}

	/**
	 * Return {@code true} ,if the file is downloaded success
	 * 
	 * @param hostName
	 *            The ftp server hostname
	 * @param port
	 *            The ftp server port,default 21
	 * @param account
	 *            The ftp server account name
	 * @param password
	 *            The ftp server account password
	 * @param directory
	 *            The ftp work directory
	 * @param fileFullName
	 *            The local file full name
	 * @param name
	 *            The new upload file name
	 * @return {@code true}if the file is downloaded success
	 */
	public static boolean download(String hostName, int port, String account, String password, String directory,
			String fileFullName, String name) {
		FTPClient ftpClient = new FTPClient();
		boolean result = false;
		try {
			ftpClient.connect(hostName, port);
			ftpClient.login(account, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024);
			ftpClient.changeWorkingDirectory(directory);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			result = ftpClient.retrieveFile(fileFullName, new FileOutputStream(name));
			return result;
		} catch (SocketException e) {
			LOG.debug("Ftp connection refused");
			return false;
		} catch (FileNotFoundException e) {
			LOG.debug("Downloading file is not find");
			return false;
		} catch (IOException e) {
			LOG.debug("Downloading file  failed");
			return false;
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				LOG.debug("Ftp connection closed error");
			}
		}
	}

	public static boolean rename(String hostName, int port, String account, String password, String directory,
			String oldFileName, String newFileName) {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(hostName, port);
			ftpClient.login(account, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.rename(oldFileName, newFileName);
			return result;
		} catch (SocketException e) {
			LOG.debug("Ftp connection refused");
			return false;
		} catch (IOException e) {
			LOG.debug("Renaming file  failed");
			return false;
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				LOG.debug("Ftp connection closed error");
			}
		}
	}

	public static boolean remove(String hostName, int port, String account, String password, String directory,
			String fileName) {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(hostName, port);
			ftpClient.login(account, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.deleteFile(fileName);
			return result;
		} catch (SocketException e) {
			LOG.debug("Ftp connection refused");
			return false;
		} catch (IOException e) {
			LOG.debug("Removing file  failed");
			return false;
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				LOG.debug("Ftp connection closed error");
			}
		}
	}

	public static boolean makeDirecotory(String hostName, int port, String account, String password, String directory,
			String newDirectory) {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(hostName, port);
			ftpClient.login(account, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.makeDirectory(newDirectory);
			return result;
		} catch (SocketException e) {
			LOG.debug("Ftp connection refused");
			return false;
		} catch (IOException e) {
			LOG.debug("Make  directory  failed");
			return false;
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				LOG.debug("Ftp connection closed error");
			}
		}
	}

	public static boolean renameDirecotory(String hostName, int port, String account, String password, String directory,
			String oldDirectory, String newDirectory) {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(hostName, port);
			ftpClient.login(account, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.rename(oldDirectory, newDirectory);
			return result;
		} catch (SocketException e) {
			LOG.debug("Ftp connection refused");
			return false;
		} catch (IOException e) {
			LOG.debug("Renamed  directory  failed");
			return false;
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				LOG.debug("Ftp connection closed error");
			}
		}
	}

	public static boolean removeDirecotory(String hostName, int port, String account, String password, String directory,
			String delDirectory) {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(hostName, port);
			ftpClient.login(account, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.removeDirectory(delDirectory);
			return result;
		} catch (SocketException e) {
			LOG.debug("Ftp connection refused");
			return false;
		} catch (IOException e) {
			LOG.debug("Removed  directory  failed");
			return false;
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				LOG.debug("Ftp connection closed error");
			}
		}
	}

	public static String[] list(String hostname, int port, String account, String password, String directory) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(hostname, port);
			ftpClient.login(account, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("utf-8");
			ftpClient.changeWorkingDirectory(directory);
			ftpClient.enterLocalPassiveMode();
			String[] list = ftpClient.listNames();
			return list;
		} catch (SocketException e) {
			LOG.debug("Ftp connection refused");
			return null;
		} catch (IOException e) {
			LOG.debug("Directory list files get failed");
			return null;
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				LOG.debug("Ftp connection closed error");
			}
		}
	}

}