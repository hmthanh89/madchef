package pages.Pinterest.CreatePinPage;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.TextBox;

import data.Pinterest.Pin;
import pages.Pinterest.GeneralPage.GeneralPage;

public class CreatePinPage extends GeneralPage {

	// Elements
	protected TextBox txtPinName = $(TextBox.class, "txtPinName");
	protected TextBox txtPinDescription = $(TextBox.class, "txtPinDescription");
	protected TextBox txtPinLink = $(TextBox.class, "txtPinLink");
	protected TextBox txtPinUpload = $(TextBox.class, "txtPinUpload");
	protected Button btnSave = $(Button.class, "btnSave");
	protected Button btnSeeItNow = $(Button.class, "btnSeeItNow");
	protected Button btnCamera = $(Button.class, "btnCamera");
	protected Button btnCapturePhoto = $(Button.class, "btnCapturePhoto");
	protected Button btnSavePhoto = $(Button.class, "btnSavePhoto");
	protected Button btnFirstPhoto = $(Button.class, "btnFirstPhoto");
	protected Button btnGalleryNext = $(Button.class, "btnGalleryNext");
	protected Button btnCreatePinNext = $(Button.class, "btnCreatePinNext");
	protected Label lblDynamicBoard = $(Label.class, "lblDynamicBoard");

	// Methods
	public void createPin(String pinName, String pinDescription, String pinLink, String pinFilePath) {
		txtPinName.waitForVisibility();

		txtPinUpload.enter(pinFilePath);
		txtPinName.enter(pinName);
		txtPinDescription.enter(pinDescription);
		txtPinLink.enter(pinLink);

		btnSave.waitForDisplay();
		btnSave.click();
		btnSeeItNow.waitForDisplay();
		btnSeeItNow.click();
	}

	public void createPin(Pin pin) {
		createPin(pin.getPinName(), pin.getNote(), pin.getLink(), pin.getImage());
	}
}
