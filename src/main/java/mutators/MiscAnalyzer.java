package mutators;

import java.io.FileReader;
import java.io.LineNumberReader;

import General.Reportable;

	// Going to use this class to generate and report on the simple features of the product
public class MiscAnalyzer implements Reportable {

	public int lineLength;
	public String filepath;
	
	public MiscAnalyzer(String filepath, int lineLength) {
		this.filepath = filepath;
		this.lineLength = lineLength;
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
	
	public void importAtTop() {
		
	}
	
	public void commentHandler() {
		
	}
	
	public void commentAtTopOfFile() {
		
	}
	
	@Override
	public String report() {
		// TODO Auto-generated method stub
		return null;
	}

}
