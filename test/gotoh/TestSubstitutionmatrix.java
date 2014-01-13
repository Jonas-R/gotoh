package gotoh;

import org.junit.Assert;
import org.junit.Test;

public class TestSubstitutionmatrix {

    //private static final String MATRIX_PATH = "/home/proj/biosoft/praktikum/genprakt-ws13/assignment1/matrices/";
    private static final String MATRIX_PATH = "matrices/";
    @Test
    public void testDayhoff() {
        String filename = "dayhoff.mat";
        Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename, "X*B");
        Assert.assertEquals(18, matrix.matrix[0][0]);
        Assert.assertEquals(43, matrix.matrix[19][19]);
        Assert.assertEquals(-53, matrix.matrix[5][4]);
        Assert.assertEquals(0, matrix.matrix[2][1]);
        Assert.assertEquals(2, matrix.matrix[19][0]);
        Assert.assertEquals(-1, matrix.matrix[16][3]);
        for (int i = 0; i < matrix.matrix.length; i++) {
            for (int j = 0; j < matrix.matrix[0].length; j++) {
                Assert.assertEquals(matrix.matrix[i][j], matrix.matrix[j][i]);
            }
        }

        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*B", matrix.getColNames());
        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*B", matrix.getRowNames());

        Assert.assertEquals(10, matrix.getMultiplicationFactor());
    }

    @Test
    public void testBlakeCohen() {
        String filename = "BlakeCohenMatrix.mat";
        Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename, "X*");
        Assert.assertEquals(9, matrix.matrix[0][0]);
        Assert.assertEquals(9, matrix.matrix[19][19]);
        Assert.assertEquals(-14, matrix.matrix[5][4]);
        Assert.assertEquals(1, matrix.matrix[2][1]);
        Assert.assertEquals(0, matrix.matrix[19][0]);
        Assert.assertEquals(0, matrix.matrix[16][3]);
        for (int i = 0; i < matrix.matrix.length; i++) {
            for (int j = 0; j < matrix.matrix[0].length; j++) {
                Assert.assertEquals(matrix.matrix[i][j], matrix.matrix[j][i]);
            }
        }

        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*", matrix.getColNames());
        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*", matrix.getRowNames());

        Assert.assertEquals(1, matrix.getMultiplicationFactor());
    }

    @Test
    public void testBlosum() {
        String filename = "blosum50.mat";
        Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename, "X*B");
        Assert.assertEquals(5, matrix.matrix[0][0]);
        Assert.assertEquals(5, matrix.matrix[19][19]);
        Assert.assertEquals(-3, matrix.matrix[5][4]);
        Assert.assertEquals(-1, matrix.matrix[2][1]);
        Assert.assertEquals(0, matrix.matrix[19][0]);
        Assert.assertEquals(-1, matrix.matrix[16][3]);
        for (int i = 0; i < matrix.matrix.length; i++) {
            for (int j = 0; j < matrix.matrix[0].length; j++) {
                Assert.assertEquals(matrix.matrix[i][j], matrix.matrix[j][i]);
            }
        }

        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*B", matrix.getColNames());
        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*B", matrix.getRowNames());

        Assert.assertEquals(1, matrix.getMultiplicationFactor());
    }

    @Test
    public void testPam() {
        String filename = "pam250.mat";
        Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename, "X*Y");
        Assert.assertEquals(2, matrix.matrix[0][0]);
        Assert.assertEquals(4, matrix.matrix[19][19]);
        Assert.assertEquals(-5, matrix.matrix[5][4]);
        Assert.assertEquals(0, matrix.matrix[2][1]);
        Assert.assertEquals(0, matrix.matrix[19][0]);
        Assert.assertEquals(0, matrix.matrix[16][3]);
        for (int i = 0; i < matrix.matrix.length; i++) {
            for (int j = 0; j < matrix.matrix[0].length; j++) {
                Assert.assertEquals(matrix.matrix[i][j], matrix.matrix[j][i]);
            }
        }

        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*Y", matrix.getColNames());
        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*Y", matrix.getRowNames());

        Assert.assertEquals(1, matrix.getMultiplicationFactor());
    }

    @Test
    public void testThreader() {
        String filename = "THREADERSimilarityMatrix.mat";
        Substitutionmatrix matrix = FileUtils.readSubstitutionMatrix(MATRIX_PATH + filename, "X*");
        Assert.assertEquals(100, matrix.matrix[0][0]);
        Assert.assertEquals(127, matrix.matrix[19][19]);
        Assert.assertEquals(0, matrix.matrix[5][4]);
        Assert.assertEquals(3, matrix.matrix[2][1]);
        Assert.assertEquals(46, matrix.matrix[19][0]);
        Assert.assertEquals(-14, matrix.matrix[16][3]);

        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*", matrix.getColNames());
        Assert.assertEquals("ARNDCQEGHILKMFPSTWYVX*", matrix.getRowNames());

        Assert.assertEquals(10, matrix.getMultiplicationFactor());
    }
}
