package protocol;

import api.object.Authentication;
import api.object.RequestMessage;
import api.object.ResponseMessage;

public class BaseProtocol<T> extends Object {
	
	public static int CONNECT_TIMEOUT = 1000;
	public static int READ_TIMEOUT = 1000;
	
	protected ResponseMessage _oResponse = null;
	protected RequestMessage _oRequest =null;
 	
	protected String _sBaseURI = "";
	
	protected Authentication _oAuthor = null; 
  
	public BaseProtocol() {
	    this._oResponse = new ResponseMessage();
	    this._sBaseURI = "";
	} 
	 
	public BaseProtocol(String sBaseURI) 
	{
		 this._sBaseURI = sBaseURI;
		 this._oResponse = new ResponseMessage();
	}
	
	public BaseProtocol(String sBaseURI, Authentication oAuthen) 
	{
		 this._sBaseURI = sBaseURI;
		 this._oAuthor = oAuthen;
		 this._oResponse = new ResponseMessage();
	}

}