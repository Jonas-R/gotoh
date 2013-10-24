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
}
