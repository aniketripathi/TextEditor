package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;

import javafx.embed.swing.SwingNode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import text_handler.FontManager;



public class Editor extends SwingNode {
	
	private JTextPane	textPane;
	private JScrollPane	scrollPane;
	private	JPanel 		panel;
	
	private Customizer customizer;
	
	private boolean updateMatcher = true;
	private Matcher matcher;
	private boolean contentChanged = false;
	private HashMap<Object, Action> editorKitActions;
	
	
	
	
	protected final class Customizer {
		
		
		private Customizer(){
			
			//default settings
			
			setBorder(new EmptyBorder(10, 15, 10, 15));
			setMarginBackground(Color.WHITE);
			
			
		}
		
		
		public Border getBorder(Border border){
			return panel.getBorder();
		}
		
		public void setBorder(Border border){
			panel.setBorder(border);
		}

		
		public Insets getMargin(){
			return panel.getInsets();
		}
		
		
		public void setMarginBackground(Color color){
			panel.setBackground(color);
		}
		
		
		public Color getMarginBackground(Color color){
			return panel.getBackground();
		}
		
		
		public void setOpaque(boolean isOpaque){
			textPane.setOpaque(isOpaque);
		}
		
		
		public int getHorizontalUnitIncrement(){
			return scrollPane.getHorizontalScrollBar().getUnitIncrement();
		}
		
		
		public int getHorizontalBlockIncrement(){
			return scrollPane.getHorizontalScrollBar().getBlockIncrement();
		}
		
		
		public int getVerticalUnitIncrement(){
			return scrollPane.getVerticalScrollBar().getUnitIncrement();
		}
		
		
		public int getVerticalBlockIncrement(){
			return scrollPane.getVerticalScrollBar().getBlockIncrement();
		}
		
	 
		public void setHorizontalUnitIncrement(int value){
			 scrollPane.getHorizontalScrollBar().setUnitIncrement(value);
		}
		
		
		public void setHorizontalBlockIncrement(int value){
			 scrollPane.getHorizontalScrollBar().setBlockIncrement(value);
		}
		
		
		public void setVerticalUnitIncrement(int value){
			 scrollPane.getVerticalScrollBar().setUnitIncrement(value);
		}
		
		
		public void setVerticalBlockIncrement(int value){
			scrollPane.getVerticalScrollBar().setBlockIncrement(value);
				
		
	}

	}
	
	
	
	
	
	
	
	
	public Editor() {
		
		createSwingContent();
		
		
		
	}
	
	
	public Customizer getCustomizer(){
			return customizer;
	}
	
	
	public void updateMatcher(){
		updateMatcher = true;
	}
	
	
	
	private void createSwingContent() {
			
			panel = new JPanel(new BorderLayout());
			
			SwingUtilities.invokeLater(() -> {
			
			textPane = new JTextPane();			
			scrollPane = new JScrollPane(panel);
	
			panel.add(textPane); 
			
			this.setContent(scrollPane);
			
			initialize();
			customizer = new Customizer();
		});
					
	}
	
	public void initialize(){
		
		// Add document listener
		textPane.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				//TODO test only for style or for text also
				
				contentChanged = true;
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				
			contentChanged = true;
				
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				contentChanged = true;
				
			}
			
		});
		// Set default Font
		setFont(FontManager.FONT_DEFAULT, FontWeight.NORMAL);
		
		// save all actions in one place
		
		editorKitActions = new HashMap<Object, Action>(textPane.getEditorKit().getActions().length);
		
		for(Action action: textPane.getEditorKit().getActions())
			editorKitActions.put(action.getValue(Action.NAME), action);
	}
	
	public boolean contentChanged(){
		return contentChanged;
	}
	
	
	public void deleteSelected(){
		
		int start = textPane.getSelectionStart();
		int end = textPane.getSelectionEnd();
		if(end != 0){	
			
		try {
			
			textPane.getDocument().remove(start, end - start);
		}
		catch (BadLocationException e) {
			e.printStackTrace();
				}
			}
		}
	
	
	
	public void cut(){
		Action cut = editorKitActions.get(DefaultEditorKit.cutAction);
		cut.actionPerformed(new ActionEvent(textPane, ActionEvent.ACTION_PERFORMED, "Cut"));
	}
	
	
	public void copy(){
		Action copy = editorKitActions.get(DefaultEditorKit.copyAction);
		copy.actionPerformed(new ActionEvent(textPane, ActionEvent.ACTION_PERFORMED,"Copy"));
	}
	
	public void paste(){
		Action paste = editorKitActions.get(DefaultEditorKit.pasteAction);
		paste.actionPerformed(new ActionEvent(textPane, ActionEvent.ACTION_PERFORMED, "Paste"));
	}
	
	
	public void setEditable(boolean editable) {
		
		textPane.setEditable(editable);
	}
	
	
	public void setSize(int width, int height){
	
		textPane.setSize(width, height);
		
	}
	
	
	public boolean getEditable() {
		
		return textPane.isEditable();
	}
	
	
	
	public void setFont(Font font, FontWeight weight) {
		
		textPane.setFont(FontManager.getAWTFont(font, weight));
	}
	
	
	
	public Font getFont() {
		
		return FontManager.getFxFont(textPane.getFont());
	}
	
	
	
	public void writeToFile(File file)throws IOException {
		
			textPane.write(new BufferedWriter(new FileWriter(file)));
			contentChanged = false;
			
	}
		
	public void loadFromFile(File file)throws IOException {
		
			textPane.read(new BufferedReader(new FileReader(file)), file);
	}
	
	
	
	/** Creates a new matcher object for a specific regex **/
	private Matcher createMatcher(String regex, boolean matchCase, boolean wholeWord){
		
		
		String newRegex = (wholeWord)?"\\b"+regex+"\\b" : regex;
		Pattern pattern = (matchCase) ? Pattern.compile(newRegex) : Pattern.compile(newRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(textPane.getText());
		return matcher;
	}
	
	
	
	/**
	 *  Selects the next word . Creates the new matcher if required**/
	public  boolean find(String findWhat, boolean matchCase, boolean wholeWord){
	 
		
		if(updateMatcher){
			matcher = createMatcher(findWhat, matchCase, wholeWord);
			updateMatcher = false;
		}
		
	 	boolean found = matcher.find();
	 
	 	if(found){
	 		textPane.setSelectionStart(matcher.start());
	 		textPane.setSelectionEnd(matcher.end());
	 	}
	 		
	 	return found;
	}
	
	
	
	//Replace the selected text with given text
	public void replaceSelected(String replaceWith){
		textPane.replaceSelection(replaceWith);
	}
	
	/** Replace all occurrence of given string with specified string **/
	public void replaceAll(String findWhat, String replaceWith, boolean matchCase){
		
		matcher = createMatcher(findWhat, matchCase, false);
		textPane.setText(matcher.replaceAll(replaceWith));
	
	}
	
	
}
