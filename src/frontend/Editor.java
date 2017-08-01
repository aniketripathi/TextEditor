package frontend;

import java.util.regex.Pattern;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.regex.Matcher;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;



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
			panel.setBorder(new EmptyBorder(20, 20, 20, 20));
			
			// Set default Font
			this.setFont(FontManager.FONT_DEFAULT, FontWeight.NORMAL);
			this.setContent(scrollPane);
		});
		
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
