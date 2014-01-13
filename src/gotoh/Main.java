package gotoh;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.BasicParser;


public class Main {

    public static void main(String[] args) {
        Configuration config = readArgs(args);
        String[][] pairs = FileUtils.readPairs(config.pairs);
        HashMap<String, Sequence> seqLib = FileUtils.readSeqLib(config.seqlib);
        Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(config.matrix, config.ignore);

        StringBuilder falseAlignments = new StringBuilder();
        StringBuilder output = new StringBuilder();
        for (String[] pair : pairs) {
            Sequence seq1 = seqLib.get(pair[0]);
            Sequence seq2 = seqLib.get(pair[1]);

            Gotoh gotoh;
            String formatString = "%2." + (int) Math.log10((double) matrix.multiplicationFactor) + "f";
            if (config.mode.equals("global")) {
                gotoh = new GlobalGotoh(seq1, seq2, matrix, config.gapOpen, config.gapExtend, config.multiplicationFactor);
            } else if (config.mode.equals("local")) {
                gotoh = new LocalGotoh(seq1, seq2, matrix, config.gapOpen, config.gapExtend, config.multiplicationFactor);
            } else {
                gotoh = new FreeshiftGotoh(seq1, seq2, matrix, config.gapOpen, config.gapExtend, config.multiplicationFactor);
            }
            Alignment ali = gotoh.runAlignment();
            output.append('>');
            output.append(seq1.getID());
            output.append(' ');
            output.append(seq2.getID());
            output.append(' ');
            output.append(String.format(Locale.US, formatString, ali.maxScore));
            if (config.printali) {
                gotoh.backtrack(ali);
                output.append(" ");
                ali.calculateSequenceIdentity();
                output.append(ali.sequenceIdentity);
            }
            output.append('\n');

            if (config.printali) {
                output.append(gotoh.seq1.getID());
                output.append(": ");
                output.append(ali.aliSeq1);
                output.append('\n');
                output.append(gotoh.seq2.getID());
                output.append(": ");
                output.append(ali.aliSeq2);
                output.append('\n');
            }
            if (config.check) {
                double checkScore = gotoh.checkScore(ali);
                if (checkScore - ali.maxScore > 0.01) {
                    falseAlignments.append(">").append(gotoh.seq1.getID()).append(":").append(gotoh.seq2.getID()).append(" ").append(ali.maxScore).append(" ").append(checkScore).append("\n");
                    falseAlignments.append(ali.aliSeq1).append("\n");
                    falseAlignments.append(ali.aliSeq2).append("\n");
                    falseAlignments.append("\n");
                }
            }
            switch (config.printmatrices) {
                case "txt":
                    output.append("MATRIX A");
                    output.append(printMatrix(gotoh.matrixA));
                    output.append('\n');
                    output.append("MATRIX I");
                    output.append(printMatrix(gotoh.matrixI));
                    output.append('\n');
                    output.append("MATRIX D");
                    output.append(printMatrix(gotoh.matrixD));
                    output.append('\n');
                    break;
                case "html":
                    output.append("<html><pre>");
                    output.append("MATRIX A");
                    output.append(printMatrix(gotoh.matrixA));
                    output.append('\n');
                    output.append("MATRIX I");
                    output.append(printMatrix(gotoh.matrixI));
                    output.append('\n');
                    output.append("MATRIX D");
                    output.append(printMatrix(gotoh.matrixD));
                    output.append('\n');
                    output.append("</pre></html>");
                    break;
            }
        }
        if (!(falseAlignments.length() == 0)) {
            FileUtils.writeFalseAlignments("gotoh_out_false_alignments.txt", falseAlignments.toString());
        }
        System.out.println(output);
    }

