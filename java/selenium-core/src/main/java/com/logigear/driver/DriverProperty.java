package com.logigear.driver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.logigear.helper.JsonHelper;

public class DriverProperty {

	private URL remoteUrl;
	private String platform;
	private String browserName;
	private DriverType driverType;
	private String driverExecutable;
	private RunningMode mode;
	private DesiredCapabilities capabilities;
	private List<String> arguments;
	private String provider;

	public DesiredCapabilities getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(String capabilities) {
		this.capabilities = JsonHelper.convertJsonToCapabilities(capabilities);
	}

	public String getDriverExecutable() {
		return driverExecutable;
	}

	public void setDriverExecutable(String driverExecutable) {
		this.driverExecutable = driverExecutable;
	}

	public RunningMode getMode() {
		return mode;
	}

	public void setMode(RunningMode mode) {
		this.mode = mode;
	}

	public boolean isRemoteMode() {
		if (getMode() != null && getMode() == RunningMode.Remote)
			return true;
		return false;
	}

	public URL getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) throws MalformedURLException, URISyntaxException {
		if (remoteUrl != null) {
			URI uri = new URI(remoteUrl);
			this.remoteUrl = uri.toURL();
		}
	}

	public DriverType getDriverType() {
		return driverType;
	}

	public void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}

	/**
	 * @return the arguments
	 */
	public List<String> getArguments() {
		return arguments;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(String arguments) {
		this.arguments = JsonHelper.convertJsonToArguments(arguments);
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
