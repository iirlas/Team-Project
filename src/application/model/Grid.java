package application.model;

import java.util.ArrayList;

import utility.GameObject;

public class Grid extends GameObject {

	private ArrayList<GameObject> tiles = new ArrayList<GameObject>();
	
	public Grid() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void Start() {
		// TODO Auto-generated method stub
		for (GameObject tile : tiles) {
			tile.Start();
		}
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		for (GameObject tile : tiles) {
			tile.Update();
		}
	}

}
