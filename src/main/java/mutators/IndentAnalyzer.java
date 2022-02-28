package mutators;

import java.io.BufferedReader;
import java.io.FileReader;
	
	// class is passed filepath of translated file to assess the consistency of indentation
	// does not currently assess if the indentation is proper or in line with java style expectations
	// after testing, will insert a function that checks against a preselected indentation convention (tabs or spaces)
public class IndentAnalyzer implements Analyzer{

	private String filePath;
	private int idtTab = 0;
	private int idtSpace = 0;
	private int spaceCount = 0;
	private int exptIdt = 0;
	private int actualIdt = 0;
	private int bktCountO = 0;
	private int bktCountC = 0;
	private int spaceIndex;	//when customizing how many spaces is an indent, change this var from default (4) in constructor
	private boolean checker = false;
	private char[] iso = {'@', '<', '>', '#', '^', '*'};
	
	public IndentAnalyzer(String filepath) throws Exception {
		this.filePath = filepath;
		this.spaceIndex = 4;
		this.analyze();
	}
	
	@Override
	public String analyze() throws Exception {
		BufferedReader bfr = new BufferedReader(new FileReader(filePath));
		String tempLine = bfr.readLine();
		char charArray[];
		
			// tempLine not being null implies there will be char to make into array
			// char by char assess indent level, ending when encountering a character not representing space or tab
			// indentation ends upon the first character
		while (tempLine != null) {
			charArray = tempLine.toCharArray();
			indentCounter(charArray);	// count the indents, counts separated by type.
			
				// logic to assess indent level
			
			
			tempLine = bfr.readLine();
			
		}
		
		
		bfr.close();
			// testing feature
		String str = "Indent Consistency:	Spaces: " + idtSpace + "	Tabs: " + idtTab;
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
	public void indentCorrecter(char charArray[]) {
		for (int i=0; i<charArray.length;i++) {
			if (linearSearch(charArray[i])) {
				exptIdt++;
			}
		}
		
	}
	
	public boolean linearSearch(char c) {
		boolean out = false;
		for (int i=0; i < iso.length; i++) {
			if (c == iso[i]) {
				return out = true;
			}
		}
		
		return out;
	}
	
	public boolean bracketCheck() {
		//todo
		return false;
	}
	
		// void for now / takes char array of line being read and adjust relevant variables to count indents
	public void indentCounter(char charArray[]) {
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
						}
					}
					if ( isTab(c) ) {
						idtTab++;
					}
				} else {
					checker = true;	
				}
			}
		}
			// reset the spaceCount to 0 before reading a newline
		spaceCount = 0;
	}
}
