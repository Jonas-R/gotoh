package gotoh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileUtils {
    /*
    Read in Substitution Matrix in .mat format
    <ignore> is a String containing all chars that are assigned a score of -1 with any other pair
        (e.g. *,X etc)
    */
    private final static int IGNORE_VALUE = -1;
    public static Substitutionmatrix readSubstitutionMatrix(String path, String ignore) {
        List<String> lines = readLines(path);

        ArrayList<ArrayList<Double>> doubleMatrix = new ArrayList<>();
        int decimalPlaces = 0;
        String rowNames = "";
        String colNames = "";
        boolean is_symmetrical = false;
        int matrix_size = 0;
        for(String line : lines) {
            matrix_size = Math.max(matrix_size
                    , line.split("\\s+").length - 1);
        }

        for (String line : lines) {
            if (line.startsWith("MATRIX")) {
                String[] tokens = line.split("\\s+");
                ArrayList<Double> line_values = new ArrayList<>();
                if (tokens.length < matrix_size - 1) {
                    is_symmetrical = true;
                }
                for (int i = 0; i < tokens.length - 1; i++) {
                    if (tokens[i + 1].split("\\.").length > 1) {
                        String dec = tokens[i + 1].split("\\.")[1].trim();
                        while (dec.endsWith("0")) {
                            dec = dec.substring(0, dec.length() - 1);
                        }
                        decimalPlaces = Math.max(dec.length(), decimalPlaces);
                    }
                    line_values.add(Double.parseDouble(tokens[i + 1]));
                }
                doubleMatrix.add(line_values);
            } else if (line.startsWith("ROWINDEX")) {
                rowNames = line.split("\\s+")[1];
            } else if (line.startsWith("COLINDEX")) {
                colNames = line.split("\\s+")[1];
            }
        }
        
        int[][] matrix = 
                new int[matrix_size + ignore.length()]
                       [matrix_size + ignore.length()];
        double factor = Math.pow(10, (double) decimalPlaces);
        for (int i = 0; i < doubleMatrix.size(); i++) {
            for (int j = 0; j < doubleMatrix.get(i).size(); j++) {
                matrix[i][j] = (int) (doubleMatrix.get(i).get(j) * factor);
                if (is_symmetrical) {
                    matrix[j][i] = (int) (doubleMatrix.get(i).get(j) * factor);
                }
            }
        }
        
        //Add ignored characters
        int ignore_val = (int) (IGNORE_VALUE * factor);
        for(int i = matrix_size; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = ignore_val;
                matrix[j][i] = ignore_val;
            }
        }
        
        return new Substitutionmatrix(matrix, (int) factor
                , rowNames + ignore, colNames + ignore);
    }

    public static HashMap<String, Sequence> readSeqLib(String path) {
        List<String> lines = readLines(path);
        HashMap<String, Sequence> seqs = new HashMap<>();

        for (String line : lines) {
            String[] tokens = line.split(":");
            seqs.put(tokens[0], new Sequence(tokens[1], tokens[0]));
        }

        return seqs;
    }

    public static String[][] readPairs(String path) {
        List<String> lines = readLines(path);
        ArrayList<String[]> pairs = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            String[] pair = {tokens[0], tokens[1]};
            pairs.add(pair);
        }
        return pairs.toArray(new String[0][0]);
    }

    public static void writeFalseAlignments(String path, String content) {
        writeString(path, content);
    }

    public static void writeString(String path, String content) {
        //Open file
        Path jPath = Paths.get(path);

        //Create file if necessary
        if (!Files.exists(jPath, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createFile(jPath);
            } catch (IOException ex) {
                System.out.print(ex);
                System.exit(1);
            }
        }

        //Error if not writable
        if (!Files.isWritable(jPath)) {
            System.out.println("File " + jPath + " could not be written!");
            System.exit(1);
        }
        //Write lines
        try {
            Files.write(jPath, content.getBytes(Charset.forName("UTF-8")));
        } catch (IOException ex) {
            System.out.println(ex);
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
            while ((line = inputStream.readLine()) != null) {
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
