package pages.Pinterest.CreateBoardPage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.logigear.control.common.imp.Button;
import com.logigear.control.common.imp.CheckBox;
import com.logigear.control.common.imp.Image;
import com.logigear.control.common.imp.TextBox;

import data.Pinterest.Board;
import pages.Pinterest.GeneralPage.GeneralPage;
import utils.common.Common;
import utils.helper.Logger;

public class CreateBoardPage extends GeneralPage {

	// Dynamic-elements
	private String strImgPin = "(//div[@aria-label='add pins to empty board modal']//img)[%s]";

	protected Image imgPin(int index) {
		return new Image(strImgPin, index);
	}

	// Elements
	protected TextBox txtBoardName = $(TextBox.class, "txtBoardName");
	protected CheckBox chkboxVisibility = $(CheckBox.class, "chkboxVisibility");
	protected Button btnCreate = $(Button.class, "btnCreate");
	protected Button btnDone = $(Button.class, "btnDone");
	protected Image imgPins = $(Image.class, "imgPins");
	
	protected Button btnsavePin = $(Button.class, "btnsavePin");

	// Methods

	public void submitBoardGeneralInfo(String boardName, boolean visibility) {
		txtBoardName.waitForVisibility();
		txtBoardName.enter(boardName);
		chkboxVisibility.set(visibility);
		btnCreate.click();
		btnDone.waitForVisibility();
	}

	public void createBoard(String boardName, boolean visibility) {
		submitBoardGeneralInfo(boardName, visibility);
		btnDone.click();
	}

	public List<String> createBoard(String boardName, boolean visibility, int pin) {
		List<String> setPins = new ArrayList<String>();

		// Input general info to create a board
		submitBoardGeneralInfo(boardName, visibility);

		// Select pins
		imgPins.waitForDisplay();
		int totalPins = imgPins.getElements().size();
		if (totalPins <= 0) {
			Logger.warning("This test case failed because there are no pins");
			throw new IllegalArgumentException("This test case failed because there are no pins");
		} else {
			if (totalPins < pin) {
				pin = totalPins;
			}
			Set<Integer> selectedPins = new HashSet<Integer>();
			for (int i = 0; i < pin; i++) {
				int idx = Common.getRandomNumber(1, totalPins);
				while (selectedPins.contains(idx)) {
					idx = Common.getRandomNumber(1, totalPins);
				}
				selectedPins.add(idx);
				setPins.add(imgPin(idx).getSource());
				imgPin(idx).moveTo();
				btnsavePin.waitForDisplay();
				btnsavePin.click();
			}
		}
		// Complete create a board
		btnDone.click();
		return setPins;
	}

	public void createBoard(Board board) {
		createBoard(board.getBoardName(), board.isVisibility());
	}

	public List<String> createBoardWithPins(Board board) {
		return createBoard(board.getBoardName(), board.isVisibility(), board.getPins());
	}

}
