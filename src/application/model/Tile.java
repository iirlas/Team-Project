package application.model;

import javafx.scene.canvas.GraphicsContext;
import utility.GameObject;

public class Tile extends GameObject {

	private Sprite sprite;
	
	public Tile(Sprite sprite, int collisionType) {
		// TODO Auto-generated constructor stub
		this.setSprite(sprite);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		getSprite().play();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(GraphicsContext context, double x, double y) {
		getSprite().render(context, x, y);
	}
}
