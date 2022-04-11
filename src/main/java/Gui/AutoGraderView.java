package Gui;

import Gui.UserSettings.BracketStyles;
import Gui.UserSettings.IndentationStyles;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class AutoGraderView {

    //View Nodes
	VBox fileChooserRegion = new VBox();
	Button openFileButton = new Button("Open File");
	Button runButton = new Button("Run");
	Button clearButton = new Button("Clear");
	Button saveButton = new Button("Save");
    
    //FORMATTING
	ComboBox indentationStyle = new ComboBox(FXCollections.observableArrayList(UserSettings.IndentationStyles.values()));
	ComboBox bracketStyle = new ComboBox(FXCollections.observableArrayList(UserSettings.BracketStyles.values()));
    TextField maxLineLength = new TextField("80");
    CheckBox excludeStatementFromLoop = new CheckBox("Exclude statement in the body of a loop");
    CheckBox seperateLineForCondition = new CheckBox("Exclude conditional from same line as the condition");
	
	//INDENTIFIER
    CheckBox uppercaseClassNames = new CheckBox("Class names start with uppercase");
    CheckBox lowercaseVarNames = new CheckBox("Variable names start with lowercase");
	
	//COMMENTING
    CheckBox commentBlockAtTopOfFile = new CheckBox("Comment block at the top of each file");
    CheckBox commentBeforeEachMethod = new CheckBox("Comment before each method implementation");
	
	//FILE NAMING
	
	//MISC
    Label invalidDirectoryLabel = new Label("Warning: Invalid directory.");
    TextField reportDirectory = new TextField("100");
    CheckBox importsAtTopOfFile = new CheckBox("All import statements at the top of the file");
    CheckBox noBreakContinueOrGoTo = new CheckBox("Avoid break, continue, and goto statements");

    //View
    Parent view;

    public AutoGraderView() {
        view = createView();
    }
    
    public VBox getFileChooserRegion() {
    	return fileChooserRegion;
    }
    
    public Button getOpenFileButton() {
    	return openFileButton;
    }
    
    public Button getRunButton() {
    	return runButton;
    }
    
    public Button getClearButton() {
    	return clearButton;
    }
    
    public Button getSaveButton() {
    	return saveButton;
    }

    public ComboBox getIndentationStyle() {
    	return indentationStyle;
    }
    
    public ComboBox getBracketStyle() {
    	return bracketStyle;
    }
    
    public TextField getMaxLineLength() {
    	return maxLineLength;
    }
    
    public CheckBox getExcludeStatementFromLoop() {
    	return excludeStatementFromLoop;
    }
    
    public CheckBox getSeperateLineForCondition() {
    	return seperateLineForCondition;
    }
    
    public CheckBox getUppercaseClassNames() {
    	return uppercaseClassNames;
    }
    
    public CheckBox getLowercaseVarNames() {
    	return lowercaseVarNames;
    }
    
    public CheckBox getCommentBlockAtTopOfFile() {
    	return commentBlockAtTopOfFile;
    }
    
    public CheckBox getCommentBeforeEachMethod() {
    	return commentBeforeEachMethod;
    }
    
    public Label invalidDirectoryLabel() {
    	return invalidDirectoryLabel;
    }
    
    public TextField getReportDirectory() {
    	return reportDirectory;
    }
    
    public CheckBox getImportsAtTopOfFile() {
    	return importsAtTopOfFile;
    }
    
    public CheckBox getNoBreakContinueOrGoTo() {
    	return noBreakContinueOrGoTo;
    }
    
    private VBox createView(){
        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(15));
        vBox.getStylesheets().addAll(App.class.getResource("styles.css").toExternalForm());
        vBox.getStyleClass().add("backgroundMainPanel");
        vBox.getChildren().add(createFileChooserRegion());
        
        return vBox;
    }

    private Node createFileChooserRegion() {
    	HBox hBox = new HBox(15);
        VBox vBox = new VBox();
        
        fileChooserRegion.setPrefHeight(390);
        fileChooserRegion.setPrefWidth(200);
        fileChooserRegion.getStyleClass().add("border");
        
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.TOP_RIGHT);
        buttonBox.getChildren().addAll(openFileButton, clearButton, runButton);
        
        vBox.getChildren().addAll(fileChooserRegion, buttonBox);
        hBox.getChildren().addAll(vBox, createOptionPanels(), saveButton);
        return hBox;
    }
    
    private Node createOptionPanels() {
    	VBox stackedPanels = new VBox();
    	   
        stackedPanels.getChildren().addAll(createFormattingOptions(), createIdentifierOptions(), 
    								createCommentingOptions(), createFileNamingOptions(), 
    								createMiscOptions());
        stackedPanels.getStyleClass().add("backgroundMainPanel");
        
        ScrollPane scroll = new ScrollPane(stackedPanels);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        return scroll;
    }
    
    private TitledPane createFormattingOptions() {
        VBox vBox = new VBox(15);
    	
    	Label option1 = new Label("Specific indentation requirements");
    	Label option2 = new Label("Specific brace placement style");
    	Label option3 = new Label("Line length should not exceed X characters");
    	
    	HBox indentationBox = new HBox(5);
    	HBox bracketBox = new HBox(5);
    	HBox lineLengthBox = new HBox(5);
    	
    	indentationBox.getChildren().addAll(option1, indentationStyle);
    	bracketBox.getChildren().addAll(option2, bracketStyle);
    	lineLengthBox.getChildren().addAll(option3, maxLineLength);
    	
        vBox.getChildren().addAll(indentationBox, bracketBox, lineLengthBox, excludeStatementFromLoop, seperateLineForCondition);

    	TitledPane formatPane = new TitledPane("Formatting", vBox);
    	formatPane.setExpanded(true);
    	
    	return formatPane;
    }
    
    private TitledPane createIdentifierOptions() {
        VBox vBox = new VBox(15);
    	
    	/* Phase 2
    	CheckBox option3 = new CheckBox("Named constants and enumerated values are all caps");
    	CheckBox option4 = new CheckBox("Type parameters single uppercase letter");
    	CheckBox option5 = new CheckBox("Namespaces (package) all lowercase");
    	*/
    	
    	/* Phase 3
    	CheckBox option6 = new CheckBox("Camel case vs. Underscore convention");
    	*/
        vBox.getChildren().addAll(uppercaseClassNames, lowercaseVarNames);
    	
        TitledPane indentifierPane = new TitledPane("Indentifier", vBox);
        indentifierPane.setExpanded(false);
    	
    	return indentifierPane;
    }
    
    private TitledPane createCommentingOptions() {
        VBox vBox = new VBox(15);
  
    	/* Phase 3
    	CheckBox option3 = new CheckBox("No commented-out code");
    	CheckBox option4 = new CheckBox("Use javadocs");
    	*/
    	
        vBox.getChildren().addAll(commentBlockAtTopOfFile, commentBeforeEachMethod);
    	
        TitledPane commentPane = new TitledPane("Commenting", vBox);
        commentPane.setExpanded(false);
    	
    	return commentPane;
    }
    
    private TitledPane createFileNamingOptions() {
        VBox vBox = new VBox(15);
    	
        Label label = new Label("Coming Soon...");
    	
    	/* Phase 3
    	CheckBox option1 = new CheckBox("File names match class names");
    	CheckBox option2 = new CheckBox("Classes declared in a header file & implemented in a source file");
    	*/
    	
        vBox.getChildren().addAll(label);
    	
        TitledPane fileNamingPane = new TitledPane("File Naming", vBox);
        fileNamingPane.setExpanded(false);
    	
    	return fileNamingPane;
    }
    
    private TitledPane createMiscOptions() {
        VBox vBox = new VBox(15);
    	
    	/* Phase 2
    	CheckBox option3 = new CheckBox("All preprocessor directives at the top of the file");
    	CheckBox option4 = new CheckBox("Header files must have include guards");
    	CheckBox option5 = new CheckBox("Create a default case in all switch statements");
    	*/
    	
    	/* Phase 3
    	CheckBox option6 = new CheckBox("Put all code in a package in Java");
    	*/
        invalidDirectoryLabel.setVisible(false);
        invalidDirectoryLabel.managedProperty().bind(invalidDirectoryLabel.visibleProperty());
    	invalidDirectoryLabel.setTextFill(Color.RED);
        vBox.getChildren().addAll(invalidDirectoryLabel, reportDirectory, importsAtTopOfFile, noBreakContinueOrGoTo);
    	
        TitledPane miscPane = new TitledPane("Miscellaneous", vBox);
        miscPane.setExpanded(false);
    	
    	return miscPane;
    }

    public Parent getView() {
        return view;
    }
    
}
