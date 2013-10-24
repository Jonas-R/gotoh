package alignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	public static Substitutionmatrix readSubstitutionMatrix(String path) {
		List<String> lines = readLines(path);

		double[][] doubleMatrix = new double[20][20];
		int line_count = 0;
		String rowNames, colNames;
		for (String line : lines) {
			if (line.startsWith("MATRIX")) {
				String[] tokens = line.split(regex);

				line_count++;
			}
		}
	}

	private static List<String> readLines(String path) {
		ArrayList<String> lines = new ArrayList<String>();

		//open file
		File file = new File(path);

		//Error if not readable
		if (!file.canRead()) {
			System.err.println("File " + file.getAbsolutePath() + " could not be read!");
			System.exit(1);
		}

		BufferedReader inputStream = null;
		//Return lines
		try {
			inputStream = new BufferedReader(new FileReader(file));
			String line;
			while((line = inputStream.readLine()) != null)  {
				lines.add(line);
			}

			inputStream.close();
		} catch (FileNotFoundException ex) {
			System.err.println(file.getAbsolutePath() + " not found!");
			System.exit(1);
		} catch (IOException ex) {
			System.err.println(ex);
			System.exit(1);
		}

		return lines;
	}
}
