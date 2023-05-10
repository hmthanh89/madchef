package utils.helper;

import data.Information;

public class InfoHelper {
	
	private static ThreadLocal<Information> oInfo = new ThreadLocal<>();
	
	public static Information Fields()
	{
		if (oInfo == null) oInfo = new ThreadLocal<>();
		if (oInfo.get() == null) oInfo.set(new Information());
		return oInfo.get();
	}
}
