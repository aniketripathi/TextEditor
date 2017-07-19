package frontend;

import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SceneCreator {
	
	
	
	
	
	
	private static FlowPane rootNode = new FlowPane(Orientation.VERTICAL);
	private static MenuBar menuBar	 =  new MenuBar();
	private static FileChooser fileChoser = new FileChooser();
	
	
	
	
	
	
	private static Scene primaryScene = new Scene(rootNode);
	
	static {
		init();
		addMenus();
		System.out.print(menuBar.isResizable());
	}
	
	
	public static void bindSize(Stage stage){
		
		rootNode.prefHeightProperty().bind(stage.heightProperty());
		rootNode.prefWidthProperty().bind(stage.widthProperty());
		
		menuBar.prefHeightProperty().bind(rootNode.heightProperty());
		menuBar.prefWidthProperty().bind(rootNode.widthProperty());
	}
	
	
	public static void print(Stage stage){
		System.out.print("\n"+stage.getWidth() + "," + stage.getHeight() );
		System.out.print(rootNode.getWidth() + "," + rootNode.getHeight() );
		System.out.print(menuBar.getWidth() + "," + menuBar.getHeight() );
	}
	
	public static Scene getScene(){
		
		return primaryScene;
	}
	
	
	
	private static void init(){
	
		
		rootNode.getChildren().add(menuBar);
		
	}
	
	
	private static void addMenus(){
		
		
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
		openMenuItem.setOnAction(eventHandler -> fileChoser.showOpenDialog(new Stage()));
	
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
	
	//	menu items
		MenuItem aboutTextEditor;
		
	// create menu items
		aboutTextEditor = new MenuItem("About TextEditor");
		
	// set properties
		
	// add to aboutMenu
		aboutMenu.getItems().add(aboutTextEditor);
	
		
	/***	Add to menubar **/
		menuBar.getMenus().add(fileMenu);
		menuBar.getMenus().add(editMenu);
		menuBar.getMenus().add(aboutMenu);
		
	}
	
	
}
