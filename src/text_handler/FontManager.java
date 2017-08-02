package text_handler;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;



public class FontManager {
	
	public static final Font	FONT_DEFAULT		= new Font(20);
	public static final int		SIZE_UPPER_RANGE	= 72;
	public static final int		SIZE_LOWER_RANGE	= 2;
	
	
	
	public static ObservableList<Integer> getAllSizes() {
		
		Integer[] sizes = new Integer[36];
		
		for (int i = SIZE_LOWER_RANGE; i <= SIZE_UPPER_RANGE; i += 2)
			sizes[(i + 1) / 2 - 1] = i;
		
		return FXCollections.observableList(Arrays.asList(sizes));
	}
	
	
	
	// gives a font by appropriate data
	public static void setLabelFont(String fontFamily, String fontStyle, Integer fontSize, Label label) {
		
		Font font;
		
		if (fontSize == null) fontSize = (int) FONT_DEFAULT.getSize();
		
		font = Font.font(fontFamily, stringToFontWeight(fontStyle), stringToFontPosture(fontStyle), fontSize.doubleValue());
		
		if (font == null)
			font = FONT_DEFAULT;
		
		label.setFont(font);
	}
	
	
	
	public static FontWeight stringToFontWeight(String fontStyle) {
		
		FontWeight weight = FontWeight.NORMAL;
		
		if (fontStyle != null) if (fontStyle.contains("Bold"))
			weight = FontWeight.BOLD;
		return weight;
	}
	
	
	
	public static FontPosture stringToFontPosture(String fontStyle) {
		
		FontPosture posture = FontPosture.REGULAR;
		
		if (fontStyle != null) if (fontStyle.contains("Italic"))
			posture = FontPosture.ITALIC;
		return posture;
	}
	
	
	
	public static String stringToFontStyle(String fontStyle) {
		
		String style = "Normal";
		
		if (fontStyle.contains("Bold"))
			style = "Bold";
		
		if (fontStyle.contains("Italic"))
			style = "Italic";
		
		return style;
	}
	
	
	
	public static java.awt.Font getAWTFont(javafx.scene.text.Font font, FontWeight weight) {
		
		int style = java.awt.Font.PLAIN;
		String fontStyle = font.getStyle().toLowerCase();
		
		if (fontStyle.contains("italic"))
			style = java.awt.Font.ITALIC;
		
		if (fontStyle.contains("bold") || weight.equals(FontWeight.BOLD))
			style |= java.awt.Font.BOLD;
		
		if (fontStyle.contains("normal"))
			style = java.awt.Font.PLAIN;
		
		return new java.awt.Font(font.getName(), style, (int) font.getSize());
		
	}
	
	
	
	public static Font getFxFont(java.awt.Font awtFont) {
		
		String name = awtFont.getFamily();
		
		FontWeight weight = ((awtFont.getStyle() & java.awt.Font.BOLD) != 0) ? FontWeight.BOLD : FontWeight.NORMAL;
		FontPosture posture = ((awtFont.getStyle() & java.awt.Font.ITALIC) != 0) ? FontPosture.ITALIC : FontPosture.REGULAR;
		
		return Font.font(name, weight, posture, awtFont.getSize());
	}
	
}
