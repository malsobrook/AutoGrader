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
		
		this.report();
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
		
		if (expectedIdt != actual) {
			if (ca.length == 0) {
				// ignore for now
			} else {
				errorCount++;
			}
		}
		
		
		for (int i = 0; i < ca.length; i++) {
			
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
	
	public void report() {
			// indent consistency
		if (tabC > spaceC) {
			this.repo.setMajorityIDA((double)tabC / (double)(spaceC + tabC));
			this.repo.setIdtStyle("Tab");
		}
		
		if (tabC < spaceC) {
			this.repo.setMajorityIDA((double)spaceC / (double)(spaceC + tabC));
			this.repo.setIdtStyle("Space");
		}
		
		if (tabC == 0 && spaceC == 0) {
			this.repo.setMajorityIDA(0);
			this.repo.setIdtStyle("None");
		}
		
		if (tabC == spaceC) {
			this.repo.setMajorityIDA(0.5);
			this.repo.setIdtStyle("Tab"); // if a 50/50 split occurs we simply display they chose tabs.
		}
		
			// consistency with choice
		if ( UserSettings.getInstance().getIndentationRequirement().toString().equals("Tab") ) {
			this.repo.setIDAMatchPercent( ((double)tabC / (double)(spaceC + tabC)) );
		}
		if ( UserSettings.getInstance().getIndentationRequirement().toString().equals("Space") ) {
			this.repo.setIDAMatchPercent( ((double)spaceC / (double)(spaceC + tabC)) );
		}
		if ( UserSettings.getInstance().getIndentationRequirement().toString().equals("None") ) {
			this.repo.setIDAMatchPercent(0);
		}
			// indent correctness
		if ( this.errorCount == 0) {
			this.repo.setIDACorrectPercent(1.0);
		} else {
			this.repo.setIDACorrectPercent( ((double)lineNumb - (double)errorCount) / (double)lineNumb);
		}
	}
	
}
