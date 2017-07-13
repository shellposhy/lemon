package cn.com.lemon.http.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

public class Ftps {

	public static boolean upload(String hosName, int port, String userName, String password, String directory,
			String srcFileName, String destName) throws IOException {
		FTPClient ftpClient = new FTPClient();
		FileInputStream fis = null;
		boolean result = false;
		try {
			ftpClient.connect(hosName, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			File srcFile = new File(srcFileName);
			fis = new FileInputStream(srcFile);
			ftpClient.changeWorkingDirectory(directory);
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("utf-8");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			result = ftpClient.storeFile(destName, fis);
			return result;
		} catch (SocketException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			IOUtils.closeQuietly(fis);
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	public static boolean download(String ftpUrl, String userName, int port, String password, String directory,
			String destFileName, String downloadName) throws IOException {
		FTPClient ftpClient = new FTPClient();
		boolean result = false;
		try {
			ftpClient.connect(ftpUrl, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setBufferSize(1024);
			ftpClient.changeWorkingDirectory(directory);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			result = ftpClient.retrieveFile(destFileName, new FileOutputStream(downloadName));
			return result;
		} catch (SocketException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	public static boolean rename(String ftpUrl, String userName, int port, String password, String directory,
			String oldFileName, String newFileName) throws IOException {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpUrl, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.rename(oldFileName, newFileName);
			return result;
		} catch (SocketException e) {
			throw e;
		} catch (IOException e) {
			throw new IOException("连接ftp服务器失败！", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	public static boolean remove(String ftpUrl, String userName, int port, String password, String directory,
			String fileName) throws IOException {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpUrl, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.deleteFile(fileName);// 删除远程文件
			return result;
		} catch (SocketException e) {
			throw e;
		} catch (IOException e) {
			throw new IOException("连接ftp服务器失败！", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	public static boolean makeDirecotory(String ftpUrl, String userName, int port, String password, String directory,
			String newDirectory) throws IOException {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpUrl, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.makeDirectory(newDirectory);// 创建新目录
			return result;
		} catch (SocketException e) {
			throw e;
		} catch (IOException e) {
			throw new IOException("连接ftp服务器失败！", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	public static boolean renameDirecotory(String ftpUrl, String userName, int port, String password, String directory,
			String oldDirectory, String newDirectory) throws IOException {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpUrl, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.rename(oldDirectory, newDirectory);
			return result;
		} catch (SocketException e) {
			throw e;
		} catch (IOException e) {
			throw new IOException("连接ftp服务器失败！", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	public static boolean removeDirecotory(String ftpUrl, String userName, int port, String password, String directory,
			String deldirectory) throws IOException {
		boolean result = false;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpUrl, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(directory);
			result = ftpClient.removeDirectory(deldirectory);// 删除目录
			return result;
		} catch (SocketException e) {
			throw e;
		} catch (IOException e) {
			throw new IOException("连接ftp服务器失败！", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	public static String[] list(String ftpUrl, String userName, int port, String password, String directory)
			throws IOException {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpUrl, port);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("utf-8");
			ftpClient.changeWorkingDirectory(directory);
			ftpClient.enterLocalPassiveMode();
			String[] list = ftpClient.listNames();
			return list;
		} catch (SocketException e) {
			throw e;
		} catch (IOException e) {
			throw new IOException("连接ftp服务器失败！", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

}