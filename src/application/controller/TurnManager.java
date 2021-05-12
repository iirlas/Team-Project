package application.controller;

import java.util.ArrayList;
import java.util.Stack;

import application.model.Penguin;
import application.model.Player;
import javafx.geometry.Point2D;

public class TurnManager {

	private Stack<Point2D> savedState = new Stack<>();
	private ArrayList<Player> players;
	private int currentIndex;
	private Penguin penguin;

	public TurnManager(ArrayList<Player> players) {
		// TODO Auto-generated constructor stub
		this.players = players;
	}

	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return players.get(currentIndex);
	}

	public void next() {
		currentIndex = Math.floorMod(++currentIndex, players.size());
		penguin = null;
	}

	public void select(Penguin penguin) {
		// TODO Auto-generated method stub
		this.penguin = penguin;
	}

	public Penguin getSelected() {
		// TODO Auto-generated method stub
		return penguin;

	}

	public void moveSelected(double x, double y) {
		// TODO Auto-generated method stub
		Point2D position = penguin.getPosition();
		Point2D nextPosition = position.add(x, y);
		savedState.add(position);
		savedState.add(penguin.getDirection());
		penguin.setDirection(nextPosition.subtract(position).normalize());
		penguin.setPosition(nextPosition);
	}

	public void undoSelected() {
		if (!savedState.empty()) {
			penguin.setDirection(savedState.pop());
			penguin.setPosition(savedState.pop());
		}
	}
}
