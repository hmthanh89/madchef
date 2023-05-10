package pages.Pinterest.CreatePinPage;

import data.Pinterest.Pin;
import utils.common.Constants;

public class CreatePinPage_MOBILE extends CreatePinPage {

	@Override
	public void createPin(Pin pin) {
		if (!btnFirstPhoto.isDisplayed(Constants.LONG_TIME/2)) {
			btnCamera.waitForDisplay();
			btnCamera.click();
			btnCapturePhoto.waitForDisplay();
			btnCapturePhoto.click();
			btnSavePhoto.waitForDisplay();
			btnSavePhoto.click();
		} else {
			btnFirstPhoto.click();
			btnGalleryNext.click();
		}
		txtPinName.waitForDisplay();
		txtPinName.enter(pin.getPinName());
		txtPinDescription.enter(pin.getNote());
		btnCreatePinNext.click();
		lblDynamicBoard.setDynamicValue(pin.getBoardName());
		lblDynamicBoard.waitForDisplay();
		lblDynamicBoard.click();
	}
}
