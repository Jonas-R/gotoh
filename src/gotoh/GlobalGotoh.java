package gotoh;

public class GlobalGotoh extends Gotoh {
	public GlobalGotoh(Sequence seq1, Sequence seq2, Substitutionmatrix subMatrix, double gapOpen, double gapExtend){
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
		return Math.max(matrixA[x-1][y-1] + submatrix.matrix[seq1.get(x-1)][seq2.get(y-1)], Math.max(matrixD[x][y], matrixI[x][y]));
	}

	public Alignment getAlignmentScore() {
		double maxScore = matrixA[matrixA.length-1][matrixA[0].length-1] / (double) submatrix.multiplicationFactor;
		return new Alignment(maxScore, matrixA.length - 1, matrixA[0].length - 1);
	}
	
}
