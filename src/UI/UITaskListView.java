package UI;

import java.util.ArrayList;

import task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
                return new UITaskListCell();
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
}
