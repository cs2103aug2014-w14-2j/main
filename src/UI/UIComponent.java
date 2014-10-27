package UI;

import javafx.stage.Stage;

import java.util.logging.*;
import java.util.ArrayList;

import application.Controller;
import application.Task;
import application.WaveLogger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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

/**
 * Main Class for the UI, That integrates all components into one and return 
 * a scene instance back to the Controller for display
 * 
 * @author Tan Young Sing
 */
public class UIComponent {
    
    private final String SUGGESTION_TEXT = "Hello User! I am WaveWave.";
    private WaveLogger logger;
	private final int LISTVIEW_DISPLAY_HEIGHT = 550;
	private final String LISTVIEW_STYLESHEET = "taskDisplay_outer";
	private final String ROOTPANE_STYLESHEET = "rootPane";
	
	private final int APPLICATION_WIDTH = 650;
	private final int APPLICATION_HEIGHT = 650;
	
	private final String APP_DEFAULT_FONT = "Ariel";
	private final String APP_DEFAULT_STYLESHEET = "application.css";
	private final String CMDINPUT_PLACEHOLDER_STYLESHEET = "cmdBox_outer";
	
	private Scene scene;
	private BorderPane rootPane;
	private UICmdInputBox cmdInputBox;
	private UITaskListView floatingTaskListView, eventReminderTaskListView;
	
	private final String LISTVIEW_HEADING_REMINDER = "Reminder & Events";
	private final String LISTVIEW_HEADING_TASK = "Tasks";
	
	private Text reminderTaskTitle,floatingTaskTitle;
	
	public Scene getScene() {
		return scene;
	}
	
	public BorderPane getRootPane() {
		return rootPane;
	}

	public UICmdInputBox getCmdInputBox() {
		return cmdInputBox;
	}

	public UITaskListView getFloatingTaskListView() {
		return floatingTaskListView;
	}

	public UITaskListView getEventReminderTaskListView() {
		return eventReminderTaskListView;
	}

	public UIComponent() {
		initializeLoggerFileHandler();
		initializeComponents();
		setupScene();
		initializeStyleSheet();
	}

	private void initializeStyleSheet() {
		Font.loadFont(UIComponent.class.getResource("Raleway-Regular.TTF").toExternalForm(), 10);
		Font.loadFont(UIComponent.class.getResource("Bemio.otf").toExternalForm(), 10);
		scene.getStylesheets().add(getClass().getResource(APP_DEFAULT_STYLESHEET).toExternalForm());
		rootPane.getStyleClass().add(ROOTPANE_STYLESHEET);
	}
	
	private void initializeLoggerFileHandler() {
        try {
        	logger = new WaveLogger("UIComponent");
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
	}
	
	private void setupScene() {
		scene = new Scene(rootPane, APPLICATION_WIDTH, APPLICATION_HEIGHT);
		scene.setOnKeyPressed(new UISceneListener(cmdInputBox));
		logger.log(Level.INFO, "The Scene is created.");
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
		
		logger.log(Level.INFO, "A VBox is created.");
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
		
		logger.log(Level.INFO, "A HBox is created.");
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
		
		logger.log(Level.INFO, "A Text label is created.");
		return textLabel;
	}
	
	private VBox getUserInputComponentHolder() {
		VBox userInputComponentHolder = createVBox(8, new Insets(15, 15, 15, 15), 0, 120, CMDINPUT_PLACEHOLDER_STYLESHEET);
		Text suggestionText = createText(SUGGESTION_TEXT, 12, FontWeight.NORMAL, APP_DEFAULT_FONT, null);
		
		cmdInputBox = new UICmdInputBox(suggestionText);
		userInputComponentHolder.getChildren().addAll(cmdInputBox.getCmdInputBox(), suggestionText);
		return userInputComponentHolder;
	}

	private VBox getMainComponentHolder() {
		HBox taskListViewComponentHolder = createHBox(10, new Insets(10, 0, 10, 0), 0, 0, "");
		VBox mainComponentHolder = createVBox(5, new Insets(15, 15, 0, 15), 0, 0, "");

		VBox userInputComponentHolder = getUserInputComponentHolder();
		VBox timedAndDeadlineTaskHolder = getTimedAndDeadlineTaskHolder();
		VBox floatingTaskListViewHolder = getFloatingTaskListViewHolder();
		
		taskListViewComponentHolder.getChildren().addAll(timedAndDeadlineTaskHolder, floatingTaskListViewHolder);
		mainComponentHolder.getChildren().addAll(userInputComponentHolder, taskListViewComponentHolder);
		return mainComponentHolder;
	}

	private VBox getFloatingTaskListViewHolder() {
		VBox innerBox = createVBox(10, new Insets(5, 10, 30, 10), 0, LISTVIEW_DISPLAY_HEIGHT, LISTVIEW_STYLESHEET); 
		floatingTaskTitle = createText(LISTVIEW_HEADING_TASK, 15, FontWeight.BOLD, APP_DEFAULT_FONT, null);

		floatingTaskListView = new UITaskListView(cmdInputBox, "Task");
		innerBox.getChildren().addAll(floatingTaskTitle, floatingTaskListView.getListView());
		
		return innerBox;
	}

	private VBox getTimedAndDeadlineTaskHolder() {
		VBox innerBox = createVBox(10, new Insets(5, 10, 30, 10), 0, LISTVIEW_DISPLAY_HEIGHT, LISTVIEW_STYLESHEET); 
		reminderTaskTitle = createText(LISTVIEW_HEADING_REMINDER, 15, FontWeight.BOLD, APP_DEFAULT_FONT, null);
		
		eventReminderTaskListView = new UITaskListView(cmdInputBox, "Event");
		innerBox.getChildren().addAll(reminderTaskTitle, eventReminderTaskListView.getListView());

		return innerBox;
	}
	
	public void setFloatingTaskHeading(String title) {
		floatingTaskTitle.setText(title);
	}
	
	public void setReminderTaskHeading(String title) {
		reminderTaskTitle.setText(title);
	}

	public void updateTaskList(ArrayList<Task> items) {
	    ObservableList<Task> taskList = FXCollections.observableArrayList();
	    taskList.setAll(items);
	    floatingTaskListView.populateTaskListWithData(taskList);
	    floatingTaskListView.clearSelection();
	    
	    logger.log(Level.INFO, "Task ListView is updated.");
	}
	
	public void updateReminderList(ArrayList<Task> items) {
	    ObservableList<Task> taskList = FXCollections.observableArrayList();
	    taskList.setAll(items);
	    eventReminderTaskListView.populateTaskListWithData(taskList);
	    eventReminderTaskListView.clearSelection();
	    
		logger.log(Level.INFO, "Reminder & Event ListView is updated.");
	}
	
    public void showStage(Stage primaryStage) {
    	try {
    		primaryStage.setScene(this.getScene());
    		primaryStage.setResizable(false);
    		primaryStage.setTitle("WaveWave[0.2]");
    		primaryStage.show();
    		Controller.getTasks();
        	logger.log(Level.INFO, "UI has been successfully displayed.");
    	} catch (Exception ex) {
    		logger.log(Level.WARNING, "UI failed to initialize", ex);
    	}

    }
}
