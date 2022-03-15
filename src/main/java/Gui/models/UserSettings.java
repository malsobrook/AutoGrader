package Gui.models;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import javafx.beans.property.*;

public class UserSettings {

	//This can be housed somewhere else or stay here
	public enum IndentationStyles { Option1, Option2, Option3; }
	public enum BracketStyles { Option1, Option2, Option3; }
	
	//FORMATTING
	private IndentationStyles indentationRequirement;
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
	private boolean importsAtTopOfFile;
	private boolean noBreakContinueOrGoTo;
	
	public UserSettings() {
		this.maxLineLength = 80;
		this.seperateLineForCondition = false;
		this.uppercaseClassNames = false;
		this.lowercaseVarNames = false;
		this.commentBeforeEachMethod = false;
		this.commentBlockAtTopOfFile = false;
		this.importsAtTopOfFile = false;
		this.noBreakContinueOrGoTo = false;
	}
	
	public IndentationStyles getIndentationRequirement() {
		return indentationRequirement;
	}

	public void setIndentationRequirement(IndentationStyles indentationRequirement) {
		this.indentationRequirement = indentationRequirement;
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
}
