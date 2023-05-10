package pages.Pinterest.PinPage;

import data.Pinterest.Pin;
import pages.Pinterest.BoardPage.BoardPage_MOBILE;

public class PinPage_MOBILE extends PinPage {

	BoardPage_MOBILE boardPage = new BoardPage_MOBILE();

	@Override
	public boolean isPinCreated(Pin pin) {
		return boardPage.isPinDisplayed(pin.getNote());
	}
}
