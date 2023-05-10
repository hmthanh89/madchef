package com.logigear.driver.appium.android;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.manager.BaseDriver;

import io.appium.java_client.android.AndroidDriver;

public class RemoteAndroidDriver extends BaseDriver {

    public RemoteAndroidDriver(DriverProperty property) {
        super(property);
    }

    @Override
    public void createWebDriver() {
        //this.webDriver = new AndroidDriver<>(property.getRemoteUrl(), property.getCapabilities());
    	this.webDriver = new AndroidDriver(property.getRemoteUrl(), property.getCapabilities());
    }
}