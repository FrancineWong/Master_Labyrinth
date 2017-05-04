package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.Token;

public class TokenTest {

	@Test
	public void testToken() {
		Token.initTokens();
		Token token = new Token(5);
		String expected = "Four-leaf Clover";
		String actual = token.getValue();
		assertTrue("Expected Token to be " + expected + " and got " + actual, expected.equals(actual));
	}
	
	@Test
	public void compareTokensTrue() {
		Token.initTokens();
		Token first = new Token(1);
		Token sec = new Token(1);
		boolean expected = true;
		boolean actual = first.equals(sec);
		assertTrue("Expected Tokens to be equal but got " + actual, expected == actual);
	}
	
	@Test
	public void compareTokensFalse() {
		Token.initTokens();
		Token first = new Token(1);
		Token sec = new Token(2);
		boolean expected = false;
		boolean actual = first.equals(sec);
		assertTrue("Expected Tokens to be different but got " + actual, expected == actual);
	}
	
}
