package code;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] playerName) {
		//pass  the player names from arguments to method logic
		
		//check the validation of the arguments
		//number of arguments has to be 2,3,4
		if(playerName.length<2 && playerName.length>4){
			throw new IllegalArgumentException("Argument numbers should be 2, 3 or 4!");
		}else{
			new GameApplet(playerName);
		}
	}
}
