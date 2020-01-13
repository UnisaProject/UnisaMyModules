package za.ac.unisa.lms.tools.liberesource.forms;

public class Alphabet {

	private int ID;
	private char alphabet;
	private boolean enabled = false;
	private final String link = "resource.do?act=eresourcebySpecific&alphabet=";
	
	public Alphabet(int ID, char alphabet, boolean enabled){
		
		this.ID = ID;
		this.alphabet = alphabet;
		this.enabled = enabled;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}
	
	public void setAlphabet(char alphabet){
		this.alphabet = alphabet;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}
	
	public int getID(){
		return ID;
	}
	
	public char getAlphabet(){
		return alphabet;
	}
	
	public boolean getEnabled(){
		return enabled; 
	}
	
	public String getLink(){
		return link+alphabet;
	}
}
