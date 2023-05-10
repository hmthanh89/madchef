package enums;

public enum Authenticate {
	
	 BasicAuth("basic"), Auth2("oauth2"), PreemptiveAuth("preemptive"), DigestAuth("digest"), FormAuth("form");
	 private String _sValue;
	 Authenticate(String sValue) { this._sValue = sValue; }
	 public String getValue() { return this._sValue; }

}
