package General;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Gui.UserSettings;
import Gui.UserSettings.BracketStyles;
import Gui.UserSettings.IndentationTypes;
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
    @Option(names = { "--indentationRequirement"}, description = "Valid values: \"Spaces\" or \"Tabs.\"")
    public String indentationRequirement = IndentationTypes.Spaces.toString();
    
    @Option(names = { "--spaces"}, description = "Used to specify number of spaces when using --indentationRequirement=Spaces")
    public int numberOfSpaces;
    
    @Option(names = { "--bracePlacementStyle"}, description = "Valid values: \"Inline\" or \"Newline.\"")
    public String bracePlacementStyle = BracketStyles.Inline.toString();
    
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
    
    @Option(names = { "--reportDir"}, description = "Directory for reports to be saved to.")
    public String reportDir;

	@Override
	public void run() {
		Validation();
		
		if(this.directory != null) {
			try {
				int oldValue = this.files.size();
				Files.find(Paths.get(this.directory),
				           Integer.MAX_VALUE,
				           (filePath, fileAttr) -> fileAttr.isRegularFile())
				        .forEach(x -> { 
				        	File subFile = x.toFile();
				            String ext = subFile.getName().lastIndexOf(".") == -1 ? "" : subFile.getName().substring(subFile.getName().lastIndexOf("."));
				            if((ext.equals(".java") || ext.equals(".jar")) && CheckFileSize(subFile)) {
				            	Main.fileList.add(subFile);
				            }
				        });
				if(this.files.size() == oldValue) {
					System.out.println(this.directory + " did not contain any .java or .jar files.");
					System.exit(0);
				}
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
	    	obj.setIndentationRequirement(IndentationTypes.valueOf(this.indentationRequirement));
	    	obj.setBracePlacementStyle(BracketStyles.valueOf(this.bracePlacementStyle));
	    	obj.setMaxLineLength(this.maxLineLength);
	    	obj.setExcludeStatementFromLoop(this.excludeStatementFromLoop);
	    	obj.setSeperateLineForCondition(this.seperateLineForCondition);
	    	obj.setUppercaseClassNames(this.uppercaseClassNames);
	    	obj.setLowercaseVarNames(this.lowercaseVarNames);
	    	obj.setCommentBlockAtTopOfFile(this.commentBlockAtTopOfFile);
	    	obj.setCommentBeforeEachMethod(this.commentBeforeEachMethod);
	    	obj.setReportDirectory(this.reportDir);
	    	obj.setImportsAtTopOfFile(this.importsAtTopOfFile);
	    	obj.setNoBreakContinueOrGoTo(this.noBreakContinueOrGoTo);
		}
    	
    	if(this.saveSettings) {
        	obj.saveSettings();
    	}
	}
	
	//Alerts user if file size is larger than 1GB.
    private Boolean CheckFileSize(File file) {
    	if(file.length() > 1e+9) {
    		Scanner reader = new Scanner(System.in);
    		System.out.println("***Large File Detected***");
    		System.out.println("The current file is \"+ file.length()/1e+9 + \". Would you like to continue? [y/n]");
    		
    		String result = reader.next();
    		reader.close();
    		return result.toLowerCase().equals("y") ? true : false;
    	}
    	return true;
    }
    
    private void Validation() {
    	Boolean errorFound = false;
    	
    	if(this.files.isEmpty() && this.directory == null) {
			System.out.println("No file(s) or directory defined. Include a file or a directory path. ");
			errorFound = true;
		}		
		
		try {
			Enum.valueOf(IndentationTypes.class, this.indentationRequirement);
		}
		catch (Exception ex) {
			System.out.println(this.indentationRequirement + " is not a valid Indentation Requirement type. Use -h for more information.");
			errorFound = true;
		}
		
		try {
			Enum.valueOf(BracketStyles.class, this.bracePlacementStyle);
		}
		catch (Exception ex) {
			System.out.println(this.bracePlacementStyle + " is not a valid Bracket Placement style. Use -h for more information.");
			errorFound = true;
		}
		
		if(Enum.valueOf(IndentationTypes.class, this.indentationRequirement) == IndentationTypes.Spaces && !(this.numberOfSpaces > 0)) {
			System.out.println("Error: --spaces must an integer greater than zero when using --indentationRequirement=Spaces");
			errorFound = true;
		}
		
		if(!(this.maxLineLength > 0)) {
			System.out.println("Error: --maxLineLength must an integer greater than zero.");
			errorFound = true;
		}
		
		if(this.reportDir != null &&
			!Files.exists(Path.of(this.reportDir))) {
			System.out.println("Error: --reportDir must have a valid directory path.");
			errorFound = true;
		}
    
    	if(errorFound == true) {
    		System.exit(0);
    	}
    }
}
