package frontend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingNode;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import text_handler.FontManager;


public class SceneCreator {
	
	private BorderPane	rootNode;
	private MenuBar		menuBar;
	private FileChooser	fileChoser;
	
	private Scene	primaryScene;
	private Stage	stage;
	private Editor	editor;
	private File 	currentFile = null;
	private SwingNode swingNode;
	
	
	
	public SceneCreator(Stage stage) {
		init(stage);		// initialize
		addMenus();			// add menus
	}
	
	
	
	/** Returns the scene created by the scene creator **/
	public Scene getScene() {
		
		return primaryScene;
	}
	
	
	
	/** Initialize the Scene Creator **/
	
	private void init(Stage stage) {
		
		this.stage = stage;
		rootNode = new BorderPane();
		menuBar = new MenuBar();
		fileChoser = new FileChooser();
		primaryScene = new Scene(rootNode);
		swingNode = new SwingNode();
		editor = new Editor(swingNode);
		
		// editor.setFont(FontManager.FONT_DEFAULT, FontWeight.NORMAL);
		
		rootNode.setTop(menuBar);
		rootNode.setCenter(swingNode);
		
	}
	
	
	
	private File displayFileOpener() {
		
	String fileName = "new_file.txt";
	File 	directory = null;
	if(currentFile != null){
		directory = currentFile.getParentFile();
		fileName = currentFile.getName();
	}
	
		fileChoser.setTitle("Open");
			if(directory != null && directory.isDirectory())
				fileChoser.setInitialDirectory(directory);
		fileChoser.setInitialFileName(fileName);
		return fileChoser.showOpenDialog(stage);
	
	}
	
	
	
	private File displayFileSaver() {
		
		String fileName = "new_file.txt";
		File 	directory = null;
		if(currentFile != null){
			directory = currentFile.getParentFile();
			fileName = currentFile.getName();
		}
		
		
		fileChoser.setTitle("Save");
		if(directory != null && directory.isDirectory())
			fileChoser.setInitialDirectory(directory);
		fileChoser.setInitialFileName(fileName);
		return fileChoser.showSaveDialog(stage);
	}
	
	
	/** Creates and displays a new find dialog **/
	private void displayFindDialog() {
		
		/** Create the dialog box **/
		
		// Create children
		
		Dialog<Void> findDialog = new Dialog<Void>();
		
		
		
		HBox topContainer = new HBox();
		VBox dataContainer = new VBox(),
				buttonContainer = new VBox();
		
		HBox findBox = new HBox();
		Label findLabel = new Label("Find");
		TextField findWhat = new TextField(editor.getSelectedText());
		findBox.getChildren().addAll(findLabel, findWhat);
		
		
		CheckBox caseCheckBox = new CheckBox("Match Case");
		CheckBox wholeCheckBox = new CheckBox("Whole Word");
		dataContainer.getChildren().addAll(findBox, wholeCheckBox, caseCheckBox);
		
		Button findNext = new Button("Find Next");
		buttonContainer.getChildren().add(findNext);
		
		
		
		BorderPane statusPane = new BorderPane();
		Label statusLabel = new Label("Search string");
		statusLabel.setFont(new Font(12));
		statusPane.setBottom(statusLabel);
		dataContainer.getChildren().add(statusPane);
		
		topContainer.getChildren().addAll(dataContainer, buttonContainer);
		findDialog.getDialogPane().setContent(topContainer);
		
		findDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		/** Setting style **/
		findDialog.setTitle("Find");
		
		findBox.setSpacing(5);
		dataContainer.setSpacing(10);
		topContainer.setSpacing(20);
		
		findDialog.setResizable(false);
		findWhat.requestFocus();
		
		
		//set listener
		
		   findWhat.textProperty().addListener(arg -> editor.updateMatcher());
		   caseCheckBox.selectedProperty().addListener(arg -> editor.updateMatcher());
		   wholeCheckBox.selectedProperty().addListener(arg -> editor.updateMatcher());	
		//set button actions
			findNext.setOnAction(event -> {
				
				boolean found = editor.find(findWhat.getText(), caseCheckBox.isSelected(), wholeCheckBox.isSelected()); 
				if(found)
					statusLabel.setText("Matching string found!");
				else 
					statusLabel.setText("No more matches!");
			});
		
			
		
		// show the dialog
		findDialog.showAndWait();
		
	}
	
	
	
