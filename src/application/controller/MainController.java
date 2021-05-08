package application.controller;

import java.awt.Panel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

import application.model.ConfigParser;
import application.model.ImageParser;
import application.model.Sprite;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import utility.Parser;

public class MainController {
	@FXML
	private Label title;
	@FXML
	private Canvas canvas;

	@FXML
	public void initialize() throws FileNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		GraphicsContext context = canvas.getGraphicsContext2D();

		String directory = "Assets";
		ImageParser imageParser = new ImageParser(Paths.get(directory, "animations.txt").toString());
		imageParser.parse();
		Sprite sprite = imageParser.getSprite("P1-BAZOOKA-BACK");
		sprite.play();
		AnimationTimer mainLoop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// Update

				// render
				context.setFill(Color.BLUE);
				context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				//render 
				sprite.render(context, 0, 0);
//				BoundingBox bounds= sprite.getBounds(0);
//				
//				context.drawImage(sprite.getImage(), bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
			}
		};
		mainLoop.start();
	}

	public void handle(Stage stage, MouseEvent event) {
		Point2D mousePosition = canvas.sceneToLocal(event.getX(), event.getY());
		System.out.println(mousePosition);
	}

	public void handle(Stage stage, KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println(event.getCode());
		if (event.getCode() == KeyCode.ENTER) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Menu.fxml"));
			try {
				stage.setScene(new Scene(loader.load()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//stage.show();
		}
	}
}
