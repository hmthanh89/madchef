package pages.Pinterest.PinPage;

import com.logigear.control.common.imp.Image;
import com.logigear.control.common.imp.Label;
import com.logigear.control.common.imp.Link;

import data.Pinterest.Pin;
import pages.Pinterest.GeneralPage.GeneralPage;

public class PinPage extends GeneralPage {

	// Elements
	protected Link lnkPin = $(Link.class, "lnkPin");
	protected Label lblPinName = $(Label.class, "lblPinName");
	protected Image imgPin = $(Image.class, "imgPin");

	// Methods
	public String getPinName() {
		lblPinName.waitForDisplay();
		return lblPinName.getText();
	}

	public String getPinLink() {
		String strLink = lnkPin.getReference();
		if (strLink.endsWith("/")) {
			strLink = strLink.substring(0, strLink.length() - 1);
		}
		return strLink;
	}

	public String getPinDescription() {
		return imgPin.getAlt();
	}

	public boolean isPinCreated(String pinName, String pinLink, String pinDescription) {
		return (pinName.equals(getPinName()) && pinLink.equals(getPinLink()) && imgPin.isDisplayed()
				&& pinDescription.equals(getPinDescription()));
	}

	public boolean isPinCreated(Pin pin) {
		return isPinCreated(pin.getPinName(), pin.getLink(), pin.getNote());
	}
}
