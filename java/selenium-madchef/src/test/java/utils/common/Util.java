package utils.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Util {

	public static List<List<Object>> combineData(List<Object[]> listObjs) {
		Set<List<Object>> combs = getCombinations(listObjs);
		List<List<Object>> list = new ArrayList<List<Object>>(combs);
		return list;
	}
	
	public static Set<List<Object>> getCombinations(List<Object[]> listObjs) {
	    Set<List<Object>> combinations = new HashSet<List<Object>>();
	    Set<List<Object>> newCombinations;

	    int index = 0;

	    for(Object i: listObjs.get(0)) {
	        List<Object> newList = new ArrayList<Object>();
	        newList.add(i);
	        combinations.add(newList);
	    }
	    
	    index++;
	    while(index < listObjs.size()) {
	        Object[] nextList = listObjs.get(index);
	        newCombinations = new HashSet<List<Object>>();
	        for(List<Object> first: combinations) {
	            for(Object second: nextList) {
	                List<Object> newList = new ArrayList<Object>();
	                newList.addAll(first);
	                newList.add(second);
	                newCombinations.add(newList);
	            }
	        }
	        combinations = newCombinations;

	        index++;
	    }

	    return combinations;
	}
	
	public static List<String[]> readCsvFile(String path) {
		List<String[]> data = new ArrayList<String[]>();
		try (BufferedReader csvReader = new BufferedReader(new FileReader(path))) {
			String row = "";
			while ((row = csvReader.readLine()) != null) {
				String[] tmp = row.split(",");
				data.add(tmp);
			}
		} catch (Exception e) {}
		return data;
	}
}
