package mutators;

import General.Reportable;
import General.Reporter;
import java.io.FileReader;
import java.io.LineNumberReader;


public class BracketAnalyzer implements Reportable{

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
		// for int i = 0 ; i<CA.length;i++
			// if find keyword token
				// determine if bracket is inline, if not, expect on next
				// seperate method for this ^
				// make appropriate changes for inline dected
		
		
		for (int i = 0; i < CA.length; i++) {
			if (keywordSearch(CA[i])) {
				if(!(detectBracketInLine(CA, i))) {
					anticipateNext = true;
				}
			}
		}






//		for (int i = 0; i < CA.length; i++) {
//			if (methodSearch(CA[i])) {
//				System.out.println("detected");
//				if (CA[i] == '{') {
//					after = bracketReader.getLineNumber();
//					if (before == after) {
//						oneTrueBraceStyle = true;
//						inLine++;
//					} else if (after > before) {
//						allmanBraceStyle = true;
//						nextLine++;
//					}
//				}
//			} else {
//				//System.out.println("noth");
//			}
//		}
	}

	
		// uses char array that has found a keyword at given index, starts from that index to see if there is a bracket after the keyword inline.
	private boolean detectBracketInLine(char[] ca, int index) {
		for (int i = index; i < ca.length; i++) {
			if (ca[i] == '{') {
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
	public String report() {
		// TODO Auto-generated method stub
		return null;
	}

}


