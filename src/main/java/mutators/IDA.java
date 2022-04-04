package mutators;

import java.io.*;
import General.Reportable;
import General.Reporter;

public class IDA implements Reportable{
	
	boolean OneTBS;
	char[] carray;
	char[] iso = {'@', 	  '!', 	   '?',   '#',  '^',   '*',   '%',    '`'};
	int spaceIndex;
	int lineNumb = 0;
	Reporter repo;
	String filePath;
	
		// respective indent counters
	int spaceC = 0;
	int tabC = 0;
	
		// for IndentCorrector
	boolean loopinpr = false;
	boolean errorFlag = false;
	boolean bt = false;
	boolean nextLineBracket = false;
	boolean breakOut = false;
	
	int superIndex = 0;
	int expectedBrackets = 0;
	int bracketCount = 0;
	int unclosedbrackets = 0;
	int expectedIdt = 0;
	int nestLvl = 0;
	
	
	public IDA (String filepath) throws Exception {
		this.filePath = filepath;
		this.spaceIndex = 4;
		this.repo = new Reporter(filePath);
		this.OneTBS = true;
		this.analyze();
	}
	
	public void analyze() throws Exception {
		BufferedReader bfr = new BufferedReader(new FileReader(filePath));
		String temp = bfr.readLine();
		lineNumb++;
		
		System.out.println("	  Actual	Expected");
		while(temp != null) {
			carray = temp.toCharArray();
			int ic = indentCounter(carray);
			indentCorrector(carray, ic);
			temp = bfr.readLine();
			lineNumb++;
		}
		System.out.println("\n\n\n|-----------------------------Report--------------------------------|");
		String report = report();
		if(report != null) {
			System.out.println(report);
		} else {
			System.out.println("No indent discrepancies detected");
		}
			// test feature
		String str = "Indent Consistency:	Spaces: " + spaceC + "	Tabs: " + tabC;
		System.out.println(str);
		
		bfr.close();
		
	}
	

	// takes char array of line being read and adjust relevant variables to count indents, returns indent level of line read.
	public int indentCounter(char CA[]) {
		int temp = 0;
		int count = 0;
		for(char c: CA) {
			if (c == '$' || c == '&') {
				if (c == '$') {
					count++;
					if (count >= spaceIndex) {
						count = 0;
						temp++;
						spaceC++;
					}
				}
				if (c == '&') {
					temp++;
					tabC++;
				}
			} else {
				break;
			}
		}
		return temp;
	}
	

	private void indentCorrector(char ca[], int actual) {

		
		for (int i = 0; i < ca.length; i++) {
			if (ca[i] == '}') {
				if (bracketCount > expectedBrackets) {
					bracketCount--;
				} else if (bracketCount == expectedBrackets) {
					bracketCount--; expectedBrackets--;
					expectedIdt--;
					superIndex = i;
					breakOut = true;
					break;
				}
			}
		}
		
		
		if (ca.length == 0) {
			System.out.println("Line:" + lineNumb + "        " + "B" + "    " + expectedIdt);	// B for Blank Line
		} else {
			System.out.println("Line:" + lineNumb + "        " + actual + "    " + expectedIdt);
		}
		if (expectedIdt != actual) {
			if (ca.length == 0) {
				// ignore for now
			} else {
				this.repo.errorGen("Indentation error on", lineNumb);
			}
		}
		
		
		for (int i = 0; i < ca.length; i++) {
			// if we find a keyword
				// expect a bracket, this line or next
				// add 1 to anticipated opening brackets
				// add 1 to expected indent level depending on bracket style
			
			
			// search for opening bracket
			// found one
				// add 1 to bracket count
				// if we anticipate a bracket on 'next line' and this is it
					// stop anticipating that bracket
					// add to expected indent level because we didn't before
			
			
			// search for closing bracket
			//found one
				// if bracket count is greater than anticipated count
					// lower bracket count by 1
				// else if they are equal
					// lower bracket count and anticipated count by 1
					// lower expected indent level by one
			
			if (keySearch(ca[i])) {
				expectedBrackets++;
				if (OneTBS) {
					expectedIdt++;
				} else {
					nextLineBracket = true;
				}
			}
			
			if (ca[i] == '{') {
				bracketCount++;
				if (nextLineBracket) {
					nextLineBracket = false;
					expectedIdt++;
				}
			}
			
			
		}
		
		// starting from break
		// assess indent level
		
		// if we broke out, run script again starting from index we broke on (default 0)
		// method for this
		
		
		
	}
	

	private boolean keySearch(char c) {
		for (int i=0; i < iso.length; i++) {
			if (c == iso[i]) {
				return true;
			}
		}
		return false;
	}
	
		// called externally to get all reports of this type.
	public String report() {
		String str = repo.report();
		return str;
	}
	
}
