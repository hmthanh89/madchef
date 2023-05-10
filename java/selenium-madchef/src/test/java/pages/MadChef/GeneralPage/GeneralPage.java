package pages.MadChef.GeneralPage;

import static java.time.Duration.ofSeconds;
import org.openqa.selenium.Dimension;
import com.logigear.control.common.IElement;
import com.logigear.control.common.imp.Element;
import com.logigear.driver.manager.Driver;
import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import utils.base.PageFactory;
import utils.helper.Logger;

public class GeneralPage extends PageFactory {

	// Elements
	protected Element eleProfile = $(Element.class, "GeneralPage.eleProfile");

	// Methods
	public void goToProfilePage() {
		eleProfile.waitForDisplay();
		eleProfile.click();
	}
	
	public void swipeToElementByVertical(double startAtVerticalPercentage, int stepUnits, double anchorhorizontalPercentage, String locator){
		/* This method is used for mobile only
		 * startAtVerticalPercentage: Enter the percent compare to height of screen. E.g. 40
		 *anchorhorizontalPercentage: Enter the percent compare to width of screen. E.g. 40
		 *locator: Enter locator as string. E.g. btnAdd.getLocatorAsString()
		 *stepUnits: Enter the unit of pixel Swipe Up. E.g. 200 or  -200
		 */
		
		IElement iLocator = new Element(locator);
		Boolean isFoundElement;
		
		isFoundElement = iLocator.isDisplayed();
		Dimension size = Driver.getWebDriver().manage().window().getSize();
		int anchor = (int) (size.width * anchorhorizontalPercentage/100);
		int startPoint = (int) (size.height * startAtVerticalPercentage/100);
		int endPoint = startPoint + stepUnits;
		
		while (!isFoundElement) {
			if (endPoint>size.height) {
				Logger.warning("Cannot find element !!!");
				break;
			}
			//TouchAction<?> action = new TouchAction<>((AppiumDriver<?>) Driver.getWebDriver());
			//action.press(PointOption.point(anchor, startPoint)).waitAction(WaitOptions.waitOptions(ofSeconds(1)))
			//		.moveTo(PointOption.point(anchor, endPoint)).release().perform();
			isFoundElement = iLocator.isDisplayed();
			startPoint = endPoint;
			endPoint = startPoint + stepUnits;
		}
	}
	
	public void swipeVertical(double startAtVerticalPercentage, int stepUnits, double anchorhorizontalPercentage){
		/* This method is used for mobile only
		 * startAtVerticalPercentage: Enter the percent compare to height of screen. E.g. 40
		 * anchorhorizontalPercentage: Enter the percent compare to width of screen. E.g. 40
		 * stepUnits: Enter the unit of pixel Swipe Up. E.g. 200 or  -200
		 */
		
		Dimension size = Driver.getWebDriver().manage().window().getSize();
		int anchor = (int) (size.width * anchorhorizontalPercentage/100);
		int startPoint = (int) (size.height * startAtVerticalPercentage/100);
		int endPoint = startPoint + stepUnits;
		//TouchAction<?> action = new TouchAction<>((AppiumDriver<?>) Driver.getWebDriver());
		//action.press(PointOption.point(anchor, startPoint)).waitAction(WaitOptions.waitOptions(ofSeconds(1)))
		//			.moveTo(PointOption.point(anchor, endPoint)).release().perform();
	}
}
