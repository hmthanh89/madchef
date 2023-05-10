package api.object;

import enums.Authenticate;

public class Authentication {
	  private String _sUsername = "";
	  private String _sPassword = "";
	  private String _sAccessToken = "";
	  private Authenticate _eAuthType = Authenticate.BasicAuth;
	  
	  public Authentication(){};
	  
	  public void BasicAuthor(String username, String password) {
	    this._sUsername = username;
	    this._sPassword = password;
	    this._eAuthType = Authenticate.BasicAuth;
	  }
	  
	  public void PreemptiveAuthor(String username, String password) {
	    this._sUsername = username;
	    this._sPassword = password;
	    this._eAuthType = Authenticate.PreemptiveAuth;
	  }
	  
	  public void DegestAuthor(String username, String password) {
	    this._sUsername = username;
	    this._sPassword = password;
	    this._eAuthType = Authenticate.DigestAuth;
	  }
	  
	  public void Author2(String accToken) {
	    this._sAccessToken = accToken;
	    this._eAuthType = Authenticate.Auth2;
	  }
	  
	  public String getUsername() { return this._sUsername; }
	  
	  public void setUsername(String username) { this._sUsername = username; }
	  
	  public String getPassword() { return this._sPassword; }
	  
	  public void setPassword(String password) { this._sPassword = password; }
	  
	  public String getAccessToken() { return this._sAccessToken; }
	  
	  public void setAccessToken(String accessToken) { this._sAccessToken = accessToken; }
	  
	  public Authenticate getAuthType() { return this._eAuthType; }
	  
	  public void setAuthType(Authenticate authType) { this._eAuthType = authType; }

}
