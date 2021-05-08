package application.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import application.model.ImageParser;
import application.model.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuController {
	@FXML
	Label startLabel;

	@FXML
	Label quitLabel;

	@FXML
	Canvas canvas;

	@FXML
	public void initialize() throws FileNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		GraphicsContext context = canvas.getGraphicsContext2D();
		
		ImageParser imageParser = new ImageParser(Paths.get("Assets", "animations.txt").toString());
		imageParser.parse();
		Sprite splash = imageParser.getSprite("SPLASH");
		splash.play();
		AnimationTimer loop = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				context.setFill(Color.BLUE);
				context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				splash.render(context, 56, 50, 713, 500);
				
			}
		};
		loop.start();
		
		startLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// change scene
				Stage stage = (Stage) canvas.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Main.fxml"));
				try {
					stage.setScene(new Scene(loader.load()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		quitLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				Platform.exit();
				System.exit(0);
			}
		});
	}
}
