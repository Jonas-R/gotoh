package alignment;

public class GlobalGotoh extends Gotoh {
	public GlobalGotoh(Sequence seq1, Sequence seq2, Substitutionmatrix subMatrix, int gapOpen, int gapExtend){
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
}
