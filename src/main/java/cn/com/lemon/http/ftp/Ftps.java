package cn.com.lemon.http.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import static cn.com.lemon.base.Preassert.checkNotNull;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public final class Ftps {
	private static final Logger LOG = Logger.getLogger(Ftps.class.getName());
	private static final FTPClient ftpClient = new FTPClient();

	protected String url;
	protected int port;
	protected String appkey;
	protected String appSecret;
	protected boolean connected = false;
	
	public static void main(String[] args) {
		new Ftps("192.168.2.39", 21);
	}

	public boolean write(String appkey, String appSecret, String path, String name, InputStream stream) {
		checkNotNull(appkey);
		checkNotNull(appSecret);
		if (connected) {
			try {
				if (ftpClient.login(appkey, appSecret)) {
					ftpClient.changeWorkingDirectory(path);
					ftpClient.storeFile(name, stream);
					stream.close();
					ftpClient.logout();
					return true;
				} else {
					LOG.debug("===== [" + appkey + "]  is't exist or password error=====");
					return false;
				}
			} catch (IOException e) {
				LOG.debug("===== [" + appkey + "]  Ftp Login is  an  I/O error=====");
				return false;
			} finally {
				connected = false;
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
						connected = false;
					} catch (IOException e) {
						LOG.debug("=====Ftp disconnect is exist error=====");
					}
				}
			}
		}
		return false;
	}

	{
		try {
			ftpClient.connect(url, port);
			connected = true;
		} catch (SocketException e) {
			LOG.debug("===== [" + url + "] [" + port + "] Ftp connected is  an  socket error=====");
			e.printStackTrace();
		} catch (IOException e) {
			LOG.debug("===== [" + url + "] [" + port + "] Ftp connected is  an I/O error=====");
			e.printStackTrace();
		}
	}

	public Ftps(String url, int port) {
		this.url = url;
		this.port = port;
	}
}
