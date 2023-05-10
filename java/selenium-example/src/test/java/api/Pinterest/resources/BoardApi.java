package api.Pinterest.resources;

import api.object.RequestMessage;
import api.object.ResponseMessage;

public class BoardApi extends BaseResource {

	public BoardApi() {
		super();
	}
	
	public ResponseMessage createBoard(String body) {
		try {
			RequestMessage request = new RequestMessage();
			request.setResources("/v1/boards/");
			request.setBody(body);
			
			return webService.POST(request);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public ResponseMessage deleteBoard(String board) {
		try {
			RequestMessage request = new RequestMessage();
			request.setResources("/v1/boards/{board}/");
			request.setPathPrameter("board", board);
			
			return webService.DELETE(request);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}
