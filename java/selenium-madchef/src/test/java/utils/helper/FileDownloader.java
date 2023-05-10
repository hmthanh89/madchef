package utils.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.WebDriver;

public class FileDownloader {

	private String _DownloadPath = System.getProperty("java.io.tmpdir");
	private WebDriver _Driver;

	/**
	 * @return the downloadPath
	 */
	public String getDownloadPath() {
		return _DownloadPath;
	}

	public FileDownloader(WebDriver driver) {
		this._Driver = driver;
	}

	/**
	 * @param downloadPath
	 *            the downloadPath to set
	 */
	public void setDownloadPath(String downloadPath) {
		this._DownloadPath = downloadPath;
	}

	public String download(String url, String downloadedFileExtension) {
		Set<org.openqa.selenium.Cookie> cookies = _Driver.manage().getCookies();
		File file = null;
		try {
			CookieStore cookieStore = new BasicCookieStore();
			for (org.openqa.selenium.Cookie seleniumCookie : cookies) {
				BasicClientCookie basicClientCookie = new BasicClientCookie(
						seleniumCookie.getName(), seleniumCookie.getValue());
				basicClientCookie.setDomain(seleniumCookie.getDomain());
				basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
				basicClientCookie.setPath(seleniumCookie.getPath());
				cookieStore.addCookie(basicClientCookie);
			}

			HttpClient client = HttpClientBuilder.create()
					.setDefaultCookieStore(cookieStore).build();
			HttpGet request = new HttpGet(url);

			HttpResponse resp = client.execute(request);
			if (resp != null && resp.getStatusLine().getStatusCode() == 200) {
				BufferedInputStream bis = new BufferedInputStream(resp
						.getEntity().getContent());
				file = new File(getDownloadPath() + UUID.randomUUID()
						+ downloadedFileExtension);
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(file));
				int inByte;
				while ((inByte = bis.read()) != -1) {
					bos.write(inByte);
				}
				bis.close();
				bos.close();
				return file.getAbsolutePath();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
}
