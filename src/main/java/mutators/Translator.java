package mutators;

import java.io.*;
import java.util.Objects;
import mutators.*;

public class Translator {
	private String ogfilepath;
	private String newPath = "test2.java";
	private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private String tempLine;
	private String[] keywords =    {"if", "else", "while", "for", "class", "try", "catch", "throws", "interface", "public", "private", "protected"}; 
	private String[] keywordSubs = { "@",    ">",     "<",   "#",     "%",   "^",     "*",      "-",         "+", 	   "~",       ":",         "/"};		
		//"_, $, and & " are taken by letters, spaces, and tabs, respectively
	
	public Translator(String ogfilepath) {
		this.ogfilepath = Objects.requireNonNull(ogfilepath);
	}
	
		// takes given file and returns easy to read version, translator only deals with indents and keywords
	public void translate() throws Exception {
		BufferedReader bfr = new BufferedReader(new FileReader(ogfilepath));
		String path = createDumpFile();
		BufferedWriter bwr = new BufferedWriter(new FileWriter(path));
		tempLine = bfr.readLine();
		
		while(tempLine != null) {
			tempLine = aggregateFunction(tempLine);
			bwr.write(tempLine + "\n");
			tempLine = bfr.readLine();
		}
		
		bfr.close();
		bwr.close();
		
		IndentAnalyzer idt = new IndentAnalyzer(path);
	}
	
	
	
	public String aggregateFunction(String input) {
		String output = input;
		output = output.toLowerCase();
		output = whiteOut(output);
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
		String output = null;
		output = input.replace(' ', '$');
		output = output.replace('\t', '&');
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
	
		// creates a dumpfile for translated documents that will be stored locally, temporarily. 
		// May in future support option to check/validate files in dumplocation to prevent overwriting
	public String createDumpFile() {
		String path = System.getProperty("user.home") + File.separator + "testing.java";
		File dumpFile = new File(path);
		return dumpFile.getPath();
	}
	
		// closes the reader, may remove
	public void close(BufferedReader bfr) throws IOException {
		bfr.close();
	}
	
		// passes an untranslated line to an Analyzer that may require it
	public void passLine(String line) {
		//TODO pass line before translation to naming convention analyzer.
	}
	
		// passes the original file path to an Analyzer that may require it
	public void passFile(String filePath) {
		//TODO pass newPath variable to analyzer
	}
}
