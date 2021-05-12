package application.model;

import java.util.ArrayList;
import java.util.Collection;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Sprite extends Transition {

	private Image image;
	private int animationType;
	private int currentBoundsIndex;
	private ArrayList<BoundingBox> boundingBoxes = new ArrayList<BoundingBox>();

	public Sprite(Image image, int animationType, Collection<BoundingBox> boundingBoxes) {
		// TODO Auto-generated constructor stub
		this.setImage(image);
		this.setAnimationType(animationType);
		if (boundingBoxes != null && !boundingBoxes.isEmpty()) {
			this.boundingBoxes.addAll(boundingBoxes);
		}
		setCycleDuration((Duration.millis(1000)));
		setInterpolator(Interpolator.LINEAR);
		setCycleCount(Animation.INDEFINITE);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getAnimationType() {
		return animationType;
	}

	public void setAnimationType(int animationType) {
		this.animationType = animationType;
	}

	public void addBounds(BoundingBox bounds) {
		boundingBoxes.add(bounds);
	}

	public BoundingBox getBounds() {
		// TODO Auto-generated method stub
		return boundingBoxes.get(currentBoundsIndex);
	}

	public BoundingBox getBounds(int index) {
		return boundingBoxes.get(index);
	}

	public int size() {
		return boundingBoxes.size();
	}

	public void render(GraphicsContext context, double x, double y) {
		render(context, x, y, getBounds().getWidth(), getBounds().getHeight());
	}

	public void render(GraphicsContext context, double x, double y, double width, double height) {
		context.drawImage(image, getBounds().getMinX(), getBounds().getMinY(), getBounds().getWidth(),
				getBounds().getHeight(), x, y, width, height);
	}

	@Override
	public void interpolate(double frac) {
		// TODO animation type
		currentBoundsIndex = (int) Math.max(0,
				Math.min(boundingBoxes.size() - 1, Math.round(frac * (boundingBoxes.size() - 1))));
	}
}
