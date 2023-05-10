package com.logigear.control.common.imp;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.google.common.base.Stopwatch;
import com.logigear.control.base.imp.BaseControl;
import com.logigear.control.base.imp.Clickable;
import com.logigear.control.common.IVideo;
import com.logigear.driver.manager.Driver;

public class Video extends Clickable implements IVideo {
	private Logger logger = Logger.getLogger(Video.class);

	public Video(String locator) {
		super(locator);
	}

	public Video(By locator) {
		super(locator);
	}

	public Video(String locator, Object... value) {
		super(locator, value);
	}

	public Video(BaseControl parent, String locator) {
		super(parent, locator);
	}

	public Video(BaseControl parent, By locator) {
		super(parent, locator);
	}

	public Video(BaseControl parent, String locator, Object... value) {
		super(parent, locator, value);
	}

	private int getReadyState() {
		Object value = Driver.execJavaScript("return arguments[0].readyState;", getElement());
		if (value == null)
			return 0;
		return Integer.parseInt(value.toString());
	}

	@Override
	public void play() {
		try {
			logger.debug(String.format("Play video at %s", getLocator().toString()));
			Driver.execJavaScript("arguments[0].play();", getElement());
		} catch (Exception e) {
			logger.error(
					String.format("Has error when playing video '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}

	@Override
	public void pause() {
		try {
			logger.debug(String.format("Pause video at %s", getLocator().toString()));
			Driver.execJavaScript("arguments[0].pause();", getElement());
		} catch (Exception e) {
			logger.error(
					String.format("Has error when pausing video '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}

	@Override
	public void mute() {
		try {
			logger.debug(String.format("Mute video at %s", getLocator().toString()));
			Driver.execJavaScript("arguments[0].muted = true;", getElement());
		} catch (Exception e) {
			logger.error(String.format("Has error when mute video '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}

	@Override
	public void unmute() {
		try {
			logger.debug(String.format("Unmute video at %s", getLocator().toString()));
			Driver.execJavaScript("arguments[0].muted = false;", getElement());
		} catch (Exception e) {
			logger.error(
					String.format("Has error when unmuting video '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}
	}

	@Override
	public double currentTime() {
		try {
			logger.debug(String.format("Get video's current time at %s", getLocator().toString()));
			Object value = Driver.execJavaScript("return arguments[0].currentTime;", getElement());
			if (value == null) {
				logger.error(String.format("Couldn't get video's current time at '%s': %s", getLocator().toString()));
				return -1;
			}
			return Double.parseDouble(value.toString());
		} catch (Exception e) {
			logger.error(String.format("Has error when getting video's current time at '%s': %s",
					getLocator().toString(), e.getMessage()));
			throw e;
		}
	}

	@Override
	public double duration() {
		try {
			logger.debug(String.format("Get video's duration at %s", getLocator().toString()));
			Object value = Driver.execJavaScript("return arguments[0].duration;", getElement());
			if (value == null) {
				logger.error(String.format("Couldn't get video's duration at '%s': %s", getLocator().toString()));
				return -1;
			}
			return Double.parseDouble(value.toString());
		} catch (Exception e) {
			logger.error(String.format("Has error when getting video's duration at '%s': %s", getLocator().toString(),
					e.getMessage()));
			throw e;
		}
	}

	@Override
	public void waitForVideoLoaded() {
		waitForVideoLoaded(Driver.getTimeOut());
	}

	@Override
	public void waitForVideoLoaded(int timeOutInSeconds) {
		try {
			if (Driver.isWaitForAjax()) 
				Driver.waitForAjaxJQueryProcess();

			logger.debug(String.format("Wait for video loaded %s", getLocator().toString()));
			Stopwatch sw = Stopwatch.createStarted();
			while (getReadyState() < 4 && sw.elapsed(TimeUnit.SECONDS) <= (long) timeOutInSeconds);
		} catch (Exception e) {
			logger.error(String.format("Has error when waiting for video loaded '%s': %s", getLocator().toString(),
					e.getMessage()));
			throw e;
		}
	}

	@Override
	public void waitForVideoPlayed() {
		waitForVideoPlayed(Driver.getTimeOut());
	}

	@Override
	public void waitForVideoPlayed(int timeOutInSeconds) {
		try {
			if (Driver.isWaitForAjax()) 
				Driver.waitForAjaxJQueryProcess();

			logger.debug(String.format("Wait for video played %s", getLocator().toString()));
			Stopwatch sw = Stopwatch.createStarted();
			while (currentTime() <= 0 && sw.elapsed(TimeUnit.SECONDS) <= (long) timeOutInSeconds);
		} catch (Exception e) {
			logger.error(String.format("Has error when waiting for video played '%s': %s", getLocator().toString(),
					e.getMessage()));
			throw e;
		}
	}

	
	@Override
	public void waitForVideoEnded(int timewait) {
		try {
			if (Driver.isWaitForAjax()) 
				Driver.waitForAjaxJQueryProcess();

			logger.debug(String.format("Wait for video ended %s in %s", getLocator().toString(), timewait));
			Stopwatch sw = Stopwatch.createStarted();
			while (getReadyState() > 0 && sw.elapsed(TimeUnit.SECONDS) <= (long) timewait);
		} catch (Exception e) {
			logger.error(String.format("Has error when waiting for video ended '%s' in %s: %s", getLocator().toString(), timewait,
					e.getMessage()));
			throw e;
		}
	}
}
