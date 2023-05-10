package com.logigear.helper;

import java.io.FileReader;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.logigear.driver.DriverProperty;
import com.logigear.driver.DriverType;
import com.logigear.driver.RunningMode;

public class BrowserSettingHelper {

	public static DriverProperty getDriverProperty(String file, String sectionName, String testCaseName) {
		DriverProperty property = new DriverProperty();
		
		try {
			Ini ini = new Ini(new FileReader(file));
			Section section = ini.get(sectionName);
			if (section == null) {
				throw new Exception(String.format("Cannot find '%s' in file '%s'", sectionName, file));
			}
			String mode = section.get("mode");
			String platform = section.get("platform");
			String driverType = section.get("driver");
			String browserName = driverType.toLowerCase();
			String driverExecutable = section.get("executable");
			String remoteUrl = section.get("remoteUrl");
			String capabilities = section.get("capabilities");
			String args = section.get("arguments");
			String provider = section.get("provider");
			
			property.setDriverExecutable(driverExecutable);
			property.setDriverType(converStringToDriverType(driverType));
			property.setPlatform(platform);
			property.setBrowserName(browserName);
			property.setRemoteUrl(remoteUrl);
			property.setMode(converStringToRunningMode(mode));
			property.setCapabilities(addTestCaseName(capabilities, testCaseName));
			property.setArguments(args);
			property.setProvider(provider);
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return property;
	}

	private static String addTestCaseName(String capabilities, String testCaseName) {
		if (capabilities == null)
			return null;

		StringBuffer buf = new StringBuffer(capabilities);
		buf.insert(1, "\"name\":\"" + testCaseName + "\",");
		return buf.toString();
	}

	private static RunningMode converStringToRunningMode(String mode) throws Exception {
		try {
			return RunningMode.valueOf(mode);
		} catch (Exception e) {
			throw new Exception(String.format("Don't allow the '%s'. Please use %s for your configuration", mode,
					RunningMode.asString()));
		}
	}

	private static DriverType converStringToDriverType(String type) throws Exception {
		try {
			return DriverType.valueOf(type);
		} catch (Exception e) {
			throw new Exception(String.format("Don't allow the '%s'. Please use %s for your configuration", type,
					DriverType.asString()));
		}
	}

}
