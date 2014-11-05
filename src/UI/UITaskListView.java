package UI;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import application.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

//@author A0111824R
/**
 * Abstracted ListView Class
 * 
 * @author Tan Young Sing
 */
public abstract class UITaskListView {

    protected ListView<UITaskListItem> taskList;
    protected UICmdInputBox cmdInputBox;
    private final String TASKLIST_DEFAULT_STYLE = "taskList_style";

    private final int DISPLAY_WIDTH = 300;
    private final int DISPLAY_HEIGHT = 500;

	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public UITaskListView(UICmdInputBox cmdInputBox) {
        taskList = new ListView<UITaskListItem>();
        this.cmdInputBox = cmdInputBox;
        setTaskListProperty();
    }

	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    private void setTaskListProperty() {
        taskList.setPrefHeight(DISPLAY_HEIGHT);
        taskList.setPrefWidth(DISPLAY_WIDTH);
        taskList.getStyleClass().add(TASKLIST_DEFAULT_STYLE);

        taskList.setCellFactory(new Callback<ListView<UITaskListItem>, ListCell<UITaskListItem>>() {
            @Override
            public ListCell<UITaskListItem> call(ListView<UITaskListItem> list) {
                return new TaskListCell();
            }
        });
    }

	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public boolean isFocused() {
    	return taskList.isFocused();
    }
    
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public void clearSelection() {
    	taskList.getSelectionModel().clearSelection();
    }
    
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public int getSelectedItemIndex() {
    	return taskList.getSelectionModel().getSelectedIndex();
    }
    
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public ObservableList<UITaskListItem> getSelectedItem() {
    	return taskList.getSelectionModel().getSelectedItems();
    }
   
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public void populateTaskListWithData(ArrayList<Task> items, boolean isLeftPane) {
    	ObservableList<UITaskListItem> convertedList = FXCollections.observableArrayList();
    	
    	if(items.size() == 0) {
    		convertedList.setAll(generateEmptyList(items, isLeftPane));
    	} else {
    		convertedList.setAll(generateListItems(items));
    	} 
    	taskList.setItems(convertedList);
    }

	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    public ListView<UITaskListItem> getListView() {
        return taskList;
    }
    
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    protected abstract ArrayList<UITaskListItem> generateListItems(ArrayList<Task> items);
    
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    protected abstract ArrayList<UITaskListItem> generateEmptyList(ArrayList<Task> items, boolean isLeftPane);
    
    
	//@author A0111824R
    /**
     *
     * @author Tan Young Sing
     */
    class TaskListCell extends ListCell<UITaskListItem> { 
        static private final int TASK_CONTAINER_WIDTH = 260;
        static private final int TASK_CONTAINER_HEIGHT = 70;
        static private final int TASK_CONTAINER_SPACING = 15;

        private final String CONTAINER_HEIGHT = "-fx-cell-size: %s;";

        private String COLOR_DEFAULT_PRIORITY = "rgba(37, 232, 154, 1)";
        private String COLOR_HIGH_PRIORITY = "rgba(249, 104, 114, 1)";
        private String COLOR_COMPLETED = "rgba(188, 187, 185, 1)";
        
        private Rectangle contentPlaceHolder;
        private Text indexLabel;
        
    	//@author A0111824R
        /**
         *
         * @author Tan Young Sing
         */
        private Rectangle createRectangle(int width, int height, int arcWidth, int arcHeight, Color c) {
        	Rectangle rect = new Rectangle(width, height);
            rect.setArcHeight(arcHeight);
            rect.setArcWidth(arcWidth);
            rect.setFill(c);
            return rect;
        }

    	//@author A0111824R
        /**
         *
         * @author Tan Young Sing
         */
        private Text createText(String text, int textWidth, int size, String fontFamily, FontWeight weight, Color color) {
            Text textLabel = new Text(text);
            textLabel.setWrappingWidth(textWidth);
            textLabel.setFont(Font.font(fontFamily, weight, size));
            textLabel.setFill(color);
            return textLabel;
        }
        
