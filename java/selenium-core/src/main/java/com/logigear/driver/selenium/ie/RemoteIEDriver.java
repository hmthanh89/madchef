package com.logigear.driver.selenium.ie;

import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.manager.BaseDriver;

public class RemoteIEDriver extends BaseDriver {

	public RemoteIEDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createWebDriver() {
		InternetExplorerOptions ops = new InternetExplorerOptions();

		ops.merge(property.getCapabilities());

		webDriver = new RemoteWebDriver(property.getRemoteUrl(), ops);
	}

}
