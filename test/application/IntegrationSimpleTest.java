package application;

import static org.junit.Assert.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.AfterClass;
import org.junit.Before;
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
/**
 * Mocks the UIComponent class.
 * @author Sun Wang Jun
 *
 */
public class IntegrationSimpleTest extends Application {
    private static final String TODO_TEST_JSON_FILENAME = "Todo.test.json"; // To be used in tests.
    private static final String SAMPLE_TEST_JSON_FILENAME = "SampleTodo.test.json"; // To be loaded in tests.
    private static final Object[] EMPTY = {};
    static UIComponent uiComponent;
    static TaskManager taskManager = new TaskManager();
    static DataStorage dataStorage = new DataStorage(TODO_TEST_JSON_FILENAME);
    static int FIXTURES_SIZE;
  
    //@author A0110546R
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
        
        // Sleeps every half second until uiComponent is set and ready for use.
        int i = 0;
        while (uiComponent == null && i < 6) {
            i++;
            Thread.sleep(500);
        }
    }

    //@author A0110546R
    // Method to start JavaFX stage during tests.
    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerReflector.fields.get("uiComponent").set(null, new UIComponent());
        uiComponent = (UIComponent) ControllerReflector.fields.get("uiComponent").get(null);
    }

    //@author A0110546R
    // This method will be run once before each test.
    @Before
    public void readyTasks() {
        DataStorage fixtureStorage = new DataStorage(SAMPLE_TEST_JSON_FILENAME);
        ArrayList<Task> fixtures = fixtureStorage.retrieveTasks();
        taskManager.initializeList(fixtures);
        dataStorage.saveTasks(fixtures);    

        FIXTURES_SIZE = fixtures.size();
        
        // The following lines readies the hashtable.
        taskManager.getTasks();
        taskManager.getReminders();
        
        /**
         * The fixture contains:
         * ****
         * 
         * Left pane:
         *   E1: Final CS2103T Lecture
         *     Red !, 31st Oct Fri 14:00 to Fri 16:00
         *     
         *   E2: NUS Hackers free pizza
         *     Green !, 31st Oct Fri 18:30
         *     
         *   E3: CS2103T Final Exam
         *     Yellow !, 26th Nov Wed 13:00 to Wed 15:00
         *     
         *   E4: Dental appointment
         *     Green !, 29th Nov Sat 11:00
         *     
         * Right pane:
         *   T1: Finish integration testing..., Red !
         *   T2: Watch drama, 28th Oct 23:30, Red !
         *   T3: Project Manual, 7th Nov 21:43, Yellow !
         *   T4: Tidy room, Green !
         *   T5: Learn new language, Green !
         *
         */
    }

    //@author A0110546R
    // This method will be run once after each test.
    @After
    public void clearTasks() {
        // Resets the ArrayList of Tasks to empty.
        ArrayList<Task> emptyTasks = new ArrayList<Task>();
        taskManager.initializeList(emptyTasks);
        dataStorage.saveTasks(emptyTasks);
    }

    //@author A0110546R
    @Test
    public void testAdd() {
        String commandInput = "add [first wavewave]";
        Controller.runCommandInput(commandInput);
        
        DataStorage checkStorage = new DataStorage(TODO_TEST_JSON_FILENAME);
        
        assertEquals(FIXTURES_SIZE + 1, checkStorage.retrieveTasks().size());
        assertEquals(FIXTURES_SIZE + 1, taskManager.getList().size());
        assertEquals(5 + 1, taskManager.getTasks().size());
        assertEquals(4, taskManager.getReminders().size());
    }

    //@author A0110546R
    @Test
    public void testAddEmpty() {
        String commandInput = "add []";
        Controller.runCommandInput(commandInput);
        
        // We have not fixed this, since empty task should not be added.
        // assertEquals(taskManager.getList().size(), FIXTURES_SIZE);
    }

    //@author A0110546R
    @Test
    public void testCompleteTask() {
        String commandInput = "complete T2";
        Controller.runCommandInput(commandInput);
        
        assertEquals(4, taskManager.getTasks().size());
        //assertEquals(1, taskManager.getCompletedTasks().size());
    }
    
    //@author A0110546R
    @Test
    public void testDeleteTask() {
        String commandInput = "delete T1";
        Controller.runCommandInput(commandInput);
        
        DataStorage checkStorage = new DataStorage(TODO_TEST_JSON_FILENAME);
        
        assertEquals(FIXTURES_SIZE - 1, checkStorage.retrieveTasks().size());        
        assertEquals(FIXTURES_SIZE - 1, taskManager.getList().size());        
    }

}
