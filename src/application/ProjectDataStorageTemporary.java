import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ProjectDataStorageTemporary {
	
	private ArrayList<String> tasks = new ArrayList<String>();
	private String filename = "tasks.txt";
	
	private void initiateFile() {
		File file = new File(filename);
		try {
			FileWriter fw = new FileWriter(file);
			fw.close();
			
		} catch (IOException e) {
			
		}
	}
	
	private void retrieveList() {
		clearList();
		try {
			FileReader file = new FileReader(filename);
			BufferedReader listBuilder = new BufferedReader(file);
			String entry;
			while ((entry = listBuilder.readLine()) != null) {
				tasks.add(entry);
			}
			listBuilder.close();
			file.close();
		} catch (IOException e) {
			
		}
	}
	
	private void saveListToFile() {
		try {
			FileWriter file = new FileWriter(filename, false);
			PrintWriter taskSave = new PrintWriter(file);
			for (int i = 0; i < tasks.size(); i++) {
				taskSave.println(tasks.get(i));
			}
			taskSave.close();
			file.close();
		} catch (IOException e) {
			
		}
	}
	
	private void clearList() {
		tasks.clear();
	}

}
