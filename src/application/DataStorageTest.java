package application;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.joda.time.DateTime;

import org.junit.Test;

public class DataStorageTest {
	
	DataStorage test;

	@Test
	public void test1() {
		test = new DataStorage("Test.json");
		ArrayList<Task> testArray = new ArrayList<Task>();
		
		
	}

}
