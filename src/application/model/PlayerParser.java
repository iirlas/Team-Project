package application.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import utility.GameObject;
import utility.Parser;

public class PlayerParser extends Parser {

	private HashMap<Integer, GameObject> gameObjects;
	private double tileWidth;
	private double tileHeight;

	public HashMap<Integer, GameObject> getGameObjects() {
		return gameObjects;
	}

	public void setGameObjects(HashMap<Integer, GameObject> gameObjects) {
		this.gameObjects = gameObjects;
	}

	public double getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(double tileWidth) {
		this.tileWidth = tileWidth;
	}

	public double getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(double tileHeight) {
		this.tileHeight = tileHeight;
	}

	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		ArrayList<Player> players = new ArrayList<>();
		for (GameObject gameObject : getGameObjects().values()) {
			if (gameObject instanceof Player) {
				players.add((Player) gameObject);
			}
		}
		return players;
	}

	public void player(String arguments, String line) {
		Scanner argumentScanner = new Scanner(arguments);
		int id = argumentScanner.nextInt();
		int movement = argumentScanner.nextInt();
		double angle = argumentScanner.nextDouble();
		argumentScanner.close();

		if (line == null) {
			// add player
			Player player = new Player(movement);
			getGameObjects().put(id, player);
//			players.put(id, player);
		} else {
			Scanner lineScanner = new Scanner(line);
			Player player = (Player) getGameObjects().get(id);
			Penguin penguin = (Penguin) getGameObjects().get(lineScanner.nextInt());
			penguin.setPosition(lineScanner.nextDouble() * getTileWidth(), lineScanner.nextDouble() * getTileHeight());
			penguin.setDirection(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)));
			player.addPenguin(penguin);
			lineScanner.close();
		}
	}

}
