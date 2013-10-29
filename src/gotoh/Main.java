package gotoh;

import java.util.HashMap;

import gotoh.Substitutionmatrix;
import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Switch;


public class Main {
	public static void main(String[] args) {
		Configuration config = readArgs(args);
		String[][] pairs = FileUtils.readPairs(config.pairs);
		HashMap<String, Sequence> seqLib = FileUtils.readSeqLib(config.seqlib);
		Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(config.matrix);

		for (String[] pair : pairs) {
			Sequence seq1 = seqLib.get(pair[0]);
			Sequence seq2 = seqLib.get(pair[1]);
			Gotoh gotoh;
			if (config.mode.equals("global")) {
				gotoh = new GlobalGotoh(seq1, seq2, matrix, config.gapOpen, config.gapExtend);
			} else if (config.mode.equals("local")) {
				gotoh = new LocalGotoh(seq1, seq2, matrix, config.gapOpen, config.gapExtend);
			} else {
				gotoh = new FreeshiftGotoh(seq1, seq2, matrix, config.gapOpen, config.gapExtend);
			}
			Alignment ali = gotoh.runAlignment();
			System.out.println(">" + seq1.getID() + " " + seq2.getID() + " " + ali.maxScore);
			if (config.printali) {
				gotoh.backtrack(ali);
				System.out.println(gotoh.seq1.getID() + ": " + ali.aliSeq1);
				System.out.println(gotoh.seq2.getID() + ": " + ali.aliSeq2);
			}
		}
	}

	private static Configuration readArgs(String[] args) {
		JSAP jsap = new JSAP();
        FlaggedOption pairs = new FlaggedOption("pairs")
        	.setRequired(true)
        	.setShortFlag(JSAP.NO_SHORTFLAG)
        	.setLongFlag("pairs");

        FlaggedOption seqlib = new FlaggedOption("seqlib")
        	.setRequired(true)
        	.setShortFlag(JSAP.NO_SHORTFLAG)
        	.setLongFlag("seqlib");

        FlaggedOption matrix = new FlaggedOption("matrix")
        	.setRequired(false)
        	.setShortFlag('m')
        	.setLongFlag("matrix")
        	.setDefault("dayhoff");

        FlaggedOption go = new FlaggedOption("go")
	    	.setRequired(false)
	    	.setShortFlag(JSAP.NO_SHORTFLAG)
	    	.setLongFlag("go")
	    	.setDefault("-12");

        FlaggedOption ge = new FlaggedOption("ge")
	    	.setRequired(false)
	    	.setShortFlag(JSAP.NO_SHORTFLAG)
	    	.setLongFlag("ge")
	    	.setDefault("-1");

        FlaggedOption mode = new FlaggedOption("mode")
	    	.setRequired(false)
	    	.setShortFlag(JSAP.NO_SHORTFLAG)
	    	.setLongFlag("mode")
	    	.setDefault("freeshift");

        FlaggedOption printmatrices = new FlaggedOption("printmatrices")
	    	.setRequired(false)
	    	.setShortFlag(JSAP.NO_SHORTFLAG)
	    	.setLongFlag("printmatrices")
	    	.setDefault("none");

        Switch printcheck = new Switch("check")
			.setShortFlag(JSAP.NO_SHORTFLAG)
			.setLongFlag("check");

        Switch printali = new Switch("printali")
			.setShortFlag(JSAP.NO_SHORTFLAG)
			.setLongFlag("printali");

        Configuration configuration = null;
        try {
        	jsap.registerParameter(pairs);
        	jsap.registerParameter(seqlib);
        	jsap.registerParameter(go);
        	jsap.registerParameter(ge);
        	jsap.registerParameter(mode);
        	jsap.registerParameter(printmatrices);
        	jsap.registerParameter(printcheck);
        	jsap.registerParameter(printali);
        	jsap.registerParameter(matrix);
        	JSAPResult config = jsap.parse(args);

        	if (!config.success()) {
        		System.out.print(ERROR_MESSAGE);
        		System.exit(1);
        	}

        	configuration = new Configuration(config.getString("pairs"), config.getString("seqlib"), config.getString("matrix"),
        			Double.parseDouble(config.getString("go")), Double.parseDouble(config.getString("ge"))
        			, config.getString("mode"), config.getBoolean("printali"), config.getString("printmatrices"), config.getBoolean("check"));
        } catch (JSAPException ex) {
        	System.out.println(ex);
        	System.exit(1);
        }

        return configuration;
	}
	//TODO Error Message
	private static final String ERROR_MESSAGE = "java -jar train.jar --db <dssp-file> --method <gor1|gor3|gor4> --model <model-file>\nOptions:\n" +
			"\t--db <dssp-file>\tpath to training file\n\t--method <gor1|gor3|gor4>\tmethod\n\t--model <model file>\t" +
			"model file output\n";
}