	/** Create and display find and replace dialog **/
	private void displayReplaceDialog() {
		
		/** Create the dialog box **/
		
		// Create children
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
		CheckBox wholeCheckBox = new CheckBox("Whole Word");
		dataContainer.getChildren().addAll(findBox, replaceBox, caseCheckBox, wholeCheckBox);
		
		Button findNext = new Button("Find Next");
		Button replace = new Button("Replace");
		Button replaceAll = new Button("ReplaceAll");
		
		
		BorderPane statusPane = new BorderPane();
		Label statusLabel = new Label("Search string");
		statusLabel.setFont(new Font(12));
		statusPane.setBottom(statusLabel);
		dataContainer.getChildren().add(statusPane);
		
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
		findWhat.requestFocus();
		// add listener
		   findWhat.textProperty().addListener(arg -> editor.updateMatcher());
		   caseCheckBox.selectedProperty().addListener(arg -> editor.updateMatcher());
		   wholeCheckBox.selectedProperty().addListener(arg -> editor.updateMatcher());
		   
		   //add actions to buttons
		   findNext.setOnAction(event -> editor.find(findWhat.getText(), caseCheckBox.isSelected(), wholeCheckBox.isSelected()));
		   replace.setOnAction(event -> editor.replaceSelected(findWhat.getText(), replaceWith.getText(), caseCheckBox.isSelected(), wholeCheckBox.isSelected()));
		   replaceAll.setOnAction(event -> editor.replaceAll(findWhat.getText(), replaceWith.getText(), caseCheckBox.isSelected(), wholeCheckBox.isSelected()));
		
		   
		// show the dialog
		replaceDialog.showAndWait();
		
	}
	
	
	
	/** Display about dialog. The about dialog displays the information about the text editor **/
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
	
	
	
