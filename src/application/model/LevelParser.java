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
	private HashMap<Integer, Tile> tiles = new HashMap<Integer, Tile>();
	private ImageParser imageParser;
	private Rectangle2D dimensions;

	public LevelParser(File file, ImageParser imageParser) {
		// TODO Auto-generated constructor stub
		super(file);
		this.imageParser = imageParser;
	}

	public Rectangle2D getDimensions() {
		// TODO Auto-generated method stub
		return dimensions;
	}

	public ArrayList<Tile> getTiles() {
		// TODO Auto-generated method stub
		return tilemap;
	}

	public void tiles(String arguments, String line) {
		if (line == null)
			return;

		Scanner lineScanner = new Scanner(line);
		String spriteName = lineScanner.next();
		int id = lineScanner.nextInt();
		int collisionType = lineScanner.nextInt();
		Tile tile = new Tile(imageParser.getSprite(spriteName), collisionType);
		tiles.put(id, tile);
		lineScanner.close();
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
			tilemap.add(tiles.get(lineScanner.nextInt()));
		}
		lineScanner.close();
	}

}
