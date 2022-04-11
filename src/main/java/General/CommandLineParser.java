package General;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Gui.UserSettings;
import Gui.UserSettings.BracketStyles;
import Gui.UserSettings.IndentationStyles;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class CommandLineParser implements Runnable {  
    @Option(names = { "-h", "--help", "-?", "-help"}, usageHelp = true, description = "Display usage help.")
    public boolean help;
    
    @Option(names = { "-s", "--save"}, description = "Save settings to JSON file.")
    public boolean saveSettings;
    
    @Option(names = { "-j", "--json"}, description = "Use JSON file for settings")
    public boolean useJson;
    
    @Parameters(index = "0..*", description = "File(s) to be graded.")
    public List<File> files = new ArrayList<File>();
    
    @Option(names = { "-d", "--dir"}, description = "Directory to be graded.")
    public String directory;
    
    //FORMATTING
    @Option(names = { "--indentationRequirement"}, description = "Valid values: Option1, Option2, Option3")
    public String indentationRequirement = IndentationStyles.Option1.toString();
    
    @Option(names = { "--bracePlacementStyle"}, description = "Valid values: Option1, Option2, Option3")
    public String bracePlacementStyle = BracketStyles.Option1.toString();
    
    @Option(names = { "--maxLineLength"}, description = "The max length of a line in the file.")
    public int maxLineLength;
    
    @Option(names = { "--excludeStatementFromLoop"}, description = "")
    public boolean excludeStatementFromLoop;
    
    @Option(names = { "--seperateLineForCondition"}, description = "")
    public boolean seperateLineForCondition;
    
    //IDENTIFIER
    @Option(names = { "--uppercaseClassNames"}, description = "Class names must begin in uppercase.")
    public boolean uppercaseClassNames;
    
    @Option(names = { "--lowercaseVarNames"}, description = "Variables names must begin lowercase.")
    public boolean lowercaseVarNames;
    
    //COMMENTING
    @Option(names = { "--commentBlockAtTopOfFile"}, description = "Require a comment block at the top of the file.")
    public boolean commentBlockAtTopOfFile;
    
    @Option(names = { "--commentBeforeEachMethod"}, description = "Require a comment block at the top of each method.")
    public boolean commentBeforeEachMethod;
    
    //FILE NAMING
    
    //MISC
    @Option(names = { "--importsAtTopOfFile"}, description = "Require imports at the top of the file.")
    public boolean importsAtTopOfFile;
    
    @Option(names = { "--noBreakContinueOrGoTo"}, description = "Exlude the following keywords: break, continue, go to")
    public boolean noBreakContinueOrGoTo;

	@Override
	public void run() {
		if(this.files.isEmpty() && this.directory == null) {
			System.out.println("No file(s) or directory defined. Include a file or a directory path. ");
			System.exit(0);
		}
		
		if(this.directory != null) {
			try {
				Files.find(Paths.get(this.directory),
				           Integer.MAX_VALUE,
				           (filePath, fileAttr) -> fileAttr.isRegularFile())
				        .forEach(x -> { 
				        	File subFile = x.toFile();
				            String ext = subFile.getName().lastIndexOf(".") == -1 ? "" : subFile.getName().substring(subFile.getName().lastIndexOf("."));
				            if((ext.equals(".java") || ext.equals(".jar")) && checkFileSize(subFile)) {
				            	Main.fileList.add(subFile);
				            }
				        });
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!this.files.isEmpty()) {
			Main.fileList.addAll(files);
		}
		
		UserSettings obj = UserSettings.getInstance();
    	
		if(!this.useJson) {
	    	obj.setIndentationRequirement(IndentationStyles.valueOf(this.indentationRequirement));
	    	obj.setBracePlacementStyle(BracketStyles.valueOf(this.bracePlacementStyle));
	    	obj.setMaxLineLength(this.maxLineLength);
	    	obj.setExcludeStatementFromLoop(this.excludeStatementFromLoop);
	    	obj.setSeperateLineForCondition(this.seperateLineForCondition);
	    	obj.setUppercaseClassNames(this.uppercaseClassNames);
	    	obj.setLowercaseVarNames(this.lowercaseVarNames);
	    	obj.setCommentBlockAtTopOfFile(this.commentBlockAtTopOfFile);
	    	obj.setCommentBeforeEachMethod(this.commentBeforeEachMethod);
	    	obj.setImportsAtTopOfFile(this.importsAtTopOfFile);
	    	obj.setNoBreakContinueOrGoTo(this.noBreakContinueOrGoTo);
		}
    	
    	if(this.saveSettings) {
        	obj.saveSettings();
    	}
	}
	
	//Alerts user if file size is larger than 1GB.
    private Boolean checkFileSize(File file) {
    	if(file.length() > 1e+9) {
    		Scanner reader = new Scanner(System.in);
    		System.out.println("***Large File Detected***");
    		System.out.println("The current file is \"+ file.length()/1e+9 + \". Would you like to continue? [y/n]");
    		
    		String result = reader.next();
    		if (result.toLowerCase().equals("y")){
    		    return true;
    		} else {
    		    return false;
    		}
    	}
    	return true;
    }
}
