package General;

import javax.swing.UIManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
	}
}