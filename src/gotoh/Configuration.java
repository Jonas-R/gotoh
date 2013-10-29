package gotoh;

public class Configuration {
	String pairs;
	String seqlib;
	String matrix;
	double gapOpen;
	double gapExtend;
	int multiplicationFactor;
	String mode;
	boolean printali;
	String printmatrices;
	boolean check;

	public Configuration(String pairs, String seqlib, String matrix,
			double gapOpen, double gapExtend, int multiplicationFactor, String mode, boolean printali,
			String printmatrices, boolean check) {
		this.pairs = pairs;
		this.seqlib = seqlib;
		this.matrix = matrix;
		this.gapOpen = gapOpen;
		this.gapExtend = gapExtend;
		this.mode = mode;
		this.printali = printali;
		this.printmatrices = printmatrices;
		this.check = check;
	}
}
