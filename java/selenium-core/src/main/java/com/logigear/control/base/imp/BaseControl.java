package com.logigear.control.base.imp;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.logigear.control.base.IBaseControl;
import com.logigear.control.common.imp.Element;
import com.logigear.driver.manager.Driver;

public class BaseControl implements IBaseControl {

	private Logger logger = Logger.getLogger(Element.class);
	private String locator;
	private By byLocator;
	private String dynamicLocator;
	private BaseControl parent;

	public BaseControl(String locator) {
		this.locator = locator;
		this.dynamicLocator = locator;
		this.byLocator = getByLocator();
	}

	public BaseControl(By byLocator) {
		this.byLocator = byLocator;
	}

	public BaseControl(String locator, Object... args) {
		this.dynamicLocator = locator;
		this.locator = String.format(dynamicLocator, args);
		this.byLocator = getByLocator();
	}

	public BaseControl(BaseControl parent, String locator) {
		this.locator = locator;
		this.dynamicLocator = locator;
		this.byLocator = getByLocator();
		this.parent = parent;
	}

	public BaseControl(BaseControl parent, By byLocator) {
		this.byLocator = byLocator;
		this.parent = parent;
	}

	public BaseControl(BaseControl parent, String locator, Object... args) {
		this.dynamicLocator = locator;
		this.locator = String.format(dynamicLocator, args);
		this.byLocator = getByLocator();
		this.parent = parent;
	}


	private By getByLocator() {
		String body = this.locator.replaceAll("[\\w\\s]*=(.*)", "$1").trim();
		String type = this.locator.replaceAll("([\\w\\s]*)=.*", "$1").trim();
		switch (type) {
		case "css":
			return By.cssSelector(body);
		case "id":
			return By.id(body);
		case "link":
			return By.linkText(body);
		case "xpath":
			return By.xpath(body);
		case "text":
			return By.xpath(String.format("//*[contains(text(), '%s')]", body));
		case "name":
			return By.name(body);
		default:
			return By.xpath(locator);
		}
	}

	/**
	 * Set value for dynamic control.
	 * 
	 * @Example TextBox myTextBox = new TextBox("//input[@value='%s']"); </br>
	 *          myTextBox.setDynamicValue("example");
	 * @param args
	 */
	@Override
	public void setDynamicValue(Object... args) {
		this.locator = String.format(this.dynamicLocator, args);
		this.byLocator = getByLocator();
	}

	@Override
	public By getLocator() {
		return this.byLocator;
	}

	@Override
	public void scrollToView() {
		Driver.execJavaScript("arguments[0].scrollIntoView(true);", getElement());
	}

	@Override
	public WebElement getElement() {
		if (Driver.isWaitForAjax()) {
			Driver.waitForAjaxJQueryProcess();
		}
		if (parent != null)
			return parent.getElement().findElement(getLocator());
		return Driver.findElement(getLocator());
	}

