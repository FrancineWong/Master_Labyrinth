package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Card {

	private static final int NUMBER_TO_COLLECT = 3;
	
	private ArrayList<Token> _tokens = new ArrayList<Token>();
	
	public Card() {
		// Generate random number between 1-20 and 25
		int[] ints = new Random().ints(1, 22).distinct().limit(NUMBER_TO_COLLECT).toArray();
		// Set values of 21 to 25.
		for (int i = 0; i < ints.length; i++) {
			if (ints[i] == 21 || ints[i] == 22) {
				ints[i] = 25;
			}
			_tokens.add(new Token(ints[i]));
		}
		// Sort tokens
		for (int i = 0; i < _tokens.size() - 1; i++) {
			if (_tokens.get(i).getKey() > _tokens.get(i + 1).getKey()) {
				Collections.swap(_tokens, i, i + 1);
			}
		}
	}
	
	public ArrayList<Token> getTokens() {
		return this._tokens;
	}
	
	public String toString() {
		return this._tokens.toString();
	}
}
