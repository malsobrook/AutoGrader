package General;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reporter {
		// stores two maps, one with all the linenumbers and error messages, and one with only one value
		// the first value is the type of error (brackets, indents, comments, etc) and the other value the previous map.
	public Map<Integer, String> map = new HashMap<Integer, String>();
	public Map<String, Map<Integer, String>> superMap = new HashMap<String, Map<Integer, String>>();
	public ArrayList<Map<String, Map<Integer, String>>> mapList = new ArrayList<Map<String, Map<Integer, String>>>();
	public String mapType;
	
	
	public Reporter(String mapType) {
		this.mapType = mapType;
	}
	
	
	public void lineStore(int lineNumb, String errorType) {
		map.put(lineNumb, errorType);
		
	}
	
	public void mapDone() {
		superMap.put(mapType, map);
	}
	
	public Map<String, Map<Integer, String>> getSuperMap(){
		return this.superMap;
	}
}