    	//@author A0111824R
        /**
         *
         * @author Tan Young Sing
         */
        private StackPane getPriorityIndicator(int priority, String displayID, int height, Task item) {
        	String indicator_color = COLOR_DEFAULT_PRIORITY;
        	
        	if(item.isCompleted()) {
        		indicator_color = COLOR_COMPLETED;
        	} else {
        		if(priority == 0) {
        			 indicator_color = COLOR_DEFAULT_PRIORITY;
        		} else if (priority != 0) {
        			indicator_color = COLOR_HIGH_PRIORITY;
        		}
        	}
        	
        	Rectangle priorityIndicator = createRectangle(40, height-10, 0, 0, Color.web(indicator_color));
        	indexLabel = createText(displayID, 0, 20, "Bemio", FontWeight.BOLD, Color.WHITE);

        	StackPane stack = new StackPane();
        	stack.setPadding(new Insets(0, 0, 0, 0));
			stack.setMaxHeight(height-10);
			stack.setMaxWidth(40);
        	
			StackPane.setAlignment(priorityIndicator, Pos.TOP_LEFT);
        	StackPane.setAlignment(indexLabel, Pos.CENTER);
			stack.getChildren().addAll(priorityIndicator, indexLabel);	
     
        	return stack;
        }
        
    	//@author A0111824R
        /**
         *
         * @author Tan Young Sing
         */
        private String getDateString(DateTime currentDate) {
        	String output = "";
        	DateTime systemTime = new DateTime();
        	
        	DateTime taskDate = new DateTime(currentDate.getYear(), currentDate.getMonthOfYear(), currentDate.getDayOfMonth(), 0, 0);
        	DateTime today = new DateTime(systemTime.getYear(), systemTime.getMonthOfYear(), systemTime.getDayOfMonth(), 0, 0);
        	DateTime tomorrow = today.plus(Period.days(1));
        	
        	DateTime end = today.plus(Period.days(7));
        	
        	Interval interval = new Interval(today, end);
        	
        	if(interval.contains(currentDate)) {
        		if(taskDate.equals(today)) {
        			output = "Today - " + taskDate.toString("dd MMM yyyy");
        		} else if (taskDate.equals(tomorrow)) {
        			output = "Tomorrow - " + taskDate.toString("dd MMM yyyy");
        		} else {
            		output = currentDate.dayOfWeek().getAsText();
        		}
        	} else {
        		output = currentDate.toString("dd MMM yyyy");
        	}
        	
        	return output;
        }
        
    	//@author A0111824R
        /**
         *
         * @author Tan Young Sing
         */
        private int getContentHeight(int length) {
        	if(length < 80) {
        		return 65;
        	} else if (length > 80 && length < 140) {
        		return 105;
        	} else if (length > 140 && length < 200) {
        		return 155;
        	} else if (length > 200 && length < 260){
        		return 205;
        	} else if (length > 260 && length < 320) {
        		return 255;
        	} else if (length > 320 && length < 380){
        		return 305;
        	} else if (length > 380 && length < 440){
        		return 355;
        	} else {
        		return 405;
        	}
        }
        
    	//@author A0111824R
        /**
         *
         * @author Tan Young Sing
         */
        private String generateTaskDate(Task item) {
        	String output = "";
        	
        	if(item.getDate() != null && item.getEndDate() == null) {
        		output += item.getDate().toString("h:mm a");
        	} else if(item.getDate() != null && item.getEndDate() != null) {
        		output += item.getDate().toString("h:mm a");
        	}
        	
        	if(item.getDate() == null && item.getEndDate() != null) {
        		output += "Due on: " + item.getEndDate().toString("dd MMM yyy, h:mm a");
        	} else if(item.getDate() != null && item.getEndDate() != null) {
        		if(item.getDate().equals(item.getEndDate())) {
        			output += " - " + item.getEndDate().toString("h:mm a");
        		} else {
        			output += " - " + item.getEndDate().toString("dd MMM yy, h:mm a");
        		}
        	} 
        	
        	return output;
        }
        
    	//@author A0111824R
        /**
         *
         * @author Tan Young Sing
         */
        private StackPane createOutstandingLabel() {
        	Rectangle outstandingLabel = createRectangle(290, 15, 0, 0, Color.web("rgba(255, 120, 120, 1)"));
        	Text labelText = createText("OUTSTANDING", 190, 10, "Raleway", FontWeight.BOLD, Color.WHITE);
        	
			StackPane stack = new StackPane();
			StackPane.setAlignment(outstandingLabel, Pos.TOP_LEFT);
			StackPane.setMargin(labelText, new Insets(0, 50, 0, 70));
			stack.getChildren().addAll(outstandingLabel, labelText);
        	
        	return stack;
        }

