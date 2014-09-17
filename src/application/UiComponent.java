package application;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class UiComponent {
    
	private Scene scene;
	private BorderPane rootPane;
	private GridPane summaryPane, userPane;
	private TextArea timeTaskedDisplay, floatingTaskedDisplay, summaryDisplay, pendingDisplay;
	private TextField cmdInput, suggestionDisplay;
	
	public UiComponent() {
		inIt();
	}
	
	public void inIt() {
		rootPane = new BorderPane();
		summaryPane = new GridPane();
		userPane = new GridPane();
		
		timeTaskedDisplay = new TextArea();
		timeTaskedDisplay.setPrefRowCount(10);
	    timeTaskedDisplay.setPrefColumnCount(100);
	    timeTaskedDisplay.setWrapText(true);
	    timeTaskedDisplay.setPrefWidth(300);
	    timeTaskedDisplay.setEditable(false);
	    timeTaskedDisplay.setFocusTraversable(false);
	    
		floatingTaskedDisplay = new TextArea();
		floatingTaskedDisplay.setPrefRowCount(10);
	    floatingTaskedDisplay.setPrefColumnCount(100);
	    floatingTaskedDisplay.setWrapText(true);
	    floatingTaskedDisplay.setPrefWidth(300);
	    floatingTaskedDisplay.setEditable(false);
	    floatingTaskedDisplay.setFocusTraversable(false);
	    
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
		
		cmdInput = new TextField();
		cmdInput.setPrefColumnCount(900);
		cmdInput.setFocusTraversable(false);
		
		suggestionDisplay = new TextField();
		suggestionDisplay.setPrefColumnCount(900);
		suggestionDisplay.setEditable(false);
		suggestionDisplay.setFocusTraversable(false);
	
		userPane.addColumn(0,suggestionDisplay, cmdInput);
	    summaryPane.addColumn(0,summaryDisplay, pendingDisplay);
	    
	    rootPane.setLeft(timeTaskedDisplay);
	    rootPane.setCenter(floatingTaskedDisplay);
		rootPane.setRight(summaryPane);
		rootPane.setBottom(userPane);
		
		scene = new Scene(rootPane,900,500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void focusCommandInputBox() {
		cmdInput.requestFocus();
	}
	
	
}
