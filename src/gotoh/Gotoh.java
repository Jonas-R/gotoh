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
			int gapOpen, int gapExtend) {
		this.seq1 = seq1;
		this.seq2 = seq2;
		this.submatrix = submatrix;
		this.gapOpen = gapOpen;
		this.gapExtend = gapExtend;

		matrixA = new int[seq1.length()][seq2.length()];
		matrixI = new int[seq1.length()][seq2.length()];
		matrixD = new int[seq1.length()][seq2.length()];

		for (int i = 0; i < matrixI.length; i++) {
			matrixI[0][i] = Integer.MIN_VALUE;
			matrixD[i][0] = Integer.MIN_VALUE;
		}
	}

	public void fillMatrixI(int i, int j) {
		int v1 = matrixA[i-1][j] + gapOpen + gapExtend;
		int v2 = matrixI[i-1][j] + gapExtend;
		matrixI[i][j] = Math.max(v1, v2);
	}

	public void fillMatrixD(int i, int j) {
		int v1 = matrixA[i][j-1] + gapOpen + gapExtend;
		int v2 = matrixD[i][j-1] + gapExtend;
		matrixD[i][j] = Math.max(v1, v2);
	}

}
