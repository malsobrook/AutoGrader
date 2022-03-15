package Gui;

import Gui.controllers.MainViewController;
import Gui.views.AutoGraderView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AutoGraderApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Create View
    	AutoGraderView mainView = new AutoGraderView();

        //Create Controller
    	MainViewController controller = new MainViewController(mainView);

        //Show stage
        primaryStage.setTitle("AutoGrader App");
        primaryStage.getIcons().add(new Image(getClass().getResource("/img/AutoGraderIcon.png").toExternalForm()));
        primaryStage.setScene(new Scene(mainView.getView()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

