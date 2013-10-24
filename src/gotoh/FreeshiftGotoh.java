package gotoh;

public class FreeshiftGotoh extends Gotoh {
	public FreeshiftGotoh(Sequence seq1, Sequence seq2, Substitutionmatrix subMatrix, int gapOpen, int gapExtend){
		super(seq1, seq2, subMatrix, gapOpen, gapExtend);
	}

	public void initA() {
		int gapcost = gapOpen;
		for (int i = 0; i < matrixA.length; i++) {
			gapcost += gapExtend;
			matrixA[0][i] = gapcost;
			matrixA[i][0] = gapcost;
		}
	}

	public int getMaxValue(int x, int y) {
		return Math.max(matrixA[x][y], Math.max(matrixI[x][y], matrixD[x][y]));
	}

	public int getAlignmentScore() {
		return matrixA[matrixA.length-1][matrixA[0].length-1];
	}
}
