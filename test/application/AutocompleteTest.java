package application;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.stage.Stage;

import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Test;

import Task.Task;
import UI.UIAutoComplete;
import UI.UIAutoCompleteListener;
import UI.UICmdInputBox;
import UI.UIComponent;

public class AutocompleteTest extends Application {
	
	public static UIAutoComplete autocomplete;
	public static UICmdInputBox inputBox;
	public static UIAutoCompleteListener acListener;
	public static UIComponent uiComponent;
	
	@BeforeClass
	public static void beforeClass() {	
		inputBox = new UICmdInputBox(new Text(), new Text(), uiComponent);
		acListener = new UIAutoCompleteListener(inputBox);
		autocomplete = new UIAutoComplete(inputBox, acListener);
	}
	
	  
    //@author A0111824R
    // This is used to set up integration testing.
    // This method will always run once before the tests begin.
    @BeforeClass
    public static void initializeIntegrationTest() throws Exception {      
        // Run the JavaFX thread in the background.
        Thread t = new Thread("JavaFX Background") {
            @Override
            public void run() {
                launch(IntegrationSimpleTest.class);
            }
        };
        t.setDaemon(true);
        t.start();
        
        // Sleeps every half second until uiComponent is set and ready for use.
        int i = 0;
        while (uiComponent == null && i < 6) {
            i++;
            Thread.sleep(500);
        }
    }
    
    //@author A0111824R
    // Method to start JavaFX stage during tests.
    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerReflector.fields.get("uiComponent").set(null, new UIComponent());
        uiComponent = (UIComponent) ControllerReflector.fields.get("uiComponent").get(null);
    }
	
    //@author A0111824R
	@Test
	public void testRunAutoComplete() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		
	    Method method = UIAutoComplete.class.getDeclaredMethod("runAutoComplete", String.class);
	    method.setAccessible(true);
	    
	    String userInput = "a";
	    String result = (String) method.invoke(autocomplete, userInput);	
	    assertEquals(result.trim(), "ADD");
	    
	    userInput = "e";
	    result = (String) method.invoke(autocomplete, userInput);
	    assertEquals(result.trim(), "EDIT");

	    userInput = "d";
	    result = (String) method.invoke(autocomplete, userInput);
	    assertEquals(result.trim(), "DELETE");
	    
	    userInput = "z";
	    result = (String) method.invoke(autocomplete, userInput);
	    assertEquals(result.trim(), "");
	    
	    userInput = "se";
	    result = (String) method.invoke(autocomplete, userInput);
	    assertEquals(result.trim(), "SEARCH");
	    
	    userInput = "sh";
	    result = (String) method.invoke(autocomplete, userInput);
	    assertEquals(result.trim(), "SHOW");
	    
	    userInput = "q";
	    result = (String) method.invoke(autocomplete, userInput);
	    assertEquals(result.trim(), "QUIT");
	    
	    userInput = "HELP";
	    result = (String) method.invoke(autocomplete, userInput);
	    assertEquals(result.trim(), "HELP");
	    
	}
}
