package gotoh;

public class LocalGotoh extends Gotoh {
	public LocalGotoh(Sequence seq1, Sequence seq2, Substitutionmatrix subMatrix, double gapOpen, double gapExtend, int multiplicationFactor){
		super(seq1, seq2, subMatrix, gapOpen, gapExtend, multiplicationFactor);
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

	public void backtrack(Alignment ali) {
		int posLeftSeq1 = seq1.length();
		int posLeftSeq2 = seq2.length();
		StringBuilder alignedSeq1 = new StringBuilder();
		StringBuilder alignedSeq2 = new StringBuilder();

		int x = ali.xMax;
		int y = ali.yMax;

		while(x > 0 && y > 0 && matrixA[x][y] > 0){
			if(matrixA[x][y] == matrixA[x-1][y-1] + submatrix.matrix[seq1.get(x-1)][seq2.get(y-1)] ){
				alignedSeq1.append(seq1.getAsChar(x-1));
				alignedSeq2.append(seq2.getAsChar(y-1));
				x--;
				y--;
				posLeftSeq1--;
				posLeftSeq2--;
			}
			else if(matrixA[x][y] == matrixD[x][y]){
				int gapLength = 1;
				alignedSeq1.append('-');
				alignedSeq2.append(seq2.getAsChar(y-1));
				while(matrixA[x][y - gapLength] + gapOpen + gapLength * gapExtend == matrixA[x][y]){
					if(y == gapLength){
						alignedSeq1.append('-');
						alignedSeq2.append(seq2.getAsChar(y - gapLength - 1));
						gapLength++;
					}
					else break;
				}
				y -= gapLength;
				posLeftSeq2 -= gapLength;
			}
			else if(matrixA[x][y] == matrixI[x][y]){
				int gapLength = 1;
				alignedSeq1.append(seq1.getAsChar(x-1));
				alignedSeq2.append('-');
				while(matrixA[x - gapLength][y]+ gapOpen + gapLength * gapExtend  == matrixA[x][y]){
					if(x - gapLength > 0){
						alignedSeq1.append(seq1.getAsChar(x - gapLength -1));
						alignedSeq2.append('-');
						gapLength++;
					}
					else break;
				}
				x -= gapLength;
				posLeftSeq1 -= gapLength;
			}
		}

		int firstPos = Math.max (x, y);
		for (int i = 0; i < firstPos; i++) {
			if ((x - i) <= 0) {
				alignedSeq1.append('-');
				alignedSeq2.append(seq2.getAsChar(y - i));
			}
			else if ((y - i) <= 0) {
				alignedSeq1.append(seq1.getAsChar(x - i));
				alignedSeq1.append('-');
			}
			else {
				alignedSeq1.append(seq1.getAsChar(x - i));
				alignedSeq2.append(seq2.getAsChar(y - i));
			}
		}
		alignedSeq1 = alignedSeq1.reverse();
		alignedSeq2 = alignedSeq2.reverse();

		int lastPos = Math.min(x, y) - 1;
		int alignmentLength = Math.max(seq1.length(), seq2.length());
		for (int i = x; i < alignmentLength; i++) {
			if (i < seq1.length()) {
				alignedSeq1.append(seq1.getAsChar(i));
			} else {
				alignedSeq1.append('-');
			}
		}
		for (int i = y; i < alignmentLength; i++) {
			if (i < seq2.length()) {
				alignedSeq2.append(seq2.getAsChar(i));
			} else {
				alignedSeq2.append('-');
			}
		}
		ali.addAlignment(alignedSeq1.toString(), alignedSeq2.toString());
	}
}
