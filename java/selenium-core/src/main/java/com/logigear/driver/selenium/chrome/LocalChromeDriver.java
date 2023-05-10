package com.logigear.driver.selenium.chrome;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.manager.BaseDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LocalChromeDriver extends BaseDriver {

	public LocalChromeDriver(DriverProperty property) {
		super(property);
	}

	@Override
	public void createWebDriver() {
		
		String driverVerion = (String) property.getCapabilities().getCapability("driverVersion");
		WebDriverManager.chromedriver().browserVersion(driverVerion).setup();
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--start-maximized");

		ops.addArguments(property.getArguments());
		ops.merge(property.getCapabilities());
		
		webDriver = new ChromeDriver(ops);
		
		/*
		 * WebDriverManager.chromedriver().setup();
		 * 
		 * ChromeOptions options = new ChromeOptions();
		 * options.addArguments("--start-maximized"); ChromeDriver driver = new
		 * ChromeDriver(options);
		 * 
		 * //webDriver = new ChromeDriver(options);
		 * driver.get("https://www.google.com/");
		 */

	}
}
