package tuanpv.tool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	public static int parseInt(String string) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(string);
		if (matcher.find())
			return Integer.parseInt(matcher.group());

		return 0;
	}

	public static void main(String[] args) {
		int i = parseInt("http://webtruyen.com/vu-cuc-thien-ha/832/");
		System.out.println(i);
	}
}
