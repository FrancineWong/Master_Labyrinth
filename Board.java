package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This class contains the class definition and function definitions for the Magic
 * Labyrinth game board. It will be a fully functioning game board with references 
 * to other objects that it will later call. 
 * 
 * Board Index:
 * 			NORTH
 *  0  1  2  3  4  5  6
 *  7  8  9  10 11 12 13
 *  14 15 16 17 18 19 20
 *  21 22 23 24 25 26 27
 *  28 29 30 31 32 33 34
 *  35 36 37 38 39 40 41
 *  42 43 44 45 46 47 48
 */
public class Board {
	
	// Board size
	private static final int WIDTH = 7;
	private static final int HEIGHT = 7;
	
	
	// The size of this should be 15 (Then 34 tiles that can be moved)
	// NOTE: since the immovable tile locations are just row/cols that equal 1, 3, or 5,
	//       that can be handled by the GUI
	private static final Tile[] IMMOVABLE_TILES = {
			// new Tile(2, 3);  This places a Tile at (2, 3) that is not movable
			new Tile(0, 0, new Path(false, true, true, false)), 
			new Tile(0, 2, new Path(false, true, true, true)), 
			new Tile(0, 4, new Path(false, true, true, true)), 
			new Tile(0, 6, new Path(false, false, true, true)),
			new Tile(2, 0, new Path(true, true, true, false)), 
			new Tile(2, 2, new Path(true, true, true, false)), 
			new Tile(2, 4, new Path(false, true, true, true)), 
			new Tile(2, 6, new Path(true, false, true, true)),
			new Tile(4, 0, new Path(true, true, true, false)), 
			new Tile(4, 2, new Path(true, true, false, true)), 
			new Tile(4, 4, new Path(true, false, true, true)), 
			new Tile(4, 6, new Path(true, false, true, true)),
			new Tile(6, 0, new Path(true, true, false, false)), 
			new Tile(6, 2, new Path(true, true, false, true)), 
			new Tile(6, 4, new Path(true, true, false, true)), 
			new Tile(6, 6, new Path(true, false, false, true)),
	};
	
	// Dealing with tokens
	private static ArrayList<ArrayList<Integer>> NO_TOKEN_TILES = new ArrayList<ArrayList<Integer>>();
	private int currentToken = 0;
	
	// Board
	protected ArrayList<ArrayList<Tile>> layout;
	
