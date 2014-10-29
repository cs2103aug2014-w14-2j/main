package application;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.stage.Stage;
import UI.UIComponent;

/**
 * 
 * 
 * @author Tan Young Sing
 */


public class UIComponentTest extends Application{

	public static UIComponent ui;
    
	@BeforeClass
	public static void beforeClass() {
		ui = new UIComponent();
	}
	
	@Test
	public void test() {
		assertNotNull(ui.getCmdInputBox());
		assertNotNull(ui.getEventReminderTaskListView());
		assertNotNull(ui.getFloatingTaskListView());
		assertNotNull(ui.getRootPane());
		assertNotNull(ui.getScene());
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		beforeClass();
	}

}
