package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UiComponent {
    
	private Scene scene;
	private BorderPane rootPane;
	private TextArea timeTaskedDisplay, floatingTaskedDisplay, summaryDisplay, pendingDisplay;
	private TextField cmdInput;
	
	public UiComponent() {
		initializeComponents();
		setupScene();
		initializeStyleSheetToComponents();
	}
	
	private void initializeStyleSheetToComponents() {
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		rootPane.getStyleClass().add("rootPane");
	}
	
	private void setupScene() {
		scene = new Scene(rootPane,900,500);
	}
	
	private void initializeComponents() {
		rootPane = new BorderPane();
	    rootPane.setLeft(getLeftHBox());
	    rootPane.setCenter(getCenterHBox());
		rootPane.setRight(getRightHBox());
		rootPane.setBottom(getBottomHBox());
	}
	
	private HBox getBottomHBox() {
		HBox hbox = new HBox();
		
		VBox vbox = new VBox(0);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15,15,15,15));
				
		cmdInput = new TextField();
		cmdInput.setPrefColumnCount(900);
		cmdInput.setFocusTraversable(false);
		
		Text suggestionText = new Text("Input Command : ");
		
		vbox.getChildren().addAll(suggestionText,cmdInput);
		hbox.getChildren().addAll(vbox);
		
		return hbox;
	}
	
	private HBox getRightHBox() {
		HBox hbox = new HBox();
		
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15,15,15,0));
		
		summaryDisplay = new TextArea();
		summaryDisplay.setPrefRowCount(10);
	    summaryDisplay.setPrefColumnCount(100);
	    summaryDisplay.setWrapText(true);
	    summaryDisplay.setPrefWidth(300);
	    summaryDisplay.setPrefHeight(250);
	    summaryDisplay.setEditable(false);
	    summaryDisplay.setFocusTraversable(false);
		
		pendingDisplay = new TextArea();
		pendingDisplay.setPrefRowCount(10);
	    pendingDisplay.setPrefColumnCount(100);
	    pendingDisplay.setWrapText(true);
	    pendingDisplay.setPrefWidth(300);
	    pendingDisplay.setPrefHeight(250);
		pendingDisplay.setText("SURPRISE");
		pendingDisplay.setEditable(false);
		pendingDisplay.setFocusTraversable(false);
		
		vbox.getChildren().addAll(summaryDisplay,pendingDisplay);
		hbox.getChildren().addAll(vbox);
		
		return hbox;
	}
	
	private HBox getCenterHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 15, 15, 0));
		hbox.setMaxHeight(500);
		hbox.setMaxWidth(300);
		
		floatingTaskedDisplay = new TextArea();
		floatingTaskedDisplay.setPrefRowCount(10);
	    floatingTaskedDisplay.setPrefColumnCount(100);
	    floatingTaskedDisplay.setWrapText(true);
	    floatingTaskedDisplay.setPrefWidth(300);
	    floatingTaskedDisplay.setEditable(false);
	    floatingTaskedDisplay.setFocusTraversable(false);
	    
	    hbox.getChildren().addAll(floatingTaskedDisplay);
		return hbox;
	}
	
	private HBox getLeftHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 0, 15, 15));
		hbox.setMaxHeight(500);
		hbox.setMaxWidth(300);
		
		timeTaskedDisplay = new TextArea();
		timeTaskedDisplay.setPrefRowCount(10);
	    timeTaskedDisplay.setPrefColumnCount(100);
	    timeTaskedDisplay.setWrapText(true);
	    timeTaskedDisplay.setPrefWidth(300);
	    timeTaskedDisplay.setEditable(false);
	    timeTaskedDisplay.setFocusTraversable(false);
	    
	    hbox.getChildren().addAll(timeTaskedDisplay);
		return hbox;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void focusCommandInputBox() {
		cmdInput.requestFocus();
	}
	
	
}
