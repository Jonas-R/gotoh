package gotoh;

public class FreeshiftGotoh extends Gotoh {
	public FreeshiftGotoh(Sequence seq1, Sequence seq2, Substitutionmatrix subMatrix, double gapOpen, double gapExtend, int multiplicationFactor){
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
		return Math.max(matrixA[x-1][y-1] + submatrix.matrix[intSeq1[x-1]][intSeq2[y-1]], Math.max(matrixD[x][y], matrixI[x][y]));
	}

	public Alignment getAlignmentScore() {
		int maxScore = Integer.MIN_VALUE;
		int xMax = 0;
		int yMax = 0;
		for (int i = 0; i < matrixA.length; i++) {
			if (matrixA[i][matrixA[i].length - 1] > maxScore) {
				maxScore = matrixA[i][matrixA[i].length - 1];
				xMax = i;
				yMax = matrixA[i].length-1;
			}
		}
		for (int j = 0; j < matrixA[0].length; j++) {
			if (matrixA[matrixA.length-1][j] > maxScore) {
				maxScore = matrixA[matrixA.length-1][j];
				xMax = matrixA.length-1;
				yMax = j;
			}
		}
		return new Alignment(maxScore / (double) submatrix.multiplicationFactor, xMax, yMax);
	}

	public void backtrack(Alignment ali) {
		StringBuilder alignedSeq1 = new StringBuilder();
		StringBuilder alignedSeq2 = new StringBuilder();

		int x = ali.xMax;
		int y = ali.yMax;

		while(x > 0 && y > 0){
			if(matrixA[x][y] == matrixA[x-1][y-1] + submatrix.matrix[intSeq1[x-1]][intSeq2[y-1]] ){
				alignedSeq1.append(seq1.getAsChar(x-1));
				alignedSeq2.append(seq2.getAsChar(y-1));
				x--;
				y--;
			}
			else if(matrixA[x][y] == matrixD[x][y]){
				int gapLength = 1;
				alignedSeq1.append('-');
				alignedSeq2.append(seq2.getAsChar(y-1));
				while(matrixA[x][y - gapLength] + gapOpen + gapLength * gapExtend != matrixA[x][y]) {
					if(y > gapLength){
						alignedSeq1.append('-');
						alignedSeq2.append(seq2.getAsChar(y - gapLength - 1));
						gapLength++;
					}
					else break;
				}
				y -= gapLength;
			}
			else if(matrixA[x][y] == matrixI[x][y]){
				int gapLength = 1;
				alignedSeq1.append(seq1.getAsChar(x-1));
				alignedSeq2.append('-');
				while(matrixA[x - gapLength][y] + gapOpen + gapLength * gapExtend  != matrixA[x][y]){
					if(x > gapLength){
						alignedSeq1.append(seq1.getAsChar(x - gapLength -1));
						alignedSeq2.append('-');
						gapLength++;
					}
					else break;
				}
				x -= gapLength;
			}
		}
		int startAlignment = x + y;
		while(x > 0){
			alignedSeq1.append(seq1.getAsChar(x - 1));
			alignedSeq2.append('-');
			x--;
		}
		while(y > 0){
				alignedSeq1.append('-');
				alignedSeq2.append(seq2.getAsChar(y - 1));
				y--;
		}

		alignedSeq1 = alignedSeq1.reverse();
		alignedSeq2 = alignedSeq2.reverse();
		int endAlignment = alignedSeq1.length();
		x = ali.xMax + 1;
		y = ali.yMax + 1;
		while (x <= seq1.length()) {
			alignedSeq1.append(seq1.getAsChar(x-1));
			alignedSeq2.append('-');
			x++;
		}
		while (y <= seq2.length()) {
			alignedSeq1.append('-');
			alignedSeq2.append(seq2.getAsChar(y-1));
			y++;
		}
		ali.addAlignment(alignedSeq1.toString(), alignedSeq2.toString(), startAlignment, endAlignment);
	}
}
