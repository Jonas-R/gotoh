package gotoh;

public class LocalGotoh extends Gotoh {
	public LocalGotoh(Sequence seq1, Sequence seq2, Substitutionmatrix subMatrix, double gapOpen, double gapExtend){
		super(seq1, seq2, subMatrix, gapOpen, gapExtend);
		initA();
	}

	public void initA() {
		for (int i = 0; i < matrixA.length; i++) {
			matrixA[0][i] = 0;
			matrixA[i][0] = 0;
		}
	}

	public int getMaxValue(int x, int y) {
		int max = Math.max(matrixA[x][y], Math.max(matrixI[x][y], matrixD[x][y]));
		return (max == 0) ? 1 : max;
	}

	public double getAlignmentScore() {
		int maxScore = Integer.MIN_VALUE;
		for (int i = 0; i < matrixA.length; i++) {
			for (int j = 0; j < matrixA.length; j++) {
				maxScore = Math.max(maxScore, matrixA[i][j]);
			}
		}
		return maxScore / (double) submatrix.multiplicationFactor;
	}
}
