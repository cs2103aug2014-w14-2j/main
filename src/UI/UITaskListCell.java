package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import task.Task;

//@author A0111824R
/**
 * This class will override the default ListCell generate for the LISTVIEW component
 * @author Tan Young Sing
 */
public class UITaskListCell extends ListCell<UITaskListItem> { 
    static private final int TASK_CONTAINER_WIDTH = 260;
    static private final int TASK_CONTAINER_HEIGHT = 70;
    
    private final int UI_OFFSET = 10;
    private final int UI_NON_PRIORITY = 0;
    
    private final String UI_DEFAULT_FONT = "Bemio";
    private final String UI_DESCRIPTION_FONT = "Raleway";
    
    private final int UI_MAXWIDTH_PRIORITY_INDICATOR = 40;
    
    private final int UI_PERIOD_ONE = 1;
    private final int UI_PERIOD_WEEK = 7;
    
    private final String UI_DATEFORMAT = "dd MMM yyyy";
    private final String UI_TIMEFORMAT = "h:mm a";
    private final String UI_DATETIMEFORMAT = "dd MMM yyy, h:mm a";
    
    private final String UI_OVERDUE_LABEL = "Outstanding";
    private final String UI_DEFAULT_PADDING = "-fx-padding: 0 5 0 5;";
    private final String UI_HEADING_STYLE = " -fx-padding: 3 0 3 0; -fx-background-color: #bcbbb9;";
    private final String UI_EMPTY_TASK_STYLE = "-fx-background-color: rgb(227, 227, 227, 1);";
    private final String UI_EMPTY_TASKS_DESIGN = "-fx-padding: 3 0 3 0; -fx-background-color: #FFB347;";
    private final String UI_HELP_TASK_DESIGN = "-fx-padding: 3 0 3 0; -fx-background-color: #44b6ae;";
    
    private final int UI_OUTSTANDING_HEIGHT = 15;
    private final int UI_OUTSTANDING_WIDTH = 290;
   
	private final String LISTITEM_HEADER = "header";
	private final String LISTITEM_DEFAULT = "default";
	private final String LISTITEM_SEPARATOR = "float_separator";

    private final String CONTAINER_HEIGHT = "-fx-cell-size: %s;";

    private String COLOR_DEFAULT_PRIORITY = "rgba(37, 232, 154, 1)";
    private String COLOR_HIGH_PRIORITY = "rgba(249, 104, 114, 1)";
    private String COLOR_COMPLETED = "rgba(188, 187, 185, 1)";
    private String COLOR_OUTSTANDING = "rgba(255, 120, 120, 1)";
    
    private Rectangle contentPlaceHolder;
    private Text indexLabel;
    
	//@author A0111824R
    /**
     * Creates a Rectangle Object
     * @param width : Width of the rectangle
     *  	  height : Height of the rectangle
     *   	  arcWidth : Curve Border Top Half
     *        arcHeight : Curve Border Bottom Half
     *        Color : Color of the Rectangle
     * @return Rectangle
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
     * Create a Text Object
     * @param text : String of the text
     * 		  textWidth: Word Wrap Width
     * 		  size : font size
     *    	  fontFamily: font-family of the display text
     *        fontWeight: weight of the font default: NORMAL
     *        color : color of the text
     * @return Text
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
     * @param priority : (0,1) 0 is non prioritize and 1 is prioritize
     *  	  displayID : Task ID
     *        height : height of the priority Indicator
     *        item : Task object that will retrieve all specific data for display
     * @return StackPane
     * @author Tan Young Sing
     */
    private StackPane getPriorityIndicator(int priority, String displayID, int height, Task item) {
    	String indicator_color = COLOR_DEFAULT_PRIORITY;
    	
    	if(item.isCompleted()) {
    		indicator_color = COLOR_COMPLETED;
    	} else {
    		if(priority == UI_NON_PRIORITY) {
    			 indicator_color = COLOR_DEFAULT_PRIORITY;
    		} else if (priority != UI_NON_PRIORITY) {
    			indicator_color = COLOR_HIGH_PRIORITY;
    		}
    	}
    	
    	Rectangle priorityIndicator = createRectangle(UI_MAXWIDTH_PRIORITY_INDICATOR, height-UI_OFFSET, 0, 0, Color.web(indicator_color));
    	indexLabel = createText(displayID, 0, 20, UI_DEFAULT_FONT, FontWeight.BOLD, Color.WHITE);

    	StackPane stack = new StackPane();
    	stack.setPadding(new Insets(0, 0, 0, 0));
		stack.setMaxHeight(height-UI_OFFSET);
		stack.setMaxWidth(UI_MAXWIDTH_PRIORITY_INDICATOR);
    	
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
    	DateTime tomorrow = today.plus(Period.days(UI_PERIOD_ONE));
    	
    	DateTime end = today.plus(Period.days(UI_PERIOD_WEEK));
    	
    	Interval interval = new Interval(today, end);
    	
    	if(interval.contains(currentDate)) {
    		if(taskDate.equals(today)) {
    			output = "Today - " + taskDate.toString(UI_DATEFORMAT);
    		} else if (taskDate.equals(tomorrow)) {
    			output = "Tomorrow - " + taskDate.toString(UI_DATEFORMAT);
    		} else {
        		output = currentDate.dayOfWeek().getAsText();
    		}
    	} else {
    		output = currentDate.toString(UI_DATEFORMAT);
    	}
    	
    	return output;
    }
    
