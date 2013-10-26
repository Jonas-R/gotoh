package gotoh;

public class LocalGotoh extends Gotoh {
	public LocalGotoh(Sequence seq1, Sequence seq2, Substitutionmatrix subMatrix, double gapOpen, double gapExtend){
		super(seq1, seq2, subMatrix, gapOpen, gapExtend);
		initA();
	}

	public void initA() {
		for (int i = 0; i < matrixA.length; i++) {
			matrixA[i][0] = 0;
		}
		for (int j = 0; j < matrixA[0].length; j++) {
			matrixA[0][j] = 0;
		}
	}

	public int getMaxValue(int x, int y) {
		int max = Math.max(matrixA[x-1][y-1] + submatrix.matrix[seq1.get(x-1)][seq2.get(y-1)], Math.max(matrixD[x][y], matrixI[x][y]));
		return (max < 0) ? 0 : max;
	}

	public Alignment getAlignmentScore() {
		int maxScore = Integer.MIN_VALUE;
		int xMax = 0;
		int yMax = 0;
		for (int i = 0; i < matrixA.length; i++) {
			for (int j = 0; j < matrixA[0].length; j++) {
				if (matrixA[i][j] > maxScore) {
					maxScore = 	matrixA[i][j];
					xMax = i;
					yMax = j;
				}
			}
		}
		return new Alignment (maxScore / (double) submatrix.multiplicationFactor, xMax, yMax);
	}
}
