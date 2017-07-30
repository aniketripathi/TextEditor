package frontend;

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
	
}
