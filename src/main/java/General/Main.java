package General;
// main class
// main > handler > analyzer > flagging > production > done

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import picocli.CommandLine;
import mutators.IDAnalyzer;
import mutators.Handler;

public class Main {
	//Populates from CommandLineParser if via CLI, or MainViewController if via GUI
	public static List<File> fileList = new ArrayList<File>();
	private static String filepath = "userSettings.json";
	
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
		
		Map<String, ?> handleMap = getJson(filepath);
		
		for(File file : fileList) {
			Handler trs = new Handler(file.getAbsolutePath(), handleMap);
			try {
				trs.handle();
			} catch (Exception e) {
				System.out.println("error on pass to Translator");
				e.printStackTrace();
			}
			
			
			System.out.println("done");
		}
	}
	
		// returns a map version of the json file stored locally
	public static Map<String, ?> getJson(String filePath) throws Exception{
		Gson gson = new Gson();
		Reader reader = Files.newBufferedReader(Paths.get(filePath));
		Map<String, ?> map = gson.fromJson(reader, Map.class);
		for (Map.Entry<String, ?> entry : map.entrySet()) {
	        // System.out.println(entry.getKey() + "=" + entry.getValue());
	    }
		reader.close();
		
		return map;
	}
}