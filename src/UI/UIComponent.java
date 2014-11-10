package UI;

import javafx.stage.Stage;

import java.util.logging.*;
import java.util.ArrayList;

import task.Task;
import application.Controller;
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

/**
 * Main Class for the UI, That integrates all components into one and return 
 * a scene instance back to the Controller for display
 * 
 * @author Tan Young Sing
 */
public class UIComponent {
    
    private final String SUGGESTION_TEXT = "\u2022 Try adding a task with add [description]";
    private final String GUIDE_TEXT = "\u2022 Hello user! I am WaveWave.";
    
    private WaveLogger logger;
	private final int LISTVIEW_DISPLAY_HEIGHT = 550;
	private final String LISTVIEW_STYLESHEET = "taskDisplay_outer";
	private final String ROOTPANE_STYLESHEET = "rootPane";
	
	private final int APPLICATION_WIDTH = 650;
	private final int APPLICATION_HEIGHT = 700;
	
	private final String APP_DEFAULT_FONT = "Ariel";
	private final String APP_DEFAULT_STYLESHEET = "application.css";
	private final String CMDINPUT_PLACEHOLDER_STYLESHEET = "cmdBox_outer";
	
	private Scene scene;
	private BorderPane rootPane;
	private Text suggestionText, guideMsgText;
	private UICmdInputBox cmdInputBox;
	private UITaskListView floatingTaskListView, eventReminderTaskListView;
	
	private final String LISTVIEW_HEADING_REMINDER = "Events";
	private final String LISTVIEW_HEADING_TASK = "Tasks";
	
	private Text reminderTaskTitle,floatingTaskTitle;
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     * @param return the scene object back to the Controller
     */
	public Scene getScene() {
		return scene;
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     * @param return the panel that holds all the components
     */
	public BorderPane getRootPane() {
		return rootPane;
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     * @param return inputBox 
     */
	public UICmdInputBox getCmdInputBox() {
		return cmdInputBox;
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     * @param return the ListView on the right
     */
	public UITaskListView getFloatingTaskListView() {
		return floatingTaskListView;
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     * @param return the ListView on the left
     */
	public UITaskListView getEventReminderTaskListView() {
		return eventReminderTaskListView;
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public UIComponent() {
		initializeLoggerFileHandler();
		initializeComponents();
		setupScene();
		initializeStyleSheet();
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private void initializeStyleSheet() {
		scene.getStylesheets().add(getClass().getResource(APP_DEFAULT_STYLESHEET).toExternalForm());
		rootPane.getStyleClass().add(ROOTPANE_STYLESHEET);
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private void initializeLoggerFileHandler() {
        try {
        	logger = new WaveLogger("UIComponent");
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private void setupScene() {
		scene = new Scene(rootPane, APPLICATION_WIDTH, APPLICATION_HEIGHT);
		scene.setOnKeyPressed(new UISceneListener(cmdInputBox));
		logger.log(Level.INFO, "The Scene is created.");
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private void initializeComponents() {
		rootPane = new BorderPane();
		rootPane.setCenter(getMainComponentHolder());
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
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
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
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
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
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
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private VBox getUserInputComponentHolder() {
		VBox userInputComponentHolder = createVBox(8, new Insets(15, 15, 15, 15), 0, 120, CMDINPUT_PLACEHOLDER_STYLESHEET);
		suggestionText = createText(SUGGESTION_TEXT, 12, FontWeight.NORMAL, APP_DEFAULT_FONT, null);
		guideMsgText = createText(GUIDE_TEXT, 12, FontWeight.NORMAL, APP_DEFAULT_FONT, null);
		
		cmdInputBox = new UICmdInputBox(suggestionText, guideMsgText, this);
		userInputComponentHolder.getChildren().addAll(cmdInputBox.getCmdInputBox(), guideMsgText, suggestionText);
		return userInputComponentHolder;
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
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

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private VBox getFloatingTaskListViewHolder() {
		VBox innerBox = createVBox(10, new Insets(5, 10, 30, 10), 0, LISTVIEW_DISPLAY_HEIGHT, LISTVIEW_STYLESHEET); 
		floatingTaskTitle = createText(LISTVIEW_HEADING_TASK, 15, FontWeight.BOLD, APP_DEFAULT_FONT, null);

		floatingTaskListView = new UIFloatingTaskListView(cmdInputBox);
		innerBox.getChildren().addAll(floatingTaskTitle, floatingTaskListView.getListView());
		
		return innerBox;
	}

    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	private VBox getTimedAndDeadlineTaskHolder() {
		VBox innerBox = createVBox(10, new Insets(5, 10, 30, 10), 0, LISTVIEW_DISPLAY_HEIGHT, LISTVIEW_STYLESHEET); 
		reminderTaskTitle = createText(LISTVIEW_HEADING_REMINDER, 15, FontWeight.BOLD, APP_DEFAULT_FONT, null);
		
		eventReminderTaskListView = new UIEventTaskListView(cmdInputBox);
		innerBox.getChildren().addAll(reminderTaskTitle, eventReminderTaskListView.getListView());

		return innerBox;
	}
	
    //@author A0111824R
    /**
     * Update both the title and the view of the left panel
     * 
     * @param input the arraylist of tasks to be populated on the listview
     *        input the String to replace the current title of the heading
     */
	public void updateLeftPanel(ArrayList<Task> items, String title) {
		reminderTaskTitle.setText(title);
	    eventReminderTaskListView.populateTaskListWithData(items, true);
	    eventReminderTaskListView.clearSelection();    
		logger.log(Level.INFO, "Reminder & Event ListView is updated.");
	}
	
    //@author A0111824R
    /**
     * Update both the title and the view of the right panel
     * 
     * @param input the arraylist of tasks to be populated on the listview
     *        input the String to replace the current title of the heading
     */
	public void updateRightPanel(ArrayList<Task> items, String title) {
		floatingTaskTitle.setText(title);
		floatingTaskListView.populateTaskListWithData(items, false);
	    floatingTaskListView.clearSelection();
	    
	    logger.log(Level.INFO, "Task ListView is updated.");
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public void setRightPanelTitle(String title) {
		floatingTaskTitle.setText(title);
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public void setLeftPanelTitle(String title) {
		reminderTaskTitle.setText(title);
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public UITaskListView getRightView () {
		return floatingTaskListView;
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public UITaskListView getLeftView () {
		return eventReminderTaskListView;
	}
	
    //@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
	public void setSuggestionText(String text) {
		suggestionText.setText(UITooltips.TOOLTIP_BULLET + text);
	}
	
    //@author A0111824R
    /**
     * @param stage will be used to initialize the UI.
     * @author Tan Young Sing
     */
    public void showStage(Stage primaryStage) {
    	try {
    		primaryStage.setScene(this.getScene());
    		primaryStage.setResizable(false);
    		primaryStage.setTitle("WaveWave");
    		primaryStage.show();
    		Controller.getTasks();
        	//logger.log(Level.INFO, "UI has been successfully displayed.");
    	} catch (Exception ex) {
    		//logger.log(Level.WARNING, "UI failed to initialize", ex);
    	}

    }
}
