package application;

import java.util.Timer;
import java.util.TimerTask;

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

/**
 * Abstracted ListView Class
 * 
 * @author Tan Young Sing
 */
public class UITaskListView {

    private ListView<Task> taskList;

    private final int DISPLAY_WIDTH = 300;
    private final int DISPLAY_HEIGHT = 500;
    private final String FLOATING = "Task";
    private final String EVENT = "Event";
    
    private final String TASKLIST_DEFAULT_STYLE = "taskList_style";
    private UICmdInputBox cmdInputBox;
    
	private final String CMD_DELETE_FLOATING_TASK = "DELETE %s";
	private final String CMD_DELETE_EVENT_TASK = "DELETE %s";
	
	public String type;
    private Timer animTimer;
    
    public UITaskListView(UICmdInputBox cmdInputBox, String type) {
        taskList = new ListView<Task>();
        this.cmdInputBox = cmdInputBox;
        this.type = type;
        this.animTimer = new Timer();
        setTaskListProperty();
    }

    private void setTaskListProperty() {
        taskList.setPrefHeight(DISPLAY_HEIGHT);
        taskList.setPrefWidth(DISPLAY_WIDTH);
        taskList.getStyleClass().add(TASKLIST_DEFAULT_STYLE);

        taskList.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
            @Override
            public ListCell<Task> call(ListView<Task> list) {
                return new TaskListCell();
            }
        });
        
        if(type.equals(FLOATING)) {
        	taskList.setOnKeyPressed(new UITaskListViewListener(CMD_DELETE_FLOATING_TASK, cmdInputBox, this));
        } else if(type.equals(EVENT)) {
        	taskList.setOnKeyPressed(new UITaskListViewListener(CMD_DELETE_EVENT_TASK, cmdInputBox, this));
        }
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
    
    public ObservableList<Task> getSelectedItem() {
    	return taskList.getSelectionModel().getSelectedItems();
    }
    
    private void shrinkList() {
	    animTimer.scheduleAtFixedRate(new TimerTask() {
	    	@Override
	        public void run() {
	            while (true) {
	            	double newHeight = taskList.getHeight() - 3;
	              	if(newHeight <= 0) {
	              		this.cancel();
	              	} else {
	              		taskList.setPrefHeight(newHeight);
	              	}
	            }
	        }
	    }, 2000, 20);
	    
    }
    
    private void expandList() {
    	
    }
   
    public void populateTaskListWithData(ObservableList<Task> items) {
    	if(items.isEmpty()) {
    		shrinkList();
    	} else {
    		
    	}
    
    	taskList.setItems(items);
    }

    public ListView<Task> getListView() {
        return taskList;
    }

    class TaskListCell extends ListCell<Task> { 
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
            textLabel.setBoundsType(TextBoundsType.VISUAL);
            textLabel.setTextAlignment(TextAlignment.LEFT);
            textLabel.setFont(Font.font(fontFamily, weight, size));
            textLabel.setFill(color);
            return textLabel;
        }
        
        private StackPane getPriorityIndicator(int priority, String displayID) {
        	String indicator_color = COLOR_DEFAULT_PRIORITY;
        	
        	if(priority > 1 && priority < 4) {
        		indicator_color = COLOR_MEDIUM_PRIORITY;
        	} else if (priority >= 5) {
        		indicator_color = COLOR_HIGH_PRIORITY;
        	}
        	
        	Rectangle priorityIndicator = createRectangle(50, 50, 5, 5, Color.web(indicator_color));
    		
        	if(type.equals(FLOATING)) {
        		indexLabel = createText(displayID, 0, 20, "Bemio", FontWeight.BOLD, Color.WHITE);
        	} else if(type.equals(EVENT)) {
        		indexLabel = createText(displayID, 0, 20, "Bemio", FontWeight.BOLD, Color.WHITE);
        	}
        	
        	StackPane stack = new StackPane();
        	stack.setPadding(new Insets(0, 0, 10, 0));
			stack.setPrefHeight(60);
			stack.setPrefWidth(60);
			stack.getChildren().addAll(priorityIndicator, indexLabel);	
     
        	return stack;
        }
        
        private int getContentHeight(int length) {
        	if (length < 50) {
        		return 80;
        	} else if (length < 100) {
        		return 180;
        	} else {
        		return 280;
        	}
        }
        
        private String generateTaskDescription(Task item) {
        	String output = item.getDescription();
        	
        	if(item.getDate() != null) {
        		output += "\nDATE: " + item.getDate().toString("dd/MM/yyyy HH:mm");
        	} 
        	
        	if(item.getEndDate() != null) {
        		output += "\nEND DATE: " + item.getEndDate().toString("dd/MM/yyyy HH:mm");
        	} 
        	
        	return output;
        }

        @Override
        public void updateItem(Task item, boolean empty) {
        	super.updateItem(item, empty);
        	
        	if(!empty) {
        		if (item != null) {
        			String output = generateTaskDescription(item);
        			
        			Text text = createText(output, 150, 12, "raleway", FontWeight.NORMAL, Color.BLACK);
        			int height = getContentHeight(output.length());
        			this.setStyle(String.format(CONTAINER_HEIGHT, height));
        			contentPlaceHolder = createRectangle(260, height-10, 10, 10, Color.WHITE);
        			
        			HBox taskInnerContentHolder = new HBox(TASK_CONTAINER_SPACING);
        			taskInnerContentHolder.setPadding(new Insets(10, 0, 0, 15));
        			taskInnerContentHolder.getChildren().addAll(getPriorityIndicator(item.getPriority(), item.getDisplayID()), text);
        			
        			StackPane stack = new StackPane();
        			stack.setPrefHeight(TASK_CONTAINER_HEIGHT);
        			stack.setPrefWidth(TASK_CONTAINER_WIDTH);
        			stack.getChildren().addAll(contentPlaceHolder, taskInnerContentHolder);
        			setGraphic(stack);
        		}
            }else {
        		setGraphic(null);
        	}
        }
    }
}
