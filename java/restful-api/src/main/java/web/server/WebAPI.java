package web.server;

import api.object.Authentication;
import protocol.RestAssuredProtocol;

public class WebAPI  extends RestAssuredProtocol
{
	public WebAPI() {}
	public WebAPI(String sBaseURI) { super(sBaseURI); }
	public WebAPI(String sBaseURI, Authentication oAuthen) { super(sBaseURI, oAuthen); }

}
