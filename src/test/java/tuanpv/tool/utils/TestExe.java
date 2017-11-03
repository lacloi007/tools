package tuanpv.tool.utils;

import java.io.File;
import java.io.IOException;

public class TestExe {
	public static void main(String[] args) throws Exception {
		File path = new File("C:\\eclipse\\workspace_training\\tool\\data\\external\\");
		//Runtime.getRuntime().exec("kindlegen.exe ..\\..\\output\\kiem-dong-cuu-thien\\book.opf", null, path);

		//ProcessBuilder builder = new ProcessBuilder("kindlegen.exe", "../../output/kiem-dong-cuu-thien/book.opf");
		ProcessBuilder builder = new ProcessBuilder("kindlegen.exe ../../output/kiem-dong-cuu-thien/book.opf");
		builder.directory(path);
		builder.start();
	}
}
