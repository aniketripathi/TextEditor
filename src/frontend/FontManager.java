package frontend;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class FontManager {
	
	public FontManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static final int SIZE_UPPER_RANGE = 72;
	public static final int SIZE_LOWER_RANGE = 2;
	
	public static ObservableList<Integer> getAllSizes(){
		
		
		Integer[] sizes = new Integer[36];
		
		for(int i = SIZE_LOWER_RANGE; i <= SIZE_UPPER_RANGE; i+=2)			sizes[(i+1)/2 - 1] = i;
		
		return FXCollections.observableList(Arrays.asList(sizes));
	}
	
	// gives a font by appropriate data
	public static void setLabelFont(String fontFamily, String fontStyle, Integer fontSize, Label label){
		Font font;
		
		FontWeight weight = FontWeight.NORMAL;
		FontPosture posture = FontPosture.REGULAR;
		
	if(fontStyle != null){	
		
		fontStyle = fontStyle.toLowerCase();
		
		if(fontStyle.contains("bold")) weight = FontWeight.BOLD;
		
		if(fontStyle.contains("italic")) posture = FontPosture.ITALIC;
	}
	
	if(fontSize == null)	fontSize = 50;
	
	font = Font.font(fontFamily, weight, posture, fontSize.doubleValue());
	
	if(font != null)
		font = Font.font(fontFamily, FontWeight.NORMAL, FontPosture.REGULAR, fontSize.doubleValue());
		
		label.setFont(font);
		
	}
	
}
