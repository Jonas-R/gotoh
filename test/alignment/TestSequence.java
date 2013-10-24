package alignment;

import gotoh.FileUtils;
import gotoh.Sequence;

import org.junit.Assert;
import org.junit.Test;

public class TestSequence {
	private static final String SEQLIB_PATH = "/home/proj/biosoft/praktikum/genprakt-ws13/assignment1/domains.seqlib";

	@Test
	public void TestSequence() {
		Assert.assertEquals("", Sequence.decodeSequence(Sequence.encodeSequence("")));
		String s1 = "MRRLRRLVHLVLLCPFSKGLQGRLPGLRVKYVLLVWLGIFVGSWMVYVHYSSYSELCRGHVCQVVICDQY";
		Assert.assertEquals(s1, Sequence.decodeSequence(Sequence.encodeSequence(s1)));
	}

	@Test
	public void TestReadPairs() {
		Sequence[] seqs = FileUtils.readSeqLib(SEQLIB_PATH);
		Assert.assertEquals(seqs[0].toString().trim(), "11asB00:AYIAKQRQISFVKSHFSRQLEERLGLIEVQAPILSRVGDGTQDNLSGAEKAVQVKVKALPDAQFEVVHSLAKWKRQTLGQHDFSAGEGLYTHMKALRPDEDRLSPLHSVYVDQWDWERVMGDGERQFSTLKSTVEAIWAGIKATEAAVSEEFGLAPFLPDQIHFVHSQELLSRYPDLDAKGRERAIAKDLGAVFLVGIGGKLSDGHRHDVRAPDYDDWSTPSELGHAGLNGDILVWNPVLEDAFELSSMGIRVDADTLKHQLALTGDEDRLELEWHQALLRGEMPQTIGGGIGQSRLTMLLLQLPHIGQVQAGVWPAAVRESVPSL");
		Assert.assertEquals(seqs[4].toString().trim(), "1a0aB00:MKRESHKHAEQARRNRLAVALHELASLIPAEWKQQNVSAAPSKATTVEAACRYIRHLQQNGS");
		Assert.assertEquals(seqs[seqs.length-1].toString().trim(), "8ickA01:ETLNGGITDMLTELANFEKNVSQAIHKYNAYRKAASVIAKYPHKIKSGAEAKKLPGVGTKIAEKIDEFLATGKLRKLEKIRQ");
	}
}
