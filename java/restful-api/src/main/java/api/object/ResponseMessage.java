package api.object;

import java.util.HashMap;
import java.util.Map;

public class ResponseMessage {
	
	private Object _orgContent = null;
	private int _statusCode = 0;
	private Map<String, Object> _lstHeaders = null;
	private String _sContent = "";

	public ResponseMessage() {
		this._lstHeaders = new HashMap<String, Object>();
	}
	
	public void addHeader(String name, Object value){
		this._lstHeaders.put(name, value);
	}
	
	public Object getHeader(String name){
		return this._lstHeaders.get(name);
	}
	
	public Map<String, Object> getHeaders(){
		return this._lstHeaders;
	}

	public int getStatusCode() {
		return _statusCode;
	}

	public void setStatusCode(int _statusCode) {
		this._statusCode = _statusCode;
	}
	
	public void setContent(String content) {
		this._sContent = content;
	}
	
	public String getContent(){
		return this._sContent;
	}
	
	public void setOriginalContent(Object originalContent) {
		this._orgContent = originalContent;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOriginalContent(Class<T> clazz) {
		return (T) this._orgContent;
	}

	


  
  
  
  
  
}
