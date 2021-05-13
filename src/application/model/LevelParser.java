package application.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javafx.geometry.Rectangle2D;
import utility.GameObject;
import utility.Parser;

public class LevelParser extends Parser {

	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	private HashMap<Integer, GameObject> gameObjects;
	private Rectangle2D dimensions;
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	public HashMap<Integer, GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(HashMap<Integer, GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}

	public Rectangle2D getDimensions() {
		return dimensions;
	}

	public void setDimensions(Rectangle2D dimensions) {
		this.dimensions = dimensions;
	}

	public void dimensions(String arguments, String line) {
		if (line != null)
			return;
		Scanner argumentScanner = new Scanner(arguments);
		this.setDimensions(new Rectangle2D(argumentScanner.nextDouble(), argumentScanner.nextDouble(),
				argumentScanner.nextDouble(), argumentScanner.nextDouble()));
		argumentScanner.close();
	}

	public void levelmap(String arguments, String line) {
		if (line == null)
			return;
		Scanner lineScanner = new Scanner(line);
		while (lineScanner.hasNextInt()) {
			getTiles().add((Tile) getGameObjects().get(lineScanner.nextInt()));
		}
		lineScanner.close();
	}
}
