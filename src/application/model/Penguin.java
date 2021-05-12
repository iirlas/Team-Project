package application.model;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import utility.GameObject;

public class Penguin extends GameObject {

	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private Point2D position = new Point2D(0, 0);
	private Point2D direction = new Point2D(0, 1);
	private int speed;
	private int health;
	private int power;

	public Penguin(int speed, int health, int power) {
		// TODO Auto-generated constructor stub
		this.speed = speed;
		this.health = health;
		this.power = power;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		for (Sprite sprite : sprites) {
			sprite.play();
		}
	}

	public void add(Sprite sprite) {
		// TODO Auto-generated method stub
		sprites.add(sprite);
	}

	public void setPosition(double x, double y) {
		// TODO Auto-generated method stub
		position = new Point2D(x, y);
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public Point2D getDirection() {
		return direction;
	}

	public void setDirection(Point2D direction) {
		this.direction = direction.normalize();
	}

	public void setDirection(double x, double y) {
		// TODO Auto-generated method stub
		this.direction = new Point2D(x, y).normalize();
	}

	public void move(double x, double y) {
		// TODO Auto-generated method stub
		Point2D nextPosition = position.add(x, y);
		direction = nextPosition.subtract(position).normalize();
		position = nextPosition;
	}
	
	public void render(GraphicsContext context, double width, double height) {
		double angle = getDirection().angle(1, 0);
		if (getDirection().dotProduct(0, -1) < 0) {
			angle = 360 - angle;
		}
		int index = (int) Math.floor(angle / 90);
		sprites.get(index).render(context, position.getX(), position.getY(), width, height);
	}
}
