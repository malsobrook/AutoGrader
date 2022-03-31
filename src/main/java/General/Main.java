package General;
// main class
// main > translator > analyzer > flagging > production > done

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;

import mutators.IndentAnalyzer;
import mutators.Translator;


public class Main {
	
	private static String filepath;
	
	public static void main(String args[]) throws Exception {
		
			// set better look and feel. Throws happen here.
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		
		//do main things
			// open file browser get and store file path
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			java.io.File file = fc.getSelectedFile();
			System.out.println(file.getPath());
			filepath = file.getPath();
		}
		
			// send file path to translator
		Translator trs = new Translator(filepath);
		try {
			trs.translate();
		} catch (Exception e) {
			System.out.println("error on pass to Translator");
			e.printStackTrace();
		}
		
		
		System.out.println("\n\n\n|-----------------------------Report--------------------------------|");
		
		System.out.println("done");
		
		// main program should have no other function besides launching and passing to respective mutators. ???
		// maybe remain open window to use the function multiple times
	}
}
