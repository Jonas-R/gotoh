package gotoh;

public class Configuration {

    public String pairs;
    public String seqlib;
    public String matrix;
    public double gapOpen;
    public double gapExtend;
    public int multiplicationFactor;
    public String mode;
    public boolean printali;
    public String printmatrices;
    public boolean check;
    public String ignore;

    public Configuration(String pairs, String seqlib, String matrix,
            double gapOpen, double gapExtend, int multiplicationFactor, String mode, boolean printali,
            String printmatrices, boolean check, String ignore) {
        this.pairs = pairs;
        this.seqlib = seqlib;
        this.matrix = matrix;
        this.gapOpen = gapOpen;
        this.gapExtend = gapExtend;
        this.mode = mode;
        this.printali = printali;
        this.printmatrices = printmatrices;
        this.check = check;
        this.ignore = ignore;
    }
}
