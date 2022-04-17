package General;
// main class
// main > handler > analyzer > flagging > production > done

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Gui.UserSettings.BracketStyles;
import Gui.UserSettings.IndentationTypes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import picocli.CommandLine;
import mutators.IDAnalyzer;
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
			
			
			System.out.println("done");
		}
		
	}
}