package application.model;

import java.util.HashMap;
import java.util.Scanner;

import javafx.geometry.Point2D;
import utility.GameObject;
import utility.Parser;

public class ObjectParser extends Parser {

	private HashMap<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();
	private HashMap<String, Sprite> sprites;

	public void setGameObjects(HashMap<Integer, GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}

	public HashMap<Integer, GameObject> getGameObjects() {
		// TODO Auto-generated method stub
		return gameObjects;
	}

	public HashMap<String, Sprite> getSprites() {
		return sprites;
	}

	public void setSprites(HashMap<String, Sprite> sprites) {
		this.sprites = sprites;
	}

	public void tiles(String arguments, String line) {
		if (line == null)
			return;

		Scanner lineScanner = new Scanner(line);
		String spriteName = lineScanner.next();
		int id = lineScanner.nextInt();
		int collisionType = lineScanner.nextInt();
		Tile tile = new Tile(getSprites().get(spriteName), collisionType);
		getGameObjects().put(id, tile);
		lineScanner.close();
	}

	public void penguin(String arguments, String line) {
		// create penguin object with animations
		Scanner argumentScanner = new Scanner(arguments);
		int id = argumentScanner.nextInt();
		int speed = argumentScanner.nextInt();
		int health = argumentScanner.nextInt();
		int power = argumentScanner.nextInt();
		argumentScanner.close();
		
		if (line == null) {
			Penguin penguin = new Penguin(speed, health, power);
			getGameObjects().put(id, penguin);
		} else {
			
			Penguin penguin = (Penguin) getGameObjects().get(id);
			penguin.add(getSprites().get(line));
			
		}
	}
}
