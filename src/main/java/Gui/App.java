package Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
    	//Create View
    	AutoGraderView mainView = new AutoGraderView();

        //Create Controller
    	MainViewController controller = new MainViewController(mainView);

        //Show View
        stage.setTitle("AutoGrader");
        stage.getIcons().add(new Image(getClass().getResource("AutoGraderIcon.png").toExternalForm()));
        stage.setScene(new Scene(mainView.getView()));
        stage.setResizable(false);
        stage.show();
    }

    public static void start() {
        launch();
    }

}