package General;
// main class
// main > handler > analyzer > flagging > production > done

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mutators.IndentAnalyzer;
import picocli.CommandLine;
import mutators.Handler;

public class Main {
	
	//Populates from CommandLineParser if via CLI, or MainViewController if via GUI
	public static List<File> fileList = new ArrayList<File>();
	
	public static void main(String args[]) throws Exception {
		if(args.length > 0) {
			CommandLineParser parser = new CommandLineParser();
			CommandLine.run(parser, args);
		}
		else {
			Gui.App.start();
		}
		
		try {
			// set better look and feel. Throws happen here.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		for(File file : fileList) {
			Handler trs = new Handler(file.getAbsolutePath());
			try {
				trs.handle();
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
}
