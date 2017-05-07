package hw9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Trey CSV class. You can create a CSV file Or read and display it.
 */
public class CSV {

	/**
	 * Will write the CSV file. Loops through the array list and writes the
	 * corresponding values.
	 * 
	 * @param arrayList
	 */
	public static void writeCSVFile(ArrayList<String> arrayList) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File("MathGame.csv"), "UTF-8");
			// Loop through CSV file that is an array list
			for (String info : arrayList) {
				pw.write(info + "\n\n");
			}
			pw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read in the old csv file and add it to displayData word by word
	 */
	public static void readCSVFile(String fileName) {
		File file = new File(fileName);
		Scanner in;
		try {
			in = new Scanner(file);
			while (in.hasNextLine()) {
				String line = in.nextLine();
				displayData(line);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Sorry, that file (game) does not exist yet. You should play one!");
		}

	}

	/**
	 * Displays the data given from readData Formats CSV file properly
	 * 
	 * @param line
	 *            - line given from readCSVFile
	 */
	public static void displayData(String line) {
		String[] tokens = line.split(",");
		for (String words : tokens) {
			System.out.printf("%20s", words);
		}
		System.out.println();
	}

}
