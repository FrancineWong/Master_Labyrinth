package code;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ExtraPanel extends JPanel {

	private Tile tile = new Tile(-1, -1);
	
	public void update(Tile tile) {
		this.tile = tile;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(new Color(139, 125, 107));
		g2d.setStroke(new BasicStroke(10));
		g.setColor(new Color(34, 139, 34));
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < tile.getPath().getDirections().size(); i++) {
			boolean isPath = tile.getPath().getDirections().get(i);
			if (isPath) {
				switch (i) {
					case 0:  // North
						g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight() / 2 - 3);
						break;
					case 1:  // East
						g2d.drawLine(getWidth(), getHeight() / 2, getWidth() / 2, getHeight() / 2);
						break;
					case 2:  // South
						g2d.drawLine(getWidth() / 2, getHeight() /2, getWidth() / 2, getHeight());
						break;
					case 3:  // West
						g2d.drawLine(0, getHeight() / 2, getWidth() / 2, getHeight() / 2);
						break;
				}
			}
		}
		if (tile.getToken() != null) {
			g2d.setColor(Color.CYAN);
			g2d.fillOval(0 + 10, 0 + 10, getWidth() - 20, getHeight() - 20);
			g2d.setColor(Color.BLACK);
			g2d.drawString(tile.getToken().getKey() + "", (getWidth() / 2) - 7,
					(getHeight() / 2) + 3);
		}
	}
	
}