	@Override
	public void setAttributeJS(String attributeName, String value) {
		try {
			logger.debug(String.format("Set attribute for %s", getLocator().toString()));
			Driver.execJavaScript(String.format("arguments[0].setAttribute('%s','%s');", attributeName, value),
					getElement());
		} catch (Exception e) {
			logger.error(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}

	}

	@Override
	public List<WebElement> getElements() {
		if (Driver.isWaitForAjax()) {
			Driver.waitForAjaxJQueryProcess();
		}
		if (parent != null)
			return parent.getElement().findElements(getLocator());
		return Driver.findElements(getLocator());
	}

	@Override
	public String getText() {
		try {
			logger.debug(String.format("Get text of element %s", getLocator().toString()));
			return getElement().getText();
		} catch (Exception e) {
			logger.error(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}

	}

	@Override
	public String getValue() {
		try {
			logger.debug(String.format("Get value of element %s", getLocator().toString()));
			return getElement().getAttribute("value");
		} catch (Exception e) {
			logger.error(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}

	}

	@Override
	public String getAttribute(String attributeName) {
		try {
			logger.debug(String.format("Get attribute '%s' of element %s", attributeName, getLocator().toString()));
			return getElement().getAttribute(attributeName);
		} catch (Exception e) {
			logger.error(String.format("Has error with control '%s': %s", getLocator().toString(), e.getMessage()));
			throw e;
		}

	}

	@Override
	public boolean isDisplayed() {
		try {
			logger.debug(String.format("is control displayed or not: %s", getLocator().toString()));
			return getElement().isDisplayed();
		} catch (Exception e) {
			logger.error(String.format("IsDisplayed: Has error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
			return false;
		}
	}

	@Override
	public boolean isDisplayed(int timeOutInSeconds) {
		try {
			waitForDisplay(timeOutInSeconds);
			return isDisplayed();
		} catch (Exception e) {
			logger.error(String.format("IsDisplayed: Has error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
			return false;
		}
	}

	@Override
	public boolean isEnabled() {
		try {
			logger.debug(String.format("is control enabled or not: %s", getLocator().toString()));
			return getElement().isEnabled();
		} catch (Exception e) {
			logger.error(String.format("IsEnabled: Has error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
			return false;
		}

	}

	@Override
	public boolean isSelected() {
		try {
			logger.debug(String.format("is control selected or not: %s", getLocator().toString()));
			return getElement().isSelected();
		} catch (Exception e) {
			logger.error(String.format("IsSelected: Has error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
			return false;
		}
	}

	@Override
	public void waitForDisplay() {
		waitForDisplay(Driver.getTimeOut());
	}

	@Override
	public void waitForDisplay(int timeOutInSeconds) {
		try {
			if (Driver.isWaitForAjax()) {
				Driver.waitForAjaxJQueryProcess();
			}
			logger.info(String.format("Wait for control display %s", getLocator().toString()));
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSeconds));

			if (parent != null)
				wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(getParent(), getLocator()));
			else
				wait.until(ExpectedConditions.presenceOfElementLocated(getLocator()));
		} catch (Exception e) {
			logger.error(String.format("WaitForDisplay: Has error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
		}
	}

	@Override
	public void waitForVisibility() {
		waitForVisibility(Driver.getTimeOut());
	}

	@Override
	public void waitForVisibility(int timeOutInSeconds) {
		try {
			if (Driver.isWaitForAjax()) {
				Driver.waitForAjaxJQueryProcess();
			}
			logger.info(String.format("Wait for control display %s", getLocator().toString()));
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSeconds));

			if (parent != null)
				wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(getParent(), getLocator()));
			else
				wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator()));
		} catch (Exception e) {
			logger.error(String.format("WaitForDisplay: Has error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
		}
	}

	@Override
	public void waitForInvisibility() {
		waitForInvisibility(Driver.getTimeOut());
	}

	@Override
	public void waitForInvisibility(int timeOutInSeconds) {
		try {
			if (Driver.isWaitForAjax()) {
				Driver.waitForAjaxJQueryProcess();
			}
			logger.info(String.format("Wait for control display %s", getLocator().toString()));
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSeconds));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(getLocator()));
		} catch (Exception e) {
			logger.error(String.format("WaitForDisplay: Has error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
		}
	}

	@Override
	public void waitForTextToBePresent(String text, int timeOutInSecond) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSecond));
			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return getElement().getText().contains(text);
				}
			});
		} catch (Exception e) {
			logger.error(String.format("waitForTextToBePresent: Has error with control '%s': %s",
					getLocator().toString(), e.getMessage()));
		}

	}

	@Override
	public void waitForTextToBeNotPresent(String text, int timeOutInSecond) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSecond));
			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return !getElement().getText().contains(text);
				}
			});
		} catch (Exception e) {
			logger.error(String.format("waitForTextToBeNotPresent: Has error with control '%s': %s",
					getLocator().toString(), e.getMessage()));
		}

	}

	@Override
	public void waitForElementNotPresent(int timeOutInSecond) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSecond));
			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return (getElements().size() < 1);
				}
			});
		} catch (Exception e) {
			logger.error(String.format("waitForElementNotPresent: Has error with control '%s': %s",
					getLocator().toString(), e.getMessage()));
		}

	}

	@Override
	public void waitForValueNotPresentInAttribute(String attribute, String value, int timeOutInSecond) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSecond));
			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return !getElement().getAttribute(attribute).contains(value);
				}
			});
		} catch (Exception e) {
			logger.error(String.format("waitForValueNotPresentInAttribute: Has error with control '%s': %s",
					getLocator().toString(), e.getMessage()));
		}

	}

	@Override
	public void waitForValuePresentInAttribute(String attribute, String value, int timeOutInSecond) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSecond));
			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return getElement().getAttribute(attribute).contains(value);
				}
			});
		} catch (Exception e) {
			logger.error(String.format("waitForValuePresentInAttribute: Has error with control '%s': %s",
					getLocator().toString(), e.getMessage()));
		}
	}

	@Override
	public void waitForElementEnabled(int timeOutInSecond) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSecond));
			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return getElement().isEnabled();
				}
			});
		} catch (Exception e) {
			logger.error(String.format("waitForElementEnabled: Has error with control '%s': %s",
					getLocator().toString(), e.getMessage()));
		}

	}

	@Override
	public void waitForElementEnabled() {
		waitForElementEnabled(Driver.getTimeOut());
	}

	@Override
	public void waitForElementDisabled(int timeOutInSecond) {
		try {
			WebDriverWait wait = new WebDriverWait(Driver.getWebDriver(), Duration.ofSeconds(timeOutInSecond));
			wait.until(new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					if (getElements().size() == 0)
						return true;
					return !getElement().isEnabled();
				}
			});
		} catch (Exception e) {
			logger.error(String.format("waitForElementDisabled: Has error with control '%s': %s",
					getLocator().toString(), e.getMessage()));
		}

	}

	@Override
	public void waitForElementDisabled() {
		waitForElementDisabled(Driver.getTimeOut());
	}

	@Override
	public void waitForPresent() {
		waitForPresent(Driver.getTimeOut());
	}

	@Override
	public void waitForPresent(int timeOutInSecond) {
		try {
			long startTime = System.currentTimeMillis() / 1000;
			long currentTime = System.currentTimeMillis() / 1000;
			while ((currentTime - startTime) < timeOutInSecond) {
				if (isDisplayed()) {
					break;
				}
				currentTime = System.currentTimeMillis() / 1000;
			}
		} catch (Exception e) {
			logger.error(String.format("waitForPresent: Has error with control '%s': %s", getLocator().toString(),
					e.getMessage()));
		}
	}

	@Override
	public void moveTo() {
		Actions actions = new Actions(Driver.getWebDriver());
		actions.moveToElement(getElement()).build().perform();
	}

	@Override
	public void mouseHoverJScript() {
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', 				true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		Driver.execJavaScript(mouseOverScript, getElement());

	}

	@Override
	public String getClassName() {
		return getAttribute("class");
	}

	@Override
	public String getLocatorAsString() {
		return this.locator;
	}

	@Override
	public void submit() {
		getElement().submit();
	}

	@Override
	public String getTagName() {
		return getElement().getTagName();
	}

	public void focus() {
		Driver.execJavaScript("arguments[0].focus();", getElement());
	}

	private WebElement getParent() {
		return parent.getElement();
	}

}
