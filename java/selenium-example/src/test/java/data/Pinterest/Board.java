package data.Pinterest;

import com.github.javafaker.Faker;

import utils.common.Common;

public class Board {

	private String boardName;
	private boolean visibility;
	private String description;
	private int pins = 0;

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPins() {
		return pins;
	}

	public void setPins(int pins) {
		this.pins = pins;
	}

	public Board() {
		Faker faker = new Faker();
		this.boardName = faker.food().ingredient() + " " + faker.food().ingredient() + " " + faker.food().ingredient();
		this.visibility = Common.getRandomBoolean();
		this.pins = Common.getRandomNumber(1, 10);
		this.description = Common.getRandomString("Description no ");
	}

	public Board(String boardName, boolean visibility, int pins) {
		this.boardName = boardName;
		this.visibility = visibility;
		this.pins = pins;
	}

	public Board(String boardName, String description) {
		this.boardName = boardName;
		this.description = description;
	}
}
