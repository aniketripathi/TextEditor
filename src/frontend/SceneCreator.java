package frontend;


import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SceneCreator {
	
	
	
	
	
	
	private BorderPane rootNode;
	private MenuBar menuBar; 
	private FileChooser fileChoser;
	
	private Scene primaryScene;
	private Stage stage;
	
	
	
	
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
		
		
		
		rootNode.setTop(menuBar);
		
	}
	
	
	private void displayFindDialog(){
		
		/** Create the dialog box **/
		
		Dialog<Void> findDialog = new Dialog<Void>();
		
		HBox topContainer = new HBox();
		VBox dataContainer = new VBox(),
			 buttonContainer = new VBox();
		
		
		HBox findBox = new HBox();
		Label find = new Label("Find");
		TextField findWhat = new TextField();
		findBox.getChildren().addAll(find, findWhat);
		
		
		HBox directionBox = new HBox();
		Label directionLabel = new Label("Direction:");
		ToggleGroup directionToggleGroup = new ToggleGroup();
		RadioButton upRadioButton= new RadioButton("Up"); 
					upRadioButton.setToggleGroup(directionToggleGroup); 
		RadioButton downRadioButton= new RadioButton("Down"); 
					downRadioButton.setToggleGroup(directionToggleGroup);
		directionBox.getChildren().addAll(directionLabel, upRadioButton, downRadioButton);
		
		
		
		CheckBox caseCheckBox = new CheckBox("Match Case");
		dataContainer.getChildren().addAll(findBox, directionBox, caseCheckBox);
		
		
		
		Button findNext = new Button("Find Next");
		buttonContainer.getChildren().add(findNext);
		
		topContainer.getChildren().addAll(dataContainer, buttonContainer);
		findDialog.getDialogPane().setContent(topContainer);
		
		findDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		/** Setting style **/
		findDialog.setTitle("Find");
		
		
		findBox.setSpacing(5);
		directionBox.setSpacing(10);
		dataContainer.setSpacing(10);
		topContainer.setSpacing(20);
		
		findDialog.setResizable(false);
		
		
		// show the dialog
		findDialog.showAndWait();
	
	}
	
	
	
	private void displayReplaceDialog(){

		/** Create the dialog box **/
		
		Dialog<Void> replaceDialog = new Dialog<Void>();
		
		HBox topContainer = new HBox();
		VBox dataContainer = new VBox(),
			 buttonContainer = new VBox();
		
		
		HBox findBox = new HBox();
		Label find = new Label("Find");
		TextField findWhat = new TextField();
		findBox.getChildren().addAll(find, findWhat);
		
		
		HBox replaceBox = new HBox();
		Label replaceLabel = new Label("Replace");
		TextField replaceWith = new TextField();
		replaceBox.getChildren().addAll(replaceLabel, replaceWith);
		
		CheckBox caseCheckBox = new CheckBox("Match Case");
		dataContainer.getChildren().addAll(findBox, caseCheckBox);
		
		
		
		Button findNext = new Button("Find Next");
		Button replace = new Button("Replace");
		Button replaceAll = new Button("ReplaceAll");
		
		
		buttonContainer.getChildren().addAll(findNext, replace, replaceAll);
		
		topContainer.getChildren().addAll(dataContainer, buttonContainer);
		replaceDialog.getDialogPane().setContent(topContainer);
		
		replaceDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		/** Setting style **/
		replaceDialog.setTitle("Find and Replace");
		
		
		findBox.setSpacing(5);
		replaceBox.setSpacing(5);
		dataContainer.setSpacing(10);
		buttonContainer.setSpacing(10);
		topContainer.setSpacing(20);
		
		findNext.setMaxWidth(Double.MAX_VALUE);
		replace.setMaxWidth(Double.MAX_VALUE);
		replaceAll.setMaxWidth(Double.MAX_VALUE);
		

		replaceDialog.setResizable(false);
		
		
		// show the dialog
		replaceDialog.showAndWait();
	
	}
	
	
	private void displayAboutDialog(){
		 Alert aboutDialog = new Alert(AlertType.NONE);
		
		VBox labelContainer = new VBox();
		labelContainer.getChildren().addAll(new Label("Text Editor Basic \n version 1.0"), new Label("By Aniket Kumar Tripathi"));
		labelContainer.setSpacing(5);
		
		aboutDialog.getDialogPane().setContent(labelContainer);
		aboutDialog.getButtonTypes().add(ButtonType.OK);
		aboutDialog.setTitle("About");
		aboutDialog.getDialogPane().setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		aboutDialog.setResizable(false);
		
		
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
				findMenuItem.setOnAction(eventHandler -> displayFindDialog());
				replaceMenuItem.setOnAction(eventHandler -> displayReplaceDialog());
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
	
		aboutMenu.setOnAction(eventHandler -> displayAboutDialog());
		
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
