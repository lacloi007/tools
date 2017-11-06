package tuanpv.tool.utils;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

public class TestExe {
	public static void main(String[] args) {
		// File path = new
		// File("C:\\eclipse\\workspace_training\\tool\\data\\external\\");
		// Runtime.getRuntime().exec("kindlegen.exe
		// ..\\..\\output\\kiem-dong-cuu-thien\\book.opf", null, path);

		// ProcessBuilder builder = new ProcessBuilder("kindlegen.exe",
		// "../../output/kiem-dong-cuu-thien/book.opf");
		/*
		 * ProcessBuilder builder = new
		 * ProcessBuilder("kindlegen.exe ../../output/kiem-dong-cuu-thien/book.opf"
		 * ); builder.directory(path); builder.start();
		 */

		File directory = new File("C:\\eclipse\\workspace_training\\tool\\data\\external\\");
		ProcessBuilder builder = new ProcessBuilder(
				"C:\\eclipse\\workspace_training\\tool\\data\\external\\kindlegen.exe",
				"..\\..\\output\\kiem-dong-cuu-thien\\book.opf");
		builder.redirectOutput(Redirect.INHERIT);
		builder.redirectError(Redirect.INHERIT);
		builder.redirectInput(Redirect.INHERIT);
		builder.directory(directory);
		try {
			Process process = builder.start();
			int waitFlag = process.waitFor();

			/*if (waitFlag == 0) {
				int returnVal = process.exitValue();
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
