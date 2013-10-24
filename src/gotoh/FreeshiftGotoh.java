package gotoh;

public class FreeshiftGotoh extends Gotoh {
	public FreeshiftGotoh(Sequence seq1, Sequence seq2, Substitutionmatrix subMatrix, double gapOpen, double gapExtend){
		super(seq1, seq2, subMatrix, gapOpen, gapExtend);
		initA();
	}

	public void initA() {
		int gapcost = gapOpen;
		for (int i = 1; i < matrixA.length; i++) {
			gapcost += gapExtend;
			matrixA[i][0] = gapcost;
		}
		gapcost = gapOpen;
		for (int j = 1; j < matrixA[0].length; j++) {
			gapcost += gapExtend;
			matrixA[0][j] = gapcost;
		}
	}

	public int getMaxValue(int x, int y) {
		return Math.max(matrixA[x][y], Math.max(matrixI[x][y], matrixD[x][y]));
	}

	public double getAlignmentScore() {
		int maxScore = Integer.MIN_VALUE;
		for (int i = 0; i < matrixA.length; i++) {
			maxScore = Math.max(maxScore, matrixA[i][matrixA[i].length - 1]);
		}
		for (int j = 0; j < matrixA[0].length; j++) {
			maxScore = Math.max(maxScore, matrixA[matrixA.length - 1][j]);
		}
		return maxScore / (double) submatrix.multiplicationFactor;
	}
}
