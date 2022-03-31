package General;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Reporter implements Reportable {
		// stores two maps, one with all the linenumbers and error messages, and one with only one value
		// the first value is the type of error (brackets, indents, comments, etc) and the other value the previous map.
	public Map<Integer, String> map = new HashMap<Integer, String>();
	public Map<String, Map<Integer, String>> superMap = new HashMap<String, Map<Integer, String>>();
	public ArrayList<Map<String, Map<Integer, String>>> mapList = new ArrayList<Map<String, Map<Integer, String>>>();
	public String mapType;
	public Stack<String> test = new Stack<String>();
	
	
	public Reporter(String mapType) {
		this.mapType = mapType;
	}
	
	
	public void errorGen(String errorType, int lineNumb) {
		map.put(lineNumb, errorType);
		test.push(errorType + ": " + String.valueOf(lineNumb));
	}
	
	public void mapDone() {
		superMap.put(mapType, map);
	}
	
	public Map<String, Map<Integer, String>> getSuperMap(){
		return this.superMap;
	}
	
	public void deleteError(int lineNumb) {
		map.remove(lineNumb);
	}
	
		// returns large string of every error or null if no errors
	public String report() {
		String out = "";
		while ( !(test.isEmpty()) ) {
			out = out + test.pop() +"\n";
		}
		if ( out.isBlank()) {
			return null;
		}
		return out;
	}
	
}
