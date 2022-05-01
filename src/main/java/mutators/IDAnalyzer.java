package mutators;

import java.io.*;
import General.Reportable;
import General.Reporter;
import Gui.UserSettings;

public class IDAnalyzer implements Reportable{
	
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
	int errorCount = 0;
	
	public IDAnalyzer (String filepath, Reporter handlerReporter) throws Exception {
		this.filePath = filepath;
		this.spaceIndex = UserSettings.getInstance().getNumberOfSpaces();
		this.repo = handlerReporter;
		this.OneTBS = true;
		this.analyze();
	}
	
	public void analyze() throws Exception {
		BufferedReader bfr = new BufferedReader(new FileReader(filePath));
		String temp = bfr.readLine();
		lineNumb++;
		
		while(temp != null) {
			carray = temp.toCharArray();
			int ic = indentCounter(carray);
			indentCorrector(carray, ic);
			temp = bfr.readLine();
			lineNumb++;
		}
		
		this.setRepoValues();
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
		
			//delete
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
				errorCount++;
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
	
	public void setRepoValues() {
			// indent consistency
		if (tabC > spaceC) {
			this.repo.setMajorityIDA((double)tabC / (double)(spaceC + tabC));
		}
		if (tabC < spaceC) {
			this.repo.setMajorityIDA((double)spaceC / (double)(spaceC + tabC));
		}
		if (tabC == 0 && spaceC == 0) {
			this.repo.setMajorityIDA(0);
		}
		if (tabC == spaceC) {
			this.repo.setMajorityIDA(0.5);
		}
		
			// consistency with choice
		if ( UserSettings.getInstance().getIndentationRequirement().toString().equals("Tab") ) {
			this.repo.setIDAMatchPercent( ((double)tabC / (double)(spaceC + tabC)) );
		}
		if ( UserSettings.getInstance().getIndentationRequirement().toString().equals("Space") ) {
			this.repo.setIDAMatchPercent( ((double)spaceC / (double)(spaceC + tabC)) );
		}
		
			// indent correctness
		if ( this.errorCount == 0) {
			this.repo.setIDACorrectPercent(1.0);
		} else {
			this.repo.setIDACorrectPercent( ((double)lineNumb - (double)errorCount) / (double)lineNumb);
		}
	}
	
}
