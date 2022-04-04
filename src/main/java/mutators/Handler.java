package mutators;

import java.io.*;
import java.util.Objects;
import mutators.*;

public class Handler {
	private String ogfilepath;
	private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private String tempLine;
	private String[] keywords =    {"if", "else", "while", "for", "class", "try", "catch", "throws", "interface"}; 
	private String[] keywordSubs = { "@",    "!",     "?",   "#",     "%",   "^",     "*",      "-",         "+"};
		//"_, $, and & " are taken by letters, spaces, and tabs, respectively
	
	public Handler(String ogfilepath) {
		this.ogfilepath = Objects.requireNonNull(ogfilepath);
	}
	
		// takes given file and returns easy to read version, translator only deals with indents and keywords
	
	// TODO VIST THIS METHOD TO REMOVE EXCESS WRITERS!!!!!!!!!!!!!!!
	public void handle() throws Exception {
		BufferedReader bfr = new BufferedReader(new FileReader(ogfilepath));
		String path = createDumpFile();
		BufferedWriter bwr = new BufferedWriter(new FileWriter(path));
		tempLine = bfr.readLine();
		
		while(tempLine != null) {
				// testing newer, limited translate to path2
			String thing = tempLine;
			thing = whiteOut(thing);
			thing = spaceTabTransform(thing);
			
			
				// original translate to path
			tempLine = aggregateFunction(tempLine);
			bwr.write(tempLine + "\n");
			tempLine = bfr.readLine();
		}
		
		bfr.close();
		bwr.close();
		
		// BracketAnalyzer bra = new BracketAnalyzer(path);
		IDA ida = new IDA(path);
		
	}
	
	
	
	public String aggregateFunction(String input) {
		String output = input;
		output = output.toLowerCase();
		output = whiteOut(output);
		output = removeSpecialChar(output);
		output = commentRemover(output);
		output = emptyQuotes(output);
		output = detectConstructor(output);
		output = detectMethod(output);
		output = elifCondenser(output);
		output = bracketCondenser(output);
		output = keywordSwap(output);
		output = noLetters(output);
		output = spaceTabTransform(output);
		return output;
	}
	
		// removes all alphabet letters
	public String noLetters(String input) {
		String output = input;
		for (char x: alphabet) {
			output = output.replace(x, '_');
		}
		return output;
	}
	
	public String removeSpecialChar(String input) {
		String output = input;
		for(int i=0; i<keywordSubs.length;i++) {
			output = output.replaceAll("(\\@|\\#|\\%|\\^|\\*|\\-|\\:|\\~|\\+|\\!|\\?|\\<|\\>)", "_");
		}
		return output;
	}
	
		// if string is entirely blank (pre translate), simply turns to empty string to avoid analyzer confusion.
	public String whiteOut(String input) {
		String output = input;
		if (output.isBlank()) {
			return "";
		} 
		return output;
	}
	
		// turns all spaces to $ and all tabs to &
	public String spaceTabTransform(String input) {
		String output = input;
		output = input.replace(' ', '$');
		output = output.replace('\t', '&');
		return output;
	}
	
		// empties ALL characters out of quotes to avoid reading of String Literals
	public String emptyQuotes(String input) {
		String output = input;
		output = output.replaceAll("\\'.*\\'", "_");
		output = output.replaceAll("\\\".*\\\"", "_");
		return output;
	}
		
		// gets rid of SINGLE LINE comments, To Be Updated
	public String commentRemover(String input) {
		String output = input;
		output = output.replaceAll("//.*", "_");
		return output;
	}
	
		// condenses "else if" statements into one token, to avoid reading them as two tokens
	public String elifCondenser(String input) {
		String output = input;
		output = output.replaceAll("else\\s+if", "!");
		return output;
	}
	
		// condense/remove brackets that open and close in the same line. Requires testing for nested occurrences
	public String bracketCondenser(String input) {
		String output = input;
		output = output.replaceAll("\\{.*\\}", "_'");
		return output;
	}
	
		// searches for all given keywords and replaces them with the corresponding symbol from keywordSub list
	public String keywordSwap(String input) {
		String output = input;
		for (int i=0; i<keywords.length; i++) {
			output = output.replaceAll("\\b" + keywords[i] + "\\b", keywordSubs[i]);
		}
		return output;
	}
	
		// inputs token in line if a method declaration is detected.
	public String detectMethod(String input) {
		if(input.matches("(?s).*\\b(public|private|protected)\\b\\s+[\\$_\\w\\[\\]\\<\\>]+\\s+[\\$_\\w]+\\s*\\([^\\)]*\\).*")) {
			input = (input.substring(0, input.length()/2) + "`" + input.substring(input.length()/2));
		}
		return input;
	}
	
		// inputs token in line if constructor is detected. 
	public String detectConstructor(String input) {
		if(input.matches("(?s).*\\b(public|private|protected)\\b\\s+[\\$_\\w]+\\s*\\([^\\)]*\\).*")) {
			input = (input.substring(0, input.length()/2) + "`" + input.substring(input.length()/2));
		}
		return input;
	}
	
		// creates a dumpfile for translated documents that will be stored locally, temporarily. 
		// May in future support option to check/validate files in dumplocation to prevent overwriting
	public String createDumpFile() {
		String path = System.getProperty("user.home") + File.separator + "dump.java";
		File dumpFile = new File(path);
		return dumpFile.getPath();
	}
	
	public String newFile(String path) {
		File newFile = new File(System.getProperty("user.home") + File.separator + path);
		return newFile.getPath();
		
	}
	
		// closes the reader, may remove
	public void close(BufferedReader bfr) throws IOException {
		bfr.close();
	}
	
	public String report() {
		// for each subfile that makes a report
			// append that report to a stringbuilder via its method
		// then return that String
		
		return null;
	}
}