    private static StringBuilder printMatrix(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]);
                sb.append('\t');
            }
            sb.append('\n');
        }
        return sb;
    }

    private static Configuration readArgs(String[] args) {
        Options options = new Options();
        Option pairs = new Option("pairs", true, "");
        Option seqlib = new Option("seqlib", true, "");
        Option matrix = new Option("m", true, "");
        Option go = new Option("go", true, "");
        Option ge = new Option("ge", true, "");
        Option mode = new Option("mode", true, "");
        Option printmatrices = new Option("printmatrices", true, "");
        Option check = new Option("check", false, "");
        Option printali = new Option("printali", false, "");
        Option ignore = new Option("ignore", true, "");

        options.addOption(pairs);
        options.addOption(seqlib);
        options.addOption(matrix);
        options.addOption(go);
        options.addOption(ge);
        options.addOption(mode);
        options.addOption(printmatrices);
        options.addOption(check);
        options.addOption(printali);
        options.addOption(ignore);

        CommandLineParser parser = new BasicParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            boolean _check = line.hasOption("check");
            boolean _printali = line.hasOption("printali");
            String _pairs = "";
            if (line.hasOption("pairs")) {
                _pairs = line.getOptionValue("pairs");
            } else {
                System.out.println(ERROR_MESSAGE);
                System.exit(1);
            }

            String _seqlib = "";
            if (line.hasOption("seqlib")) {
                _seqlib = line.getOptionValue("seqlib");
            } else {
                System.out.println(ERROR_MESSAGE);
                System.exit(1);
            }

            String _matrix;
            if (line.hasOption("m")) {
                _matrix = line.getOptionValue("m");
            } else {
                _matrix = "dayhoff";
            }
            if (!_matrix.contains("/")) {
                _matrix = MATRIX_FOLDER + _matrix;
            }
            if (!_matrix.endsWith(".mat")) {
                _matrix += ".mat";
            }

            double _go;
            int decimalPlaces = 0;
            if (line.hasOption("go")) {
                _go = Double.parseDouble(line.getOptionValue("go"));
                if (line.getOptionValue("go").split("\\.").length > 1) {
                    decimalPlaces = Math.max(line.getOptionValue("go").split("\\.")[1].trim().length(), decimalPlaces);
                };
            } else {
                _go = -12;
            }

            double _ge;
            if (line.hasOption("ge")) {
                _ge = Double.parseDouble(line.getOptionValue("ge"));
                if (line.getOptionValue("ge").split("\\.").length > 1) {
                    decimalPlaces = Math.max(line.getOptionValue("ge").split("\\.")[1].trim().length(), decimalPlaces);
                };
            } else {
                _ge = -1;
            }

            String _mode;
            if (line.hasOption("mode")) {
                _mode = line.getOptionValue("mode");
            } else {
                _mode = "freeshift";
            }
            if (!(_mode.equals("freeshift") || _mode.equals("local") || _mode.equals("global"))) {
                System.out.println(ERROR_MESSAGE);
                System.exit(1);
            }

            String _printmatrices = "";
            if (line.hasOption("printmatrices") && line.getOptionValue("printmatrices") != null) {
                _printmatrices = line.getOptionValue("printmatrices");
            } else {
                _printmatrices = "false";
            }
            if (!(_printmatrices.equals("html") || _printmatrices.equals("txt") || _printmatrices.equals("false"))) {
                System.out.println(ERROR_MESSAGE);
                System.exit(1);
            }
            
            String _ignore = "";
            if (line.hasOption("ignore")) {
                _ignore = line.getOptionValue("ignore");
            }

            return new Configuration(_pairs, _seqlib, _matrix,
                    _go, _ge, (int) Math.pow(10, decimalPlaces), _mode, _printali,
                    _printmatrices, _check, _ignore);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
            System.exit(1);
        }
        return null;
    }

    private static final String MATRIX_FOLDER = "/home/proj/biosoft/praktikum/genprakt-ws13/assignment1/matrices/";
    private static final String ERROR_MESSAGE = "usage:\nalign mode: gotoh -seqlib <seqlibfile> -pairs <pairsfile> <optional parameter>\n        in align mode gotoh will calculate the similarity scores (and the alignments if requested) based on the method of \n   Gotoh. An improved algorithm for * matching biological sequences. J. Mol. Biol., 162:705-708, 1982. \n        where seqlibfile is a file containing sequences (one a line) in format id:seq\n        where pairs is a file containing lines with at least two columns (id1 id2) separated by whitespace\n        the optional parameters are:\n                -mode <alimode> one of local|global|freeshift (defaults to freeshift)\n                -matrix <AA exchange matrix file> (defaults to dayhoff)\n                -go <gap open> float value to gap open cost (defaults to -12)\n                -ge <gap extend> float value to gap extend cost (defaults to -1) (the first gap will cost gap open + gap extend!)\n                -printali if set the alignments will be printed\n\n\ncheck mode: gotoh -check <alilibfile> <optional parameter>\n        in check mode gotoh will read alignments realign them with the settings, check if the scores differ, \n        and if the alignment implicated score differs from the input score. If none of the above condition is \n        fullfilled then the alignment is correct and no output will be generated. Otherwise the binary will report \n        the input alignment the correct alignment given the settings and the score differences. \n        This means, that if the input file contains only correct alignments no output will be generated. \n\n        params: alilibfile is a file containing alignments given by three lines in the following format:\n        >id1 id2 score <additional columns are ignored>\n        id1: <aligned sequence for id1>\n        id2: <aligned sequence for id2>\n                where aligned sequence should contain only gaps '-' and the letters from the sequence of the given id\n                to generate an example input for checking you can invoke the binary with -printali\n        the optional parameters are:\n                -mode <alimode> one of local|global|freeshift (defaults to freeshift)\n                -matrix <AA exchange matrix file> (defaults to dayhoff)\n                -go <gap open> float value to gap open cost (defaults to -12)\n                -ge <gap extend> float value to gap extend cost (defaults to -1) (the first gap will cost gap open + gap extend!)";
}
