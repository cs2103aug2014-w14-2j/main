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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 * Abstracted ListView Class
 * 
 * @author Tan Young Sing
 */
public class UITaskListView {

    private ListView<UITaskListItem> taskList;

    private final int DISPLAY_WIDTH = 300;
    private final int DISPLAY_HEIGHT = 500;
    
    private final String FLOATING = "Task";
    private final String EVENT = "Event";
    
    private final String TASKLIST_DEFAULT_STYLE = "taskList_style";
    private UICmdInputBox cmdInputBox;
    
	private final String CMD_DELETE_FLOATING_TASK = "DELETE %s";
	private final String CMD_DELETE_EVENT_TASK = "DELETE %s";
	private ObservableList<UITaskListItem> listItems;
	
	public String type;
    
    public UITaskListView(UICmdInputBox cmdInputBox, String type) {
        taskList = new ListView<UITaskListItem>();
        listItems = FXCollections.observableArrayList();
        this.cmdInputBox = cmdInputBox;
        this.type = type;
        setTaskListProperty();
    }

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
        
        if(type.equals(FLOATING)) {
        	taskList.setOnKeyPressed(new UITaskListViewListener(CMD_DELETE_FLOATING_TASK, cmdInputBox, this));
        } else if(type.equals(EVENT)) {
        	taskList.setOnKeyPressed(new UITaskListViewListener(CMD_DELETE_EVENT_TASK, cmdInputBox, this));
        }
    }
    
    private ArrayList<UITaskListItem> generateFloatingList(ArrayList<Task> items) {
    	ArrayList<UITaskListItem> listItems = new ArrayList<UITaskListItem>();
    	UITaskListItem currentHeader = null;
    	
    	for(int i =0; i<items.size(); i++) {
    		Task listItem = items.get(i);
    		
    		if(currentHeader == null) {
    			if(listItem.getEndDate() == null) {
    				currentHeader = new UITaskListItem(null, null, "Right");
    				listItems.add(currentHeader);
    			} else if(listItem.getEndDate() != null) {
    				currentHeader = new UITaskListItem(null, listItem.getEndDate(), "Right");
    				listItems.add(currentHeader);
    			} 
    		} else {
    			if(listItem.getEndDate() != null && !currentHeader.getSeparatorTitle().equalsIgnoreCase("DEADLINES")) {
    				currentHeader = new UITaskListItem(null, listItem.getEndDate(), "Right");
    				listItems.add(currentHeader);
    			} else if(listItem.getEndDate() == null && !currentHeader.getSeparatorTitle().equalsIgnoreCase("REMINDERS")){
    				currentHeader = new UITaskListItem(null, null, "Right");
    				listItems.add(currentHeader);
    			}
    		}
    	
    		listItems.add(new UITaskListItem(listItem, listItem.getDate(), "Right"));
    	}
    
    	return listItems;
    }
    
    
    private ArrayList<UITaskListItem> generateListItems(ArrayList<Task> items) {
    	DateTime currentDate = null;
    	ArrayList<UITaskListItem> listItems = new ArrayList<UITaskListItem>();
    	UITaskListItem currentHeader = null;
    	
    	for(int i = 0; i<items.size(); i++) {
    		Task t = items.get(i);
    		if(currentHeader == null) {
        		currentDate = t.getDate();
        		currentHeader = new UITaskListItem(null, t.getDate(), "Left");
        		listItems.add(currentHeader);
    		} else {
    			if(currentDate.toString("y").equals(t.getDate().toString("y"))) {
    				if(!currentDate.toString("D").equals(t.getDate().toString("D"))) {
    					currentDate = t.getDate();
    					currentHeader = new UITaskListItem(null, t.getDate(), "Left");
    					listItems.add(currentHeader);
    				}
    			} else {
    				currentDate = t.getDate();
    				currentHeader = new UITaskListItem(null, t.getDate(), "Left");
    				listItems.add(currentHeader);
    			}
    		}	
    		listItems.add(new UITaskListItem(t, t.getDate(), "Left"));
    		currentHeader.incrementNumOfTask();
    	}

    	return listItems;
    }
    
    public boolean isFocused() {
    	return taskList.isFocused();
    }
    
    public void clearSelection() {
    	taskList.getSelectionModel().clearSelection();
    }
    
    public int getSelectedItemIndex() {
    	return taskList.getSelectionModel().getSelectedIndex();
    }
    
    public ObservableList<UITaskListItem> getSelectedItem() {
    	return taskList.getSelectionModel().getSelectedItems();
    }
   
    public void populateTaskListWithData(ArrayList<Task> items) {
    	ObservableList<UITaskListItem> convertedList = FXCollections.observableArrayList();
    	
    	if(this.type.equals(EVENT)) {
    		convertedList.setAll(generateListItems(items));
    	} else if (this.type.equals(FLOATING)){
    		convertedList.setAll(generateFloatingList(items));
    	}
    	
    	taskList.setItems(convertedList);
    }

    public ListView<UITaskListItem> getListView() {
        return taskList;
    }

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
        
        private Rectangle createRectangle(int width, int height, int arcWidth, int arcHeight, Color c) {
        	Rectangle rect = new Rectangle(width, height);
            rect.setArcHeight(arcHeight);
            rect.setArcWidth(arcWidth);
            rect.setFill(c);
            return rect;
        }

        private Text createText(String text, int textWidth, int size, String fontFamily, FontWeight weight, Color color) {
            Text textLabel = new Text(text);
            textLabel.setWrappingWidth(textWidth);
            textLabel.setFont(Font.font(fontFamily, weight, size));
            textLabel.setFill(color);
            return textLabel;
        }
        
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
        
        private int getContentHeight(int length) {
        	if (length < 140) {
        		return 100;
        	} else if (length > 140 && length < 200) {
        		return 150;
        	} else if (length > 200 && length < 260){
        		return 200;
        	} else if (length > 260 && length < 320) {
        		return 250;
        	} else if (length > 320 && length < 380){
        		return 300;
        	} else if (length > 380 && length < 440){
        		return 350;
        	} else {
        		return 400;
        	}
        }
        
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
        
        private StackPane createOutstandingLabel() {
        	Rectangle outstandingLabel = createRectangle(90, 15, 5, 5, Color.RED);
        	Text labelText = createText("OUTSTANDING", 190, 10, "Raleway", FontWeight.BOLD, Color.WHITE);
        	
			StackPane stack = new StackPane();
			StackPane.setAlignment(outstandingLabel, Pos.TOP_LEFT);
			StackPane.setMargin(labelText, new Insets(0, 6, 0, 8));
			stack.getChildren().addAll(outstandingLabel, labelText);
        	
        	return stack;
        }

        @Override
        public void updateItem(UITaskListItem item, boolean empty) {
        	super.updateItem(item, empty);
        	
        	if(!empty) {
        		if (item != null && item.getType().equals("default")) {
        			Task taskItem = item.getTask();
        			
        			VBox descriptionBox = new VBox(5);
        			
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
        			}
        			
        			Text descriptionText = createText(taskItem.getDescription(), 190, 14, "", FontWeight.NORMAL, Color.BLACK);
        			
        			int height = getContentHeight(taskItem.getDescription().length());
        			this.setStyle("-fx-padding: 0 5 0 5;" + String.format(CONTAINER_HEIGHT, height));
        			contentPlaceHolder = createRectangle(270, height-10, 5, 5, Color.WHITE);
        			descriptionBox.getChildren().addAll(descriptionText);
        			
        			HBox taskInnerContentHolder = new HBox(TASK_CONTAINER_SPACING);
        			VBox.setMargin(descriptionText, new Insets(0, 0, 0, 0));
        			VBox vbox = new VBox(10);
        			vbox.getChildren().addAll(descriptionBox);	
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
        			String output = getDateString(item.getDate());
        			Text text = createText(output, 0, 15, "Ariel", FontWeight.BOLD, Color.WHITE);
        			
        			StackPane stack = new StackPane();
        			stack.getChildren().addAll(text);
        			StackPane.setAlignment(text, Pos.TOP_LEFT);
        			StackPane.setMargin(text, new Insets(0, 0, 0, 10));
        			setGraphic(stack);
        		} else if(item.getTask() == null && item.getType().equals("float_separator")) {
        			
        			String cellHeight = String.format(CONTAINER_HEIGHT, "10px");
        			this.setStyle(" -fx-padding: 3 0 3 0; -fx-background-color: #bcbbb9;" + cellHeight);
        			String output = item.getSeparatorTitle();
        			Text text = createText(output, 0, 15, "Ariel", FontWeight.BOLD, Color.WHITE);
        			
        			StackPane stack = new StackPane();
        			stack.getChildren().addAll(text);
        			StackPane.setAlignment(text, Pos.TOP_LEFT);
        			StackPane.setMargin(text, new Insets(0, 0, 0, 10));
        			setGraphic(stack);
        		}
            } else {
            	this.setStyle("-fx-background-color: rgb(227, 227, 227, 1);");
        		setGraphic(null);
        	}
        }
    }
}
