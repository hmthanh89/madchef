package enums;

public enum Param {
	  PathParam("path"), 
	  QueryParam("query"), 
	  FormParam("form"), 
	  Cokie("cookie"), 
	  Header("header"), 
	  AttachedFile("attach");
	  
	  private String _sValue;
	  Param(String sValue) { this._sValue = sValue; }
	  public String getValue() { return this._sValue; }
	  
	  
	  

}
