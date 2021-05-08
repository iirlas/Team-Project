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

	public void image(String argument, String line) {
		if (line == null) {
			File file = new File(getFile().getParent(), argument);
			// Scanner scanner = new Scanner(file);
			image = new Image(file.toURI().toString(), true);
			// scanner.close();
		} else {
			Scanner lineScanner = new Scanner(line);
			String name = lineScanner.next();
			int type = lineScanner.nextInt();
			Sprite sprite = new Sprite(image, type, null);
			while (lineScanner.hasNext()) {
				BoundingBox bounds = new BoundingBox(lineScanner.nextDouble(), lineScanner.nextDouble(),
						lineScanner.nextDouble(), lineScanner.nextDouble());
				sprite.addBounds(bounds);
			}
			getSprites().put(name, sprite);
			lineScanner.close();
		}
	}
}
