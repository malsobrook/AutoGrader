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
	private int exptIdt = 0;
	public int lineNumb = 0;
	private int bCount = 0;
		// checker variables
	private boolean bt = false;
	private boolean cont = false;
	
		// preference variables
	private boolean OneTBS = true;		// default option is 1TBS (brace placement style: inline)
	private int spaceIndex;	//when customizing how many spaces is an indent, change this var from default (4) in constructor
	
	private boolean checker = false;
	public Reporter repo = new Reporter("Indent");
		// meta symbols, loops  then class/methods
	private char[] iso = {'@', '!', '?', '#', '^', '*'};
	private char[] cm = {};
	
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
		System.out.println(repo.report());
		
		System.out.println(str);
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
			// assess indent level to expected indent level
		if (cont) {
			for (int i=0; i<charArray.length; i++) {
				if (charArray[i] == '}') {
					if (bCount == 0) {
						exptIdt--;
						cont = false;
						break;
					} else {
						bCount--;
					}
				}
				if (charArray[i] == '{') {
					bCount++;
				}
			}
		}
		
		System.out.println("Line:" + lineNumb + "        " + idtLevel+ "    " + exptIdt);
		if (idtLevel != exptIdt) {
			repo.errorGen(lineNumb, "Indent Level Incorrect");
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
					boolean bool = false;
					exptIdt++;
					for (int j=i; j<charArray.length;j++) {
						if (charArray[j] == '{') {
							bool = true;
							cont = true;
						}
					}
					if (bool == false) {
						repo.errorGen(lineNumb, "Missing bracket according to 1TBS");
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
		return tempCount;
	}
}
