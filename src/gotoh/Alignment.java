package gotoh;

public class Alignment {
	double maxScore;
	int xMax;
	int yMax;
	int startOfAlignment;
	int endOfAlignment;
	String aliSeq1;
	String aliSeq2;

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
}
