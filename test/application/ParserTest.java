package application;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.NoSuchMethodException;
import java.util.ArrayList;

//@author A0090971Y
import org.junit.Test;

import Parser.Parser;

public class ParserTest {
    
    Parser test = new Parser();
    
  //@author A0090971Y
    @Test  
    public void testParseTaskDesc() throws NoSuchMethodException,     
    InvocationTargetException, IllegalAccessException {
       
        Method method = Parser.class.getDeclaredMethod("parseTaskDesc", String.class,String.class);
        method.setAccessible(true);
        
        Object input1_1 = "Add [drink tea]";
        Object input1_2 = "ADD";
        Object input2_1 = "EDIT T2 [make cakes]";
        Object input2_2 = "EDIT";
        Object input3_1 = "Edit";
        Object input3_2 = "EDIT";
        Object input4_1 = "exit";
        Object input4_2 = "EXIT";
        
        String output1 = (String)method.invoke(test,input1_1,input1_2); 
        String output2 = (String)method.invoke(test,input2_1,input2_2);
        String output3 = (String)method.invoke(test, input3_1,input3_2);
        String output4 = (String)method.invoke(test, input4_1,input4_2);
        
        assertEquals("drink tea",output1);
        assertEquals("make cakes",output2);
        assertEquals(null,output3);
        assertEquals("",output4);
        
    }
  
    
  //@author A0090971Y
    @Test
    public void testParsePriority() throws NoSuchMethodException,
    IllegalAccessException, InvocationTargetException {
        Method method = Parser.class.getDeclaredMethod("parsePriority", String.class,String.class);
        method.setAccessible(true);
        
        Object input1_1 = "Add [drink tea] !!!";
        Object input1_2 = "drink tea";
        Object input2_1 = "Edit [write responses!!] ";
        Object input2_2 = "write responses!!";
        Object input3_1 = "Add [!!! book tickets] !!!!!!!!!!";
        Object input3_2 = "!!! book tickets";
        
        int output1 = (int)method.invoke(test,input1_1,input1_2);
        int output2 = (int)method.invoke(test,input2_1,input2_2);
        int output3 = (int)method.invoke(test, input3_1,input3_2);
        assertEquals(3,output1);
        assertEquals(0,output2);
        assertEquals(10,output3);
    }
   
  //@author A0090971Y
    @Test
    public void testParseTaskIDs() throws NoSuchMethodException,
    IllegalAccessException, InvocationTargetException {
        Method method = Parser.class.getDeclaredMethod("parseTaskIDs", String.class);
        method.setAccessible(true);
        
        Object input1 = "DELETE e2 t1";
        Object input2 = "Edit T4 [write responses] 2pm";
        Object input3 = "ADD [do laundry]";
        // test case for invalid command
        Object input4 = "go home";
        
        ArrayList<String> output1 = (ArrayList<String>)method.invoke(test,input1);
        ArrayList<String> output2 = (ArrayList<String>)method.invoke(test,input2);
        ArrayList<String> output3 = (ArrayList<String>)method.invoke(test, input3);
        ArrayList<String> output4 = (ArrayList<String>)method.invoke(test, input4);
        
        ArrayList<String> expectedOutput1 =new ArrayList<String>();
        expectedOutput1.add("E2");
        expectedOutput1.add("T1");
        
        ArrayList<String> expectedOutput2 =new ArrayList<String>();
        expectedOutput2.add("T4");
        
        ArrayList<String> expectedOutput3 =new ArrayList<String>();
        ArrayList<String> expectedOutput4 =new ArrayList<String>();
        
        assertEquals(expectedOutput1,output1);
        assertEquals(expectedOutput2,output2);
        assertEquals(expectedOutput3,output3);
        assertEquals(expectedOutput4,output3);
    }
    
}
