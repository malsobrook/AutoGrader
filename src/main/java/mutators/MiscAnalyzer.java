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
	Reporter repo;
	public BufferedReader fileReader;
	
	public MiscAnalyzer(String filepath, Reporter handlerReporter) {
		this.filepath = filepath;
		this.lineLength = UserSettings.getInstance().getMaxLineLength();
		this.repo = handlerReporter;
		try {
			fileReader = new BufferedReader(new FileReader(filepath));
		} catch (FileNotFoundException e) {
			// TODO Add messaged that filepath couldn't be found
			e.printStackTrace();
		}
		report();
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
		try {
			fileReader = new BufferedReader(new FileReader(this.filepath));
		} catch (FileNotFoundException e) {
			// TODO Add messaged that filepath couldn't be found
			e.printStackTrace();
		}
		for (String line = fileReader.readLine(); line != null; line = fileReader.readLine()) {
			boolean importFound;
			
			importFound = line.strip().matches("^\\s*import(.*);+");
			
			//Assumes that if any of these keywords have appeared you've surpassed the "top" of the file.
			pastTopOfFile = line.matches("^\\s*(public|final|abstract|private|static|protected|class|enum)(.*)");
			
			if(!pastTopOfFile && importFound) {
				fileReader.close();
				return true;
			}
			else if(pastTopOfFile) {
				fileReader.close();
				return false;
			}
		}
		//Only when no imports are found or all are at the top
		fileReader.close();
		return true;
	}
	
	public void commentHandler() {

	}
	
	//Parses file to ensure the first entry to the file is a comment.
	public boolean commentAtTopOfFile() throws IOException {
		boolean pastTopOfFile;
		try {
			fileReader = new BufferedReader(new FileReader(this.filepath));
		} catch (FileNotFoundException e) {
			// TODO Add messaged that filepath couldn't be found
			e.printStackTrace();
		}
		for (String line = fileReader.readLine(); line != null; line = fileReader.readLine()) {
			boolean commentFound;
			
			commentFound = line.strip().matches("^(\\/\\/|\\/\\*).*");
			
			//Assumes that if any of these keywords have appeared you've surpassed the "top" of the file.
			pastTopOfFile = line.matches("^\\s*(public|final|abstract|private|static|protected|class|enum)(.*)");
			
			if(commentFound == true && pastTopOfFile == false) {
				fileReader.close();
				return true;
			}
			else if(pastTopOfFile == true && commentFound == false) {
				fileReader.close();
				return false;
			}
		}
		fileReader.close();
		return false;
	}
	
	@Override
	public void report() {
		int count = 0;
		int passedChecks = 0;
		
		try {
			if(UserSettings.getInstance().isImportsAtTopOfFile()) {
				repo.setMAImportAtTop(this.importAtTop());
				passedChecks += repo.isMAImportAtTop() ? 1 : 0;
				count++;
			}
			if(UserSettings.getInstance().isCommentBlockAtTopOfFile()) {
				repo.setMACommentAtTop(this.commentAtTopOfFile());
				passedChecks += repo.isMACommentAtTop() ? 1 : 0;
				count++;
			}
			
			if(count != 0) {
				repo.setMACorrectPercent(((double)passedChecks/(double)count) * 100);
			}
		} catch (IOException e) {
			// TODO Add message for IOException failure.
			e.printStackTrace();
		}
	}

}
