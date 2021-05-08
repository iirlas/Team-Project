package application.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.ArrayList;

import application.model.ConfigParser;
import application.model.Grid;
import application.model.ImageParser;
import application.model.LevelParser;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LevelController {

	@FXML
	Canvas canvas;
	@FXML
	AnchorPane pane;
	
	GridPane gridPane = new GridPane();
	
	Grid grid;

	@FXML
	public void initialize() throws FileNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String directory = "Assets";
		ImageParser imageParser = new ImageParser(Paths.get(directory, "animations.txt").toString());
		imageParser.parse();

		ConfigParser configParser = new ConfigParser(Paths.get(directory, "Levels.config").toString());
		configParser.parse();
		
		ArrayList<File> files = configParser.getFiles();
		gridPane.setMaxSize(canvas.getWidth(), canvas.getHeight());
		for (int index = 0, size = 100, rowCount = (int) (canvas.getWidth() / size); index < files.size(); index++) {
			Button button = new Button("Level " + (index + 1));
			button.setPrefWidth(size);
			button.setPrefHeight(size);
			
			File file = files.get(index); 
			button.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					LevelParser levelParser = new LevelParser(file, imageParser);
					try {
						levelParser.parse();
						grid = new Grid(levelParser.getDimensions(), levelParser.getTiles());
						gridPane.setDisable(true);
						for (Node node : gridPane.getChildren()) {
							node.setVisible(false);							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//
				}
			});
			gridPane.add(button, index % rowCount, index / rowCount);
		}
		pane.getChildren().add(gridPane);

		GraphicsContext context = canvas.getGraphicsContext2D();
		AnimationTimer loop = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				context.setFill(Color.BLUE);
				context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				if (grid != null) {
					grid.render(context);
				}
			}
		};
		loop.start();
	}
}
