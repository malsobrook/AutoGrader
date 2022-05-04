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
	
	// indent items
	public double IDAConsistentPercentage;
	public double IDAMatchPercent;
	public double IDACorrectPercent;
	public double IDAScore;
	public String IDAStyle;
	// Misc Items
	public boolean MAImportAtTop;
	public double MACorrectPercent;
	public boolean MACommentAtTop;
	public String IDAMajStyle;
	// bracket items
	public double BrkConsistentPercentage;
	public double BrkMatchPercent;
	public double BrkCorrectPercent; 
	public double BrkScore;
	public String BrkStyle;
	public String BrkMajStyle;
	
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
	
	public void report() {
		// TODO remove outdated? or repurpose.
	}
	
		// get and set the percentage of the majority indent style author used
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
	
	public double getMajorityBrk() {
		return this.BrkConsistentPercentage;
	}
	
	public void setMajorityBrk(double i) {
		this.BrkConsistentPercentage = i*100;
	}
	
	public String getBrkStyle() {
		return this.BrkStyle;
	}
	
	public void setBrkStyle(String i) {
		this.BrkStyle = i;
	}
	
	public double getBrkMatchPercent() {
		return this.BrkMatchPercent;
	}
	
	public void setBrkMatchPercent(double i) {
		this.BrkMatchPercent = i*100;
	}
	
	public void setBrkCorrectPercent(double i) {
		this.BrkCorrectPercent = i*100;
	}
	
	public double getBrkCorrectPercent() {
		return this.BrkCorrectPercent;
	}
	
	public void setBrkMajStyle(String i) {
		this.BrkMajStyle = i;
	}
	
	public String getBrkMajStyle() {
		return this.BrkMajStyle;
	}
	
	public double calculateBrkScore() {
		double result = ( (BrkMatchPercent + BrkConsistentPercentage + BrkCorrectPercent) / 3 );
		return result;
	}
}