package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UiComponent {
    
    private final String SUGGESTION_TEXT = "Hello User! I am WaveWave.";
    
	private final int LISTVIEW_DISPLAY_HEIGHT = 550;
	private final String LISTVIEW_STYLESHEET = "taskDisplay_outer";
	
	private final int APPLICATION_WIDTH = 650;
	private final int APPLICATION_HEIGHT = 650;
	
	private final String APP_DEFAULT_FONT = "Ariel";
	private final String APP_DEFAULT_STYLESHEET = "application.css";
	private final String CMDINPUT_PLACEHOLDER_STYLESHEET = "cmdBox_outer";
	
	private Scene scene;
	private BorderPane rootPane;
	private TextField cmdInputBox;
	private TaskListView floatingTaskListView, eventAndRemainderTaskListView;

	public Scene getScene() {
		return scene;
	}
	
	public UiComponent() {
		initializeComponents();
		setupScene();
		initializeStyleSheetToComponents();
	}

	private void initializeStyleSheetToComponents() {
		scene.getStylesheets().add(getClass().getResource(APP_DEFAULT_STYLESHEET).toExternalForm());
		rootPane.getStyleClass().add("rootPane");
	}

	private void setupScene() {
		scene = new Scene(rootPane, APPLICATION_WIDTH, APPLICATION_HEIGHT);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override 
            public void handle(KeyEvent ke) { 
                String currentText = cmdInputBox.getText();
                if(!cmdInputBox.isFocused() && ke.getText().matches("^[a-zA-Z0-9_]*$")) {
                    focusCommandInputBox();
                    cmdInputBox.setText(currentText);
                    cmdInputBox.positionCaret(currentText.length());
                }
            } 
        });
	}

	private void initializeComponents() {
		rootPane = new BorderPane();
		rootPane.setCenter(getMainComponentHolder());
	}
	
	private VBox createVBox(int spacing, Insets padding, int prefWidth, int prefHeight, String style) {
		VBox vBox = new VBox(spacing);
		vBox.setPadding(padding);
		vBox.getStyleClass().add(style);
		vBox.setPrefHeight(prefHeight);
		
		if(prefWidth != 0) {
			vBox.setPrefWidth(prefWidth);
		} else {
			vBox.setFillWidth(true);
		}
		
		return vBox;
	}
	
	private HBox createHBox(int spacing, Insets padding, int prefWidth, int prefHeight, String style) {
		HBox hBox = new HBox(spacing);
		hBox.setPadding(padding);
		hBox.getStyleClass().add(style);
		hBox.setPrefWidth(prefWidth);
		
		if(prefHeight != 0) {
			hBox.setPrefHeight(prefHeight);
		} else {
			hBox.setFillHeight(true);
		}
		
		return hBox;
	}
	
	private Text createText(String text, int size, FontWeight weight, String fontFamily, Color color) {
		Text textLabel = new Text(text);
		textLabel.setTextAlignment(TextAlignment.JUSTIFY);
		textLabel.setFont(Font.font(fontFamily, weight, size));
		
		if(color == null) {
			color = Color.WHITE;
		}
		
		textLabel.setFill(color);
		return textLabel;
	}
	
	private VBox getUserInputComponentHolder() {
		VBox userInputComponentHolder = createVBox(8, new Insets(15, 15, 15, 15), 0, 120, CMDINPUT_PLACEHOLDER_STYLESHEET);
		Text suggestionText = createText(SUGGESTION_TEXT, 12, FontWeight.NORMAL, APP_DEFAULT_FONT, null);
		cmdInputBox = new CmdInputBox(suggestionText).getCmdInputBox();
		userInputComponentHolder.getChildren().addAll(cmdInputBox, suggestionText);
		return userInputComponentHolder;
	}

	private VBox getMainComponentHolder() {
		HBox taskListViewComponentHolder = createHBox(10, new Insets(10, 0, 10, 0), 0, 0, "");
		VBox mainComponentHolder = createVBox(5, new Insets(15, 15, 0, 15), 0, 0, "");

		VBox timedAndDeadlineTaskHolder = getTimedAndDeadlineTaskHolder();
		VBox floatingTaskListViewHolder = getFloatingTaskListViewHolder();
		VBox userInputComponentHolder = getUserInputComponentHolder();

		taskListViewComponentHolder.getChildren().addAll(timedAndDeadlineTaskHolder, floatingTaskListViewHolder);
		mainComponentHolder.getChildren().addAll(userInputComponentHolder, taskListViewComponentHolder);
		return mainComponentHolder;
	}

	private VBox getFloatingTaskListViewHolder() {
		VBox innerBox = createVBox(10, new Insets(5, 10, 30, 10), 0, LISTVIEW_DISPLAY_HEIGHT, LISTVIEW_STYLESHEET); 
		Text taskTitle = createText("Tasks", 15, FontWeight.BOLD, APP_DEFAULT_FONT, null);

		floatingTaskListView = new TaskListView();
		ObservableList<Task> items = FXCollections.observableArrayList();
		floatingTaskListView.populateTaskListWithData(items);
		innerBox.getChildren().addAll(taskTitle, floatingTaskListView.getListView());
		
		return innerBox;
	}

	private VBox getTimedAndDeadlineTaskHolder() {
		VBox innerBox = createVBox(10, new Insets(5, 10, 30, 10), 0, LISTVIEW_DISPLAY_HEIGHT, LISTVIEW_STYLESHEET); 
		Text taskTitle = createText("Reminder & Events", 15, FontWeight.BOLD, APP_DEFAULT_FONT, null);
		
		eventAndRemainderTaskListView = new TaskListView();
        ObservableList<Task> items = FXCollections.observableArrayList();
        eventAndRemainderTaskListView.populateTaskListWithData(items);
		innerBox.getChildren().addAll(taskTitle, eventAndRemainderTaskListView.getListView());

		return innerBox;
	}
	
    private void focusCommandInputBox() {
        cmdInputBox.requestFocus();
    }
	
	public void updateTaskList(ArrayList<Task> items) {
	    ObservableList<Task> taskList = FXCollections.observableArrayList();
	    taskList.setAll(items);
	    floatingTaskListView.populateTaskListWithData(taskList);
	}
	
	public void updateReminderList(ArrayList<Task> items) {
	    ObservableList<Task> taskList = FXCollections.observableArrayList();
	    taskList.setAll(items);
	    eventAndRemainderTaskListView.populateTaskListWithData(taskList);
	}
}
