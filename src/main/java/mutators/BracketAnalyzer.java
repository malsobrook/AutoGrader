package mutators;

import General.Reportable;
import General.Reporter;
import java.io.FileReader;
import java.io.LineNumberReader;


public class BracketAnalyzer implements Reportable {

	private final String filepath;
	private int inLine = 0;
	private int nextLine = 0;

	private boolean anticipateNext = false;
	private boolean oneTrueBraceStyle = true;
	private boolean allmanBraceStyle = false;

	private char[] searchingFor = {'@', 	'!', 	'?',	 '#', 	'^', 	'*', 	'%', 		'`'};
	//								if, 	else, 	while, 	  for,  try,  catch,  class, (represents methods/constructors)

	private LineNumberReader bracketReader;
	public Reporter reporter = new Reporter("Bracket");


	public BracketAnalyzer(String filepath) throws Exception {
		this.filepath = filepath;
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

		
		bracketReader.close();
	}


	public void bracketChecker (char CA[]) {
		
		// if anticipateNext is true
			// set anticipateNext to false
			// call method detectBracketNextLine()
				// if method returns true, we found next line bracket, make appropriate changes
			// else call an error "bracket anticipated, but never found
					
		
		// for int i = 0 ; i<CA.length;i++
			// if find keyword token
				// determine if bracket is inline, if not, expect on next
				// seperate method for this ^
				// make appropriate changes for inline dected
		
		
		if (anticipateNext) {
			if( ! ( detectBracketNextLine(CA) ) ) {
				// make appropriate logs / changes
			}
		}
		
		for (int i = 0; i < CA.length; i++) {
			if (keywordSearch(CA[i])) {
				if(!(detectBracketInLine(CA, i))) {
					anticipateNext = true;
					// make appropriate logs / changes
				}
			}
		}
		
	}

	
		// uses char array that has found a keyword at given index, starts from that index to see if there is a bracket after the keyword inline.
	private boolean detectBracketInLine(char[] ca, int index) {
		for (int i = index; i < ca.length; i++) {
			if (ca[i] == '{') {
				System.out.println("found inline bracket on:" + bracketReader.getLineNumber()); 	// this is only for testing purposes, delete when no longer necessary
				return true;
			}
		}
		return false;
	}
	
	private boolean detectBracketNextLine(char[] ca) {
		// assess if there's a bracket in this line, return true if true, otherwise false
		
		return true;	// placeholder, change as necessary
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
	public String report() {
		// TODO Auto-generated method stub
		return null;
	}

}


