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
	public String errorType;
	public Stack<String> test = new Stack<String>();
	
	// repo items
	public int percentage;
	public int matchPercent;
	public int correctPercent;
	public String idtStyle;
	
	
	public Reporter(String errorType) {
		this.errorType = errorType;
	}
	
	
	public void errorGen(String errorType, int lineNumb) {
		map.put(lineNumb, errorType);
		test.push(errorType + ": " + String.valueOf(lineNumb));
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
	
		// get and set the percentage of the majority indent style used
	public int getMajorityIdt() {
		return this.percentage;
	}
	
	public void setMajorityIdt(int i) {
		this.percentage = i;
	}
	
		// get and set the majority indent style used
	public String getIdtStyle() {
		return this.idtStyle;
	}
	
	public void setIdtStyle(String s) {
		this.idtStyle = s;
	}
	
		// assess and return the style used with the style demanded.
	public int getIdtMatch() {
		return this.matchPercent;
	}
	
	public void setIdtMatch() {
	}
	
	public int getIdtCorrect() {
		return this.correctPercent;
	}
	
}
