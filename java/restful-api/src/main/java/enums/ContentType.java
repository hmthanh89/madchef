package enums;

public enum ContentType {
	 TEXT("text/plain"), JSON("application/json"), XML("application/xml"), HTML("text/html"),
	  URLENC("application/x-www-form-urlencoded"), BINARY("application/octet-stream");
	  
	  private String _sValue;
	  ContentType(String sValue) { this._sValue = sValue; }
	  public String asString() { return this._sValue; }
}
