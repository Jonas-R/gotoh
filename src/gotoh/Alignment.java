package gotoh;

import java.util.List;

public class Alignment {

    public double maxScore;
    public int xMax;
    public int yMax;
    public int startOfAlignment;
    public int endOfAlignment;
    public String aliSeq1;
    public String aliSeq2;
    public double sequenceIdentity;
    public double coverage;
    public int startInSequence;
    public int endInSequence;
    //Length of Sequence 1 (without gaps and unaligned parts)
    public int lengthSeq1;

    public Alignment(double maxScore, int xMax, int yMax) {
        this.maxScore = maxScore;
        this.xMax = xMax;
        this.yMax = yMax;
        aliSeq1 = "";
        aliSeq2 = "";
    }

    public void addAlignment(String aliSeq1, String aliSeq2, int startOfAlignment, int endOfAlignment) {
        this.aliSeq1 = aliSeq1;
        this.aliSeq2 = aliSeq2;
        this.startOfAlignment = startOfAlignment;
        this.endOfAlignment = endOfAlignment;
    }

    public void calculateSequenceIdentity() {
        int identical = 0;
        for (int i = startOfAlignment; i < endOfAlignment; i++) {
            if (aliSeq1.charAt(i) == aliSeq2.charAt(i)) {
                identical++;
            }
        }
        sequenceIdentity = identical / (double) aliSeq1.substring(startOfAlignment, endOfAlignment).length();
    }

    public void calculateCoverage() {
        String seq1 = stripGaps(aliSeq1);
        String aligned_part = stripGaps(aliSeq1.substring(startOfAlignment, endOfAlignment));
        String before_aligned;
        if (startOfAlignment > 0) {
            before_aligned = stripGaps(aliSeq1.substring(0, startOfAlignment - 1));
        } else {
            before_aligned = "";
        }
        coverage = (aligned_part.length()) / (double) (seq1.length());
        startInSequence = before_aligned.length();
        endInSequence = startInSequence + aligned_part.length();
        lengthSeq1 = seq1.length();
    }

    private String stripGaps(String s) {
        String s_ = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '-') {
                s_ += s.charAt(i);
            }
        }
        return s_;
    }

    public double calculateTotalCoverage(List<Alignment> alis) {
        int alignedResidues = 0;
        for (int i = 0; i < lengthSeq1; i++) {
            for (Alignment ali : alis) {
                if (ali.startInSequence <= i && ali.endInSequence >= i) {
                    alignedResidues++;
                    break;
                }
            }
        }
        return alignedResidues / (double) lengthSeq1;
    }

    public boolean isSensibleAlignment() {
        return sequenceIdentity >= 0.4 && (endInSequence - startInSequence > 60 || coverage >= 0.6);
    }
}
