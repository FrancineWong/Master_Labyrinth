package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import code.GameApplet.GameState;

public class MapPanel extends JPanel {

	// Save some CPU usage
	private int repaintTime = 0;

	protected int tileWidth;
	protected int tileHeight;
	protected ArrayList<ArrayList<Tile>> layout = new ArrayList<ArrayList<Tile>>();
	protected Pawn[] players;

	// For highlighting and selecting
	protected int[] slidablePos = new int[] { 1, 3, 5, -1, -3, -5 };
	protected int slideSelect = 1;
	protected int highlightRow = 1;
	protected int highlightCol = 1;
	protected boolean invert = false;

	// Stuff for calling
	protected boolean canSlide = false;
	protected boolean canMove = false;

	private GameApplet base;

	// Images
	private BufferedImage corner, complete, straight, tee, yellow, blue, green, red, token;
	AffineTransform identity = new AffineTransform();

	public MapPanel(GameApplet base) {
		this.base = base;
		String dir = System.getProperty("user.dir") + "/src/data/";
		try {
			corner = ImageIO.read(new File(dir + "corner.png"));
			complete = ImageIO.read(new File(dir + "complete.png"));
			straight = ImageIO.read(new File(dir + "straight.png"));
			tee = ImageIO.read(new File(dir + "tee.png"));
			yellow = ImageIO.read(new File(dir + "yellow.png"));
			blue = ImageIO.read(new File(dir + "blue.png"));
			green = ImageIO.read(new File(dir + "green.png"));
			red = ImageIO.read(new File(dir + "red.png"));
			token = ImageIO.read(new File(dir + "token.png"));
		} catch (IOException e) {

		}
	}

	public void update(ArrayList<ArrayList<Tile>> layout, Pawn[] players) {
		this.layout = layout;
		this.players = players;
		this.tileHeight = this.getHeight() / layout.size();
		this.tileWidth = this.getWidth() / layout.get(0).size();
		repaint();
	}

	public void selectLeft() {
		if (this.slideSelect == 0) {
			// Set to the last
			this.slideSelect = slidablePos.length - 1;
		} else {
			this.slideSelect--;
		}
	}

	public void selectRight() {
		if (this.slideSelect == this.slidablePos.length - 1) {
			// Set to the beginning
			this.slideSelect = 0;
		} else {
			this.slideSelect++;
		}
	}

	public void flipInvert() {
		this.invert = !invert;
	}

	public void moveUp() {
		if (highlightRow > 0) {
			this.highlightRow--;
		}
	}

	public void moveDown() {
		if (highlightRow < 6) {
			this.highlightRow++;
		}
	}

	public void moveLeft() {
		if (highlightCol > 0) {
			this.highlightCol--;
		}
	}

	public void moveRight() {
		if (highlightCol < 6) {
			this.highlightCol++;
		}
	}

	public void submitLocation() {
		if (base.currentState == GameState.SLIDESELECT) {
			if (slideSelect < 3) {
				System.out.println("Picked Column " + slidablePos[slideSelect]);
			} else {
				System.out.println("Picked Row " + -1 * slidablePos[slideSelect]);
			}
		} else if (base.currentState == GameState.DIRECTION) {
			// Row left or right
			if (slideSelect > 2) {
				if (invert) {
					System.out.println("Slide from the right.");
				} else {
					System.out.println("Slide from the left.");
				}
				canSlide = true;
				// Column Up or down
			} else {
				if (invert) {
					System.out.println("Slide from the bottom.");
				} else {
					System.out.println("Slide from the Top.");
				}
			}
			canSlide = true;
		} else if (base.currentState == GameState.MOVESELECT) {
			System.out.println("Tried to move pawn to (" + highlightCol + ", " + highlightRow + ").");
			canMove = true;
		} else {
			System.out.println("Waiting for Logic");
		}
	}

	public boolean isCanSlide() {
		return canSlide;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean b) {
		this.canMove = b;
	}

	public int getMoveIndex() {
		if (canMove) {
			return Board.getTileIndex(highlightRow, highlightCol);
		}
		canMove = false;
		return -1;
	}

	public void setCanSlide(boolean canSlide) {
		this.canSlide = canSlide;
	}

	public int getSlideSelect() {
		return slideSelect;
	}

	public int getSlideValue() {
		return Math.abs(slidablePos[slideSelect]);
	}

