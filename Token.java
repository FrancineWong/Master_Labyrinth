package code;

import java.util.HashMap;

public class Token {

	// List of tokens
	private static HashMap<Integer, String> _token = new HashMap<Integer, String>();
	
	// Current represented token
	private int _key;
	
	
	// Upon creation, it saves a key of which Token the instance represents
	public Token(int i) {
		this._key = i;
	}
	
	/**
	 * Must be called before pulling tokens
	 */
	public static void initTokens() {
		_token.put(1,"Crab Apples");
		_token.put(2,"Pine Cones");
		_token.put(3,"Oak Leaves");
		_token.put(4,"Oil");
		_token.put(5,"Four-leaf Clover");
		_token.put(6,"Eating Garlic");
		_token.put(7,"Raven's Feather");
		_token.put(8,"Henbane");
		_token.put(9,"Spiders");
		_token.put(10,"Skull Moss");
		_token.put(11,"Magic Wand");
		_token.put(12,"Quartz Crystal");
		_token.put(13,"Toads");
		_token.put(14,"Fire Salamanders");
		_token.put(15,"Weasel");
		_token.put(16,"Silver Thistle");
		_token.put(17,"Snake");
		_token.put(18,"Emeralds");
		_token.put(19,"Root of Mandrake");
		_token.put(20,"Black Rooster");
		_token.put(25,"Berries");
	}
	
	public boolean equals(int key) {
		return this._key == key;
	}
	
	public boolean equals(Token t) {
		return this._key == t.getKey();
	}
	
	public String getValue(){
		return _token.get(this._key);
	}
	
	public int getKey() {
		return this._key;
	}

}
