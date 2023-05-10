package api.Pinterest.functions;

import api.Pinterest.resources.PinApi;
import api.object.ResponseMessage;
import data.ResponseObject;
import data.Pinterest.Board;
import data.Pinterest.Pin;
import io.restassured.response.Response;
import utils.common.Constants;

public class PinFunc {

	private PinApi api;

	public PinFunc() {
		api = new PinApi();
	}

	public ResponseObject createPin(Board board, Pin pin) {
		String sboard = Constants.USERNAME + "/" + board.getBoardName().toLowerCase().replaceAll(" ", "-");
		String body = "{\"board\": \"%s\",\"note\": \"%s\",\"image_url\":\"%s\"}";
		body = String.format(body, sboard, pin.getNote(), pin.getImageUrl());

		ResponseMessage response = api.createPin(body);
		ResponseObject responseObject = new ResponseObject();
		responseObject.setMessage(response.getOriginalContent(Response.class).getBody().jsonPath().get("message"));
		responseObject.setStatusCode("" + response.getStatusCode());

		return responseObject;
	}

	public ResponseObject createPin(Pin pin) {
		String body = "{\"note\": \"%s\",\"image_url\":\"%s\"}";
		body = String.format(body, pin.getNote(), pin.getImageUrl());

		ResponseMessage response = api.createPin(body);
		ResponseObject responseObject = new ResponseObject();
		responseObject.setMessage(response.getOriginalContent(Response.class).getBody().jsonPath().get("message"));
		responseObject.setStatusCode("" + response.getStatusCode());

		return responseObject;
	}

}
