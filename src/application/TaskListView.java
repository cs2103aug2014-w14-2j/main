package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.util.Callback;

public class TaskListView {

	private ListView<String> taskList;
	
	private final int DISPLAY_WIDTH = 300;
	private final int DISPLAY_HEIGHT = 500;
	private final String TASKLIST_DEFAULT_STYLE = "taskList_style";
	
	public TaskListView() {
		taskList = new ListView<String>();
		setTaskListProperty();
	}
	
	private void setTaskListProperty() {
		taskList.setPrefHeight(DISPLAY_HEIGHT);
		taskList.setPrefWidth(DISPLAY_WIDTH);
		taskList.getStyleClass().add(TASKLIST_DEFAULT_STYLE);
		
		taskList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				return new TaskListCell();
			}
		});
	}
	
	public void populateTaskListWithData(ObservableList<String> items) {
		taskList.setItems(items);
	}
	
	public ListView<String> getListView() {
		return taskList;
	}
	
	static class TaskListCell extends ListCell<String> {
		
		private Rectangle createRectangle(int width, int height, int arcWidth, int arcHeight, Color c) {
			Rectangle rect = new Rectangle(width, height);
			rect.setArcHeight(arcHeight);
			rect.setArcWidth(arcWidth);
			rect.setFill(c);
			return rect;
		}
		
		private Text createText(String text, int textWidth, int size) {
			Text textLabel = new Text(text);
			textLabel.setWrappingWidth(150);
			textLabel.setBoundsType(TextBoundsType.VISUAL); 
			textLabel.setTextAlignment(TextAlignment.LEFT);
			textLabel.setFont(Font.font("Ariel", FontWeight.NORMAL, 12));
			return textLabel;
		}
		
		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			Rectangle contentPlaceHolder = createRectangle(260, 70, 10, 10, Color.WHITE);
			Rectangle priorityIndicator = createRectangle(50, 50, 0, 0, Color.web("rgba(238,188,11,1)"));
			
			if (item != null) {		
				Text text = createText(item, 150, 12); 
	
				HBox taskInnerContentHolder = new HBox(15);
				taskInnerContentHolder.setPadding(new Insets(10, 0, 0, 15));
				taskInnerContentHolder.getChildren().addAll(priorityIndicator, text);
				
				StackPane stack = new StackPane();
				stack.setPrefHeight(70);
				stack.setPrefWidth(260);
				stack.getChildren().addAll(contentPlaceHolder, taskInnerContentHolder);
				setGraphic(stack);
			}
		}
	}
}


