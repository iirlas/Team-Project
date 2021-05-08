package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/Menu.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//
//				@Override
//				public void handle(KeyEvent event) {
//					controller.handle(primaryStage, event);
//				}
//			});
//			scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//				@Override
//				public void handle(MouseEvent event) {
//					// TODO Auto-generated method stub
//					controller.handle(primaryStage, event);
//				}
//			});
			
			primaryStage.setTitle("Atomic Penguin Warfare");
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(false);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
