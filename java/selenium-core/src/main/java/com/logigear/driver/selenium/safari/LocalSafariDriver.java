package com.logigear.driver.selenium.safari;

import org.openqa.selenium.safari.SafariDriver;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.manager.BaseDriver;

public class LocalSafariDriver extends BaseDriver {

	public LocalSafariDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createWebDriver() {
		webDriver = new SafariDriver();
	}
}
