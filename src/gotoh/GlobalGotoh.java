package gotoh;

public class GlobalGotoh extends Gotoh {
	public GlobalGotoh(Sequence seq1, Sequence seq2, Substitutionmatrix subMatrix, double gapOpen, double gapExtend, int multiplicationFactor){
		super(seq1, seq2, subMatrix, gapOpen, gapExtend, multiplicationFactor);
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

	public void backtrack(Alignment ali) {
		int posLeftSeq1 = seq1.length();
		int posLeftSeq2 = seq2.length();
		StringBuilder alignedSeq1 = new StringBuilder();
		StringBuilder alignedSeq2 = new StringBuilder();

		int x = ali.xMax;
		int y = ali.yMax;

		while(x > 0 && y > 0){
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
				while(matrixA[x][y - gapLength] + gapOpen + gapLength * gapExtend != matrixA[x][y]) {
					if(y > gapLength){
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
				while(matrixA[x - gapLength][y] + gapOpen + gapLength * gapExtend  != matrixA[x][y]){
					if(x > gapLength){
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

		while(posLeftSeq1 > 0){
			alignedSeq1.append(seq1.getAsChar(posLeftSeq1 - 1));
			alignedSeq2.append('-');
			posLeftSeq1--;
		}
		while(posLeftSeq2 > 0){
				alignedSeq1.append('-');
				alignedSeq2.append(seq2.getAsChar(posLeftSeq2 - 1));
				posLeftSeq2--;
		}
		ali.addAlignment(alignedSeq1.reverse().toString(), alignedSeq2.reverse().toString());
	}
}
