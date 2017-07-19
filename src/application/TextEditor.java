package application;
	

import frontend.SceneCreator;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;


public class TextEditor extends Application {
	
	public static final String TITLE = "Text Editor";
	
	
	@Override
	public void start(Stage primaryStage) {
	
	primaryStage.setTitle(TITLE);
	primaryStage.setIconified(false);
	primaryStage.setResizable(true);
	primaryStage.initStyle(StageStyle.DECORATED);
	
	Scene primaryScene = SceneCreator.getScene();
	SceneCreator.bindSize(primaryStage);
	primaryStage.setScene(primaryScene);
	
	primaryStage.widthProperty().addListener( (obs, oldval, newval) -> SceneCreator.print(primaryStage) );
	
	primaryStage.show();
	
	
	SceneCreator.print(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
