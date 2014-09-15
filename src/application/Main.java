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
			BorderPane root = new BorderPane();
			GridPane rightPane = new GridPane();
			GridPane bottomPane = new GridPane();
			
			TextArea txtField1 = new TextArea();
			txtField1.setPrefRowCount(10);
		    txtField1.setPrefColumnCount(100);
		    txtField1.setWrapText(true);
		    txtField1.setPrefWidth(300);
		    txtField1.setEditable(false);
		    txtField1.setFocusTraversable(false);
		    
			TextArea txtField2 = new TextArea();
			txtField2.setPrefRowCount(10);
		    txtField2.setPrefColumnCount(100);
		    txtField2.setWrapText(true);
		    txtField2.setPrefWidth(300);
		    txtField2.setEditable(false);
		    txtField2.setFocusTraversable(false);
		    
			TextArea txtField3 = new TextArea();
			txtField3.setPrefRowCount(10);
		    txtField3.setPrefColumnCount(100);
		    txtField3.setWrapText(true);
		    txtField3.setPrefWidth(300);
		    txtField3.setPrefHeight(250);
		    txtField3.setEditable(false);
		    txtField3.setFocusTraversable(false);
		    
			TextArea txtField4 = new TextArea();
			txtField4.setPrefRowCount(10);
		    txtField4.setPrefColumnCount(100);
		    txtField4.setWrapText(true);
		    txtField4.setPrefWidth(300);
		    txtField4.setPrefHeight(250);
			txtField4.setText("SURPRISE");
			txtField4.setEditable(false);
			txtField4.setFocusTraversable(false);
			
			TextField cmdInput = new TextField();
			cmdInput.setPrefColumnCount(900);
			
			TextField suggestion = new TextField();
			suggestion.setPrefColumnCount(900);
			suggestion.setEditable(false);
			suggestion.setFocusTraversable(false);
		
			bottomPane.setRowIndex(suggestion, 1);
			bottomPane.setRowIndex(cmdInput, 2);
			bottomPane.getChildren().addAll(suggestion, cmdInput);
			
		    rightPane.setRowIndex(txtField3,1);
		    rightPane.setRowIndex(txtField4,2);
		    rightPane.getChildren().addAll(txtField3, txtField4);
		    
		    root.setLeft(txtField1);
		    root.setCenter(txtField2);
			root.setRight(rightPane);
			root.setBottom(bottomPane);
			Scene scene = new Scene(root,900,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("WAVE WAVE!");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
