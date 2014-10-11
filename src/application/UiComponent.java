package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
import javafx.event.EventHandler;

public class UiComponent {

	private final int DISPLAY_WIDTH = 300;
	private final int DISPLAY_HEIGHT = 500;
	private final String APP_DEFAULT_FONT = "Ariel";

	private Scene scene;
	private BorderPane rootPane;
	private TextArea summaryDisplay, pendingDisplay, completedTaskDisplay;
	private ListView<String> timedTaskList, taskList;

	private TextField cmdInputBox;

	//public String input_text; // added
	//public String output_text_floating = ""; // added
	//public String output_text_deadline = ""; // added

	//public int floating_task_counter = 0;
	//public ArrayList<String> floating_tasks = new ArrayList<String>();

	public Scene getScene() {
		return scene;
	}
	
	public UiComponent() {
		initializeComponents();
		setupScene();
		initializeStyleSheetToComponents();
	}

	private void initializeStyleSheetToComponents() {
		scene.getStylesheets().add(
				getClass().getResource("application.css").toExternalForm());
		rootPane.getStyleClass().add("rootPane");
	}

	private void setupScene() {
		scene = new Scene(rootPane, 650, 650);
	}

	private void initializeComponents() {
		rootPane = new BorderPane();
		rootPane.setCenter(getMainComponentHolder());
		// Side panel
		// rootPane.setRight(getRightHBox());
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
		VBox userInputComponentHolder = createVBox(8, new Insets(15, 15, 15, 15), 0, 120, "cmdBox_outer");
		Text suggestionText = createText("Did you mean this : add ?", 12, FontWeight.NORMAL, APP_DEFAULT_FONT, null);
		
		cmdInputBox = new TextField();
		cmdInputBox.setFocusTraversable(false);
		cmdInputBox.setPrefHeight(35);
		cmdInputBox.setPromptText("Ask WaveWave to do something ?");
		
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
		VBox innerBox = createVBox(10, new Insets(5, 10, 30, 10), 0, 0, "taskDisplay_outer"); 
		Text taskTitle = createText("Tasks", 15, FontWeight.BOLD, APP_DEFAULT_FONT, null);

		TaskListView floatingTaskListView = new TaskListView();
		ObservableList<String> items = FXCollections.observableArrayList(
				"Demo Task1\nDemo Task1\nDemo Task1\n", "Demo Task2",
				"Demo Task3", "Demo Task4", "Demo Task1\njadsjadkj",
				"Demo Task1\njadsjadkj", "Demo Task1\njadsjadkj");
		
		floatingTaskListView.populateTaskListWithData(items);
		innerBox.getChildren().addAll(taskTitle, floatingTaskListView.getListView());
		return innerBox;
	}

	private VBox getTimedAndDeadlineTaskHolder() {
		VBox innerBox = new VBox();
		innerBox.setSpacing(10);
		innerBox.setPadding(new Insets(5, 10, 30, 10));
		innerBox.getStyleClass().add("taskDisplay_outer");

		Text taskTitle = new Text("Reminder & Events");
		taskTitle.setTextAlignment(TextAlignment.JUSTIFY);
		taskTitle.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		taskTitle.setFill(Color.WHITE);

		timedTaskList = new ListView<String>();
		timedTaskList.setPrefHeight(DISPLAY_HEIGHT);
		timedTaskList.setPrefWidth(DISPLAY_WIDTH);
		innerBox.getChildren().addAll(taskTitle, timedTaskList);
		timedTaskList.getStyleClass().add("taskList_style");

		return innerBox;
	}


	public void focusCommandInputBox() {
		cmdInputBox.requestFocus();
	}

	public void updateFloatingTasks(ArrayList<FloatingTask> floatingTasks) {
		String output_text_floating = "";
		for (int i = 0; i < floatingTasks.size(); i++) {
			output_text_floating += "F" + Integer.toString(i + 1) + " "
					+ floatingTasks.get(i).getDescription() + "\n";
		}
	}

	public void updateDeadlineTasks(ArrayList<DeadlineTask> deadlineTasks) {
		String output_text_deadline = "";
		for (int i = 0; i < deadlineTasks.size(); i++) {
			output_text_deadline += "D" + Integer.toString(i + 1) + " "
					+ deadlineTasks.get(i).getDescription() + "     "
					+ deadlineTasks.get(i).getDeadline().toString() + "\n";
		}
	}

}
