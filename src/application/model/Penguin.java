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
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

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
		this.direction = direction;
	}

	public void setDirection(double x, double y) {
		// TODO Auto-generated method stub
		this.direction = new Point2D(x, y);
	}

	public void render(GraphicsContext context, Point2D size) {
		double angle = getDirection().angle(1, 0);
		if (getDirection().dotProduct(0, -1) < 0) {
			angle = 360 - angle;
		}
		int index = (int) Math.round(angle / 90);
		sprites.get(index).render(context, position.getX(), position.getY(), size.getX(), size.getY());
	}
}
