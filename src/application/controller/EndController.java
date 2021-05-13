package application.controller;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import application.model.ImageParser;
import application.model.Sprite;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Parser;

public class EndController {

	@FXML
	private Canvas canvas;
	
	private Sprite endSprite; 

	public EndController(Sprite sprite) {
		// TODO Auto-generated constructor stub
		endSprite = sprite;
	}

	@FXML
	public void initialize() throws FileNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		GraphicsContext context = canvas.getGraphicsContext2D();
		
		context.setFill(Color.BLUE);
		context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		endSprite.render(context, 0, 0);
	}
}
