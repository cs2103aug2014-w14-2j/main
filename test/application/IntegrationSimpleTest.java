package application;

import static org.junit.Assert.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.BeforeClass;
import org.junit.Test;

import UI.UIComponent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ControllerReflector {
    private static final Object[] EMPTY = {};

    public static Hashtable<String, Method> methods = new Hashtable<String, Method>();
    public static Hashtable<String, Field> fields = new Hashtable<String, Field>();

    public static void reflect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> controller = Controller.class;
  
        Method m[] = controller.getDeclaredMethods();
        for (Method method : m) {
            methods.put(method.getName(), method);
            method.setAccessible(true);
        }
  
        Field f[] = controller.getDeclaredFields();
        for (Field field : f) {
            fields.put(field.getName(), field);
            field.setAccessible(true);
        }
    }
}

public class IntegrationSimpleTest extends Application {
    private static final Object[] EMPTY = {};
    UIComponent uiComponent;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            ControllerReflector.reflect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ControllerReflector.methods.get("setup").invoke(null, EMPTY);
        new Thread("JavaFX Background") {
            public void run() {
                launch();
            }
        };
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerReflector.fields.get("uiComponent").set(null, new UIComponent());
        uiComponent = (UIComponent) ControllerReflector.fields.get("uiComponent").get(null);
        uiComponent.showStage(primaryStage);
    }

    @Test
    public void testAdd() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final String commandInput = "add [wavewave2]";
        System.out.println("before taskmanager");
        // Controller.main(new String[1]);
        final TaskManager t = (TaskManager) ControllerReflector.fields.get("taskManager").get(null);
        System.out.println("after taskmanager");
        System.out.println("runlater");
        Controller.runCommandInput(commandInput);

        assertEquals(t.getList().size(), 5);
    }

}
