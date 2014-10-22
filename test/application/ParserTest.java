package application;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.NoSuchMethodException;

import org.junit.Test;

public class ParserTest {
    
    Parser test = new Parser();
    
  //@author A0090971Y
    @Test  
    public void testParseContent() throws NoSuchMethodException,     
    InvocationTargetException, IllegalAccessException {
       
        Method method = Parser.class.getDeclaredMethod("parseContent", String.class);
        method.setAccessible(true);
        
        Object input1 = "Add drink tea";
        Object input2 = "EDIT 2 make cakes";
        
        String output1 = (String)method.invoke(test,input1); 
        String output2 = (String)method.invoke(test,input2);
        
        assertEquals("drink tea",output1);
        assertEquals("make cakes",output2);
        
    }
    
  //@author A0090971Y    
    @Test
    public void testParsePriority() throws NoSuchMethodException,
    IllegalAccessException, InvocationTargetException {
        Method method = Parser.class.getDeclaredMethod("parsePriority", String.class);
        method.setAccessible(true);
        
        Object input1 = new String ("Add drink tea !!!");
        Object input2 = "Edit write responses ";
        
        int output1 = (int)method.invoke(test,input1);
        int output2 = (int)method.invoke(test,input2);
        
        assertEquals(3,output1);
        assertEquals(0,output2);
    }
    
  //@author A0090971Y
    @Test
    public void testParseTaskID() throws NoSuchMethodException,
    IllegalAccessException, InvocationTargetException {
        Method method = Parser.class.getDeclaredMethod("parseTaskID", String.class);
        method.setAccessible(true);
        
        Object input1 = "DELETE 2";
        Object input2 = "Edit 4 write responses 2pm";
        Object input3 = "ADD do laundry";
        
        int output1 = (int)method.invoke(test,input1);
        int output2 = (int)method.invoke(test,input2);
        int output3 = (int)method.invoke(test, input3);

        assertEquals(2,output1);
        assertEquals(4,output2);
        assertEquals(0,output3);
    }
}
