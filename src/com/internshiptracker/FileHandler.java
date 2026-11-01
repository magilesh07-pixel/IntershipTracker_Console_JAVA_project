package com.internshiptracker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading and writing internship records to a plain text file so that
 * data persists between runs of the program.
 */
public class FileHandler {

	private static final String DATA_DIR = "data";
	private static final String FILE_PATH = DATA_DIR + File.separator + "internships.txt";

	/**
	 * Saves the full list of internships to disk, overwriting the previous file.
	 */
	public static void saveData(List<Internship> internships) {
		File dir = new File(DATA_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
			for (Internship i : internships) {
				writer.println(i.toFileLine());
			}
		} catch (IOException e) {
			System.out.println("Error saving data: " + e.getMessage());
		}
	}

	/**
	 * Loads the list of internships from disk. Returns an empty list if no file
	 * exists yet.
	 */
	public static List<Internship> loadData() {
		List<Internship> internships = new ArrayList<>();
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			return internships;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					try {
						internships.add(Internship.fromFileLine(line));
					} catch (Exception parseEx) {
						System.out.println("Skipping corrupted record: " + line);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error loading data: " + e.getMessage());
		}
		return internships;
	}
}
