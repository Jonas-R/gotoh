package alignment;

public class Substitutionmatrix {
	public final int[][] matrix;
	public final int multiplicationFactor;

	private final String rowNames;
	private final String columnNames;

	public Substitutionmatrix(int[][] matrix, int multiplicationFactor, String rowNames, String columnNames) {
		this.matrix = matrix;
		this.multiplicationFactor = multiplicationFactor;
		this.rowNames = rowNames;
		this.columnNames = columnNames;
	}

	public int getValue (char row, char column) {
		return matrix[rowNames.indexOf(row)][columnNames.indexOf(column)];
	}
}
