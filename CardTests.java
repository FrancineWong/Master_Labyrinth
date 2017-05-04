package tests;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.Card;

public class CardTests {
	
	@Test
	public void tesstCardCreate() {
		Card card = new Card();
		boolean expected = true;
		for (int i = 0; i < card.getTokens().size() - 1; i++) {
	        for (int j = i+1; j < card.getTokens().size(); j++) {
	             if (card.getTokens().get(i) == card.getTokens().get(j)) {
	            	 assertTrue("Expected Tokens to be distinct but got " + false, expected == false);
	             }
	        }
	    }
		assertTrue("Expected Tokens to be distinct but got " + true, expected == true);
	}
}
