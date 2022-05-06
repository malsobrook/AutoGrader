package Gui;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import General.Main;
import Gui.UserSettings.BracketStyles;
import Gui.UserSettings.IndentationTypes;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import mutators.Handler;

public class MainViewController {

    //Model
    List<File> fileList = new ArrayList<File>();
    UserSettings obj = UserSettings.getInstance();
    AutoGraderView view;

    public MainViewController(AutoGraderView view){
        setView(view);
    }

    @SuppressWarnings("unchecked")
	public void setView(AutoGraderView view){    
    	loadSettings(view);
        
    	this.view = view;
    	
    	//Adds user selected files and closes the view.
        view.getRunButton().setOnAction(event -> {
        	for(File file : this.fileList) {
    			Handler trs = new Handler(file.getAbsolutePath());
    			try {
    				trs.handle();
    			} catch (Exception e) {
    				System.out.println("error on pass to Translator");
    				e.printStackTrace();
    			}
    			
    			
    			System.out.println("done");
    		}
        	
        	this.fileList.clear();
        	view.getFileTree().getRoot().getChildren().clear();
        });
        
        //Clears file chooser region
        view.getClearButton().setOnAction(event -> {
        	this.fileList.clear();
        	view.getFileTree().getRoot().getChildren().clear();
        });
        
        //Changes visibility of textbox for Indentation Spaces
        view.getIndentationType().setOnAction((event) -> {
        	if(view.getIndentationType().getSelectionModel().getSelectedItem() == IndentationTypes.Spaces) {
        		view.getNumberOfSpaces().setVisible(true);
        	}
        	else {
        		view.getNumberOfSpaces().setVisible(false);
        		view.getNumberOfSpaces().setText("");
        	}
        });
        
        //Text validation
        view.getMaxLineLength().focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { 
                    if (!view.getMaxLineLength().getText().matches("^[1-9]\\d*$")) {
                    	view.getMaxLineLength().setText("");
                    	view.getMaxLineLength().setPromptText("Enter a whole number greater than zero.");
                    }
                }
            });
        
      //Text validation
        view.getNumberOfSpaces().focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                    if (!view.getNumberOfSpaces().getText().matches("^[1-9]\\d*$")) {
                    	view.getNumberOfSpaces().setText("");
                    	view.getNumberOfSpaces().setPromptText("Enter a whole number greater than zero.");
                    }
                }
            });
        
        //Populates UserSettings with defined settings and saves to userSettings.json
        view.getSaveButton().setOnAction(event -> { 
        	saveSettings(view);
			 event.consume();
		 });
        
        //Check if report directory is valid
        view.getReportDirectory().focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { 
            	if(!Files.exists(Path.of(view.getReportDirectory().getText()))) {
					view.getInvalidDirectoryLabel().setVisible(true);
					view.getReportDirectory().setText("");
				}
				else {
					view.getInvalidDirectoryLabel().setVisible(false);
				}
            }
        });
        
        //Opens FileChooser control
		 view.getOpenFileButton().setOnAction(event -> { 
			int oldValue = this.fileList.size();
			JFileChooser fc = new JFileChooser("Open File Resource");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fc.setFileFilter(new FileNameExtensionFilter("Java file", new String[] {"java", "jar"}));
			if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				addFileToRegion(file);
				
				if(this.fileList.size() == oldValue) {
					noFilesAlert(file);
				}
			}
			 event.consume();
		 });
		 
		//Defines logic for drag'n'drop compatibility
        view.getFileTree().setOnDragOver(event -> {
			Dragboard board = event.getDragboard();
			if (board.hasFiles()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});
        
        //Defines logic for accepting drag files
        view.getFileTree().setOnDragDropped(event -> {
        	int oldValue = this.fileList.size();
			Dragboard board = event.getDragboard();
			if (board.hasFiles()) {
				board.getFiles().forEach(file -> {
					
					addFileToRegion(file);
					
					if(this.fileList.size() == oldValue) {
						noFilesAlert(file);
					}
				});

				event.setDropCompleted(true);
			} else {
				event.setDropCompleted(false);
			}
			event.consume();
		});

    }
    
    //Alerts user if file size is larger than 1GB.
    private static Boolean checkFileSize(File file) {
    	if(file.length() > 1e+9) {    		
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Large File Detected");
    		alert.setHeaderText("A Large File Has Been Chosen");
    		alert.setContentText("The current file is "+ file.length()/1e+9 + ". Would you like to continue?");

    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK){
    		    return true;
    		} else {
    		    return false;
    		}
    	}
    	return true;
    }
    
    //Saves view settings to UserSettings and updates userSettings.json
    private void saveSettings(AutoGraderView view) {
    	obj.setIndentationRequirement(Enum.valueOf(IndentationTypes.class, view.getIndentationType().getValue().toString()));
    	obj.setNumberOfSpaces(view.getNumberOfSpaces().getText() == "" ? 0 : Integer.valueOf(view.getNumberOfSpaces().getText()));
    	obj.setBracePlacementStyle(Enum.valueOf(BracketStyles.class, view.getBracketStyle().getValue().toString()));
    	obj.setMaxLineLength(Integer.valueOf(view.getMaxLineLength().getText()));
    	obj.setExcludeStatementFromLoop(view.getExcludeStatementFromLoop().isSelected());
    	obj.setSeperateLineForCondition(view.getSeperateLineForCondition().isSelected());
    	obj.setUppercaseClassNames(view.getUppercaseClassNames().isSelected());
    	obj.setLowercaseVarNames(view.getLowercaseVarNames().isSelected());
    	obj.setCommentBlockAtTopOfFile(view.getCommentBlockAtTopOfFile().isSelected());
    	obj.setCommentBeforeEachMethod(view.getCommentBeforeEachMethod().isSelected());
    	obj.setReportDirectory(view.getReportDirectory().getText());
    	obj.setImportsAtTopOfFile(view.getImportsAtTopOfFile().isSelected());
    	obj.setNoBreakContinueOrGoTo(view.getNoBreakContinueOrGoTo().isSelected());
    	
    	obj.saveSettings();
    }
    
    //Pulls default UserSettings values to initially populate view
    @SuppressWarnings("unchecked")
	private void loadSettings(AutoGraderView view) {
    	view.getIndentationType().setValue(obj.getIndentationRequirement());
    	view.getNumberOfSpaces().setText(String.valueOf(obj.getNumberOfSpaces()));
    	view.getBracketStyle().setValue(obj.getBracePlacementStyle());
    	view.getMaxLineLength().setText(String.valueOf(obj.getMaxLineLength()));
    	view.getExcludeStatementFromLoop().setSelected(obj.isExcludeStatementFromLoop());
    	view.getSeperateLineForCondition().setSelected(obj.isSeperateLineForCondition());
    	view.getUppercaseClassNames().setSelected(obj.isUppercaseClassNames());
    	view.getLowercaseVarNames().setSelected(obj.isLowercaseVarNames());
    	view.getCommentBlockAtTopOfFile().setSelected(obj.isCommentBlockAtTopOfFile());
    	view.getCommentBeforeEachMethod().setSelected(obj.isCommentBeforeEachMethod());
    	view.getReportDirectory().setText(obj.getReportDirectory());
    	view.getImportsAtTopOfFile().setSelected(obj.isImportsAtTopOfFile());
    	view.getNoBreakContinueOrGoTo().setSelected(obj.isNoBreakContinueOrGoTo());
    }
    
    //Adds directory and its files to TreeView
    private void createTree(TreeItem<FilePath> rootItem) throws IOException {

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootItem.getValue().getPath())) {

            for (Path path : directoryStream) {

                TreeItem<FilePath> newItem = new TreeItem<FilePath>( new FilePath( path));
                newItem.setExpanded(true);
                
                String fileName = path.getFileName().toString();
            	String ext = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf("."));
                
                if (Files.isDirectory(path)) {
                	ImageView directoryImage = new ImageView(getClass().getResource("directory.png").toExternalForm());
        			directoryImage.setFitHeight(20);
        	    	directoryImage.setFitWidth(20);
        	    	newItem.setGraphic(directoryImage);
                	rootItem.getChildren().add(newItem);
                    createTree(newItem);
                }
                else if ((ext.equals(".java") || ext.equals(".jar")) && checkFileSize(path.toFile())){
                	ImageView fileImage = new ImageView(getClass().getResource("file.png").toExternalForm());
        	    	fileImage.setFitHeight(20);
        	    	fileImage.setFitWidth(20);
        	    	newItem.setGraphic(fileImage);
                	rootItem.getChildren().add(newItem);
                	this.fileList.add(path.toFile());
                }
            }
        }
        catch( Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void addFileToRegion(File file) {
    	if(file.isDirectory()) {
			ImageView directoryImage = new ImageView(getClass().getResource("directory.png").toExternalForm());
			directoryImage.setFitHeight(20);
	    	directoryImage.setFitWidth(20);
			TreeItem<FilePath> treeItem = new TreeItem<FilePath>( new FilePath( Paths.get( file.getAbsolutePath())), directoryImage);
			try {
				createTree(treeItem);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			treeItem.setExpanded(true);
			view.getFileTree().getRoot().getChildren().add(treeItem);
		}
		else {
			ImageView fileImage = new ImageView(getClass().getResource("file.png").toExternalForm());
	    	fileImage.setFitHeight(20);
	    	fileImage.setFitWidth(20);
			view.getFileTree().getRoot().getChildren().add(new TreeItem(file.getName(), fileImage));
			this.fileList.add(file);
		}
    }
    
    //Show message that no .java or .jar were found
    private void noFilesAlert(File file) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("No Files Detected");
		alert.setHeaderText("No Files Detected");
		alert.setContentText(file.getAbsolutePath() + " did not contain any .java or .jar files.");
		alert.show();
    }

}
