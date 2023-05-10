package api.Pinterest.resources;

import utils.helper.PropertiesHelper;
import web.server.WebAPI;

public class BaseResource {

	public WebAPI webService = null;
	private String _baseURI = "";
	private String _accessToken = "";

	public BaseResource() {
		if (loadInfo()) {
			webService = new WebAPI(_baseURI);
			webService.Author().Author2(_accessToken);
		} else
			webService = new WebAPI();
	}
	
	public BaseResource(String baseURI) {
		_baseURI = baseURI;
		webService = new WebAPI(baseURI);
	}

	public BaseResource(String baseURI, String accessToken) {
		_baseURI = baseURI;
		_accessToken = accessToken;

		webService = new WebAPI(_baseURI);
		webService.Author().Author2(_accessToken);
	}
	
	public boolean loadInfo() {
		try {
			_baseURI = PropertiesHelper.getPropValue("profile.api.host");
			_accessToken = PropertiesHelper.getPropValue("profile.api.token");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
