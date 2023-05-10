package com.logigear.control.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface IBaseControl {

	WebElement getElement();

	List<WebElement> getElements();

	void setAttributeJS(String attributeName, String value);

	String getText();

	String getValue();
	
	String getLocatorAsString();
	
	By getLocator();

	void setDynamicValue(Object... args);

	String getAttribute(String attributeName);

	boolean isDisplayed();

	boolean isEnabled();

	boolean isSelected();

	void waitForDisplay();

	void waitForDisplay(int timeOutInSeconds);
	
	void waitForVisibility();
	
	void waitForVisibility(int timeOutInSeconds);
	
	void waitForInvisibility();
	
	void waitForInvisibility(int timeOutInSeconds);

	void submit();

	void waitForTextToBePresent(String text, int timeOutInSecond);

	void waitForTextToBeNotPresent(String text, int timeOutInSecond);

	void waitForElementNotPresent(int timeOutInSecond);

	void waitForValueNotPresentInAttribute(String attribute, String value,
			int timeOutInSecond);

	void waitForValuePresentInAttribute(String attribute, String value,
			int timeOutInSecond);

	void waitForElementEnabled(int timeOutInSecond);

	void waitForElementEnabled();

	void waitForElementDisabled(int timeOutInSecond);

	void waitForElementDisabled();

	boolean isDisplayed(int timeOutInSeconds);

	void scrollToView();

	void waitForPresent();

	void waitForPresent(int timeOutInSecond);

	void moveTo();

	String getClassName();

	void mouseHoverJScript();
	
	void focus();
	
	String getTagName();
}
