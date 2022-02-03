package mutators;

import java.io.BufferedReader;
import java.io.FileReader;
	
	// class is passed filepath of translated file to assess the consistency of indentation
	// does not currently assess if the indentation is proper or in line with java style expectations
	// after testing, will insert a function that checks against a preselected indentation convention (tabs or spaces)
public class IndentAnalyzer implements Analyzer{

	private String filePath;
	private int indentCount;	// dated, possible remove
	private int idtTab = 0;
	private int idtSpace = 0;
	private int spaceCount = 0;
	private int spaceIndex;	//when customizing how many spaces is an indent, change this var from default (4) in constructor
	private boolean checker = false;
	
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
		while(tempLine != null) {
			charArray = tempLine.toCharArray();
			checker = false;
			for (char c: charArray) {
				if (checker ==  false) {	// checker indicates if we have encountered a non indent character, thus skips the rest of the line.
					if ( isSpace(c) || isTab(c) ) {
						if ( isSpace(c) ) {
							// space count +1, asses if equal to spaceIndex var, default is currently 4, then reset space counter
							spaceCount++;
							if (spaceCount >= spaceIndex) {
								indtReset();
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
			tempLine = bfr.readLine();
			
		}
		
		
		bfr.close();
			// testing feature
		String str = "Indent Consistency:	Spaces: " + idtSpace + "	Tabs: " + idtTab;
		System.out.println(str);
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
	
	public void indtReset() {
		spaceCount = 0;
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

}
