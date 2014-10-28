package application;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import UI.UIComponent;

/**
 * 
 * 
 * @author Tan Young Sing
 */
public class UIComponentTest {

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

}
