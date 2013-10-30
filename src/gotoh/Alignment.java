package gotoh;

public class Alignment {
	double maxScore;
	int xMax;
	int yMax;
	int xStart;
	int yStart;
	String aliSeq1;
	String aliSeq2;

	public Alignment(double maxScore, int xMax, int yMax) {
		this.maxScore = maxScore;
		this.xMax = xMax;
		this.yMax = yMax;
		aliSeq1 = "";
		aliSeq2 = "";
	}

	public void addAlignment(String aliSeq1, String aliSeq2, int xStart, int yStart) {
		this.aliSeq1 = aliSeq1;
		this.aliSeq2 = aliSeq2;
		this.xStart = xStart;
		this.yStart = yStart;
	}
}
