package utility;

import javafx.geometry.Point2D;

public abstract class GameObject {
	
	private Point2D position;
	
	public abstract void Start();
	
	public abstract void Update();
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
}
