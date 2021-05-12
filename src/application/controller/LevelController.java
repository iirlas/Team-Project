package application.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import application.model.ConfigParser;
import application.model.Grid;
import application.model.ImageParser;
import application.model.LevelParser;
import application.model.ObjectParser;
import application.model.Penguin;
import application.model.Player;
import application.model.PlayerParser;
import application.model.Tile;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.Light.Point;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import utility.GameObject;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LevelController {

	@FXML
	private Canvas canvas;
	@FXML
	private AnchorPane pane;

	private GridPane gridPane = new GridPane();
	private Grid grid;
	private HashMap<Integer, GameObject> gameObjects;
//	protected Rectangle2D dimensions;
//	protected ArrayList<Tile> tiles;
	protected ArrayList<Player> players;
	protected TurnManager turnManager;

	@FXML
	public void initialize() throws FileNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		initObjects();
		initLevel();

		GraphicsContext context = canvas.getGraphicsContext2D();
		AnimationTimer loop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				canvas.requestFocus();

				context.setFill(Color.BLUE);
				context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

				if (grid == null)
					return;

				for (GameObject gameObject : gameObjects.values()) {
					gameObject.update();
				}

				grid.render(context);

				for (GameObject gameObject : gameObjects.values()) {
					if (gameObject instanceof Penguin) {
						((Penguin) gameObject).render(context, grid.getTileWidth(), grid.getTileHeight());
					}
				}

//				for (GameObject gameObject : objectParser.getGameObjects().values()) {
//					Penguin penguin = (Penguin) gameObject;
//					penguin.render(context, grid.getTileSize());
//				}
			}
		};
		loop.start();
	}

	private String getPath(String filename) {
		final String directory = "Assets";
		return Paths.get(directory, filename).toString();
	}

	private void initObjects() throws FileNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ImageParser imageParser = new ImageParser(getPath("animations.txt"));
		imageParser.parse();

		ObjectParser objectParser = new ObjectParser(getPath("objects.config"), imageParser.getSprites());
		objectParser.parse();
		gameObjects = objectParser.getGameObjects();
	}

	private void initLevel() throws FileNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ConfigParser configParser = new ConfigParser(getPath("Levels.config"));
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
					try {
						LevelParser levelParser = new LevelParser(file, gameObjects);
						levelParser.parse();
						grid = new Grid(levelParser.getDimensions(), levelParser.getTiles());
						gridPane.setDisable(true);
						for (Node node : gridPane.getChildren()) {
							node.setVisible(false);
						}

						PlayerParser playerParser = new PlayerParser(getPath("Player.config"), gameObjects,
								grid.getTileWidth(), grid.getTileHeight());
						playerParser.parse();
						turnManager = new TurnManager(playerParser.getPlayers());
						players = playerParser.getPlayers();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			gridPane.add(button, index % rowCount, index / rowCount);
		}
		pane.getChildren().add(gridPane);

		canvas.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
//				for (GameObject )
				if (grid == null)
					return;

				Point2D mousePosition = new Point2D(event.getX(), event.getY());
				ArrayList<Penguin> penguins = turnManager.getCurrentPlayer().getPenguins();
				for (Penguin penguin : penguins) {
					Point2D position = penguin.getPosition();
					Rectangle2D bounds = new Rectangle2D(position.getX(), position.getY(), grid.getTileWidth(),
							grid.getTileHeight());
					if (bounds.contains(mousePosition)) {
						turnManager.select(penguin);
					}
				}
			}

		});

		canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (turnManager.getSelected() == null)
					return;
				switch (event.getCode()) {
				case UP:
					turnManager.moveSelected(0, -grid.getTileHeight());
					break;
				case DOWN:
					turnManager.moveSelected(0, grid.getTileHeight());
					break;
				case LEFT:
					turnManager.moveSelected(-grid.getTileWidth(), 0);
					break;
				case RIGHT:
					turnManager.moveSelected(grid.getTileWidth(), 0);
					break;
				case BACK_SPACE:
					turnManager.undoSelected();
					break;
				default:
					break;
				}
			}
		});
	}
}
