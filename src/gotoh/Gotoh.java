package gotoh;

public abstract class Gotoh {

    public Sequence seq1;
    public Sequence seq2;

    public int[] intSeq1;
    public int[] intSeq2;

    public Substitutionmatrix submatrix;

    public int[][] matrixA;
    public int[][] matrixD;
    public int[][] matrixI;

    public int gapOpen;
    public int gapExtend;

    public Gotoh(Sequence seq1, Sequence seq2, Substitutionmatrix submatrix,
            double gapOpen, double gapExtend, int multiplicationFactor) {
        this.seq1 = seq1;
        this.seq2 = seq2;
        this.intSeq1 = seq1.sequence;
        this.intSeq2 = seq2.sequence;
        this.submatrix = submatrix;
        if (multiplicationFactor <= submatrix.getMultiplicationFactor()) {
            this.gapOpen = (int) (gapOpen * submatrix.getMultiplicationFactor());
            this.gapExtend = (int) (gapExtend * submatrix.getMultiplicationFactor());
        } else {
            this.gapOpen = (int) (gapOpen * multiplicationFactor);
            this.gapExtend = (int) (gapExtend * multiplicationFactor);
            this.submatrix.applyMultiplicationFactor(multiplicationFactor / this.submatrix.getMultiplicationFactor());
        }

        matrixA = new int[seq1.length() + 1][seq2.length() + 1];
        matrixI = new int[seq1.length() + 1][seq2.length() + 1];
        matrixD = new int[seq1.length() + 1][seq2.length() + 1];
        for (int[] matrixD1 : matrixD) {
            matrixD1[0] = Integer.MIN_VALUE + 1000;
        }
        for (int j = 0; j < matrixI[0].length; j++) {
            matrixI[0][j] = Integer.MIN_VALUE + 1000;
        }

    }

    public Alignment runAlignment() {
        fillMatrices();
        return getAlignmentScore();
    }

    public void fillMatrices() {
        for (int i = 1; i < matrixA.length; i++) {
            for (int j = 1; j < matrixA[0].length; j++) {
                fillMatrixI(i, j);
                fillMatrixD(i, j);
                fillMatrixA(i, j);
            }
        }
    }

    public void fillMatrixA(int i, int j) {
        matrixA[i][j] = getMaxValue(i, j);
    }

    public void fillMatrixI(int i, int j) {
        matrixI[i][j] = Math.max(matrixA[i - 1][j] + gapOpen + gapExtend, matrixI[i - 1][j] + gapExtend);
    }

    public void fillMatrixD(int i, int j) {
        matrixD[i][j] = Math.max(matrixA[i][j - 1] + gapOpen + gapExtend, matrixD[i][j - 1] + gapExtend);
    }

    public double checkScore(Alignment ali) {
        int score = 0;
        for (int i = ali.startOfAlignment; i < ali.endOfAlignment; i++) {
            if (ali.aliSeq1.charAt(i) == '-') {
                score += gapOpen + gapExtend;
                i++;
                while (ali.aliSeq1.charAt(i) == '-') {
                    score += gapExtend;
                    i++;
                }
            } else if (ali.aliSeq2.charAt(i) == '-') {
                score += gapOpen + gapExtend;
                i++;
                while (ali.aliSeq2.charAt(i) == '-') {
                    score += gapExtend;
                    i++;
                }
            } else {
                score += submatrix.getValue(ali.aliSeq1.charAt(i), ali.aliSeq2.charAt(i));
            }
        }
        return (score / (double) submatrix.getMultiplicationFactor());
    }

    abstract public Alignment getAlignmentScore();

    abstract public int getMaxValue(int x, int y);

    abstract public void backtrack(Alignment ali);
}
