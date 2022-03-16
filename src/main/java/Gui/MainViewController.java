package Gui;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;

import Gui.UserSettings.BracketStyles;
import Gui.UserSettings.IndentationStyles;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

public class MainViewController {

    //Model
    List<File> fileList;
    UserSettings obj;

    public MainViewController(AutoGraderView view){
        setView(view);
    }

    public void setView(AutoGraderView view){    
        //link Model with View
        //view.getAccountHolderDetailsLabel().textProperty().bind(account.accountHolderProperty());
        //view.getAccountBalanceDetailsLabel().textProperty().bind(account.accountBalanceProperty().asString());
        
        //link Controller to View - methods for buttons
		/*
		 * view.getDepositButton().setOnAction(event -> {
		 * account.deposit(getAmount(view.getAmountTextField())); event.consume(); });
		 * view.getWithdrawalButton().setOnAction(event -> {
		 * account.withdraw(getAmount(view.getAmountTextField())); event.consume(); });
		 */
    	loadSettings(view);
        
        //This is where we connect the run button to the logic setup to execute over the files.
        view.getRunButton().setOnAction(null);
        
        view.getSaveButton().setOnAction(event -> { 
        	saveSettings(view);
			 event.consume();
		 });
        
		 view.getOpenFileButton().setOnAction(event -> { FileChooser fc = new
			 FileChooser(); fc.setTitle("AutoGrader");
			  
			 File file = fc.showOpenDialog(null);
			 if(checkFileSize(file))
			 {
				 fileList.add(file);
				 view.getFileChooserRegion().getChildren().add(new Label(file.getName()));
			 }
			 event.consume();
		 });

        view.getFileChooserRegion().setOnDragOver(event -> {
			Dragboard board = event.getDragboard();
			if (board.hasFiles()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

        view.getFileChooserRegion().setOnDragDropped(event -> {
			Dragboard board = event.getDragboard();
			if (board.hasFiles()) {
				board.getFiles().forEach(file -> {
					if(checkFileSize(file)) {
						fileList.add(file);
						view.getFileChooserRegion().getChildren().add(new Label(file.getName()));
					}
				});

				event.setDropCompleted(true);
			} else {
				event.setDropCompleted(false);
			}
			event.consume();
		});

    }

    private Boolean checkFileSize(File file) {
    	if(file.length() > 1e+9) {    		
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Large File Detected");
    		alert.setHeaderText("A Large File Has Been Chosen");
    		alert.setContentText("The current file isc"+ file.length()/1e+9 + ". Would you like to continue?");

    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == ButtonType.OK){
    		    return true;
    		} else {
    		    return false;
    		}
    	}
    	return true;
    }
    
    private void saveSettings(AutoGraderView view) {
    	UserSettings obj = new UserSettings();
    	
    	obj.setIndentationRequirement((IndentationStyles) view.getIndentationStyle().getValue());
    	obj.setBracePlacementStyle((BracketStyles) view.getBracketStyle().getValue());
    	obj.setMaxLineLength(Integer.valueOf(view.getMaxLineLength().getText()));
    	obj.setExcludeStatementFromLoop(view.getExcludeStatementFromLoop().isSelected());
    	obj.setSeperateLineForCondition(view.getSeperateLineForCondition().isSelected());
    	obj.setUppercaseClassNames(view.getUppercaseClassNames().isSelected());
    	obj.setLowercaseVarNames(view.getLowercaseVarNames().isSelected());
    	obj.setCommentBlockAtTopOfFile(view.getCommentBlockAtTopOfFile().isSelected());
    	obj.setCommentBeforeEachMethod(view.getCommentBeforeEachMethod().isSelected());
    	obj.setImportsAtTopOfFile(view.getImportsAtTopOfFile().isSelected());
    	obj.setNoBreakContinueOrGoTo(view.getNoBreakContinueOrGoTo().isSelected());
    	
    	try (FileWriter writer = new FileWriter("C:\\Users\\Taylor\\Downloads\\userSettings.json")) {
        	new Gson().toJson(obj, writer);
        } catch (Exception e) {
        	//Maybe show dialog saying save failed???
            e.printStackTrace();
        }
    }
    
    private void loadSettings(AutoGraderView view) {
    	try (FileReader reader = new FileReader("C:\\Users\\Taylor\\Downloads\\userSettings.json")) {
        	obj = new Gson().fromJson(reader, UserSettings.class);
        }
        catch(Exception e) {
        	obj = new UserSettings();
        }
    	
    	view.getIndentationStyle().setValue(obj.getIndentationRequirement());
    	view.getBracketStyle().setValue(obj.getBracePlacementStyle());
    	view.getMaxLineLength().setText(String.valueOf(obj.getMaxLineLength()));
    	view.getExcludeStatementFromLoop().setSelected(obj.isExcludeStatementFromLoop());
    	view.getSeperateLineForCondition().setSelected(obj.isSeperateLineForCondition());
    	view.getUppercaseClassNames().setSelected(obj.isUppercaseClassNames());
    	view.getLowercaseVarNames().setSelected(obj.isLowercaseVarNames());
    	view.getCommentBlockAtTopOfFile().setSelected(obj.isCommentBlockAtTopOfFile());
    	view.getCommentBeforeEachMethod().setSelected(obj.isCommentBeforeEachMethod());
    	view.getImportsAtTopOfFile().setSelected(obj.isImportsAtTopOfFile());
    	view.getNoBreakContinueOrGoTo().setSelected(obj.isNoBreakContinueOrGoTo());
    }
}
