package application.model;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import utility.GameObject;

public class Grid {

	private ArrayList<Tile> tiles;
	private Rectangle2D dimensions;

	public Grid(Rectangle2D dimensions, ArrayList<Tile> tiles) {
		// TODO Auto-generated constructor stub
		this.dimensions = dimensions;
		this.tiles = tiles;
	}

	public double getTileWidth() {
		return dimensions.getWidth();
	}

	public double getTileHeight() {
		return dimensions.getHeight();
	}

	public Tile getTile(int column, int row) {
		return tiles.get(column + (int) (row * dimensions.getMinX()));
	}

	public Tile getTile(double x, double y) {
		return getTile((int) (x / dimensions.getWidth()), (int) (y / dimensions.getHeight()));
	}

	public void render(GraphicsContext context) {
		for (int index = 0; index < tiles.size(); index++) {
			double x = dimensions.getWidth() * (index % (int) dimensions.getMinX());
			double y = dimensions.getHeight() * (index / (int) dimensions.getMinX());
			tiles.get(index).getSprite().render(context, x, y, dimensions.getWidth(), dimensions.getHeight());
		}
	}
}
