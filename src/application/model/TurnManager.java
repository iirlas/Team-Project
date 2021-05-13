package application.model;

import java.util.ArrayList;
import java.util.Stack;

import javafx.geometry.Point2D;

public class TurnManager {

	private Stack<Point2D> savedState = new Stack<>();
	private ArrayList<Penguin> usedPenguins = new ArrayList<>();
	private ArrayList<Player> players;
	private Penguin selected;
	private int currentIndex;
	private int usedMovement;
	private Grid grid;

	public TurnManager(ArrayList<Player> players, Grid grid) {
		// TODO Auto-generated constructor stub
		this.players = players;
		this.grid = grid;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return getPlayers().get(currentIndex);
	}

	public Player getNextPlayer() {
		// TODO Auto-generated method stub
		return getPlayers().get(Math.floorMod(currentIndex + 1, getPlayers().size()));
	}

	public void next() {
		usedPenguins.clear();
		currentIndex = Math.floorMod(++currentIndex, getPlayers().size());
		usedMovement = 0;
	}

	public boolean isTurnComplete() {
		return usedPenguins.size() == getCurrentPlayer().countAlive() || getMovesLeft() == 0;
	}

	public void select(Penguin penguin) {
		// TODO Auto-generated method stub
		this.selected = penguin;
	}

	public Penguin getSelected() {
		// TODO Auto-generated method stub
		return selected;

	}

	public void moveSelected(double x, double y) {
		// TODO Auto-generated method stub
		Point2D position = selected.getPosition();
		Point2D nextPosition = position.add(x, y);
		if (getMovesLeft() > 0 && selected.canMoveTo(grid.getTile(nextPosition.getX(), nextPosition.getY()))
				&& selected.canMoveFrom(grid.getTile(position.getX(), position.getY()))) {
			savedState.add(position);
			savedState.add(selected.getDirection());
			selected.setDirection(nextPosition.subtract(position).normalize());
			selected.setPosition(nextPosition);
			usedMovement++;
		}
	}

	public void undoSelected() {
		if (!savedState.empty()) {
			selected.setDirection(savedState.pop());
			selected.setPosition(savedState.pop());
			usedMovement--;
		}
	}

	public boolean canSelect(Penguin penguin) {
		// TODO Auto-generated method stub
		return savedState.empty() && !usedPenguins.contains(penguin);
	}

	public void completeMovement() {
		if (!isValid(selected.getPosition())) {
			return;
		}

		savedState.clear();
		usedPenguins.add(selected);
		Point2D facingPosition = selected.getPosition().add(selected.getDirection().getX() * grid.getTileWidth(),
				selected.getDirection().getY() * grid.getTileHeight());
		for (Penguin penguin : getNextPlayer().getPenguins()) {
			if (penguin.getPosition().equals(facingPosition)) {
				penguin.addHealth(-this.selected.getPower());
			}
		}
		selected = null;
	}

	private boolean isValid(Point2D position) {
		// TODO Auto-generated method stub
		for (Player player : getPlayers()) {
			for (Penguin penguin : player.getPenguins()) {
				if (penguin.getHealth() > 0 && !penguin.equals(this.selected)
						&& penguin.getPosition().equals(position)) {
					return false;
				}
			}
		}

		return true;
	}

	private int getMovesLeft() {
		if (selected != null) {
			return Math.min(selected.getSpeed() - savedState.size() / 2,
					getCurrentPlayer().getMovement() - usedMovement);
		} else {
			return getCurrentPlayer().getMovement() - usedMovement;
		}
	}
}
