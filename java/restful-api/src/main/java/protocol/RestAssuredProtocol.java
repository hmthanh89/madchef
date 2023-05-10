package protocol;

import java.io.File;

import api.object.Authentication;
import api.object.Parameter;
import api.object.RequestMessage;
import api.object.ResponseMessage;
import enums.Authenticate;
import enums.Param;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredProtocol extends BaseProtocol<Response> implements IProtocol
{
	public RestAssuredProtocol() {}
	
	public RestAssuredProtocol(String sBaseURI) { 
		super(sBaseURI); 
	}
	public RestAssuredProtocol(String sBaseURI, Authentication oAuthen) { 
		super(sBaseURI, oAuthen);
	}
	
	public ResponseMessage GET(RequestMessage oMsg) throws Exception { return PerformActions(oMsg, Method.GET); }
	
	public ResponseMessage POST(RequestMessage oMsg) throws Exception { return PerformActions(oMsg, Method.POST); }
	
	public ResponseMessage PUT(RequestMessage oMsg) throws Exception { return PerformActions(oMsg, Method.PUT); }
	
	public ResponseMessage DELETE(RequestMessage oMsg) throws Exception { return PerformActions(oMsg, Method.DELETE); }
	
	public ResponseMessage HEAD(RequestMessage oMsg) throws Exception { return PerformActions(oMsg, Method.HEAD); }
	
	public Authentication Author() {
		if (this._oAuthor ==null) this._oAuthor = new Authentication();
		return this._oAuthor ; 
	}
	  
	public void setAuthor(Authentication author) { this._oAuthor = author; }
	
	private ResponseMessage PerformActions(RequestMessage oMsg, Method oMethod) throws Exception {
		
		  if (oMsg == null || oMethod == null )  throw new Exception("Request message is invalid");
		  if ( oMsg.getResources().isEmpty()) throw new Exception("Path of the ResquestMessage is empty");
		  this._oRequest = oMsg;
		  String sResource =_oRequest.getResources();
		  
		  setEnv();
		  RequestSpecification oRequestSpec = setParameters();
		  RestAssuredConfig oConfig = configTimeOut();
		
		  Response oResponse = 
				  RestAssured.given()
				  .config(oConfig)
				  .spec(oRequestSpec)
				  .when().request(oMethod, sResource);
		  
		  convertToResponseMessage(oResponse);
		  return this._oResponse;
	}
	
	//
	private void setEnv() throws Exception {
		
		  if (this._oRequest.getBaseURI()!="") 	RestAssured.baseURI = this._oRequest.getBaseURI() ;
		  else RestAssured.baseURI = this._sBaseURI;
		  
		  if (RestAssured.baseURI.isEmpty()) throw new Exception("baseURI is empty");
		  
		  if (_oRequest.getAuthor()!=null) setAuthentication(_oRequest.getAuthor());
		  else setAuthentication(this._oAuthor);
	}
	
	private RequestSpecification setParameters() throws Exception
	{
		RequestSpecBuilder builder = new RequestSpecBuilder();
		//set parameters  
		for (Parameter<?> param : _oRequest.getListParams())
		{
		    if (param.getType() == Param.PathParam) builder.addPathParam(param.getName(), param.getValue());
		    else if (param.getType() == Param.QueryParam) builder.addQueryParam(param.getName(), new Object[] { param.getValue() });
		    else if (param.getType() == Param.FormParam) builder.addFormParam(param.getName(), new Object[] { param.getValue() }); 
		    else if (param.getType() == Param.AttachedFile)
		    {
			      File f = new File(param.getName());
			      if (f.exists() && f.isFile()) builder.addMultiPart(f) ;
			      else throw new Exception(String.format("the attached file %s doesn't exist",param.getName()));
		    } 
		}
		//set body 
		if (!_oRequest.getBody().equals("") && !_oRequest.getBody().equals(null)) builder.setBody(_oRequest.getBody());
	    //set content type
		if (!_oRequest.getContentType().equals("") && !_oRequest.getContentType().equals(null)) builder.setContentType(_oRequest.getContentType());
				  
		return builder.build();
	}
	
	private RestAssuredConfig configTimeOut(){
		
		int iConnectTimeOut =  BaseProtocol.CONNECT_TIMEOUT ;
		int iReadTimeOut =  BaseProtocol.READ_TIMEOUT;
		
		if (_oRequest.getConnectTimeOut()>0) iConnectTimeOut = _oRequest.getConnectTimeOut(); 
	    if (_oRequest.getReadTimeOut()>0) iReadTimeOut = _oRequest.getReadTimeOut();
		
		HttpClientConfig client = HttpClientConfig.httpClientConfig()
				.setParam("http.connection.timeout", iConnectTimeOut )
                .setParam("http.socket.timeout", iReadTimeOut);
		
		return RestAssured.config().httpClient(client);
	}
	
	private void setAuthentication(Authentication oAuth)
	{
		  if (oAuth == null) return;
		     
		  if (oAuth.getAuthType() == Authenticate.BasicAuth) {
		    RestAssured.authentication = RestAssured.basic(oAuth.getUsername(), oAuth.getPassword());
		    return;
		  } 
		  if (oAuth.getAuthType() == Authenticate.PreemptiveAuth) {
		    RestAssured.authentication = RestAssured.preemptive().basic(oAuth.getUsername(), oAuth.getPassword());
		    return;
		  } 
		  if (oAuth.getAuthType() == Authenticate.FormAuth) {
		    RestAssured.authentication = RestAssured.form(oAuth.getUsername(), oAuth.getPassword());
		    return;
		  } 
		  if (oAuth.getAuthType() == Authenticate.DigestAuth) {
		    RestAssured.authentication = RestAssured.digest(oAuth.getUsername(), oAuth.getPassword());
		    return;
		  } 
		  if (oAuth.getAuthType() == Authenticate.Auth2) {
		    RestAssured.authentication = RestAssured.oauth2(oAuth.getAccessToken());
		    return;
		  } 
	}
	
	private void convertToResponseMessage(Response oResponse) {
		
		this._oResponse.setOriginalContent(oResponse);
		this._oResponse.setContent(oResponse.asString());
		this._oResponse.setStatusCode(oResponse.getStatusCode());

		for (Header entity : oResponse.getHeaders()) {
			this._oResponse.addHeader(entity.getName(), entity.getValue());
		}
	}
}
