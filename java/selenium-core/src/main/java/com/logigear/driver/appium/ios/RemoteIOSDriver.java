package com.logigear.driver.appium.ios;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.manager.BaseDriver;

import io.appium.java_client.ios.IOSDriver;

public class RemoteIOSDriver extends BaseDriver {

    public RemoteIOSDriver(DriverProperty property) {
        super(property);
    }

    @Override
    public void createWebDriver() {
        //this.webDriver = new IOSDriver<>(property.getRemoteUrl(), property.getCapabilities());
        this.webDriver = new IOSDriver(property.getRemoteUrl(), property.getCapabilities());
    }
}