package mutators;

import General.Reportable;
import General.Reporter;
import Gui.UserSettings;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Iterator;


public class BracketAnalyzer implements Reportable {

	private final String filepath;
	private int inLine = 0;
	private int nextLine = 0;
	private int errorCount = 0;

	private boolean anticipateNext = false;
	private boolean oneTrueBraceStyle = true;
	private boolean allmanBraceStyle = false;

	private char[] searchingFor = {'@', 	'!', 	'?',	 '#', 	'^', 	'*', 	'%', 		'`'};
	//								if, 	else, 	while, 	  for,  try,  catch,  class, (represents methods/constructors)

	private LineNumberReader bracketReader;
	public Reporter repo;


	public BracketAnalyzer(String filepath, Reporter repo) throws Exception {
		this.filepath = filepath;
		this.repo = repo;
		this.analyze();

	}

	public void analyze() throws Exception {
		this.bracketReader = new LineNumberReader(new FileReader(filepath));
		String temporaryLine = null;
		char[] charArray;


		while ((temporaryLine = bracketReader.readLine()) != null) {
			charArray = temporaryLine.toCharArray();
			bracketChecker(charArray);
		}
		
		this.report();
		bracketReader.close();
	}


	public void bracketChecker (char CA[]) {
		if (anticipateNext) {
			anticipateNext = false;
			if( ! ( detectBracketNextLine(CA) ) ) {
				System.out.println("Bracket anticipated, but never found.");
				errorCount++;
			}
		}
		
		for (int i = 0; i < CA.length; i++) {
			if (keywordSearch(CA[i])) {
				if(!(detectBracketInLine(CA, i))) {
					anticipateNext = true;
					int lineNum = bracketReader.getLineNumber();
					bracketReader.setLineNumber(lineNum ++);
				}
			}
		}
		
	}

	
		// uses char array that has found a keyword at given index, starts from that index to see if there is a bracket after the keyword inline.
	private boolean detectBracketInLine(char[] ca, int index) {
		for (int i = index; i < ca.length; i++) {
			if (ca[i] == '{') {
				inLine++;
				return true;
			}
		}
		return false;
	}
	
	private boolean detectBracketNextLine(char[] ca) {
		// assess if there's a bracket in this line, return true if true, otherwise false
		for (int i = 0; i < ca.length; i++) {
			if (ca[i] == '{') {
				nextLine ++;
				return true;
			}
		}
		return false;
	}

		// uses char array to detect if char is present in our keyword array.
	public boolean keywordSearch(char c) {
		for (int i=0; i < searchingFor.length; i++) {
			if (c == searchingFor[i]) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void report() {
		// bracket choice and consistency
		if (inLine > nextLine) {
			repo.setBrkStyle("Inline");
			repo.setMajorityBrk( (double)inLine / (double)(nextLine + inLine) );
		}
		if (inLine < nextLine) {
			repo.setBrkStyle("Newline");
			repo.setMajorityBrk( (double)nextLine / (double)(nextLine + inLine) );
		}
		if (inLine ==  nextLine) {
			repo.setBrkStyle("NextLine");
			repo.setMajorityBrk(0.5);
		}
		
		// consistency with choice
		if ( UserSettings.getInstance().getBracePlacementStyle().toString().equals("Inline") ) {
			this.repo.setBrkMatchPercent( ((double)inLine / (double)(inLine + nextLine)) );
		}
		if ( UserSettings.getInstance().getBracePlacementStyle().toString().equals("Newline") ) {
			this.repo.setBrkMatchPercent( ((double)nextLine / (double)(inLine + nextLine)) );
		}
		if ( UserSettings.getInstance().getIndentationRequirement().toString().equals("None") ) {
			this.repo.setBrkMatchPercent(0);
		}
		
		// correctness check
		if (errorCount == 0) {
			this.repo.setBrkCorrectPercent(1.0);
		} else {
			double result = (  ( (double)inLine + (double)nextLine - (double)errorCount) ) / ( (double)inLine + (double)nextLine);
			if (result < 0.0) {
				result = 0.0;
			}
			this.repo.setBrkCorrectPercent(result);
		}
		
	}
}


