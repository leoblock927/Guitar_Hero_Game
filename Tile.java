import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Tile {
	final int originalTileSize = 16;
	final int scale = 3;
	final int tileSize = originalTileSize * scale;
	
	int tileY = 100;
	
	public Tile(Graphics g) {
		Graphics2D t = (Graphics2D)g;
		int randX = ((int) (Math.random()*4))*100;
		t.setColor(Color.yellow);
		t.fillRect(randX,tileY,tileSize,tileSize);
	}
	
	public int getTileY() {
		return tileY;
	}
	public void setTileY(int newY) {
		tileY = newY;
	}
}
