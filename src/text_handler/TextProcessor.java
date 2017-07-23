package text_handler;

import java.io.IOException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.ahmadsoft.ropes.Rope;

public class TextProcessor {
	
	Rope rope;
	
	public TextProcessor() {
		
		rope = Rope.BUILDER.build("");
		
	}
	
	public TextProcessor(String string){
		rope = Rope.BUILDER.build(string);
	}

	
	public void append(char arg0) {
		rope = rope.append(arg0);
	
	}
	
	
	public void append(String string){
		rope = rope.append(string);	
		
	}
	

	public void delete(int start, int end){
		rope = rope.delete(start, end);
		
	}
	
	
	public void delete(int pos){
		delete(pos, pos+1);
	}

	
	public void insert(int where, String what){
		rope = rope.insert(where, what);
	}
	
	
	public void write(Writer out)throws IOException {
		rope.write(out);
	}
	
	
	public Matcher matcher(Pattern pattern){
		return rope.matcher(pattern);
	}
	
	
	public boolean isEmpty(){
		return rope.isEmpty();
	}
	
	
	public int getLength(){
		return rope.length();
	}
	
	
	public String get(int start, int end){
		return rope.subSequence(start, end).toString();
	}
	
	public String getString(){
		return rope.toString();
	}

}

