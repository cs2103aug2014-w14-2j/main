package data;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.IOException;

//@author A0115864B
/**
 * Unit test for class ConfigManager
 * 
 *
 */
public class ConfigManagerTest {

    ConfigManager test;
    
    //@author A0115864B
    @Test
    public void testGetConfig() {
        test = new ConfigManager("configTest.json");
        assertEquals(1, test.getHomeViewType());
    }
    
    //@author A0115864B
    @Test
    public void testSetConfig() {
        test = new ConfigManager("configOutput.json");
        test.setHomeViewType(4);
        
        byte b1 = 0;
        byte b2 = 1;
        try {
            FileInputStream f1 = new FileInputStream("ConfigOutput.json");
            FileInputStream f2 = new FileInputStream("ConfigOutputExpected.json");
            DataInputStream d1 = new DataInputStream(f1);
            DataInputStream d2 = new DataInputStream(f2);
            b1 = d1.readByte();
            b2 = d2.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        assertEquals(b1, b2);
        
    }

}
