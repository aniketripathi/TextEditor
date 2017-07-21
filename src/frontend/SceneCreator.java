package frontend;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class SceneCreator {
	
	private BorderPane	rootNode;
	private MenuBar		menuBar;
	private FileChooser	fileChoser;
	
	private Scene	primaryScene;
	private Stage	stage;
	
	
	
	public SceneCreator(Stage stage) {
		init(stage);
		addMenus();
	}
	
	
	
	public Scene getScene() {
		
		return primaryScene;
	}
	
	
	
	private void init(Stage stage) {
		
		this.stage = stage;
		rootNode = new BorderPane();
		menuBar = new MenuBar();
		fileChoser = new FileChooser();
		primaryScene = new Scene(rootNode);
		
		rootNode.setTop(menuBar);
		
	}
	
	private void displayFileOpener(){
		fileChoser.setTitle("Open");
		fileChoser.showOpenDialog(stage);
	}
	
	
	private void displayFileSaver(){
		fileChoser.setTitle("Save");
		fileChoser.showSaveDialog(stage);
	}
	
	private void displayFindDialog() {
		
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
		RadioButton upRadioButton = new RadioButton("Up");
		upRadioButton.setToggleGroup(directionToggleGroup);
		RadioButton downRadioButton = new RadioButton("Down");
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
	
	
	
	private void displayReplaceDialog() {
		
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
		dataContainer.getChildren().addAll(findBox, replaceBox, caseCheckBox);
		
		Button findNext = new Button("Find Next");
		Button replace = new Button("Replace");
		Button replaceAll = new Button("ReplaceAll");
		
		buttonContainer.getChildren().addAll(findNext, replace, replaceAll);
		
		topContainer.getChildren().addAll(dataContainer, buttonContainer);
		replaceDialog.getDialogPane().setContent(topContainer);
		
		replaceDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		/** Setting style **/
		replaceDialog.setTitle("Find and Replace");
		
		findBox.setSpacing(30);
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
	
	
	
	private void displayAboutDialog() {
		
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
	
	
	
	private void displayFontDialog() {
		
		Dialog<Void> fontDialog = new Dialog<Void>();
		
		fontDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		GridPane gridPane = new GridPane();
		
		HBox fontBox = new HBox();
		
		Label fontLabel = new Label("Font");
		ListView<String> fontView = new ListView<String>(FXCollections.observableList(Font.getFontNames()));
		fontBox.getChildren().addAll(fontLabel, fontView);
		gridPane.add(fontBox, 0, 0);
		
		HBox sizeBox = new HBox();
		Label sizeLabel = new Label("Size");
		ListView<String> sizeView = new ListView<String>(FXCollections.observableArrayList());
		sizeBox.getChildren().addAll(sizeLabel, sizeView);
		gridPane.add(sizeBox, 1, 0);
		
		HBox styleBox = new HBox();
		Label styleLabel = new Label("Style");
		ArrayList<String> styleList = new ArrayList<String>();
		styleList.add("Bold");
		styleList.add("Italic");
		styleList.add("Bold and Italic");
		ComboBox<String> styleComboBox = new ComboBox<String>(FXCollections.observableList(styleList));
		styleBox.getChildren().addAll(styleLabel, styleComboBox);
		gridPane.add(styleBox, 2, 0);
		
		HBox sampleBox = new HBox();
		
		Border border = new Border(new BorderStroke(javafx.scene.paint.Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));
		Label sampleLabel = new Label("ABC");
		gridPane.add(sampleBox, 0, 1);
		gridPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		
		fontDialog.getDialogPane().setContent(gridPane);
		
		// set settings
		gridPane.setHgap(10);
		gridPane.setVgap(20);
		
		fontBox.setSpacing(10);
		fontView.setEditable(false);
		fontView.setMaxHeight(200);
		fontView.setMaxWidth(150);
		
		sizeBox.setSpacing(10);
		sizeView.setEditable(false);
		sizeView.setMaxHeight(150);
		sizeView.setMaxWidth(75);
		
		styleBox.setSpacing(10);
		styleComboBox.setMaxWidth(120);
		
		sampleBox.setAlignment(Pos.CENTER);
		sampleLabel.setPadding(new Insets(10));
		sampleLabel.setBorder(border);
		sampleLabel.setFont(new Font("Calibri", 50));
		sampleBox.getChildren().add(sampleLabel);
		
		// show the dialog
		
		fontDialog.show();
		
	}
	
	
	
	private void addMenus() {
		
		/** File Menu **/
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
		
		// set properties
		openMenuItem.setOnAction(eventHandler -> displayFileOpener());
		
		saveAsMenuItem.setOnAction(eventHandler -> displayFileSaver());
		
		exitMenuItem.setOnAction(eventHandler -> {
			Platform.exit();
			System.exit(0);
		});
		
		// add to file menu
		fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem);
		
		/** Edit menu **/
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
		
		// set properties
		findMenuItem.setOnAction(eventHandler -> displayFindDialog());
		replaceMenuItem.setOnAction(eventHandler -> displayReplaceDialog());
		// add to editMenu
		editMenu.getItems().addAll(cutMenuItem, copyMenuItem, pasteMenuItem, deleteMenuItem, findMenuItem, replaceMenuItem);
		
		/** format menu **/
		Menu formatMenu = new Menu("Format");
		formatMenu.setId("formatMenu");
		
		// menu items
		MenuItem fontMenuItem;
		
		// create menu items
		fontMenuItem = new MenuItem("Font");
		
		// set properties
		fontMenuItem.setOnAction(eventHandler -> displayFontDialog());
		
		// add to menu
		formatMenu.getItems().add(fontMenuItem);
		
		/** About menu **/
		
		Menu aboutMenu = new Menu("About");
		aboutMenu.setId("aboutMenu");
		
		aboutMenu.setOnAction(eventHandler -> displayAboutDialog());
		
		// menu items
		MenuItem aboutTextEditor;
		
		// create menu items
		aboutTextEditor = new MenuItem("About TextEditor");
		
		// set properties
		
		// add to aboutMenu
		aboutMenu.getItems().add(aboutTextEditor);
		
		/*** Add to menu bar **/
		menuBar.getMenus().addAll(fileMenu, editMenu, formatMenu, aboutMenu);
		
	}
	
}
