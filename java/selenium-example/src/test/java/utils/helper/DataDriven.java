package utils.helper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;

public class DataDriven {
	
	@DataProvider
	public Object[][] loadData(Method oTestMethod) throws IOException, JSONException
	{
		String sResourceName = DataDriven.getResourceFileName(oTestMethod);
		
		return loadDataTest(sResourceName, oTestMethod.getName());
	}
	
	private static String getResourceFileName(Method oMethod)
	{
		String sClassName = oMethod.getDeclaringClass().getSimpleName();
		String sPackageName = oMethod.getDeclaringClass().getPackage().getName();
		String temp = sPackageName.substring(sPackageName.lastIndexOf(".") + 1, sPackageName.length());
		temp = temp.replace("_management", "");
		
		if (temp.indexOf("_") < 0)
		{
			return sClassName;
		}
		else
		{
			return temp.substring(0, temp.indexOf("_")) + "/" + sClassName;
		}
	}
	
	/**
     * load data from a json file then returning data set as a two demension Object array
     * @param sFileName		json file name
     * @param sKey			data items key
     * @return				loaded data set
     */
	public static Object[][] loadDataTest(String sFileName, String sKey) throws IOException, JSONException
	{
		JSONObject jDataSet = loadJsonContent(sFileName);
		
		List<ArrayList<Object>> lstDataTest = loadData(jDataSet.getJSONObject(sKey));
		
		
		return lstDataTest.stream()
			    .map(item -> item.stream().toArray(Object[]::new))
			    .toArray(Object[][]::new);
	}
	
	/**
	 * load data from a json (support data set inside data set)
	 * 
	 * @param jDataSet			a json that contains data test
	 * @return
	 * Example jDataSet is :
	 *{
	 * "structure":["headers", "response"],
	 * "data":[
	 * 		{
	 * 			"headers": {
	 * 					"structure":["realm"],
	 * 					"data":[
	 * 							{"realm":"adviser"},
	 * 							{"realm":"customer"}
	 * 						]
	 * 				},
	 * 			"response":"{}"
	 * 		}
	 * ]
	 *}.
	 * The result will be: [[adviser, {}], [customer, {}]]
	 * */
	private static List<ArrayList<Object>> loadData(JSONObject jDataSet) throws JSONException
	{
		JSONArray jArrayStructure = jDataSet.optJSONArray("structure");
		JSONArray jData = jDataSet.optJSONArray("data");
		
		if (null == jArrayStructure || 0 == jArrayStructure.length() 
			|| null == jData || 0 == jData.length()) return null;
		
		List<ArrayList<Object>> lstDataTest = new ArrayList<ArrayList<Object>>();
		
		for (int indexData = 0; indexData < jData.length(); indexData++)
		{
			JSONObject jDataItem = jData.optJSONObject(indexData);
			List<ArrayList<Object>> lstTempDataTest = new ArrayList<ArrayList<Object>>();
			lstTempDataTest.add(new ArrayList<Object>());
			if (null == jDataItem) continue;
			
			for (int indexStructure = 0; indexStructure < jArrayStructure.length(); indexStructure++)
			{
				String key = jArrayStructure.optString(indexStructure);
				Object oItem = jDataItem.get(key);
				boolean bIsDataSet = (oItem instanceof JSONObject) ? isDataSet((JSONObject)oItem) : false;
				
				if (bIsDataSet)
				{
					lstTempDataTest = makeCombination(lstTempDataTest, loadData((JSONObject)oItem));
				}
				else
				{
					lstTempDataTest.forEach(item->item.add(oItem));
				}
			}
			lstDataTest.addAll(lstTempDataTest);
		}
		
		return lstDataTest;
	}
	
	/**
	 * Check if a json object is a data set
	 * */
	private static boolean isDataSet(JSONObject jValue)
	{	
		return ((2 == jValue.length()) && jValue.has("data") && jValue.has("structure"));
	}
	
	/**
	 * make combinition between two lists
	 * Example: list1 = [a, b]
	 * 			list2 = [c, d]
	 * the result will be: [[a, c], [a, d], [b, c], [b, d]]
	 * 
	 * @param lstFirst		the first list
	 * @param lstSecond		the second list
	 * @return				the list that contains combination of lstFirst and lstSecond
	 * */
	private static List<ArrayList<Object>> makeCombination(
			List<ArrayList<Object>> lstFirst, List<ArrayList<Object>> lstSecond)
	{
		if (null == lstFirst && null == lstSecond)
		{
			return null;
		}
		else if (null == lstFirst)
		{
			return lstSecond;
		}
		else if (null == lstSecond)
		{
			return lstFirst;
		}
		
		List<ArrayList<Object>> lstResults = new ArrayList<ArrayList<Object>>();
		
		lstFirst.forEach(itemFirst->lstSecond.forEach(
				itemSecond->lstResults.add((ArrayList<Object>)Stream.of(itemFirst, itemSecond)
																	.flatMap(Collection::stream)
																	.collect(Collectors.toList()))));
		return lstResults;
		
	}
	
	/**
     * load and parse json from file
     * @param sFileName		json file name
     * @return				loaded JSONObject
     */
	private static JSONObject loadJsonContent(String sFileName) throws IOException, JSONException
	{
		String sJsonFileName = fixJsonFileName(sFileName);
		
		InputStream oInStream = new DataDriven().getClass()
											   .getClassLoader()
											   .getResourceAsStream("test/data/" + sJsonFileName);
		BufferedReader oReader = new BufferedReader(new InputStreamReader(oInStream));

		StringBuilder oStrBuilder = new StringBuilder();
		oReader.lines().forEach(line->oStrBuilder.append(line));
		
		return new JSONObject(oStrBuilder.toString());
	}
	
	/**
     * fix the extension of json file name
     * @param sFileName		json file name
     */
	private static String fixJsonFileName(String sFileName)
	{
		if (null == sFileName) return "";
		if (sFileName.endsWith(".json")) return sFileName;
		if (!sFileName.contains(".")) return sFileName + ".json";
		
		return "";
	}
	
	

}
