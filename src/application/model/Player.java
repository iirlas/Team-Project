package application.model;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import utility.GameObject;

public class Player extends GameObject {

	private int movement;
	private ArrayList<Penguin> penguins = new ArrayList<>();

	public Player(int movement) {
		// TODO Auto-generated constructor stub
		this.setMovement(movement);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public ArrayList<Penguin> getPenguins() {
		return penguins;
	}

	public void setPenguins(ArrayList<Penguin> penguins) {
		this.penguins = penguins;
	}

	public void addPenguin(Penguin penguin) {
		// TODO Auto-generated method stub
		getPenguins().add(penguin);
	}

	public int getMovement() {
		return movement;
	}

	public void setMovement(int movement) {
		this.movement = movement;
	}

	public void render(GraphicsContext context, double width, double height) {

		for (Penguin penguin : penguins) {
			if (penguin.getHealth() > 0) {
				penguin.render(context, width, height);
			}
		}
	}
}
