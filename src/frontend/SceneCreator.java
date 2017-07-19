package frontend;


import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SceneCreator {
	
	
	
	
	
	
	private BorderPane rootNode;
	private MenuBar menuBar; 
	private FileChooser fileChoser;
	
	private Scene primaryScene;
	private Stage stage;
	
	private Alert aboutDialog;
	private Alert findAndReplaceDialog;
	
	public SceneCreator(Stage stage){
		init(stage);
		addMenus();
	}
	
	
	public  Scene getScene(){
		
		return primaryScene;
	}
	
	
	
	private  void init(Stage stage){
	
		this.stage = stage;
		rootNode = new BorderPane();
		menuBar	 =  new MenuBar();
		fileChoser = new FileChooser();
		primaryScene = new Scene(rootNode);
		
		aboutDialog = new Alert(AlertType.NONE);
		aboutDialog.getDialogPane().setContent(new Label("Text Editor Basic \n version 1.0"));
		aboutDialog.getButtonTypes().add(ButtonType.OK);
		aboutDialog.setTitle("About");
		aboutDialog.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		aboutDialog.setResizable(false);
		
		
		findAndReplaceDialog = new Alert(AlertType.NONE);
		
		
		rootNode.setTop(menuBar);
		
	}
	
	
	private void showAboutDialog(){
		aboutDialog.showAndWait();
				
	}
	
	
	private  void addMenus(){
		
		
	/**  File Menu	**/
		Menu fileMenu = new Menu("File");
		fileMenu.setId("fileMenu");

	// menu items	
		MenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem;
			
	// create menu items
		newMenuItem = new MenuItem("New");
		openMenuItem = new MenuItem("Open");
		saveMenuItem = new MenuItem("Save");
		saveAsMenuItem = new MenuItem("Save As");
		exitMenuItem = new MenuItem("Exit");
		
   //set properties	
		openMenuItem.setOnAction(eventHandler -> { 
			fileChoser.setTitle("Open File");
			fileChoser.showOpenDialog(stage);
					});
		
		saveAsMenuItem.setOnAction(eventHandler -> {
			fileChoser.setTitle("Save As");
			fileChoser.showSaveDialog(stage);});
		
	
		exitMenuItem.setOnAction(eventHandler -> {
			Platform.exit();
			System.exit(0);
			});
		
	//add to file menu	
		fileMenu.getItems().add(newMenuItem);
		fileMenu.getItems().add(openMenuItem);
		fileMenu.getItems().add(saveMenuItem);
		fileMenu.getItems().add(saveAsMenuItem);
		fileMenu.getItems().add(exitMenuItem);
		
		
	/**		Edit menu 	**/	
		Menu editMenu = new Menu("Edit");
		editMenu.setId("editMenu");
	
	// menu items	
		MenuItem cutMenuItem, copyMenuItem, pasteMenuItem, deleteMenuItem, findMenuItem, replaceMenuItem;
		
	// create menu items
			 	cutMenuItem = new MenuItem("Cut");
				copyMenuItem = new MenuItem("Copy");
				pasteMenuItem = new MenuItem("Paste");
				deleteMenuItem = new MenuItem("Delete");
				findMenuItem = new MenuItem("Find");
				replaceMenuItem = new MenuItem("Replace");
				
	 //set properties		

	// add to editMenu
				editMenu.getItems().add(cutMenuItem);
				editMenu.getItems().add(copyMenuItem);
				editMenu.getItems().add(pasteMenuItem);
				editMenu.getItems().add(deleteMenuItem);
				editMenu.getItems().add(findMenuItem);
				editMenu.getItems().add(replaceMenuItem);
				

	/**		About menu **/
				
		Menu aboutMenu = new Menu("About");
		aboutMenu.setId("aboutMenu");
	
		aboutMenu.setOnAction(eventHandler -> showAboutDialog());
		
	//	menu items
		MenuItem aboutTextEditor;
		
	// create menu items
		aboutTextEditor = new MenuItem("About TextEditor");
		
	// set properties
		
	// add to aboutMenu
		aboutMenu.getItems().add(aboutTextEditor);
	
		
	/***	Add to menu bar **/
		menuBar.getMenus().add(fileMenu);
		menuBar.getMenus().add(editMenu);
		menuBar.getMenus().add(aboutMenu);
		
	}
	
	
}
