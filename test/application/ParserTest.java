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
        //invalid command, no taskID after Edit
        Object input3 = "Edit";
        // invalid command
        Object input4 = "exit";
        
        String output1 = (String)method.invoke(test,input1); 
        String output2 = (String)method.invoke(test,input2);
        String output3 = (String)method.invoke(test, input3);
        String output4 = (String)method.invoke(test, input4);
        
        assertEquals("drink tea",output1);
        assertEquals("make cakes",output2);
        assertEquals("",output3);
        assertEquals("",output4);
        
    }
    
  //@author A0090971Y    
    @Test
    public void testParsePriority() throws NoSuchMethodException,
    IllegalAccessException, InvocationTargetException {
        Method method = Parser.class.getDeclaredMethod("parsePriority", String.class);
        method.setAccessible(true);
        
        Object input1 = "Add drink tea !!!";
        //test case without priority
        Object input2 = "Edit write responses ";
        Object input3 = "Add !!! book tickets !!!!!!!!!!";
        
        int output1 = (int)method.invoke(test,input1);
        int output2 = (int)method.invoke(test,input2);
        int output3 = (int)method.invoke(test, input3);
        assertEquals(3,output1);
        assertEquals(0,output2);
        assertEquals(13,output3);
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
        // test case for invalid command
        Object input4 = "go home";
        
        int output1 = (int)method.invoke(test,input1);
        int output2 = (int)method.invoke(test,input2);
        int output3 = (int)method.invoke(test, input3);
        int output4 = (int)method.invoke(test, input4);
        
        assertEquals(2,output1);
        assertEquals(4,output2);
        assertEquals(-1,output3);
        assertEquals(-1,output3);
    }
}
