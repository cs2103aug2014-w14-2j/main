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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    	
    	for(Task listItem : items) {
    		listItems.add(new UITaskListItem(listItem, listItem.getDate()));
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
        		currentHeader = new UITaskListItem(null, t.getDate());
        		listItems.add(currentHeader);
    		} else {
    			if(currentDate.toString("y").equals(t.getDate().toString("y"))) {
    				if(!currentDate.toString("D").equals(t.getDate().toString("D"))) {
    					currentDate = t.getDate();
    					currentHeader = new UITaskListItem(null, t.getDate());
    					listItems.add(currentHeader);
    				}
    			} else {
    				currentDate = t.getDate();
    				currentHeader = new UITaskListItem(null, t.getDate());
    				listItems.add(currentHeader);
    			}
    		}	
    		listItems.add(new UITaskListItem(t, t.getDate()));
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
        
        private final String CONTAINER_HEIGHT = "-fx-cell-size: %s";
        private String COLOR_DEFAULT_PRIORITY = "rgba(37, 232, 154, 1)";
        private String COLOR_HIGH_PRIORITY = "rgba(249, 104, 114, 1)";
        private String COLOR_MEDIUM_PRIORITY = "rgba(247, 207, 89, 1)";
        
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
        
        private StackPane getPriorityIndicator(int priority, String displayID, int height) {
        	String indicator_color = COLOR_DEFAULT_PRIORITY;
        	
        	if(priority > 1 && priority < 4) {
        		indicator_color = COLOR_MEDIUM_PRIORITY;
        	} else if (priority >= 5) {
        		indicator_color = COLOR_HIGH_PRIORITY;
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
        			output = "Today";
        		} else if (taskDate.equals(tomorrow)) {
        			output = "Tomorrow";
        		} else {
            		output = currentDate.dayOfWeek().getAsText();
        		}
        	} else {
        		output = currentDate.toString("dd MMMM yyyy");
        	}
        	
        	return output;
        }
        
        private int getContentHeight(int length) {
        	if (length < 150) {
        		return 80;
        	} else if (length < 200) {
        		return 180;
        	} else {
        		return 280;
        	}
        }
        
        private String generateTaskDescription(Task item) {
        	String output = item.getDescription();
        	
        	if(item.getDate() != null) {
        		output += "\n" + item.getDate().toString("dd MMMM yyyy HH:mm");
        	} 
        	
        	if(item.getEndDate() != null) {
        		output += "\n" + item.getEndDate().toString("dd MMMM yyyy HH:mm");
        	} 
        	
        	return output;
        }

        @Override
        public void updateItem(UITaskListItem item, boolean empty) {
        	super.updateItem(item, empty);
        	
        	if(!empty) {
        		if (item != null && item.getType().equals("default")) {
        			Task taskItem = item.getTask();
        			String output = generateTaskDescription(taskItem);
        			
        			Text text = createText(output, 150, 12, "raleway", FontWeight.NORMAL, Color.BLACK);
        			int height = getContentHeight(output.length());
        			this.setStyle(String.format(CONTAINER_HEIGHT, height));
        			contentPlaceHolder = createRectangle(260, height-10, 10, 10, Color.WHITE);
        			
        			HBox taskInnerContentHolder = new HBox(TASK_CONTAINER_SPACING);
        			HBox.setMargin(text, new Insets(5, 0, 0, 0));
        			taskInnerContentHolder.getChildren().addAll(getPriorityIndicator(taskItem.getPriority(), taskItem.getDisplayID(), height), text);
        			
        			StackPane stack = new StackPane();
        			stack.setPrefHeight(TASK_CONTAINER_HEIGHT);
        			stack.setPrefWidth(TASK_CONTAINER_WIDTH);
        			stack.getChildren().addAll(contentPlaceHolder, taskInnerContentHolder);
        			setGraphic(stack);
        			
        		} else if(item != null && item.getType().equals("date")) {	
        			
        			String cellHeight = String.format(CONTAINER_HEIGHT, "1");
        			this.setStyle("-fx-background-color: #bcbbb9;" + cellHeight);
        			
        			String output = getDateString(item.getDate());
        			
        			Text text = createText(output, 220, 15, "raleway", FontWeight.BOLD, Color.WHITE);
        			StackPane stack = new StackPane();
        			StackPane.setAlignment(text, Pos.TOP_LEFT);
        			
        			stack.getChildren().addAll(text);
        			setGraphic(stack);
        		}
            } else {
            	this.setStyle("-fx-background-color: rgb(227, 227, 227, 1);");
        		setGraphic(null);
        	}
        }
    }
}
