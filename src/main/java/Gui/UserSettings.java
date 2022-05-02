package Gui;

import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;

public class UserSettings {
	private static UserSettings instance;
	   
	public enum IndentationTypes { Spaces, Tab; }
	public enum BracketStyles { Inline, Newline; }
	
	//FORMATTING
	private IndentationTypes indentationRequirement;
	private int numberOfSpaces;
	private BracketStyles bracePlacementStyle;
	private int maxLineLength;				
	private boolean excludeStatementFromLoop;
	private boolean seperateLineForCondition;	
	
	//INDENTIFIER
	private boolean uppercaseClassNames;
	private boolean lowercaseVarNames;
	
	//COMMENTING
	private boolean commentBlockAtTopOfFile;
	private boolean commentBeforeEachMethod;
	
	//FILE NAMING
	
	//MISC
	private String reportDirectory;
	private boolean importsAtTopOfFile;
	private boolean noBreakContinueOrGoTo;
	
	public IndentationTypes getIndentationRequirement() {
		return indentationRequirement;
	}

	public void setIndentationRequirement(IndentationTypes indentationRequirement) {
		this.indentationRequirement = indentationRequirement;
	}
	
	public int getNumberOfSpaces() {
		return numberOfSpaces;
	}

	public void setNumberOfSpaces(int numberOfSpaces) {
		this.numberOfSpaces = numberOfSpaces;
	}

	public BracketStyles getBracePlacementStyle() {
		return bracePlacementStyle;
	}

	public void setBracePlacementStyle(BracketStyles bracePlacementStyle) {
		this.bracePlacementStyle = bracePlacementStyle;
	}

	public int getMaxLineLength() {
		return maxLineLength;
	}

	public void setMaxLineLength(int maxLineLength) {
		this.maxLineLength = maxLineLength;
	}

	public boolean isSeperateLineForCondition() {
		return seperateLineForCondition;
	}
	
	public boolean isExcludeStatementFromLoop() {
		return excludeStatementFromLoop;
	}

	public void setExcludeStatementFromLoop(boolean excludeStatementFromLoop) {
		this.excludeStatementFromLoop = excludeStatementFromLoop;
	}

	public void setSeperateLineForCondition(boolean seperateLineForCondition) {
		this.seperateLineForCondition = seperateLineForCondition;
	}

	public boolean isUppercaseClassNames() {
		return uppercaseClassNames;
	}

	public void setUppercaseClassNames(boolean uppercaseClassNames) {
		this.uppercaseClassNames = uppercaseClassNames;
	}

	public boolean isLowercaseVarNames() {
		return lowercaseVarNames;
	}

	public void setLowercaseVarNames(boolean lowercaseVarNames) {
		this.lowercaseVarNames = lowercaseVarNames;
	}

	public boolean isCommentBlockAtTopOfFile() {
		return commentBlockAtTopOfFile;
	}

	public void setCommentBlockAtTopOfFile(boolean commentBlockAtTopOfFile) {
		this.commentBlockAtTopOfFile = commentBlockAtTopOfFile;
	}

	public boolean isCommentBeforeEachMethod() {
		return commentBeforeEachMethod;
	}

	public void setCommentBeforeEachMethod(boolean commentBeforeEachMethod) {
		this.commentBeforeEachMethod = commentBeforeEachMethod;
	}

	public String getReportDirectory() {
		return reportDirectory;
	}
	
	public void setReportDirectory(String path) {
		this.reportDirectory = path;
	}
	
	public boolean isImportsAtTopOfFile() {
		return importsAtTopOfFile;
	}

	public void setImportsAtTopOfFile(boolean importsAtTopOfFile) {
		this.importsAtTopOfFile = importsAtTopOfFile;
	}

	public boolean isNoBreakContinueOrGoTo() {
		return noBreakContinueOrGoTo;
	}

	public void setNoBreakContinueOrGoTo(boolean noBreakContinueOrGoTo) {
		this.noBreakContinueOrGoTo = noBreakContinueOrGoTo;
	}
	
   private UserSettings() {
	    this.indentationRequirement = IndentationTypes.Tab;
	    this.numberOfSpaces = 4;
		this.maxLineLength = 80;
		this.bracePlacementStyle = BracketStyles.Inline;
		this.seperateLineForCondition = false;
		this.uppercaseClassNames = false;
		this.lowercaseVarNames = false;
		this.commentBeforeEachMethod = false;
		this.commentBlockAtTopOfFile = false;
		this.reportDirectory = "";
		this.importsAtTopOfFile = false;
		this.noBreakContinueOrGoTo = false;
   }

   //Checks to see if User Settings have already been saved, returns default values if otherwise
   public static UserSettings getInstance(){
	  if(instance == null) {
		  try (FileReader reader = new FileReader("userSettings.json")) {
		       	instance = new Gson().fromJson(reader, UserSettings.class);
		       }
		       catch(Exception e) {
		   	    instance = new UserSettings();
		       }
	  }
      return instance;
   }
	
   //Save settings to userSettings.json
	public void saveSettings() {
		try (FileWriter writer = new FileWriter("userSettings.json")) {
        	new Gson().toJson(this, writer);
        } catch (Exception e) {
        	//Maybe show dialog saying save failed???
            e.printStackTrace();
        }
	}

}