	//@author A0111824R
    /**
     * calculate the height of the task items based on content length
     * @author Tan Young Sing
     */
    private int getContentHeight(int length) {
    	if(length <= 80) {
    		return 70;
    	} else if (length > 80 && length < 140) {
    		return 110;
    	} else if (length >= 140 && length < 200) {
    		return 160;
    	} else if (length >= 200 && length < 260){
    		return 210;
    	} else if (length >= 260 && length < 320) {
    		return 260;
    	} else if (length >= 320 && length < 380){
    		return 310;
    	} else if (length >= 380 && length < 440){
    		return 360;
    	} else {
    		return 410;
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
    		output += item.getDate().toString(UI_TIMEFORMAT);
    	} else if(item.getDate() != null && item.getEndDate() != null) {
    		output += item.getDate().toString(UI_TIMEFORMAT);
    	}
    	
    	if(item.getDate() == null && item.getEndDate() != null) {
    		output += "Due on: " + item.getEndDate().toString(UI_DATETIMEFORMAT);
    	} else if(item.getDate() != null && item.getEndDate() != null) {
    		if(item.getDate().equals(item.getEndDate())) {
    			output += " - " + item.getEndDate().toString(UI_TIMEFORMAT);
    		} else {
    			output += " - " + item.getEndDate().toString(UI_DATETIMEFORMAT);
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
    	Rectangle outstandingLabel = createRectangle(UI_OUTSTANDING_WIDTH, UI_OUTSTANDING_HEIGHT, 0, 0, Color.web(COLOR_OUTSTANDING));
    	Text labelText = createText(UI_OVERDUE_LABEL, 190, 10, UI_DESCRIPTION_FONT, FontWeight.BOLD, Color.WHITE);
    	
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
    		if(item instanceof UIHelpListItem) {
    			UIHelpListItem helpItem = (UIHelpListItem) item;
    			if(helpItem.isTitle()) {
        			String cellHeight = String.format(CONTAINER_HEIGHT, "10px");
        			this.setStyle(UI_HELP_TASK_DESIGN + cellHeight);
        			String output = helpItem.getDescription();
        			Text text = createText(output, 0, 15,  UI_DESCRIPTION_FONT, FontWeight.BOLD, Color.WHITE);
        			
        			StackPane stack = new StackPane();
        			stack.getChildren().addAll(text);
        			StackPane.setAlignment(text, Pos.TOP_LEFT);
        			StackPane.setMargin(text, new Insets(0, 0, 0, 10));
        			setGraphic(stack);
    			} else {
        			VBox descriptionBox = new VBox(2);
        			Text descriptionText = createText(helpItem.getDescription(), 250, 14, "", FontWeight.NORMAL, Color.BLACK);
        			int height = getContentHeight(helpItem.getDescription().length());
        			this.setStyle(UI_DEFAULT_PADDING + String.format(CONTAINER_HEIGHT, height));
        			contentPlaceHolder = createRectangle(270, height-10, 5, 5, Color.WHITE);
        			descriptionBox.getChildren().addAll(descriptionText);
        			
           			HBox taskInnerContentHolder = new HBox();
        			VBox.setMargin(descriptionText, new Insets(1, 1, 5, 1));
        			VBox vbox = new VBox(10);
        			vbox.getChildren().addAll(descriptionBox);
        			VBox.setMargin(descriptionText, new Insets(0, 0, 0, 10));
        			
        			taskInnerContentHolder.getChildren().addAll(vbox);
        			
        			StackPane stack = new StackPane();
        			StackPane.setMargin(taskInnerContentHolder, new Insets(5, 0, 0, 0));
        			stack.setPrefHeight(TASK_CONTAINER_HEIGHT);
        			stack.setPrefWidth(TASK_CONTAINER_WIDTH);
        			stack.getChildren().addAll(contentPlaceHolder, taskInnerContentHolder);
        			setGraphic(stack);
    			}	
    			
    		} else if (item != null && item.getType().equals(LISTITEM_DEFAULT)) {
    			Task taskItem = item.getTask();
    			boolean isOutstanding = false;
    			
    			VBox descriptionBox = new VBox(2);
    			
    		    if(!taskItem.isCompleted()) {
    				if(taskItem.getEndDate() != null) {	
    					if(taskItem.getEndDate().isBeforeNow()) {
    						//outstanding
    						descriptionBox.getChildren().addAll(createOutstandingLabel());
    						isOutstanding = true;
    					} 
    	        	}else if(taskItem.getDate() != null){
    					if(taskItem.getDate().isBeforeNow()) {
    						//outstanding
    						descriptionBox.getChildren().addAll(createOutstandingLabel());
    						isOutstanding = true;
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
    			
    			if(isOutstanding) {
    				height += UI_OUTSTANDING_HEIGHT;
    			}
    			
    			this.setStyle(UI_DEFAULT_PADDING + String.format(CONTAINER_HEIGHT, height));
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
    			
    		} else if(item != null && item.getType().equals(LISTITEM_HEADER)) {	
    			
    			String cellHeight = String.format(CONTAINER_HEIGHT, "10px");
    			this.setStyle(UI_HEADING_STYLE + cellHeight);
    			String output = getDateString(item.getDate()) + " (" + item.getNumberTask() + ")";
    			Text text = createText(output, 0, 15, "Ariel", FontWeight.BOLD, Color.WHITE);
    			
    			StackPane stack = new StackPane();
    			stack.getChildren().addAll(text);
    			StackPane.setAlignment(text, Pos.TOP_LEFT);
    			StackPane.setMargin(text, new Insets(0, 0, 0, 10));
    			setGraphic(stack);
    			
    		} else if(item.getTask() == null && item.getType().equals(LISTITEM_SEPARATOR)) {
    			
    			String cellHeight = String.format(CONTAINER_HEIGHT, "10px");
    			this.setStyle(UI_HEADING_STYLE + cellHeight);
    			String output = item.getSeparatorTitle() + " (" + item.getNumberTask() + ")";
    			Text text = createText(output, 0, 15,  UI_DESCRIPTION_FONT, FontWeight.BOLD, Color.WHITE);
    			
    			StackPane stack = new StackPane();
    			stack.getChildren().addAll(text);
    			StackPane.setAlignment(text, Pos.TOP_LEFT);
    			StackPane.setMargin(text, new Insets(0, 0, 0, 10));
    			
    			if(item instanceof UIEmptyTaskListItem) {
    				StackPane.setMargin(text, new Insets(0, 60, 0, 60));
    				this.setStyle(UI_EMPTY_TASKS_DESIGN + cellHeight);
    			}
    			
    			setGraphic(stack);
    		}
        } else {
        	this.setStyle(UI_EMPTY_TASK_STYLE);
    		setGraphic(null);
    	}
    }
}