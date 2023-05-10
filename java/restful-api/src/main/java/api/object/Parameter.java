package api.object;

import enums.Param;

public class  Parameter<V> extends Object{
	
	  private Param _eType = null;
	  private String _sName = "";
	  private V _oValue = null;
	  
	  public Parameter() {}
	  
	  public Parameter(Param oType, String sName, V oValue) {
	    this._eType = oType;
	    this._sName = sName;
	    this._oValue = oValue;
	  }
	  
	  public Parameter(Param oType, String sName) {
	    this._eType = oType;
	    this._sName = sName;
	  }
	  
	  public Param getType() { return this._eType; }
	  
	  public void setType(Param type) { this._eType = type; }
	  
	  public String getName() { return this._sName; }
	  
	  public void setName(String name) { this._sName = name; }
	  
	  public V getValue() { return (V)this._oValue; }
	  
	  public void setValue(V oValue) { this._oValue = oValue; }
}
