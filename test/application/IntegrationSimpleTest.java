package application;

import static org.junit.Assert.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;

import UI.UIComponent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@author A0110546R
/**
 * This class is used to reflect all the private static fields and methods of the Controller class.
 * @author Sun Wang Jun
 *
 */
class ControllerReflector {
    // Hashtable to map each method and field to their own name.
    public static Hashtable<String, Method> methods = new Hashtable<String, Method>();
    public static Hashtable<String, Field> fields = new Hashtable<String, Field>();

    public static void reflect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> controller = Controller.class;
  
        // Reflects the private static methods.
        Method m[] = controller.getDeclaredMethods();
        for (Method method : m) {
            methods.put(method.getName(), method);
            method.setAccessible(true);
        }
  
        // Reflects the private static fields.
        Field f[] = controller.getDeclaredFields();
        for (Field field : f) {
            fields.put(field.getName(), field);
            field.setAccessible(true);
        }
    }
}

//@author A0110546R
public class IntegrationSimpleTest extends Application {
    private static final Object[] EMPTY = {};
    static UIComponent uiComponent;
    static TaskManager taskManager = new TaskManager();
    static DataStorage dataStorage = new DataStorage("Todo.test.json");
    
    // This is used to set up integration testing.
    // This method will always run once before the tests begin.
    @BeforeClass
    public static void initializeIntegrationTest() throws Exception {
        try {
            ControllerReflector.reflect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Resets the ArrayList of Tasks to empty.
        ArrayList<Task> emptyTasks = new ArrayList<Task>();
        taskManager.initializeList(emptyTasks);
        dataStorage.saveTasks(emptyTasks);
        
        // Runs the setup methods to initialize other components.
        ControllerReflector.methods.get("setup").invoke(null, EMPTY);
        
        // Replaces the two static fields with our own stubs.
        ControllerReflector.fields.get("taskManager").set(null, taskManager);
        ControllerReflector.fields.get("dataStorage").set(null, dataStorage);
        
        // Run the JavaFX thread in the background.
        Thread t = new Thread("JavaFX Background") {
            @Override
            public void run() {
                launch(IntegrationSimpleTest.class);
            }
        };
        t.setDaemon(true);
        t.start();
        
        // Sleeps every one second until uiComponent is set and ready for use.
        int i = 0;
        while (uiComponent == null && i < 3) {
            i++;
            Thread.sleep(1000);
        }
    }
    
    // This method will be run once after each test.
    @After
    public void clearTasks() {
        // Resets the ArrayList of Tasks to empty.
        ArrayList<Task> emptyTasks = new ArrayList<Task>();
        taskManager.initializeList(emptyTasks);
        dataStorage.saveTasks(emptyTasks);
    }
    
    // Method to start JavaFX stage during tests.
    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerReflector.fields.get("uiComponent").set(null, new UIComponent());
        uiComponent = (UIComponent) ControllerReflector.fields.get("uiComponent").get(null);
        uiComponent.showStage(primaryStage);
    }

    @Test
    public void testAdd() {
        String commandInput = "add [first wavewave]";
        Controller.runCommandInput(commandInput);

        assertEquals(taskManager.getList().size(), 1);
    }
    
    @Test
    public void testAddEmpty() {
        String commandInput = "add []";
        Controller.runCommandInput(commandInput);
        
        assertEquals(taskManager.getList().size(), 0); // We have not fixed this.
    }

}
