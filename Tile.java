package code;

public class Tile {

	// This class is DONE.
	
	private Path path;
	private int row;
	private int col;
	private boolean immovable = false;
	private Token token = null;
	protected boolean _visited=false;

	/**
	 * If path is declared. aka immovable tiles
	 * @param row
	 * @param col
	 * @param path
	 */
	public Tile(int row, int col, Path path) {
		this.path = path;
		this.row = row;
		this.col = col;
		this.immovable = true;
	}
	
	/**
	 * If the path needs to be randomly created
	 * @param row
	 * @param col
	 */
	public Tile(int row, int col) {
		this.path = new Path();
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Rotates the path
	 * @param counterclockwise
	 */
	public void rotatePath(boolean counterclockwise) {
		this.path.rotate(counterclockwise);
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void setToken(Token t) {
		this.token = t;
	}
	
	public Token getToken() {
		return token;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void setRow(int r) {
		this.row = r;
	}
	
	public void setCol(int c) {
		this.col = c;
	}
	
	public Path getPath() {
		return this.path;
	}
	
	public boolean equals(Tile other) {
		if (other.getPath().equals(this.path) && other.getCol() == this.getCol() && this.getRow() == other.getRow()) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		if (this.token != null) {
			return "["+this.col+","+this.row+"]: " + this.path.getDirections().toString() + ": Token: "+this.token.getValue();
		} 
		return "["+this.col+","+this.row+"]: " + this.path.getDirections().toString() + ": Token: No";
	}

	public boolean isImmovable() {
		return this.immovable;
	}
	
}
