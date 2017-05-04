package tests;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import code.Board;
import code.Path;
import code.Tile;

public class BoardTests {

	/**
	 * Yay! Random testing! lol To try to get a solid test, change numPaths
	 * to how many paths you want to create and test; bigger the number, higher
	 * the reliability
	 */
	@Test public void testDefaultPath() {
		int numPaths = 100;
		ArrayList<Path> paths = new ArrayList<Path>();
		for (int i = 0; i < numPaths; i++) {
			paths.add(new Path());
		}
		boolean failedTest = false;
		int failed = 0;
		for (Path path : paths) {
			int number = 0;
			// Go through paths
			for (Boolean bool : path.getDirections()) {
				if (bool == true) {
					number++;
				}
			}
			if (number == 0) {
				failed++;
				failedTest = true;
			}
		}
		assertTrue("Tested "+numPaths+ " paths, " + failed + " failed.", !failedTest);
	}
	
	@Test public void testGivenPath() {
		Path path = new Path(true, true, false, false);
		ArrayList<Boolean> actual = path.getDirections();
		ArrayList<Boolean> expected = new ArrayList<Boolean>(Arrays.asList(true, true, false, false));
		boolean areEqual = true;
		for (int i = 0; i < 4; i++) {
			if (actual.get(i) != expected.get(i)) {
				areEqual = false;
			}
		}
		assertTrue("Built path w/ north and east. Equal: " + areEqual, areEqual);
	}
	
	@Test public void rotateClockGivenPath() {
		Path path = new Path(true, false, false, false);
		path.rotate(false);  // Rotate clockwise
		ArrayList<Boolean> actual = path.getDirections();
		ArrayList<Boolean> expected = new ArrayList<Boolean>(Arrays.asList(false, true, false, false));
		boolean areEqual = true;
		for (int i = 0; i < 4; i++) {
			if (actual.get(i) != expected.get(i)) {
				areEqual = false;
			}
		}
		assertTrue("Rotated path. Equal: " + areEqual, areEqual);
	}
	
	@Test public void rotateCountGivenPath() {
		Path path = new Path(true, false, false, false);
		path.rotate(true);  // Rotate counterclockwise
		ArrayList<Boolean> actual = path.getDirections();
		ArrayList<Boolean> expected = new ArrayList<Boolean>(Arrays.asList(false, false, false, true));
		boolean areEqual = true;
		for (int i = 0; i < 4; i++) {
			if (actual.get(i) != expected.get(i)) {
				areEqual = false;
			}
		}
		assertTrue("Rotated path. Equal: " + areEqual, areEqual);
	}
	
	@Test public void makeBoard() {
		Board board = new Board();
		// isSetup checks for null tiles, all tokens placed, distinct token locations...
		assertTrue("Board is Setup: " + board.isSetup(), board.isSetup());
	}
	
	@Test public void boardShift() {
		Board board = new Board();
		Tile extra = board.getExtra();
		board.slideRow(1, false);
		Tile newTile = board.getRow(1).get(0);
		assertTrue("Expected extra to equal first in row after switch", extra.equals(newTile));
	}
	
	@Test public void boardColShift() {
		Board board = new Board();
		Tile extra = board.getExtra();
		board.slideCol(1, false);
		Tile newTile = board.getRow(0).get(1);
		assertTrue("Expected extra to equal first in row after switch", extra.equals(newTile));
	}
	@Test public void validPathsBetweenTest01() {
		Board board = new Board();
		boolean expected=true;
		boolean actual=board.validPathBetween(1, 1, 1, 1);
		System.out.println(actual);
		assertTrue("Expected true because no movement was made, therefore dull move. This also tests whether or not the _visited variable is functioning propperly", actual==expected);
	}
	
}
