package tuanpv.tool.gui;

public class GuiUtils {
	public static Integer[] posOfLine(int left, int top, int width, int height, int splitter, int line) {
		return new Integer[] { left, top + ((height + splitter) * (line - 1)), width, height, splitter };
	}
}
