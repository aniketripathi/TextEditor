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



public class Editor  {
	
	private SwingNode	swingNode;
	
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
	
	
	
	
	
	
	
	
	public Editor(SwingNode swingNode) {
		
		this.swingNode = swingNode;
		createSwingContent();
		
		
		
	}
	
	
	public Customizer getCustomizer(){
			return customizer;
	}
	
	
	public final void updateMatcher(){
		updateMatcher = true;
	}
	
	
	
	protected void createSwingContent() {
			
			panel = new JPanel(new BorderLayout());
			
			SwingUtilities.invokeLater(() -> {
			
			textPane = new JTextPane();			
			scrollPane = new JScrollPane(panel);
	
			panel.add(textPane); 
			
			swingNode.setContent(scrollPane);
			
			initialize();
			customizer = new Customizer();
		});
					
	}
	
	protected void initialize(){
			addDocumentListener();
				// Set default Font
		setFont(FontManager.FONT_DEFAULT, FontWeight.NORMAL);
		// save all actions in one place
		editorKitActions = new HashMap<Object, Action>(textPane.getEditorKit().getActions().length);
		
		for(Action action: textPane.getEditorKit().getActions())
			editorKitActions.put(action.getValue(Action.NAME), action);
	}


	protected void addDocumentListener(){

		// Add document listener
		textPane.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				
				contentChanged = true;
				updateMatcher = true;
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				

					contentChanged = true;
					updateMatcher = true;
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {

				contentChanged = true;
				updateMatcher = true;
				if(getText() == null || getText().isEmpty())
					contentChanged = false;
				
			}
			
		});

		
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
	
	
	public void setContentChanged(boolean contentChanged){
		this.contentChanged = contentChanged;
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
	
	public String getSelectedText(){
		return textPane.getSelectedText();
	}
	
	
	public void setFont(Font font, FontWeight weight) {
		
		textPane.setFont(FontManager.getAWTFont(font, weight));
	}
	
	
	
	public Font getFont() {
		
		return FontManager.getFxFont(textPane.getFont());
	}
	
	
	/** Writes the content of the editor to the specified file **/
	public void writeToFile(File file)throws IOException {
		
			textPane.write(new BufferedWriter(new FileWriter(file)));
			contentChanged = false;
			
	}
		
	/** Reads data from the specified file and loads it into the editor **/
	public void readFromFile(File file)throws IOException {
		
			textPane.read(new BufferedReader(new FileReader(file)), file);
			addDocumentListener();
	}

	/**
	 * This is a temporary method. When adding more features , add document changing features to another class.
	 * */
	private String getText(){
		try {
			return textPane.getDocument().getText(0, textPane.getDocument().getLength());
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}return null;
	}
	
	
	public void clear(){
		try {
			textPane.getDocument().remove(0, textPane.getDocument().getLength());
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/** Creates a new matcher object for a specific regex **/
	protected Matcher createMatcher(String regex, boolean matchCase, boolean wholeWord){
		
		
		String newRegex = (wholeWord)?"\\b"+Pattern.quote(regex)+"\\b" : Pattern.quote(regex);
		Pattern pattern = (matchCase) ? Pattern.compile(newRegex) : Pattern.compile(newRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(getText());
		return matcher;
	}
	
	
	
	/**
	 *  Selects the next word . Creates the new matcher if required**/
	public  boolean find(String findWhat, boolean matchCase, boolean wholeWord){
		if(findWhat == null || findWhat.isEmpty())
			return false;
		
		if(updateMatcher){
			matcher = createMatcher(findWhat, matchCase, wholeWord);
			updateMatcher = false;
		}
		
	 	boolean found = matcher.find();
		if(found)
			textPane.select(matcher.start(), matcher.end());
	 
	 	return found;
	}
	
	
	
	//Replace the selected text with given text
	public void replaceSelected(String replaceWhat, String replaceWith, boolean matchCase, boolean wholeWord){
		
		if(replaceWhat == null || replaceWhat.isEmpty())
			return;
		
		if(updateMatcher){
			matcher = createMatcher(replaceWhat, matchCase, wholeWord);
			find(replaceWhat, matchCase, wholeWord);
		}
			
		updateMatcher = true;
		textPane.replaceSelection(replaceWith);
		textPane.setSelectionEnd(textPane.getSelectionStart() + replaceWith.length());
	}
	
	/** Replace all occurrence of given string with specified string **/
	public void replaceAll(String replaceWhat, String replaceWith, boolean matchCase, boolean wholeWord){
		
		if(replaceWhat == null || replaceWhat.isEmpty())
			return;
		
		if(updateMatcher)
			matcher = createMatcher(replaceWhat, matchCase, wholeWord);
		
		textPane.setText(matcher.replaceAll(replaceWith));
		updateMatcher = true;
	}
	
	
}
