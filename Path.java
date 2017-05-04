package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Path {

	private ArrayList<Boolean> directions;

	public Path(boolean north, boolean east, boolean south, boolean west) {
		directions = new ArrayList<Boolean>(Arrays.asList(north, east, south, west));
	}

	public Path() {
		// Make generic lists
		directions = new ArrayList<Boolean>();
		for (int i = 0; i < 4; i++) {
			directions.add(new Random().nextBoolean());
		}

		// Check booleans
		int numberPaths = 0;
		for (Boolean value : directions) {
			if (value) {
				numberPaths++;
			}
		}
		// Make sure two are valid paths
		while (numberPaths < 2) {
			int random;
			// Pick random indices
			while (!directions.get(random = new Random().nextInt(directions.size()))) {
				directions.set(random, new Boolean(true));
				numberPaths++;
				break;
			}
		}
	}

	public void rotate(boolean counter) {
		if (counter) {
			directions.add(directions.remove(0));
		} else {
			directions.add(0, directions.remove(directions.size() - 1));
		}
	}

	public ArrayList<Boolean> getDirections() {
		return this.directions;
	}
	
	public boolean equals(Path other) {
		boolean areEqual = true;
		for (int i = 0; i < directions.size(); i++) {
			if (other.getDirections().get(i) != this.directions.get(i)) {
				areEqual = false;
			}
		}
		return areEqual;
	}

	public String toString() {
		return this.directions.toString();
	}

}
