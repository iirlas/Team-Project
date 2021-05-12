package application.model;

import utility.GameObject;

public class Tile extends GameObject {

	final public static int PASSABLE= 0x1;
	final public static int BLOCKED	= 0x2;
	final public static int SLIME	= 0x4;
	
	private Sprite sprite;
	private int collisionType;

	public Tile(Sprite sprite, int collisionType) {
		this.setCollisionType(collisionType);
		// TODO Auto-generated constructor stub
		this.setSprite(sprite);
		if (sprite != null) {
			getSprite().play();
		}
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

	public int getCollisionType() {
		return collisionType;
	}

	public void setCollisionType(int collisionType) {
		this.collisionType = collisionType;
	}
}
