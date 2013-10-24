package alignment;

import org.junit.Assert;
import org.junit.Test;

public class TestSubstitutionmatrix {
	private static final String MATRIX_PATH = "/home/proj/biosoft/praktikum/genprakt-ws13/assignment1/matrices/";

	@Test
	public void testDayhoff() {
		String filename = "dayhoff.mat";
		Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename);
		Assert.assertEquals(matrix.matrix[0][0], 180);
		Assert.assertEquals(matrix.matrix[19][19], 430);
		Assert.assertEquals(matrix.matrix[5][4], -530);
		Assert.assertEquals(matrix.matrix[2][1], 0);
		Assert.assertEquals(matrix.matrix[19][0], 20);
		Assert.assertEquals(matrix.matrix[16][3], -10);
		for (int i = 0; i < matrix.matrix.length; i++) {
			for (int j = 0; j < matrix.matrix[0].length; j++) {
				Assert.assertEquals(matrix.matrix[i][j], matrix.matrix[j][i]);
			}
		}

		Assert.assertEquals(matrix.getColNames(), "ARNDCQEGHILKMFPSTWYV");
		Assert.assertEquals(matrix.getRowNames(), "ARNDCQEGHILKMFPSTWYV");

		Assert.assertEquals(matrix.getMultiplicationFactor(), 100);
	}

	@Test
	public void testBlakeCohen() {
		String filename = "BlakeCohenMatrix.mat";
		Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename);
		Assert.assertEquals(matrix.matrix[0][0], 9);
		Assert.assertEquals(matrix.matrix[19][19], 9);
		Assert.assertEquals(matrix.matrix[5][4], -14);
		Assert.assertEquals(matrix.matrix[2][1], 1);
		Assert.assertEquals(matrix.matrix[19][0], 0);
		Assert.assertEquals(matrix.matrix[16][3], 0);
		for (int i = 0; i < matrix.matrix.length; i++) {
			for (int j = 0; j < matrix.matrix[0].length; j++) {
				Assert.assertEquals(matrix.matrix[i][j], matrix.matrix[j][i]);
			}
		}

		Assert.assertEquals(matrix.getColNames(), "ARNDCQEGHILKMFPSTWYV");
		Assert.assertEquals(matrix.getRowNames(), "ARNDCQEGHILKMFPSTWYV");

		Assert.assertEquals(matrix.getMultiplicationFactor(), 1);
	}

	@Test
	public void testBlosum() {
		String filename = "blosum50.mat";
		Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename);
		Assert.assertEquals(matrix.matrix[0][0], 5);
		Assert.assertEquals(matrix.matrix[19][19], 5);
		Assert.assertEquals(matrix.matrix[5][4], -3);
		Assert.assertEquals(matrix.matrix[2][1], -1);
		Assert.assertEquals(matrix.matrix[19][0], 0);
		Assert.assertEquals(matrix.matrix[16][3], -1);
		for (int i = 0; i < matrix.matrix.length; i++) {
			for (int j = 0; j < matrix.matrix[0].length; j++) {
				Assert.assertEquals(matrix.matrix[i][j], matrix.matrix[j][i]);
			}
		}

		Assert.assertEquals(matrix.getColNames(), "ARNDCQEGHILKMFPSTWYV");
		Assert.assertEquals(matrix.getRowNames(), "ARNDCQEGHILKMFPSTWYV");

		Assert.assertEquals(matrix.getMultiplicationFactor(), 1);
	}

	@Test
	public void testPam() {
		String filename = "pam250.mat";
		Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename);
		Assert.assertEquals(matrix.matrix[0][0], 2);
		Assert.assertEquals(matrix.matrix[19][19], 4);
		Assert.assertEquals(matrix.matrix[5][4], -5);
		Assert.assertEquals(matrix.matrix[2][1], 0);
		Assert.assertEquals(matrix.matrix[19][0], 0);
		Assert.assertEquals(matrix.matrix[16][3], 0);
		for (int i = 0; i < matrix.matrix.length; i++) {
			for (int j = 0; j < matrix.matrix[0].length; j++) {
				Assert.assertEquals(matrix.matrix[i][j], matrix.matrix[j][i]);
			}
		}

		Assert.assertEquals(matrix.getColNames(), "ARNDCQEGHILKMFPSTWYV");
		Assert.assertEquals(matrix.getRowNames(), "ARNDCQEGHILKMFPSTWYV");

		Assert.assertEquals(matrix.getMultiplicationFactor(), 1);
	}

	@Test
	public void testThreader() {
		String filename = "THREADERSimilarityMatrix.mat";
		Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename);
		Assert.assertEquals(matrix.matrix[0][0], 100);
		Assert.assertEquals(matrix.matrix[19][19], 127);
		Assert.assertEquals(matrix.matrix[5][4], 0);
		Assert.assertEquals(matrix.matrix[2][1], 3);
		Assert.assertEquals(matrix.matrix[19][0], 46);
		Assert.assertEquals(matrix.matrix[16][3], -14);

		Assert.assertEquals(matrix.getColNames(), "ARNDCQEGHILKMFPSTWYV");
		Assert.assertEquals(matrix.getRowNames(), "ARNDCQEGHILKMFPSTWYV");

		Assert.assertEquals(matrix.getMultiplicationFactor(), 10);
	}
}
