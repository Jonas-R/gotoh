package gotoh;

public abstract class Gotoh {
	protected Sequence seq1;
	protected Sequence seq2;

	protected Substitutionmatrix submatrix;

	protected int[][] matrixA;
	protected int[][] matrixD;
	protected int[][] matrixI;

	protected int gapOpen;
	protected int gapExtend;

	public Gotoh(Sequence seq1, Sequence seq2, Substitutionmatrix submatrix,
			double gapOpen, double gapExtend) {
		this.seq1 = seq1;
		this.seq2 = seq2;
		this.submatrix = submatrix;
		//TODO Fix this for arbitrary precision
		this.gapOpen = (int) (gapOpen * submatrix.getMultiplicationFactor());
		this.gapExtend = (int) (gapExtend * submatrix.getMultiplicationFactor());

		matrixA = new int[seq1.length() + 1][seq2.length() + 1];
		matrixI = new int[seq1.length() + 1][seq2.length() + 1];
		matrixD = new int[seq1.length() + 1][seq2.length() + 1];

		for (int i = 0; i < matrixD.length; i++) {
			matrixD[i][0] = Integer.MIN_VALUE + 1000;
		}
		for (int j = 0; j < matrixI[0].length; j++) {
			matrixI[0][j] = Integer.MIN_VALUE + 1000;
		}
	}

	public Alignment runAlignment() {
		fillMatrices();
		return null;
	}

	public void fillMatrices() {
		for (int i = 1; i < matrixA.length; i++) {
			for (int j = 1; j < matrixA[0].length; j++) {
				fillMatrixI(i,j);
				fillMatrixD(i,j);
				fillMatrixA(i,j);
			}
		}
	}

	public void fillMatrixA(int i, int j) {
		/*System.out.println(seq1.get(i-1));
		System.out.println(seq2.get(j-1));
		System.out.println(submatrix.matrix[seq1.get(i-1)][seq2.get(j-1)]);
		System.out.println(matrixD[i][j]);
		System.out.println(matrixI[i][j]);*/
		matrixA[i][j] = Math.max(matrixA[i-1][j-1] + submatrix.matrix[seq1.get(i-1)][seq2.get(j-1)], Math.max(matrixD[i][j], matrixI[i][j]));
	}

	public void fillMatrixI(int i, int j) {
		int v1 = matrixA[i-1][j] + gapOpen + gapExtend;
		//int v2;
		//if (matrixI[i-1][j] != Integer.MIN_VALUE) {
			int v2 = matrixI[i-1][j] + gapExtend;
		//} else { v2 = Integer.MIN_VALUE; }
		matrixI[i][j] = Math.max(v1, v2);
	}

	public void fillMatrixD(int i, int j) {
		int v1 = matrixA[i][j-1] + gapOpen + gapExtend;
		//int v2;
		//if (matrixD[i][j-1] != Integer.MIN_VALUE) {
			int v2 = matrixD[i][j-1] + gapExtend;
		//} else { v2 = Integer.MIN_VALUE; }
		matrixD[i][j] = Math.max(v1, v2);
	}

	abstract double getAlignmentScore();
}
