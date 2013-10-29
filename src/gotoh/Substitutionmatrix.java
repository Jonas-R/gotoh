package gotoh;

public class Substitutionmatrix {
	public final int[][] matrix;
	public final int multiplicationFactor;

	private final String rowNames;
	private final String colNames;

	public Substitutionmatrix(int[][] matrix, int multiplicationFactor, String rowNames, String columnNames) {
		this.matrix = matrix;
		this.multiplicationFactor = multiplicationFactor;
		this.rowNames = rowNames;
		this.colNames = columnNames;
	}

	public int getValue (char row, char column) {
		return matrix[rowNames.indexOf(row)][colNames.indexOf(column)];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ROWNAMES " + rowNames + "\n");
		sb.append("COLNAMES " + colNames + "\n");
		sb.append("MULTFACT " + multiplicationFactor + "\n");

		for (int i = 0; i < matrix.length; i++) {
			sb.append("\n");
			for (int j = 0; j < matrix[0].length; j++) {
				sb.append(matrix[i][j] + "  ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public void applyMultiplicationFactor(int multiplicationFactor) {
		int[][] matrix = new int[20][20];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = matrix[i][j] * multiplicationFactor;
			}
		}
	}

	public int getMultiplicationFactor() {
		return multiplicationFactor;
	}

	public String getRowNames() {
		return rowNames;
	}

	public String getColNames() {
		return colNames;
	}
}
