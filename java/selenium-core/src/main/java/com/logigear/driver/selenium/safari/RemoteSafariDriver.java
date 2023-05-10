package com.logigear.driver.selenium.safari;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.manager.BaseDriver;

public class RemoteSafariDriver extends BaseDriver {

	public RemoteSafariDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createWebDriver() {
		SafariOptions ops = new SafariOptions();

		ops.merge(property.getCapabilities());

		webDriver = new RemoteWebDriver(property.getRemoteUrl(), ops);
	}
}
