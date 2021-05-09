package application.controller;

import java.util.HashMap;
import java.util.Scanner;

import application.model.Sprite;
import application.model.Tile;
import utility.GameObject;
import utility.Parser;

public class ObjectParser extends Parser {

	private HashMap<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();
	private HashMap<String, Sprite> sprites;

	public ObjectParser(String filename, HashMap<String, Sprite> sprites) {
		// TODO Auto-generated constructor stub
		super(filename);
		this.sprites = sprites;
	}

	public HashMap<Integer, GameObject> getGameObjects() {
		// TODO Auto-generated method stub
		return gameObjects;
	}

	public void tiles(String arguments, String line) {
		if (line == null)
			return;

		Scanner lineScanner = new Scanner(line);
		String spriteName = lineScanner.next();
		int id = lineScanner.nextInt();
		int collisionType = lineScanner.nextInt();
		Tile tile = new Tile(sprites.get(spriteName), collisionType);
		gameObjects.put(id, tile);
		lineScanner.close();
	}

	public void penguin(String arguments, String line) {
		if (line != null) {

		} else {

			// create penguin object with animations
		}
	}
}
