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

	public PlayerParser(String filepath, HashMap<Integer, GameObject> gameObjects, double tileWidth,
			double tileHeight) {
		super(filepath);
		// TODO Auto-generated constructor stub
		this.gameObjects = gameObjects;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	public ArrayList<Player> getPlayers() {
		// TODO Auto-generated method stub
		ArrayList<Player> players = new ArrayList<>();
		for (GameObject gameObject : gameObjects.values()) {
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
			gameObjects.put(id, player);
//			players.put(id, player);
		} else {
			Scanner lineScanner = new Scanner(line);
			Player player = (Player) gameObjects.get(id);
			Penguin penguin = (Penguin) gameObjects.get(lineScanner.nextInt());
			penguin.setPosition(lineScanner.nextDouble() * tileWidth, lineScanner.nextDouble() * tileHeight);
			penguin.setDirection(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle)));
			player.addPenguin(penguin);
			lineScanner.close();
		}
	}

}
