package mutators;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import General.Reportable;
import General.Reporter;
import General.Template;
import Gui.UserSettings;

	// Going to use this class to generate and report on the simple features of the product
public class MiscAnalyzer implements Reportable {

	public int lineLength;
	public String filepath;
	public BufferedReader fileReader;
	public Reporter repo;
	
	public MiscAnalyzer(String filepath, int lineLength, Reporter repo) throws Exception {
		this.filepath = filepath;
		this.repo = repo;
		this.lineLength = lineLength;
		try {
			fileReader = new BufferedReader(new FileReader(filepath));
		} catch (FileNotFoundException e) {
			// TODO Add messaged that filepath couldn't be found
			e.printStackTrace();
		}
		this.importAtTop();
		this.commentAtTopOfFile();
	}
	
	
	public void Analyze() throws Exception {
		LineNumberReader lnr = new LineNumberReader(new FileReader(filepath));
		String temp = lnr.readLine();
		
		while (temp != null) {
			lineLength(temp);
		}
		
		lnr.close();
	}
	
	
	// methods to be filled out, can change return types
	public void lineLength(String read) {
		char[] ca = read.toCharArray();
		if (ca.length > 80) {
			
		}
	}
	
	//Parses file to detect if all imports are at the top.
	public boolean importAtTop() throws IOException {
		boolean pastTopOfFile;
		for (String line = fileReader.readLine(); line != null; line = fileReader.readLine()) {
			boolean importFound;
			
			importFound = line.strip().matches("^\\s*import(.*);+");
			
			//Assumes that if any of these keywords have appeared you've surpassed the "top" of the file.
			pastTopOfFile = line.matches("^\\s*(public|final|abstract|private|static|protected|class|enum)(.*)");
			
			//Return false if any imports found after the top of the file
			if(pastTopOfFile == true && importFound == true) {
				repo.setImportsAtTop(false);
				return false;
			}
		}
		repo.setImportsAtTop(true);
		//Only when no imports are found or all are at the top
		return true;
	}
	
	//Parses file to ensure the first entry to the file is a comment.
	public boolean commentAtTopOfFile() throws IOException {
		String line = fileReader.readLine();
		if( ( line != null) && line.isBlank()) {
			line = fileReader.readLine();
		}
		if (line == null) {
			repo.setCommentAtTop(false);
			return false;
		}
		if (line.matches("^(\\/\\/|\\/\\*).*")) {
			repo.setCommentAtTop(true);
		} else {
			repo.setCommentAtTop(false);
		}
		
		return line.matches("^(\\/\\/|\\/\\*).*");
	}
	
	@Override
	public String report() {
		int count = 0;
		int passedChecks = 0;
		
		// TODO Remove and replace with the instance variable for report per file
		Template report = new Template(filepath);
		return null;
	}

}
