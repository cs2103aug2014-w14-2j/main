package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Main extends Application {
	@SuppressWarnings("static-access")
    @Override
	public void start(Stage primaryStage) {
		try {
			displayUI(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void displayUI(Stage primaryStage) {
		primaryStage.setScene(new UiComponent().getScene());
		primaryStage.setTitle("WAVE WAVE!");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
