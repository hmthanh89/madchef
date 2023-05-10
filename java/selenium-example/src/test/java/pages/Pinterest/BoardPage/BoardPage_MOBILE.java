package pages.Pinterest.BoardPage;

import java.util.ArrayList;
import java.util.List;
import com.logigear.control.common.imp.Element;
import com.logigear.control.common.imp.Image;
import com.logigear.control.common.imp.Label;
import com.logigear.driver.manager.Driver;
import utils.common.Constants;
import utils.helper.Logger;

public class BoardPage_MOBILE extends BoardPage {

	protected Label lblMoreIdeas = $(Label.class, "lblMoreIdeas");
	protected Label lblPins = $(Label.class, "lblPins");
	protected Image imgDynamicPinItem = $(Image.class, "imgDynamicPinItem");
	protected Element eleDisplayedPin =  $(Element.class, "eleDisplayedPin");
	protected Element eleMoreIdeasPinItem =  $(Element.class, "eleMoreIdeasPinItem");
	protected Element eleSelectedPin =  $(Element.class, "eleSelectedPin");
	protected Element eleSelectedPinItem =  $(Element.class, "eleSelectedPinItem");
	protected Element eleDynamicAddPin =  $(Element.class, "eleDynamicAddPin");

	@Override
	public void goToCreatePinPage() {
		if (!txtSearch.isDisplayed()) {
			lblMoreIdeas.waitForDisplay();
			lblMoreIdeas.click();
			Driver.delay(2);
			lblPins.click();
		}
		btnAdd.setDynamicValue(2);
		if (!btnAdd.isDisplayed()) {
			btnAdd.setDynamicValue(1);
		}
		btnAdd.click();
		eleCreatePin.waitForVisibility();
		eleCreatePin.click();
	}

	public boolean isPinDisplayed(String description) {
		imgDynamicPinItem.setDynamicValue(description);
		return imgDynamicPinItem.isDisplayed(Constants.SHORT_TIME / 2);
	}

	public List<String> selectPins() {
		List<String> setPins = new ArrayList<String>();
		String contentDESC;

		lblMoreIdeas.waitForDisplay();
		lblMoreIdeas.click();
		eleDisplayedPin.waitForDisplay();
		int numOfDisplayedPins = eleDisplayedPin.getElements().size();

		if (numOfDisplayedPins <= 0) {
			Logger.warning("This test case failed because there are no pins");
			throw new IllegalArgumentException("This test case failed because there are no pins");
		} else {
			swipeVertical(Constants.VERTICAL_PERCENTAGE_START_POINT, -300, Constants.HORIZONTAL_PERCENTAGE_START_POINT);
			for (int i = 1; i <= numOfDisplayedPins; i++) {
				eleDynamicAddPin.setDynamicValue(i);
				eleMoreIdeasPinItem.setDynamicValue(i);
				contentDESC = eleMoreIdeasPinItem.getAttribute("content-desc");
				contentDESC = contentDESC.split(", Description:")[0];
				setPins.add(contentDESC);
				eleDynamicAddPin.click();
			}
			lblPins.click();
		}
		return setPins;
	}

	@Override
	public List<String> getPinsSource() {
		List<String> pinsSource = new ArrayList<>();
		String contentDESC;
		eleSelectedPin.waitForDisplay();
		int selectedPins = eleSelectedPin.getElements().size();
		for (int i = 1; i <= selectedPins; i++) {
			eleSelectedPinItem.setDynamicValue(i);
			contentDESC = eleSelectedPinItem.getAttribute("content-desc");
			pinsSource.add(contentDESC);
		}
		return pinsSource;
	}
}
