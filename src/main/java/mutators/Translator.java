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
	
	public Translator(String ogfilepath) {
		this.ogfilepath = Objects.requireNonNull(ogfilepath);
		
		// call method after acquistion of filepath to begin translation
	}
	
		// takes given file and returns easy to read version, translator only deals with indents
	public void translate() throws Exception {
		BufferedReader bfr = new BufferedReader(new FileReader(ogfilepath));
		BufferedWriter bwr = new BufferedWriter(new FileWriter("C:\\Users\\Michael\\OneDrive\\Documents\\test.java"));
		tempLine = bfr.readLine();
		
		while(tempLine != null) {
			tempLine = tempLine.toLowerCase();
			tempLine = noLetters(tempLine);
			tempLine = spaceTabTransform(tempLine);
			bwr.write(tempLine + "\n");
			tempLine = bfr.readLine();
		}
		
		bwr.close();
		
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
	
	public void close(BufferedReader bfr) throws IOException {
		bfr.close();
	}
	
}
