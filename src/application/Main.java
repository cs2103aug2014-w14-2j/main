package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			showStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void showStage(Stage primaryStage) {
		UiComponent uiComponent = new UiComponent();
		primaryStage.setScene(uiComponent.getScene());
		primaryStage.setResizable(false);
		primaryStage.setTitle("WaveWave[0.1]");
		primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
