package application.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Scanner;

import javafx.animation.Animation;
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import utility.Parser;

public class ImageParser extends Parser {

	private Image image;
	private HashMap<String, Sprite> sprites; 

	public ImageParser(String filepath) {
		super(filepath);
		sprites = new HashMap<>();
	}

	public HashMap<String, Sprite> getSprites() {
		return sprites;
	}
	
	public Sprite getSprite(String name) {
		// TODO Auto-generated method stub
		return sprites.get(name);
	}

	public void image(String[] arguments, String line) {
		if (line == null) {
			File file = new File(getFile().getParent(), arguments[1]);
			//Scanner scanner = new Scanner(file);
			image = new Image(file.toURI().toString(), true);
			//scanner.close();
		} else {
			Scanner scanner = new Scanner(line);
			String name = scanner.next();
			int type = scanner.nextInt();
			Sprite sprite = new Sprite(image, type, null);
			while (scanner.hasNext()) {
				BoundingBox bounds = new BoundingBox(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
				sprite.addBounds(bounds);
			}
			getSprites().put(name, sprite);
			scanner.close();
		}
	}
}
