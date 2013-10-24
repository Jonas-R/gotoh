package alignment;

import alignment.Substitutionmatrix;

public class Main {
	public static void main(String[] args) {
		System.out.println(FileUtils.readSubstitutionMatrix("/home/proj/biosoft/praktikum/genprakt-ws13/assignment1/matrices/dayhoff.mat"));
	}
}
