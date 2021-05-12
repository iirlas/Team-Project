package application.model;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import utility.GameObject;

public class Player extends GameObject {

	private int movement;
	private ArrayList<Penguin> penguins = new ArrayList<>();

	public Player(int movement) {
		// TODO Auto-generated constructor stub
		this.movement = movement;
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
}
