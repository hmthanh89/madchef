package api.object;

import java.util.ArrayList;
import java.util.List;

import enums.Param;

public class RequestMessage {
	  private Authentication _oAuthor;
	  private String _sBaseURI = "";
	  private String _sResources=""; // path of the method
	  
	  private String _sContentType = ""; 
	  private String _sBody = "";
	  private int _iConnectTimeout=0 ;
	  private int _iReadTimeout =0;
	  private List<Parameter<?>> _lstParams;
	  
	  
	  public RequestMessage() 
	  {
		    this._sBaseURI ="";
		    this._sResources = "";
		    this._oAuthor = null;
		    this._sContentType = "application/json";
		    this._sBody = "";
		    this._iConnectTimeout = 0;
		    this._iReadTimeout =0;		    
		    this._lstParams = new ArrayList<Parameter<?>>();
	  }
	  
	  public void setHeader(String sName, Object oValue) {
	    Parameter<Object> oParam = new Parameter<Object>(Param.Header, sName, oValue);
	    this._lstParams.add(oParam);
	  }
	  
	  public void setPathPrameter(String sName, Object oValue) {
	    Parameter<Object> oParam = new Parameter<Object>(Param.PathParam, sName, oValue);
	    this._lstParams.add(oParam);
	  }
	  
	  public void setQueryPrameter(String sName, Object oValue) {
	    Parameter<Object> oParam = new Parameter<Object>(Param.QueryParam, sName, oValue);
	    this._lstParams.add(oParam);
	  }
	  
	  public void setAttachedFile(String sFile) {
	    Parameter<Object> oParam = new Parameter<Object>(Param.AttachedFile, sFile);
	    this._lstParams.add(oParam);
	  }
	  
	  public void Cookie(String sName, Object oValue) {
	    Parameter<Object> oParam = new Parameter<Object>(Param.Cokie, sName, oValue);
	    this._lstParams.add(oParam);
	  }
	  
	  public String getBaseURI() { return this._sBaseURI; }
	  
	  public void setBaseURI(String sBaseURI) { this._sBaseURI = sBaseURI; }
	  
	  public String getResources() { return this._sResources; }
	  
	  public void setResources(String endpoint) { this._sResources = endpoint; }
	  
	  public Authentication Author() {
		  if (this._oAuthor ==null) this._oAuthor = new Authentication();
			return this._oAuthor ;  
	  }
	  public Authentication getAuthor() { return this._oAuthor; }
	  
	  public void setAuthor(Authentication author) { this._oAuthor = author; }
	  
	  public int getConnectTimeOut() { return this._iConnectTimeout; }
	  
	  public void setConnectTimeOut(int iVal) { this._iConnectTimeout = iVal; }

	  public int getReadTimeOut() { return this._iReadTimeout; }
	  
	  public void setReadTimeOut(int iVal) { this._iReadTimeout = iVal; }
	  
	  public String getContentType() { return this._sContentType; }
	  
	  public void setContentType(String contentType) { this._sContentType = contentType; }
	  
	  public String getBody() { return this._sBody; }
	  
	  public void setBody(String body) { this._sBody = body; }
	  
	  public List<Parameter<?>> getListParams() { return this._lstParams; }
	  
	  public void setListParams(List<Parameter<?>> lstParams) { this._lstParams = lstParams; }
}