	public boolean getInvert() {
		return invert;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(new Color(0, 87, 1));
		g2d.fillRect(0, 0, this.getWidth() + 1, this.getHeight() + 1);
		for (int row = 0; row < layout.size(); row++) {
			ArrayList<Tile> currentRow = layout.get(row);
			for (int col = 0; col < currentRow.size(); col++) {
				Tile tile = currentRow.get(col);
				// Draw the tiles
				BufferedImage choice = pickPathImage(tile); // Pick the image

				AffineTransform tx = new AffineTransform();
				tx.rotate(rotateAngle(tile, choice), choice.getWidth() / 2, choice.getHeight() / 2); // Rotations
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

				g2d.drawImage(op.filter(choice, null), col * tileWidth, row * tileHeight, null); // Draw
																									// image

				// Draw outline of all the tiles in black
				g2d.setColor(Color.BLACK);
				g2d.drawRect(col * tileWidth, row * tileHeight, tileWidth, tileHeight);

				// Draw Token
				if (tile.getToken() != null) {
					g2d.drawImage(token, col * tileWidth, row * tileHeight, null);
					g2d.drawString(tile.getToken().getKey() + "", col * tileWidth + 25, row * tileHeight + 37);
				}

			}
		}

		// Draw the players
		for (int i = 0; i < players.length; i++) {
			BufferedImage player = red;
			switch (i) {
			case 0:
				player = red;
				break;
			case 1:
				player = blue;
				break;
			case 2:
				player = yellow;
				break;
			case 3:
				player = green;
				break;
			}
			g2d.drawImage(player, players[i].get_currentX() * tileWidth + 23,
					players[i].get_currentY() * tileHeight + 16, null);

		}

		// Highlight either a row or column to be shifted
		if (base.currentState == GameState.MOVESELECT) {
			g2d.setColor(new Color(255, 255, 0, 127));
			g2d.fillRect((this.highlightCol * tileWidth), (tileHeight * this.highlightRow), tileWidth, tileHeight);

		} else if (base.currentState == GameState.DIRECTION) {
			g2d.setColor(new Color(255, 255, 0, 127));
			// Picking either left or right to row
			if (slidablePos[slideSelect] < 0) {
				g2d.fillRect(0, -1 * (tileHeight * slidablePos[slideSelect]), this.getWidth(), tileHeight);
				g2d.setColor(new Color(255, 0, 0, 127));
				if (!invert) {
					g2d.fillRect(0, -1 * (tileHeight * slidablePos[slideSelect]), tileWidth, tileHeight);
				} else {
					g2d.fillRect(getWidth() - tileWidth - 2, -1 * (tileHeight * slidablePos[slideSelect]), tileWidth,
							tileHeight);
				}
				// Picking either top or bottom of column
			} else {
				g2d.fillRect((slidablePos[slideSelect] * tileWidth), 0, tileWidth, this.getHeight());
				g2d.setColor(new Color(255, 0, 0, 127));
				if (!invert) {
					g2d.fillRect((tileWidth * slidablePos[slideSelect]), 0, tileWidth, tileHeight);
				} else {
					g2d.fillRect((tileWidth * slidablePos[slideSelect]), getHeight() - tileHeight - 2, tileWidth,
							tileHeight);
				}
			}

		} else if (base.currentState == GameState.SLIDESELECT) {
			// Selecting where to slide
			if (slidablePos[slideSelect] < 0) {
				g2d.setColor(new Color(255, 255, 0, 127));
				g2d.fillRect(0, -1 * (tileHeight * slidablePos[slideSelect]), this.getWidth(), tileHeight);
			} else {
				g2d.setColor(new Color(255, 255, 0, 127));
				g2d.fillRect((slidablePos[slideSelect] * tileWidth), 0, tileWidth, this.getHeight());
			}
		}

	}

	public double rotateAngle(Tile tile, BufferedImage image) {
		// Get which indexes are true

		if (compareImages(image, corner)) {
			// TODO: This still needs fixed
			int startIndex;
//			for (int i = 0; i < tile.getPath().getDirections().size() - 1; i++) {
//				boolean current = tile.getPath().getDirections().get(i);
//				if (i == 0
//						&& current == tile.getPath().getDirections().get(tile.getPath().getDirections().size() - 1)) {
//					return Math.toRadians(270);
//				}
//				if (current == tile.getPath().getDirections().get(i + 1)) {
//					return Math.toRadians(i * 90);
//				}
//				
//			}
			if(tile.getPath().getDirections().get(0)&&tile.getPath().getDirections().get(1)){
				return Math.toRadians(0);
			}
			if(tile.getPath().getDirections().get(1)&&tile.getPath().getDirections().get(2)){
				return Math.toRadians(90);
			}
			if(tile.getPath().getDirections().get(2)&&tile.getPath().getDirections().get(3)){
				return Math.toRadians(180);
			}
			if(tile.getPath().getDirections().get(0)&&tile.getPath().getDirections().get(3)){
				return Math.toRadians(270);
			}
		} else if (compareImages(image, straight)) {
			// This IS Done
			if (tile.getPath().getDirections().get(0)) {
				return Math.toRadians(0);
			} else {
				return Math.toRadians(90);
			}
		} else if (compareImages(image, tee)) {
			// this needs done TODO
			//check which index is false
			for (int i = 0; i < tile.getPath().getDirections().size() - 1; i++){
				if(!tile.getPath().getDirections().get(i)){
					return Math.toRadians((i+1)*90);
				}
			}
			
		} else {
			// If all 4 are true, no need for rotations
			return Math.toRadians(0);
		}
		return Math.toRadians(0);
	}

	private BufferedImage pickPathImage(Tile tile) {
		int counter = 0;
		for (Boolean path : tile.getPath().getDirections()) {
			if (path) {
				counter++;
			}
		}
		switch (counter) {
		case 2:
			// If 2 piece, then check if the two directions are adjacent.
			boolean isCorner = false;
			int size = tile.getPath().getDirections().size();
			for (int i = 0; i < tile.getPath().getDirections().size() - 1; i++) {
				boolean current = tile.getPath().getDirections().get(i);
				if (i == 0 && current == tile.getPath().getDirections().get(size - 1)) {
					isCorner = true;
				}
				if (current == tile.getPath().getDirections().get(i + 1)) {
					isCorner = true;
				}
			}
			if (isCorner) {
				return corner;
			}
			return straight;

		case 3:
			return tee;

		case 4:
			return complete;
		default:
			return null;
		}
	}

	public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
		// The images must be the same size.
		if (imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()) {
			int width = imgA.getWidth();
			int height = imgA.getHeight();

			// Loop over every pixel.
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					// Compare the pixels for equality.
					if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
						return false;
					}
				}
			}
		} else {
			return false;
		}

		return true;
	}

}
