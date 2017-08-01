package frontend;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;

public final class Customizer {
	
	
	private JTextPane	textPane;
	private JScrollPane	scrollPane;
	private	JPanel 		panel;
	
	
	private Customizer(){
		
	}
	
	
	
	public Border getBorder(Border border){
		return panel.getBorder();
	}
	
	public void setBorder(Border border){
		panel.setBorder(border);
	}

	
	public void setMarginBackground(Color color){
		panel.setBackground(color);
	}
	
	
	public Color getMarginBackground(Color color){
		return panel.getBackground();
	}
	
	
	public void setBackground(Color color){
		textPane.setBackground(color);
	}
	
	
	public Color getBackground(Color color){
		return textPane.getBackground();
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
