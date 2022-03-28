package mutators;

import java.io.BufferedReader;
import java.io.FileReader;
import General.Reporter;
	
	// class is passed filepath of translated file to assess the consistency of indentation
	// does not currently assess if the indentation is proper or in line with java style expectations
	// after testing, will insert a function that checks against a preselected indentation convention (tabs or spaces)
public class IndentAnalyzer implements Analyzer{
	
	private String filePath;
		// counters
	private int idtTab = 0;
	private int idtSpace = 0;
	private int spaceCount = 0;
	private int tempCount = 0;
	private int blankCount;
	private int exptIdt = 0;
	public int lineNumb = 0;
	private int bCount = 0;
	private int nestLvl = 0;
		// checker variables
	private boolean bt = false;
	private boolean cont = false;
	private boolean errorFlag = false;
	
	// preference variables
	private boolean OneTBS = true;		// default option is 1TBS (brace placement style: inline)
	private int spaceIndex;	//when customizing how many spaces is an indent, change this var from default (4) in constructor
	
	private boolean checker = false;
	public Reporter repo = new Reporter("Indent");
	// meta symbols, loops  then class/methods
	private char[] iso = {'@', 	  '!', 	   '?',   '#',  '^',   '*',   '%',    '`'};
						// if,   else,   while,   for,  try, catch, class, method 
	
	public IndentAnalyzer(String filepath) throws Exception {
		this.filePath = filepath;
		this.spaceIndex = 4;
		this.analyze();
	}
	
	@Override
	public String analyze() throws Exception {
		BufferedReader bfr = new BufferedReader(new FileReader(filePath));
		String tempLine = bfr.readLine();
		lineNumb++;
		char charArray[];
		
		// tempLine not being null implies there will be char to make into array
		// char by char assess indent level, ending when encountering a character not representing space or tab
		// indentation ends upon the first character
		System.out.println("	  Actual	Expected");
		while (tempLine != null) {
			charArray = tempLine.toCharArray();
			int tc = indentCounter(charArray);	// count the indents, counts separated by type. returns indent level for the line
			indentCorrecter(charArray, tc); 
			
			
			tempLine = bfr.readLine();
			lineNumb++;
			
		}
		
		
		bfr.close();
		// testing feature
		String str = "Indent Consistency:	Spaces: " + idtSpace + "	Tabs: " + idtTab;
		String report = repo.report();
		if (!(report == null)) {
			str = str + "\n" + report;
		} else {
			str = str + "\nNo indent discrepancies detected";
		}
		
		
		System.out.println("\n" + str);
		// remove later
		return str;
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
	
	public boolean isSpace(char c) {
		if (c == '$') {
			return true;
		} else {
			return false; 
		}
	}
	
	public boolean isTab(char c) {
		if (c == '&') {
			return true;
		} else {
			return false; 
		}
	}
	
		// parse line as char array looking for specific symbols that require indents. Also examines the indent level 
		// in comparison to the expected level
	public void indentCorrecter(char charArray[], int idtLevel) {
			// cont determines if a loop is currently in place and searches for the ending '}'
		if (cont) {
			for (int i=0; i<charArray.length; i++) {
				if (charArray[i] == '}') {
					if (bCount == 0) {
						exptIdt--;
						if ( nestLvl > 0) {
							nestLvl--;
							cont = true; // ensure cont stays true, nested loop level decrease by 1.
						} else {
							cont = false;
						}
						break;
					} else {
						bCount--;
					}
				}
				if ( keySearch(charArray[i]) ) {
					bCount--;
				}
				if (charArray[i] == '{') {
					bCount++;
					if (errorFlag) {
						bCount--;
					}
					errorFlag = false;
				}
			}
		}
			// assess indent level to expected indent level
		System.out.println("Line:" + lineNumb + "        " + idtLevel+ "    " + exptIdt);
		if (idtLevel != exptIdt) {
			if ( idtLevel != 321) {
				repo.errorGen(lineNumb, "Indent Level Incorrect");
			}
		}
		
		if (bt) {
			for (int i=0; i<charArray.length;i++) {
				if (charArray[i] == '{' ) {
					bt = false;
					exptIdt++;
				}
			}
			if (bt) {
				bt = false; 
				repo.errorGen(lineNumb, "Missing bracket according to K&R Style");
			}
			
		}
		
			// line parser
		for (int i=0; i<charArray.length;i++) {
			if (keySearch(charArray[i])) {
				if (OneTBS) {
					boolean bool = false;	// used to determine if '{' is found in-line or not
					exptIdt++;
					for (int j=i; j<charArray.length;j++) {
						if (charArray[j] == '{') {
							bool = true;
							if (cont) {
								nestLvl++;
							}
							cont = true;
						}
					}
					if (bool == false) {
						repo.errorGen(lineNumb, "Missing bracket according to 1TBS");
						if (cont) {
							nestLvl++;
						}
						cont = true;
						errorFlag = true;	// used when '{' was anticipated but not found
					}
				} else {
					for (int j=i; j<charArray.length;j++) {
						if (charArray[j] == '{') {
							repo.errorGen(lineNumb, "Bracket not placed according to K&R Style");
						}
					}
					bt = true;
				}
				
			}
			
			

		}
		
	}
	
	public boolean bracketSearch(char c) {
		if (c == '{') {
			return true;
		}
		return false;
	}
	
	public boolean keySearch(char c) {
		for (int i=0; i < iso.length; i++) {
			if (c == iso[i]) {
				return true;
			}
		}
		return false;
	}
	
		// constructor finder, access modifier > no other keywords > () > '{'
	
		// takes char array of line being read and adjust relevant variables to count indents, returns indent level of line read.
	public int indentCounter(char charArray[]) {
		tempCount = 0;
		checker = false;
		for (char c: charArray) {
			if (checker ==  false) {	// checker indicates if we have encountered a non indent character, then skips the rest of the line.
				if ( isSpace(c) || isTab(c) ) {
					if ( isSpace(c) ) {
						// space count +1, asses if equal to spaceIndex var, default is currently 4, then reset space counter
						spaceCount++;
						if (spaceCount >= spaceIndex) {
							spaceCount = 0;
							idtSpace++;
							tempCount++;
						}
					}
					if ( isTab(c) ) {
						idtTab++;
						tempCount++;
					}
				} else {
					checker = true;	
				}
			}
		}
			// reset the spaceCount to 0 before reading a newline
		spaceCount = 0;
			// check if line is blank, if it is then return a large number that indicates line to be skipped in indentCorrector() because it is blank
		if(charArray.length ==  0) {
			return 321;
		}
		blankCount = tempCount;
		return tempCount;
	}
	
	public void testermethodremove() {
		if(true) {
			
		} else 	if (false) {
			
		} else if (false) {
			
		}	else 			if (true) {
			
		}
		
	}
}
