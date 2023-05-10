package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import data.Pinterest.Board;
import data.Pinterest.Pin;
import pages.Pinterest.BoardPage.BoardPage;
import pages.Pinterest.CreateBoardPage.CreateBoardPage;
import pages.Pinterest.CreatePinPage.CreatePinPage;
import pages.Pinterest.GeneralPage.GeneralPage;
import pages.Pinterest.LoginPage.LoginPage;
import pages.Pinterest.PinPage.PinPage;
import pages.Pinterest.ProfilePage.ProfilePage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Constants;
import utils.helper.Logger;

public class CreatePinTest extends TestBase {

	LoginPage loginPage;
	GeneralPage generalPage;
	ProfilePage profilePage;
	CreateBoardPage createBoardPage;
	CreatePinPage createPinPage;
	BoardPage boardPage;
	PinPage pinPage;

	@Test(description = "User is able to create a new Pin")
	public void TC01_Create_New_Pin() {
		// Create Board
		Board board = new Board();

		// Create Pin
		Pin pin = new Pin();
		pin.setBoardName(board.getBoardName());
		
		Logger.info("1. Login to Pinterest");
		loginPage.login(Constants.EMAIL, Constants.PASSWORD);

		// Accept allow notification alert
		Common.acceptAlert();

		Logger.info("2. Go to Profile page");
		generalPage.goToProfilePage();

		Logger.info("3. Create a new Board");
		profilePage.goToCreateBoardPage();
		createBoardPage.createBoard(board);

		Logger.info("4. Create a new Pin in side the Board");
		boardPage.goToCreatePinPage();
		createPinPage.createPin(pin);

		Logger.verify("VP. Verify the Pin is created successfully.");
		Assert.assertTrue(pinPage.isPinCreated(pin), "The Pin is not created correctly.");
	}
}
