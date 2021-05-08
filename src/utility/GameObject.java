package utility;

import javafx.geometry.Point2D;

public abstract class GameObject {
	
	private Point2D position;
	
	public abstract void start();
	
	public abstract void update();
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
}