    	//@author A0111824R
        /**
         *
         * @author Tan Young Sing
         */
        @Override
        public void updateItem(UITaskListItem item, boolean empty) {
        	super.updateItem(item, empty);
        	
        	if(!empty) {
        		if (item != null && item.getType().equals("default")) {
        			Task taskItem = item.getTask();
        			
        			VBox descriptionBox = new VBox(2);
        			
        		    if(!taskItem.isCompleted()) {
        				if(taskItem.getEndDate() != null) {	
        					if(taskItem.getEndDate().isBeforeNow()) {
        						//outstanding
        						descriptionBox.getChildren().addAll(createOutstandingLabel());
        					} 
        	        	}else if(taskItem.getDate() != null){
        					if(taskItem.getDate().isBeforeNow()) {
        						//outstanding
        						descriptionBox.getChildren().addAll(createOutstandingLabel());
        					} 
                		}
        			}
        			
        			String dateString = generateTaskDate(taskItem);
        			if(dateString.trim().length() != 0) {
            			Text descriptionDate = createText(dateString, 190, 11, "", FontWeight.BOLD, Color.CADETBLUE);
            			descriptionBox.getChildren().addAll(descriptionDate);
            			VBox.setMargin(descriptionDate, new Insets(0, 0, 0, 10));
        			}
        			
        			Text descriptionText = createText(taskItem.getDescription(), 190, 14, "", FontWeight.NORMAL, Color.BLACK);
        			
        			int height = getContentHeight(taskItem.getDescription().length());
        			this.setStyle("-fx-padding: 0 5 0 5;" + String.format(CONTAINER_HEIGHT, height));
        			contentPlaceHolder = createRectangle(270, height-10, 5, 5, Color.WHITE);
        			descriptionBox.getChildren().addAll(descriptionText);
        			
        			HBox taskInnerContentHolder = new HBox();
        			VBox.setMargin(descriptionText, new Insets(1, 1, 5, 1));
        			VBox vbox = new VBox(10);
        			vbox.getChildren().addAll(descriptionBox);
        			VBox.setMargin(descriptionText, new Insets(0, 0, 0, 10));
        		
        			taskInnerContentHolder.getChildren().addAll(getPriorityIndicator(taskItem.getPriority(), taskItem.getDisplayID(), height, taskItem), vbox);
        			
        			
        			StackPane stack = new StackPane();
        			StackPane.setMargin(taskInnerContentHolder, new Insets(5, 0, 0, 0));
        			stack.setPrefHeight(TASK_CONTAINER_HEIGHT);
        			stack.setPrefWidth(TASK_CONTAINER_WIDTH);
        			stack.getChildren().addAll(contentPlaceHolder, taskInnerContentHolder);
        			setGraphic(stack);
        			
        		} else if(item != null && item.getType().equals("header")) {	
        			
        			String cellHeight = String.format(CONTAINER_HEIGHT, "10px");
        			this.setStyle(" -fx-padding: 3 0 3 0; -fx-background-color: #bcbbb9;" + cellHeight);
        			String output = getDateString(item.getDate()) + " (" + item.getNumberTask() + ")";
        			Text text = createText(output, 0, 15, "Ariel", FontWeight.BOLD, Color.WHITE);
        			
        			StackPane stack = new StackPane();
        			stack.getChildren().addAll(text);
        			StackPane.setAlignment(text, Pos.TOP_LEFT);
        			StackPane.setMargin(text, new Insets(0, 0, 0, 10));
        			setGraphic(stack);
        		} else if(item.getTask() == null && item.getType().equals("float_separator")) {
        			
        			String cellHeight = String.format(CONTAINER_HEIGHT, "10px");
        			this.setStyle(" -fx-padding: 3 0 3 0; -fx-background-color: #bcbbb9;" + cellHeight);
        			String output = item.getSeparatorTitle() + " (" + item.getNumberTask() + ")";
        			Text text = createText(output, 0, 15, "Ariel", FontWeight.BOLD, Color.WHITE);
        			
        			StackPane stack = new StackPane();
        			stack.getChildren().addAll(text);
        			StackPane.setAlignment(text, Pos.TOP_LEFT);
        			StackPane.setMargin(text, new Insets(0, 0, 0, 10));
        			
        			
        			if(item instanceof UIEmptyTaskListItem) {
        				StackPane.setMargin(text, new Insets(0, 70, 0, 70));
        				this.setStyle(" -fx-padding: 3 0 3 0; -fx-background-color: #FFB347;" + cellHeight);
        			}
        			
        			setGraphic(stack);
        		}
            } else {
            	this.setStyle("-fx-background-color: rgb(227, 227, 227, 1);");
        		setGraphic(null);
        	}
        }
    }
}
