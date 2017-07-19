package application;
	

import frontend.SceneCreator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class TextEditor extends Application {
	
	public static final String TITLE = "Text Editor";
	
	
	@Override
	public void start(Stage primaryStage) {
	
	primaryStage.setTitle(TITLE);
	primaryStage.setIconified(false);
	primaryStage.setResizable(true);
	primaryStage.initStyle(StageStyle.DECORATED);
	
	
	primaryStage.setMinHeight(100);
	primaryStage.setMinWidth(200);
	SceneCreator sceneCreator = new SceneCreator(primaryStage);
	
	Scene primaryScene = sceneCreator.getScene();

	primaryStage.setScene(primaryScene);
	primaryStage.setMaximized(true);
	primaryStage.show();
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
