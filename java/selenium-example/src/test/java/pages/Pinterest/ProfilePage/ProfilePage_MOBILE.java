package pages.Pinterest.ProfilePage;

import com.logigear.control.common.imp.Element;
import utils.common.Constants;

public class ProfilePage_MOBILE extends ProfilePage {

	// Elements
	protected Element elescrollControl = $(Element.class, "elescrollControl");

	// Methods
	public void goToCreateBoardPage() {
		elescrollControl.waitForDisplay();
		swipeToElementByVertical(Constants.VERTICAL_PERCENTAGE_START_POINT, 200,
				Constants.HORIZONTAL_PERCENTAGE_START_POINT, btnAdd.getLocatorAsString());
		btnAdd.waitForElementClickable();
		btnAdd.click();
		eleCreateBoard.waitForVisibility();
		eleCreateBoard.click();
	}
}
