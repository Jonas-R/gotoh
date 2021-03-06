package gotoh;

public class Sequence {
	private static final String aa_to_int = "ARNDCQEGHILKMFPSTWYV";

	private String sSequence;
	private String sequenceID;
	public final int[] sequence;

	public Sequence(String sSequence, String seqID){
		this.sSequence = sSequence;
		sequence = encodeSequence(sSequence);
		sequenceID = seqID;
	}

	public static int[] encodeSequence(String sSequence) {
		int[] sequence = new int[sSequence.length()];
		for (int i = 0; i < sSequence.length(); i++) {
			sequence[i] = aa_to_int.indexOf(sSequence.charAt(i));
		}
		return sequence;
	}

	public static String decodeSequence(int[] sequence) {
		String sSequence = "";
		for (int i = 0; i < sequence.length; i++) {
			sSequence = sSequence + aa_to_int.charAt(sequence[i]);
		}
		return sSequence;
	}

	public int length() {
		return sequence.length;
	}

	public int get(int i) {
		return sequence[i];
	}
	
	public char getAsChar(int i) {
		return sSequence.charAt(i);
	}

	public String getID() {
		return sequenceID;
	}

	public String toString() {
		return sequenceID + ":" + sSequence + "\n";
	}
}
