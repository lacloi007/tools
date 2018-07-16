package tuanpv.tool.profile.f3;

import java.awt.EventQueue;

public class TestForm {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FMain window = new FMain();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
