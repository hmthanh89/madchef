package protocol;

import api.object.RequestMessage;
import api.object.ResponseMessage;

public interface IProtocol {
	
	  ResponseMessage GET(RequestMessage paramRequestMessage) throws Exception;
	  
	  ResponseMessage POST(RequestMessage paramRequestMessage) throws Exception;
	  
	  ResponseMessage PUT(RequestMessage paramRequestMessage) throws Exception;
	  
	  ResponseMessage DELETE(RequestMessage paramRequestMessage) throws Exception;
	  
	  ResponseMessage HEAD(RequestMessage paramRequestMessage) throws Exception;

}
