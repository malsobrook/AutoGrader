package General;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Reporter implements Reportable {
		// stores two maps, one with all the linenumbers and error messages, and one with only one value
	public Map<Integer, String> map = new HashMap<Integer, String>();
	public ArrayList<Map<String, Map<Integer, String>>> mapList = new ArrayList<Map<String, Map<Integer, String>>>();
	public String errorType;
	public Stack<String> test = new Stack<String>();
	public ArrayList<Integer> IDALineNumbs = new ArrayList<Integer>();
	public ArrayList<Integer> BracketLineNumbs = new ArrayList<Integer>();
	
	// repo items
	public double IDAConsistentPercentage;
	public double IDAMatchPercent;
	public double IDACorrectPercent;
	public double IDAScore;
	public String IDAStyle;
	public boolean MAImportAtTop;
	public double MACorrectPercent;
	
	public double getMACorrectPercent() {
		return MACorrectPercent;
	}


	public void setMACorrectPercent(double mACorrectPercent) {
		MACorrectPercent = mACorrectPercent;
	}


	public boolean isMAImportAtTop() {
		return MAImportAtTop;
	}


	public void setMAImportAtTop(boolean mAImportAtTop) {
		MAImportAtTop = mAImportAtTop;
	}


	public boolean isMACommentAtTop() {
		return MACommentAtTop;
	}


	public void setMACommentAtTop(boolean mACommentAtTop) {
		MACommentAtTop = mACommentAtTop;
	}

	public boolean MACommentAtTop;
	
	
	public Reporter(String errorType) {
		this.errorType = errorType;
		
	}
	
	
	public void errorGen(String errorType, int lineNumb) {
		map.put(lineNumb, errorType);
		test.push(errorType + ": " + String.valueOf(lineNumb));
		if (errorType.equals("indent")) {
			this.IDALineNumbs.add(lineNumb);
		}
		if (errorType.equals("Bracket")) {
			this.BracketLineNumbs.add(lineNumb);
		}
		
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
	public double getMajorityIDA() {
		return this.IDAConsistentPercentage;
	}
	
	public void setMajorityIDA(double i) {
		this.IDAConsistentPercentage = i*100;
	}
	
		// get and set the majority indent style used
	public String getIdtStyle() {
		return this.IDAStyle;
	}
	
	public void setIdtStyle(String s) {
		this.IDAStyle = s;
	}
	
		// assess and return the style used with the style demanded.
	public double getIDAMatchPercent() {
		return this.IDAMatchPercent;
	}
	
	public void setIDAMatchPercent(double i) {
		this.IDAMatchPercent = i*100;
	}
	
	public double getIDACorrectPercent() {
		return this.IDACorrectPercent;
	}
	
	public void setIDACorrectPercent(double i) {
		this.IDACorrectPercent = i*100;
	}
	
	public double calculateIDAScore() {
		double result = ( (IDAMatchPercent + IDAConsistentPercentage + IDACorrectPercent) / 3 );
		return result;
	}
	
}
