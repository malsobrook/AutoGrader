package mutators;

import General.Reporter;
import java.io.FileReader;
import java.io.LineNumberReader;


public class BracketAnalyzer implements Analyzer{

	private final String filepath;
	private int inLine = 0;
	private int nextLine = 0;
	private int before;
	private int after;

	private boolean oneTrueBraceStyle = true;
	private boolean allmanBraceStyle = false;

	private char[] searchingFor = {'`'};

	private LineNumberReader bracketReader;



	public Reporter bracketReporter = new Reporter("Bracket");


	
	public BracketAnalyzer(String filepath) throws Exception {
		this.filepath = filepath;
		this.analyze();

	}

	@Override
	public void aggregateFunc() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String keyFinder(String line) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void analyze() throws Exception {
		this.bracketReader = new LineNumberReader(new FileReader(filepath));
		String temporaryLine = null;
		char[] charArray;


		while ((temporaryLine = bracketReader.readLine()) != null) {
			charArray = temporaryLine.toCharArray();
			bracketChecker(charArray);
			before = bracketReader.getLineNumber();
			System.out.println("Line " + bracketReader.getLineNumber() + ": 1TBS: " + inLine + " K&R: " + nextLine);
		}
		
		String bracketReport = bracketReporter.report();

		bracketReader.close();
	}


	public void bracketChecker (char characterArray[]) {
		// for int i = 0 ; i<CA.length;i++
			//




		for (int i = 0; i < characterArray.length; i++) {
			if (methodSearch(characterArray[i])) {
				System.out.println("detected");
				if (characterArray[i] == '{') {
					after = bracketReader.getLineNumber();
					if (before == after) {
						oneTrueBraceStyle = true;
						inLine++;
					} else if (after > before) {
						allmanBraceStyle = true;
						nextLine++;
					}
				}
			} else {
				//System.out.println("noth");
			}
		}
	}

	public boolean methodSearch(char character) {
		for (int i=0; i < searchingFor.length; i++) {
			if (character == searchingFor[i]) {
				return true;
			}
		}
		return false;
	}

}


