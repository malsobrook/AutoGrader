package mutators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Translator {
	private String ogfilepath;
	private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private String tempLine;
	private String[] keywords = {"if", "else", "while", "for", "class", "try", "catch", "throws", "interface"}; 
	private String[] keywordSubs = { "@", ">", "<", "#", "%", "^", "*", "-", "+"};		//"_, $, and & " are taken by letters, spaces, and tabs, respectively
	
	public Translator(String ogfilepath) {
		this.ogfilepath = Objects.requireNonNull(ogfilepath);
	}
	
		// takes given file and returns easy to read version, translator only deals with indents
	public void translate() throws Exception {
		BufferedReader bfr = new BufferedReader(new FileReader(ogfilepath));
		BufferedWriter bwr = new BufferedWriter(new FileWriter("C:\\Users\\Michael\\OneDrive\\Documents\\test.java"));
		tempLine = bfr.readLine();
		
		while(tempLine != null) {
			tempLine = aggregateFunction(tempLine);
			bwr.write(tempLine + "\n");
			tempLine = bfr.readLine();
		}
		
		bfr.close();
		bwr.close();
		
	}
	
	public String aggregateFunction(String input) {
		String output = input;
		output = output.toLowerCase();
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
	
	
	public String spaceTabTransform(String input) {
		String output = null;
		output = input.replace(' ', '$');
		output = output.replace('\t', '&');
		return output;
	}
	
	public String keywordSwap(String input) {
		String output = input;
		for (int i=0; i<keywords.length; i++) {
			output = output.replaceAll("\\b" + keywords[i] + "\\b", keywordSubs[i]);
		}
		return output;
	}
	
	public void close(BufferedReader bfr) throws IOException {
		bfr.close();
	}
	
	public void passLine(String line) {
		//TODO pass line before translation to naming convention analyzer.
	}
	
}
