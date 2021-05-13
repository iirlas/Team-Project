package application.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import application.model.Sprite;
import application.model.TurnManager;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utility.GameObject;
import utility.Parser;
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
	private HashMap<String, Sprite> sprites;
	private ArrayList<Player> players;
	private TurnManager turnManager;

	@FXML
	public void initialize() throws FileNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		initObjects();
		initLevel();

		sprites.get("CURSOR").play();

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

				sprites.get("P1HEADER").render(context, 0, 0);
				sprites.get("P2HEADER").render(context, 759, 0);

				int i = 0;
				for (Player player : players) {
					player.render(context, grid.getTileWidth(), grid.getTileHeight());
					int j = 0;
					for (Penguin penguin : player.getPenguins()) {
						double x = i * 759;
						double y = j * (grid.getTileHeight() + sprites.get("HEALTH").getBounds().getHeight())
								+ sprites.get("P1HEADER").getBounds().getHeight();

						penguin.getSprite().render(context, x, y, grid.getTileWidth(), grid.getTileHeight());
						sprites.get("HEALTH").interpolate(1.0 - penguin.getHealth() / (double) penguin.getMaxHealth());
						sprites.get("HEALTH").render(context, x, y + grid.getTileHeight());
						j++;
					}
					i++;
				}

				Penguin penguin = turnManager.getSelected();
				if (penguin != null) {
					sprites.get("CURSOR").render(context, penguin.getPosition().getX(), penguin.getPosition().getY(),
							grid.getTileWidth(), grid.getTileHeight());
				}
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
		sprites = Parser.getInstance(ImageParser.class).getSprites();
		

		ObjectParser objectParser = Parser.getInstance(ObjectParser.class);
		objectParser.setFile("Assets", "objects.config");
		objectParser.setSprites(sprites);
		objectParser.parse();
		gameObjects = objectParser.getGameObjects();
	}

	private void initLevel() throws FileNotFoundException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ConfigParser configParser = Parser.getInstance(ConfigParser.class);
		configParser.setFile("Assets", "Levels.config");
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
						LevelParser levelParser = Parser.getInstance(LevelParser.class);
						levelParser.setFile(file);
						levelParser.setGameObjects(gameObjects);
						levelParser.parse();
						grid = new Grid(levelParser.getDimensions(), levelParser.getTiles());
						gridPane.setDisable(true);
						for (Node node : gridPane.getChildren()) {
							node.setVisible(false);
						}

						PlayerParser playerParser = Parser.getInstance(PlayerParser.class);
						playerParser.setFile("Assets", "Player.config");
						playerParser.setGameObjects(gameObjects);
						playerParser.setTileWidth(grid.getTileWidth());
						playerParser.setTileHeight(grid.getTileHeight());
						playerParser.parse();
						turnManager = new TurnManager(playerParser.getPlayers(), grid);
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
				if (grid == null)
					return;

				Point2D mousePosition = new Point2D(event.getX(), event.getY());
				ArrayList<Penguin> penguins = turnManager.getCurrentPlayer().getPenguins();
				for (Penguin penguin : penguins) {
					if (penguin.getHealth() > 0) {
						Point2D position = penguin.getPosition();
						Rectangle2D bounds = new Rectangle2D(position.getX(), position.getY(), grid.getTileWidth(),
								grid.getTileHeight());
						if (bounds.contains(mousePosition) && turnManager.canSelect(penguin)) {
							turnManager.select(penguin);
						}
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
				case ENTER:
					turnManager.completeMovement();
					if (turnManager.getNextPlayer().countAlive() == 0) {
						endScene();
					}else if(turnManager.isTurnComplete()) {
						turnManager.next();
					}
					break;
				default:
					break;
				}
			}
		});
	}

	private void endScene() {
		// TODO Auto-generated method stub
		Stage stage = (Stage) canvas.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/End.fxml"));
		try {
			loader.setControllerFactory(controller -> {
				Sprite sprite;
				if (turnManager.getPlayers().get(0).countAlive() > 0) {
					sprite = Parser.getInstance(ImageParser.class).getSprite("GREENVICTORY");
				}else {
					sprite = Parser.getInstance(ImageParser.class).getSprite("BLUEVICTORY");
				}
				return new EndController(sprite);						
			});
			stage.setScene(new Scene(loader.load()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
