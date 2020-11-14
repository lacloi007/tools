package tuanpv.tool.gui;

import java.awt.EventQueue;

import org.junit.jupiter.api.Test;

public class ToolFrameTest {
	@Test
	protected void testFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ToolFrame window = new ToolFrame(null);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
