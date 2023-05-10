package api.Pinterest.functions;

import api.Pinterest.resources.BoardApi;
import api.object.ResponseMessage;
import data.Pinterest.Board;
import utils.common.Constants;

public class BoardFunc {

	private BoardApi api;

	public BoardFunc() {
		api = new BoardApi();
	}

	public boolean createBoard(Board board) {
		String body = "{\"name\": \"%s\",\"description\": \"%s\"}";
		body = String.format(body, board.getBoardName(), board.getDescription());

		ResponseMessage response = api.createBoard(body);
		return (response.getStatusCode() == 201);
	}

	public boolean deleteBoard(String board) {
		ResponseMessage response = api.deleteBoard(Constants.USERNAME + "/" + board);
		return (response.getStatusCode() == 201);
	}
}
