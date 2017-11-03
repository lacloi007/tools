package tuanpv.tool.utils;

import java.util.Date;

public class TestMain {
	public static void main(String[] args) {
		Date today = new Date();
		System.out.printf("%tY-%tm-%td",today,today,today);
	}
}
