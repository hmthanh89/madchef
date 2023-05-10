package pages.Pinterest.ProfilePage;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.Element;
import pages.Pinterest.GeneralPage.GeneralPage;

public class ProfilePage extends GeneralPage {

	// Elements
	protected Button btnAdd = $(Button.class, "btnAdd");
	protected Element eleCreateBoard = $(Element.class, "eleCreateBoard");
	protected Element eleCreatePin = $(Element.class, "eleCreatePin");

	// Methods
	public void goToCreateBoardPage() {
		btnAdd.waitForElementClickable();
		btnAdd.click();
		eleCreateBoard.waitForVisibility();
		eleCreateBoard.click();
	}
}