	/** Creates and displays a new font dialog **/
	private void displayFontDialog() {
		
		// create dialog
		Dialog<ButtonType> fontDialog = new Dialog<ButtonType>();
		
		fontDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		// Create children
		
		GridPane gridPane = new GridPane();
		
		HBox fontBox = new HBox();
		Label fontLabel = new Label("Font");
		ListView<String> fontView = new ListView<String>(FontManager.fontFamilies);
		fontBox.getChildren().addAll(fontLabel, fontView);
		gridPane.add(fontBox, 0, 0);
		
		
		HBox sizeBox = new HBox();
		Label sizeLabel = new Label("Size");
		ListView<Integer> sizeView = new ListView<Integer>(FontManager.getAllSizes());
		sizeBox.getChildren().addAll(sizeLabel, sizeView);
		gridPane.add(sizeBox, 1, 0);
		
		HBox styleBox = new HBox();
		
		
		Label styleLabel = new Label("Style");
		ArrayList<String> styleList = new ArrayList<String>();
		styleList.add("Normal");
		styleList.add("Bold");
		styleList.add("Italic");
		styleList.add("Bold and Italic");
		ComboBox<String> styleComboBox = new ComboBox<String>(FXCollections.observableList(styleList));
		styleBox.getChildren().addAll(styleLabel, styleComboBox);
		gridPane.add(styleBox, 0, 1);
		
		HBox sampleBox = new HBox();
		
		Border border = new Border(new BorderStroke(javafx.scene.paint.Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));
		Label sampleLabel = new Label("ABC");
		
		gridPane.add(sampleBox, 2, 0);
		
		
		fontDialog.getDialogPane().setContent(gridPane);
		
		// set structural settings
		gridPane.setHgap(10);
		gridPane.setVgap(20);
		
		fontBox.setSpacing(10);
		fontView.setEditable(false);
		fontView.setMaxHeight(200);
		fontView.setMaxWidth(150);
		fontView.setMinHeight(200);
		fontView.setMinWidth(150);
		
		sizeBox.setSpacing(10);
		sizeView.setEditable(false);
		sizeView.setMaxHeight(200);
		sizeView.setMaxWidth(75);
		styleBox.setSpacing(10);
		styleComboBox.setMaxWidth(120);
		
		sampleBox.setAlignment(Pos.CENTER);
		sampleLabel.setPadding(new Insets(10));
		sampleLabel.setBorder(border);
		sampleLabel.setMaxSize(150, 180);
		sampleLabel.setMinSize(150, 100);
		sampleLabel.setAlignment(Pos.CENTER);
		sampleLabel.setTextAlignment(TextAlignment.CENTER);
		sampleBox.getChildren().add(sampleLabel);
		gridPane.setPrefSize(500, 225);
		// set default font
		
		fontView.getSelectionModel().select(editor.getFont().getFamily());
		sizeView.getSelectionModel().select(new Integer((int) editor.getFont().getSize()));
		styleComboBox.getSelectionModel().select(FontManager.stringToFontStyle(editor.getFont().getStyle()));
		sampleLabel.setFont(editor.getFont());
		
		// add change listener to make the label respond to selected font
		styleComboBox.getSelectionModel().selectedItemProperty()
				.addListener(changeListener -> FontManager.setLabelFont(fontView.getSelectionModel().getSelectedItem(),
						styleComboBox.getSelectionModel().getSelectedItem(), sizeView.getSelectionModel().getSelectedItem(), sampleLabel));
		fontView.getSelectionModel().selectedItemProperty()
				.addListener(changeListener -> FontManager.setLabelFont(fontView.getSelectionModel().getSelectedItem(),
						styleComboBox.getSelectionModel().getSelectedItem(), sizeView.getSelectionModel().getSelectedItem(), sampleLabel));
		sizeView.getSelectionModel().selectedItemProperty()
				.addListener(changeListener -> FontManager.setLabelFont(fontView.getSelectionModel().getSelectedItem(),
						styleComboBox.getSelectionModel().getSelectedItem(), sizeView.getSelectionModel().getSelectedItem(), sampleLabel));
		
		// show the dialog and set the font to editor as per selected font
		try {
			fontDialog.showAndWait().filter(button -> button == ButtonType.OK).ifPresent(button -> {
				String name = fontView.getSelectionModel().getSelectedItem();
				String style = styleComboBox.getSelectionModel().getSelectedItem();
				Integer size = sizeView.getSelectionModel().getSelectedItem();
				
				editor.setFont(Font.font(name, FontManager.stringToFontWeight(style), FontManager.stringToFontPosture(style),
						(size == null) ? FontManager.FONT_DEFAULT.getSize() : size), FontManager.stringToFontWeight(style));
			});
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	
	
	private boolean askConfirmation(String title, String content){
		
		boolean confirm = false;
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		
		confirmation.setHeaderText(null);
		confirmation.setTitle(title);
		confirmation.setContentText(content);
		confirmation.getDialogPane().setPrefSize(Pane.USE_COMPUTED_SIZE, Pane.USE_COMPUTED_SIZE);
		
		Optional<ButtonType> result = confirmation.showAndWait();
		
		if(result.isPresent() && result.get() == ButtonType.OK)
			confirm = true;
		
		
		return confirm;
	}
	
	
	private void showError(String title, String errorMessage){
		Alert error = new Alert(AlertType.ERROR);
		error.setHeaderText(null);
		error.setTitle("File Opening failed!");
		error.setContentText("Unable to load the file. File not supported");
		error.getDialogPane().setPrefSize(Pane.USE_COMPUTED_SIZE, Pane.USE_COMPUTED_SIZE);
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
		
		
		
		newMenuItem.setOnAction(event -> {
					boolean changed = editor.contentChanged();
					if(!changed ||
						(changed && askConfirmation("Discard Changes!", "You have unsaved changes. Are you sure you want to create a new file without saving?"))){
						editor.clear();
						editor.setContentChanged(false);
					currentFile = null;	
					}
		});
		
		
		
		openMenuItem.setOnAction(event -> {	
			boolean changed = editor.contentChanged();
			if(!changed ||
			(changed && askConfirmation("Discard Changes!" , "You have unsaved changes. Are you sure you want to open without saving?") )) {
				File file = displayFileOpener();
				if(file != null) {
					
					currentFile = file;
					try {
						if(file.canRead()){
							editor.readFromFile(file);
							editor.setContentChanged(false);
						}
						else
							showError("File Open Failed","Cannot rsead file");	
							
						}
					catch (IOException e) {
							showError("File Open Failed!", "An error occurred while saving the file. Please check whether the file format is appropriate.");
						}
					
					}
				}
			});
		
		
		
		
		saveMenuItem.setOnAction(event ->  {
			if(currentFile == null)
				saveAsMenuItem.fire();
			else {
				try {
					if(currentFile.canWrite())
						editor.writeToFile(currentFile);
					else showError("File Save Failed", "Cannot save file. Please check if you have required permissions.");
				}
				catch (IOException e) {
					showError("File Save Failed", "An error occurred while saving the current file.");
				}
			}
		});
		
		
			
		saveAsMenuItem.setOnAction(event -> { 
			File file = displayFileSaver();
			if(file != null){
				currentFile = file;
				try {
					if(file.canWrite())
						editor.writeToFile(file);
					else 
						showError("File Save Failed", "Cannot save file. Please check if you have required permissions.s");
				}
				catch (IOException e) {
					showError("File Save failed", "An error occurred while saving the file.");
				}
			}
			
		});
		
		exitMenuItem.setOnAction(event -> {
			Platform.exit();
			System.exit(0);
		});
		
		// set shortcuts
		newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
		
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
		
		
		cutMenuItem.setOnAction(event -> editor.cut());
		copyMenuItem.setOnAction(event -> editor.copy());
		pasteMenuItem.setOnAction(event -> editor.paste());
		deleteMenuItem.setOnAction(event -> editor.deleteSelected());
		
		
		findMenuItem.setOnAction(event -> displayFindDialog());
		replaceMenuItem.setOnAction(event -> displayReplaceDialog());
		
		// set shortcuts  Removed accelerator due to multiple response to same action
		
		/*cutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
		copyMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
		pasteMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
		*/findMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));
		replaceMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
		
		
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
		fontMenuItem.setOnAction(event ->  {
			displayFontDialog(); 
			System.gc();
		});
		
		// add to menu
		formatMenu.getItems().add(fontMenuItem);
		
		/** About menu **/
		
		Menu aboutMenu = new Menu("About");
		aboutMenu.setId("aboutMenu");
		
		aboutMenu.setOnAction(event -> displayAboutDialog());
		
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
