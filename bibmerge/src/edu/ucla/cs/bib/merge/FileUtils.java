package edu.ucla.cs.bib.merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
	public static void writeStringToFile(String s, String path) {
		try {
			File f = new File(path);
			FileWriter w = new FileWriter(f, false);
			// increase the buffer size since we often need to write big files
			BufferedWriter writer = new BufferedWriter(w, 1048576);
			writer.write(s);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readFileToString(String path){
		String content = "";
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))){
			String line = null;
			while((line = br.readLine()) != null) {
				content += line + System.lineSeparator();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return content;
	}
}
