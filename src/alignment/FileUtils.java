package alignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alignment.Substitutionmatrix;

public class FileUtils {
	public static Substitutionmatrix readSubstitutionMatrix(String path) {
		List<String> lines = readLines(path);

		double[][] doubleMatrix = new double[20][20];
		int line_count = 0;
		int decimalPlaces = 0;
		String rowNames = "";
		String colNames = "";
		boolean is_symmetrical = false;

		for (String line : lines) {
			if (line.startsWith("MATRIX")) {
				String[] tokens = line.split("\\s+");
				double[] line_values = new double[20];
				if (tokens.length < doubleMatrix.length) is_symmetrical = true;
				for (int i = 0; i < tokens.length - 1;i++) {
					if (tokens[i+1].split("\\.").length > 1) {
						decimalPlaces = Math.max(tokens[i+1].split("\\.")[1].trim().length(), decimalPlaces);
					}
					line_values[i] = Double.parseDouble(tokens[i+1]);
				}
				doubleMatrix[line_count] = line_values;
				line_count++;
			}
			else if (line.startsWith("ROWINDEX"))
				rowNames = line.split("\\s+")[1];
			else if (line.startsWith("COLINDEX"))
				colNames = line.split("\\s+")[1];
		}

		int[][] matrix = new int[20][20];
		double factor = Math.pow(10, (double) decimalPlaces);
		for (int i = 0; i < doubleMatrix.length; i++) {
			for (int j = 0; j < doubleMatrix[0].length; j++) {
				matrix[i][j] = (int) (doubleMatrix[i][j] * factor);
				if (is_symmetrical) {
					matrix [j][i] = (int) (doubleMatrix[i][j] * factor);
				}
			}
		}

		return new Substitutionmatrix(matrix, (int) factor, rowNames, colNames);
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
