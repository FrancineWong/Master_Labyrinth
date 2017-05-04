package tests;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Test;
import code.GameApplet;
import code.Pawn;

public class MenuStateTests {
	
	String[] playerNames;
	String[] playerNames_2;
	
	public MenuStateTests(){
		playerNames=new String[4];
		playerNames[0]="Dan";
		playerNames[1]="Carl";
		playerNames[2]="Minfan";
		playerNames[3]="Jon";
		playerNames_2=new String[3];
		playerNames_2[0]="Dan";
		playerNames_2[1]="Carl";
		playerNames_2[2]="Minfan";
	}
	
	@Test public void menuTest01(){
		GameApplet game=new GameApplet(playerNames);
		game.players[0].add_score(100);
		game.players[1].add_score(90);
		game.players[2].add_score(80);
		game.players[3].add_score(70);
		ArrayList<Integer> actual=game.scoreUp();
		ArrayList<Integer> expected=new ArrayList<Integer>();
		expected.add(100);
		expected.add(90);
		expected.add(80);
		expected.add(70);
		assertTrue("expected was " + expected.toString() + " while actual was " + actual.toString(),expected.toString().equals(actual.toString()));
	}
	@Test public void menuTest02(){
		GameApplet game=new GameApplet(playerNames);
		game.players[0].add_score(100);
		game.players[1].add_score(100);
		game.players[2].add_score(80);
		game.players[3].add_score(70);
		ArrayList<Integer> actual=game.scoreUp();
		ArrayList<Integer> expected=new ArrayList<Integer>();
		expected.add(100);
		expected.add(100);
		expected.add(80);
		expected.add(70);
		assertTrue("expected was " + expected.toString() + " while actual was " + actual.toString(),expected.toString().equals(actual.toString()));
	}
	@Test public void menuTest03(){
		GameApplet game=new GameApplet(playerNames_2);
		game.players[0].add_score(100);
		game.players[1].add_score(90);
		game.players[2].add_score(80);
		ArrayList<Integer> actual=game.scoreUp();
		ArrayList<Integer> expected=new ArrayList<Integer>();
		expected.add(100);
		expected.add(90);
		expected.add(80);
		assertTrue("expected was " + expected.toString() + " while actual was " + actual.toString(),expected.toString().equals(actual.toString()));
	}
	

}
