package application;

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
    
	private final String CMD_DELETE_FLOATING_TASK = "DELETE F%d";
	private final String CMD_DELETE_EVENT_TASK = "DELETE E%d";
	
	private String type;
    
    public UITaskListView(UICmdInputBox cmdInputBox, String type) {
        taskList = new ListView<Task>();
        this.cmdInputBox = cmdInputBox;
        this.type = type;
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
    
    public int getSelectedItemIndex() {
    	return taskList.getSelectionModel().getSelectedIndex();
    }
   
    public void populateTaskListWithData(ObservableList<Task> items) {
        taskList.setItems(items);
    }

    public ListView<Task> getListView() {
        return taskList;
    }

    static class TaskListCell extends ListCell<Task> { 
        static private final int TASK_CONTAINER_WIDTH = 260;
        static private final int TASK_CONTAINER_HEIGHT = 70;
        static private final int TASK_CONTAINER_SPACING = 15;
        static private int color_counter = 0;
        
        private String COLOR_GREEN = "rgba(43, 255, 0, 1)";
        private String COLOR_RED = "rgba(255, 79, 100, 1)";
        private String COLOR_YELLOW = "rgba(255, 246, 0, 1)";
        private String COLOR_BLUE = "rgba(0, 131, 255, 1)";
        private String COLOR_PINK = "rgba(246, 96, 171, 1)";
          
        private String[] colorArray = {COLOR_PINK, COLOR_BLUE, COLOR_YELLOW, COLOR_RED, COLOR_GREEN};
        int counter = 0;
        
        private Rectangle createRectangle(int width, int height, int arcWidth, int arcHeight, Color c) {
            Rectangle rect = new Rectangle(width, height);
            rect.setArcHeight(arcHeight);
            rect.setArcWidth(arcWidth);
            rect.setFill(c);
            return rect;
        }

        private Text createText(String text, int textWidth, int size) {
            Text textLabel = new Text(text);
            textLabel.setWrappingWidth(textWidth);
            textLabel.setBoundsType(TextBoundsType.VISUAL);
            textLabel.setTextAlignment(TextAlignment.LEFT);
            textLabel.setFont(Font.font("Ariel", FontWeight.NORMAL, size));
            return textLabel;
        }

        @Override
        public void updateItem(Task item, boolean empty) {
            super.updateItem(item, empty);
            Rectangle contentPlaceHolder;
            Rectangle priorityIndicator = createRectangle(50, 50, 0, 0, Color.web(colorArray[color_counter]));
            
            //TO-BE-DELETED
            //System.out.println((color_counter%2));
            if ((color_counter%2) == 0) {
                this.getStyleClass().add("bigger-list-cell");
                contentPlaceHolder = createRectangle(260, 170, 10, 10, Color.WHITE);
            }
            else { 
                this.getStyleClass().add("smaller-list-cell");
                contentPlaceHolder = createRectangle(260, 70, 10, 10, Color.WHITE);
            }
            
            //TO-BE-DELETED
            color_counter++;
            if(color_counter == colorArray.length) {
                color_counter = 0;
            }
            
            if (item != null) {
                Text text = createText(item.getDescription(), 150, 12);
                HBox taskInnerContentHolder = new HBox(TASK_CONTAINER_SPACING);
                taskInnerContentHolder.setPadding(new Insets(10, 0, 0, 15));
                taskInnerContentHolder.getChildren().addAll(priorityIndicator, text);
                
                StackPane stack = new StackPane();
                stack.setPrefHeight(TASK_CONTAINER_HEIGHT);
                stack.setPrefWidth(TASK_CONTAINER_WIDTH);
                stack.getChildren().addAll(contentPlaceHolder, taskInnerContentHolder);
                setGraphic(stack);
            }
        }
    }
}
