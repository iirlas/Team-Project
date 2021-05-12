package application.model;

import java.util.ArrayList;
import java.util.Stack;

import javafx.geometry.Point2D;

public class TurnManager {

	private Stack<Point2D> savedState = new Stack<>();
	private ArrayList<Penguin> usedPenguins = new ArrayList<>();
	private ArrayList<Player> players;
	private Penguin penguin;
	private int currentIndex;
	private int usedMovement;
	private Grid grid;

	public TurnManager(ArrayList<Player> players, Grid grid) {
		// TODO Auto-generated constructor stub
		this.players = players;
		this.grid = grid;
	}

	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return players.get(currentIndex);
	}

	public Player getNextPlayer() {
		// TODO Auto-generated method stub
		return players.get(Math.floorMod(currentIndex + 1, players.size()));
	}

	public void next() {
		usedPenguins.clear();
		currentIndex = Math.floorMod(++currentIndex, players.size());
		usedMovement = 0;
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
		if (getMovesLeft() > 0 && penguin.canMoveTo(grid.getTile(nextPosition.getX(), nextPosition.getY()))
				&& penguin.canMoveFrom(grid.getTile(position.getX(), position.getY()))) {
			savedState.add(position);
			savedState.add(penguin.getDirection());
			penguin.setDirection(nextPosition.subtract(position).normalize());
			penguin.setPosition(nextPosition);
			usedMovement++;
		}
	}

	public void undoSelected() {
		if (!savedState.empty()) {
			penguin.setDirection(savedState.pop());
			penguin.setPosition(savedState.pop());
			usedMovement--;
		}
	}

	public boolean canSelect(Penguin penguin) {
		// TODO Auto-generated method stub
		return savedState.empty() && !usedPenguins.contains(penguin);
	}

	public int countSelectable() {
		int count = 0;
		for (Penguin penguin : getCurrentPlayer().getPenguins()) {
			if (penguin.getHealth() > 0) {
				count++;
			}
		}
		return count;
	}

	public void completeMovement() {
		savedState.clear();
		usedPenguins.add(penguin);
		Point2D facingPosition = penguin.getPosition().add(penguin.getDirection().getX() * grid.getTileWidth(),
				penguin.getDirection().getY() * grid.getTileHeight());
		for (Penguin penguin : getNextPlayer().getPenguins()) {
			if (penguin.getPosition().equals(facingPosition)) {
				penguin.addHealth(-this.penguin.getPower());
			}
		}
		penguin.getPower();
		if (usedPenguins.size() == countSelectable() || getCurrentPlayer().getMovement() == usedMovement) {
			next();
		}
		penguin = null;
	}

	public int getMovesLeft() {
		return Math.min(penguin.getSpeed() - savedState.size() / 2, getCurrentPlayer().getMovement() - usedMovement);
	}
}