	// Extra Tile that is not currently in game board
	private Tile extraTile = null;
	
	
	public Board() {
		//Add player starts
		NO_TOKEN_TILES.add(new ArrayList<Integer>(Arrays.asList(2, 2)));
		NO_TOKEN_TILES.add(new ArrayList<Integer>(Arrays.asList(2, 4)));
		NO_TOKEN_TILES.add(new ArrayList<Integer>(Arrays.asList(4, 2)));
		NO_TOKEN_TILES.add(new ArrayList<Integer>(Arrays.asList(4, 4)));
		
		// Create board with Null tiles
		layout = new ArrayList<ArrayList<Tile>>();
		for (int row = 0; row < HEIGHT; row++) {
			layout.add(new ArrayList<Tile>());
			for (int col = 0; col < WIDTH; col++) {
				layout.get(row).add(null);
			}
		}
		// Place immovable tiles
		initializeBoard();
		randomizeTiles();
		dropTokens();
	}
	
	
	/**
	 * This function takes the current Board object and places the immovable tiles
	 * in the correct location on it. This assumes that none of the IMMOVABLE_TILES
	 * are null and that the ArrayList space in this.layout has been created. 
	 */
	private void initializeBoard() {
		for (int i=0; i < IMMOVABLE_TILES.length; i++) {
			Tile tile = IMMOVABLE_TILES[i];
			layout.get(tile.getRow()).set(tile.getCol(), tile);
		}
	}
	
	
	/**
	 * This function takes the current Board object and places randomized tiles in
	 * the places that are available, and then stores 1 tile for the extra tile.
	 * Assumes that 
	 */
	private void randomizeTiles() {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				if (layout.get(row).get(col) == null) {
					layout.get(row).set(col, createTile(row, col));
				}
			}
		}
		if (this.extraTile == null) {
			this.extraTile = createTile(-1, -1);
		}
	}
	
	/**
	 * This function drops tokens all over the board on positions not defined in NO_TOKEN_TILES.
	 */
	private void dropTokens() {
		// Initialize Tokens
		Token.initTokens();
		ArrayList<ArrayList<Integer>> spots = new ArrayList<ArrayList<Integer>>();
		Random r = new Random();
		
		// Get coordinates for the tokens
		for (int i = 0; i < 21; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>(Arrays.asList(r.nextInt(this.WIDTH-2)+1, r.nextInt(this.HEIGHT-2)+1));
			// Keep checking if in no_token or if there is already a token
			while (this.NO_TOKEN_TILES.contains(temp) || this.layout.get(temp.get(1)).get(temp.get(0)).getToken() != null) {
				temp = new ArrayList<Integer>(Arrays.asList(r.nextInt(this.WIDTH-2)+1, r.nextInt(this.HEIGHT-2)+1));
			}
			
			// Add to the token spots
			spots.add(temp);
			// Get the tile and drop
			Tile tile = layout.get(temp.get(1)).get(temp.get(0));
			if (i == 20) {
				Token toke = new Token(25);
				tile.setToken(toke);
			} else {
				Token toke = new Token(i+1);
				tile.setToken(toke);
			}
		}
		this.currentToken = 0;
	}
	
	
	/**
	 * This function builds a Tile with a random layout that has a least 2 pathways on it,
	 * and then it will @return the Tile object 
	 */
	private Tile createTile(int row, int col) {
		// TODO: This needs built
		return new Tile(row, col);
	}
	
	
	/**
	 * This function takes the extraTile Tile object and slides it into the Board layout at
	 * a certain row. It assumes that both have been instantiated. The function will slide
	 * the piece in Left to Right unless the @param invert is set to true, in which case 
	 * it will insert from the Right and move the row left.
	 * @param row is the row that the piece will be fitted into (0 indexed)
	 * @param invert 
	 * @return true 
	 */
	public Tile slideRow(int row, boolean invert) {
		ArrayList<Tile> currentRow = this.layout.get(row);  // Get selected row
		if (invert) {
			// Right to left
			currentRow.add(extraTile);  // Add extraTile to the end (right side)
			this.extraTile = currentRow.remove(0);  // Set extraTile to the first tile and remove it
			if (this.extraTile.getToken() != null) {
				currentRow.get(currentRow.size() - 1).setToken(this.extraTile.getToken());
				this.extraTile.setToken(null);
			}
		} else {
			// Left to right
			currentRow.add(0, extraTile);  // Add extraTile to the beginning
			this.extraTile = currentRow.remove(currentRow.size() - 1);  // Set the removed tile to extraTile
			if (this.extraTile.getToken() != null) {
				currentRow.get(0).setToken(this.extraTile.getToken());
				this.extraTile.setToken(null);
			}
		}
		this.layout.set(row, currentRow);  // Double check that layout gets updated
		updateTiles();
		return extraTile;
	}
	
	
	/**
	 * This function takes the extraTile Tile object and slides it into the Board layout at
	 * a certain column. It assumes that both have been instantiated. The function will slide
	 * the piece in Up to down unless the @param invert is set to true, in which case 
	 * it will insert from the Bottom and slide the col up.
	 * @param col is the column that the piece will be fitted into (0 indexed)
	 * @param invert 
	 * @return true
	 */
	public Tile slideCol(int col, boolean invert) {
		Tile temp = this.extraTile; // Last removed tile
		// Iterate through rows
		if (invert) {
			for (int row = layout.size() -1; row >= 0; row--) {
				// Set the column in a row to the temp and then temp will be the previous spot
				temp = this.layout.get(row).set(col, temp);
			}
			if (temp.getToken() != null) {
				this.layout.get(HEIGHT-1).get(col).setToken(temp.getToken());
				temp.setToken(null);
			}
		} else {
			for (int row = 0; row < layout.size(); row++) {
				// Set the column in a row to the temp and then temp will be the previous spot
				temp = this.layout.get(row).set(col, temp);
			}
			if (temp.getToken() != null) {
				this.layout.get(0).get(col).setToken(temp.getToken());
				temp.setToken(null);
			}
		}
		this.extraTile = temp;  // Extra tile becomes what was removed last
		updateTiles();
		return extraTile;
	}
	
	public boolean moveCharacter(int startRow, int startCol, int endRow, int endCol) {
		if (validPathBetween(startRow, startCol, endRow, endCol)) {
			//TODO: Check if there is a token
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This updates the tiles' col's and row's, then creates a new graph
	 */
	private void updateTiles() {
		for (int row = 0; row < HEIGHT; row++) {
			for (int col = 0; col < WIDTH; col++) {
				Tile t = layout.get(row).get(col);
				t.setRow(row); t.setCol(col);
			}
		}
	}
	
	/**
	 * Returns an index when given (row, col)
	 * @param row
	 * @param col
	 * @return
	 */
	public static int getTileIndex(int row, int col) {
		return (row * WIDTH) + col;
	}
	
	/**
	 * Returns an int array of (row, col)
	 * @param index
	 * @return
	 */
	public int[] getCord(int index) {
		int col = index % WIDTH;
		int row = index / WIDTH;
		return new int[] {row, col};
	}
	
	public boolean validPathBetween(int startRow, int startCol, int endRow, int endCol) {
		if (startRow == endRow && startCol == endCol) {
			return true;
		}
		ArrayList<Integer> possiblePaths = new ArrayList<Integer>(25);
		int index=getTileIndex(startRow,startCol);
		ArrayList<Integer> que=new ArrayList<Integer>();
		que.add(index);
		while(!que.isEmpty()){
			index=que.get(0);
			Tile cur=getTile(que.get(0));
			cur._visited=true;
			ArrayList<Boolean> directions=cur.getPath().getDirections();
			if(index-7>=0){
				if((directions.get(0)&&!(getTile(index-7)._visited))&&getTile(index-7).getPath().getDirections().get(2)){
					que.add(index-7);
					possiblePaths.add(index-7);
				}
			}
			if(index-1>=0){
				if((directions.get(3)&&!(getTile(index-1)._visited))&&getTile(index-1).getPath().getDirections().get(1)){
					que.add(index-1);
					possiblePaths.add(index-1);
				}
			}
			if(index+7<49){
				if((directions.get(2)&&!(getTile(index+7)._visited))&&getTile(index+7).getPath().getDirections().get(0)){
					que.add(index+7);
					possiblePaths.add(index+7);
				}
			}
			if(index+1<49){
				if((directions.get(1)&&!(getTile(index+1)._visited))&&getTile(index+1).getPath().getDirections().get(3)){
					que.add(index+1);
					possiblePaths.add(index+1);
				}
			}
			que.remove(0);
		}
		for(int i=0;i<49;i++){
			Tile cur=getTile(i);
			cur._visited=false;
		}
		int goal=getTileIndex(endRow,endCol);
		if(possiblePaths.contains(goal)){
			System.out.println("Path");
			return true;
		}
		System.out.println("No path");
		return false;
	}
	
	public Tile getTile(int index) {
		int[] cord=getCord(index);
		Tile output=layout.get(cord[0]).get(cord[1]);
		return output;
	}


	public Tile getExtra() {
		return this.extraTile;
	}
	
	public ArrayList<Tile> getRow(int i) {
		return this.layout.get(i);
	}
	
	public boolean isSetup() {
		boolean setup = true;
		for (ArrayList<Tile> row : this.layout) {
			for (Tile tile : row) {
				if (tile == null) {
					System.out.print("Null, ");
					setup = false;
				} else {
					System.out.println(tile.toString());
				}
			}
		}
		return setup;
	}
	
	public void setExtra(Tile t){
		extraTile=t;
	}

	public ArrayList<ArrayList<Tile>> getLayout() {
		return this.layout;

	}
	
	public String toString() {
		String string = "";
		for (ArrayList<Tile> row : this.layout) {
			for (Tile tile : row) {
				string += tile.toString() + "\n";
			}
		}
		return string;
	}
	
}
