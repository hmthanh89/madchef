package tests.web;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import data.Pinterest.Board;
import pages.Pinterest.BoardPage.BoardPage;
import pages.Pinterest.CreateBoardPage.CreateBoardPage;
import pages.Pinterest.GeneralPage.GeneralPage;
import pages.Pinterest.LoginPage.LoginPage;
import pages.Pinterest.ProfilePage.ProfilePage;
import tests.TestBase;
import utils.common.Common;
import utils.common.Constants;
import utils.helper.Logger;

public class CreateBoardTest extends TestBase {

	LoginPage loginPage;
	GeneralPage generalPage;
	ProfilePage profilePage;
	CreateBoardPage createBoardPage;
	BoardPage boardPage;
	Board board;

	@Test(description = "User is able to create a new Board without any Pin")
	public void TC01_Create_New_Board() {
		// Create Board
		board = new Board();

		Logger.info("1. Login to Pinterest");
		loginPage.login(Constants.EMAIL, Constants.PASSWORD);

		// Accept allow notification alert
		Common.acceptAlert();

		Logger.info("2. Go to Profile page");
		generalPage.goToProfilePage();

		Logger.info("3. Create a new Board");
		profilePage.goToCreateBoardPage();
		createBoardPage.createBoard(board);

		Logger.verify("VP. Verify the Board is created.");
		Assert.assertEquals(boardPage.getBoardName(), board.getBoardName(), "Board is not created with expected name.");
	}

	@Test(description = "User is able to create a new Board with some Pins")
	public void TC02_Create_New_Board_With_Pins() {
		// Create Board
		board = new Board();

		Logger.info("1. Login to Pinterest");
		loginPage.login(Constants.EMAIL, Constants.PASSWORD);

		// Accept allow notification alert
		Common.acceptAlert();

		Logger.info("2. Go to Profile page");
		generalPage.goToProfilePage();

		Logger.info("3. Create a new Board");
		profilePage.goToCreateBoardPage();

		Logger.info("4. Select some Pins");
		List<String> setPins = createBoardPage.createBoardWithPins(board);

		Logger.verify("VP. Verify the Board is created with the selected Pins.");
		Assert.assertTrue(boardPage.isBoardCreatedWithSelectedPins(board.getBoardName(), setPins),
				"Board with pins is not created correctly.");
	}

}
