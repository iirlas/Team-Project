package application.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javafx.geometry.Rectangle2D;
import utility.GameObject;
import utility.Parser;

public class LevelParser extends Parser {

	private ArrayList<Tile> tilemap = new ArrayList<Tile>();
	HashMap<Integer, GameObject> gameObjects;
	private Rectangle2D dimensions;

	public LevelParser(File file, HashMap<Integer, GameObject> gameObjects) {
		// TODO Auto-generated constructor stub
		super(file);
		this.gameObjects = gameObjects;
	}

	public Rectangle2D getDimensions() {
		// TODO Auto-generated method stub
		return dimensions;
	}

	public ArrayList<Tile> getTiles() {
		// TODO Auto-generated method stub
		return tilemap;
	}

	public void dimensions(String arguments, String line) {
		if (line != null)
			return;
		Scanner argumentScanner = new Scanner(arguments);
		this.dimensions = new Rectangle2D(argumentScanner.nextDouble(), argumentScanner.nextDouble(),
				argumentScanner.nextDouble(), argumentScanner.nextDouble());
		argumentScanner.close();
	}

	public void levelmap(String arguments, String line) {
		if (line == null)
			return;
		Scanner lineScanner = new Scanner(line);
		while (lineScanner.hasNextInt()) {
			tilemap.add((Tile) gameObjects.get(lineScanner.nextInt()));
		}
		lineScanner.close();
	}

	public void penguins(String arguments, String line) {
		if (line == null)
			return;
		Scanner lineScanner = new Scanner(line);
		Penguin penguin = (Penguin) gameObjects.get(lineScanner.nextInt());
		penguin.setPosition(lineScanner.nextDouble() * dimensions.getWidth(), lineScanner.nextDouble() * dimensions.getHeight());
		lineScanner.close();
	}
}
