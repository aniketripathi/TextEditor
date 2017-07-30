package frontend;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;


import javafx.embed.swing.SwingNode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import text_handler.FontManager;



public class Editor extends SwingNode {
	
	private JTextPane	textPane;
	private JScrollPane	scrollPane;
	
	public static enum Direction {
		UP, DOWN;
	};
	
	public Editor() {
		
		createSwingContent();
		
	}
	
	
	
	private void createSwingContent() {
		
		SwingUtilities.invokeLater(() -> {
			
			textPane = new JTextPane();
			
			scrollPane = new JScrollPane(textPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.getHorizontalScrollBar().setVisible(true);
			scrollPane.setSize(400, 400);
			
			// Set default Font
			this.setFont(FontManager.FONT_DEFAULT, FontWeight.NORMAL);
			this.setContent(textPane);
		});
		
	}
	
	
	
	public void setEditable(boolean editable) {
		
		textPane.setEditable(editable);
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
	
	
	
	/** Create a compiled pattern based on the arguments **/
	//TODO implement
	private Pattern createCompiledPattern(String exp, boolean matchCase, boolean wholeWord){
	
		return null;
	}
	
	
	/**
	 *  Selects the next word in the given direction **/
	//TODO implement
	public void find(String what, Direction direction, boolean matchCase, boolean wholeWord){
		
	
	}
	
	
	//TODO implement
	public void replace(String what, String with, boolean matchCase){
		
	}
	
	// TODO implement
	public void replaceAll(String what, String with, boolean matchCase){
		
	}
	
	
	
	
	
	
	
	
}
