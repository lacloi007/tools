package tuanpv.tool.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.TreeMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import tuanpv.swing.utils.SwingUtils;
import tuanpv.tool.profile.f2.F2Utils;

@SuppressWarnings("unused")
public class ToolFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 600;
	private static final int WIDTH = (HEIGHT * 4) / 3;
	private static final Dimension DIMENSION = new Dimension(WIDTH, HEIGHT);

	private static final String PATH_REPO = "data\\etc\\kindle";

	private ApplicationContext context;

	public ToolFrame(ApplicationContext context) {
		this.context = context;

		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Tools");
		setResizable(false);
		setSize(DIMENSION);

		init();
	}

	private static final int TOP = 10;
	private static final int LEFT = 10;
	private static final int SPLITTER = 5;
	private static final int IWIDTH = 100;
	private static final int IHEIGHT = 25;

	private void init() {
		add(SwingUtils.createComponent(JLabel.class, "Processing type",
				GuiUtils.posOfLine(LEFT, TOP, IWIDTH, IHEIGHT, SPLITTER, 1)));
		add(SwingUtils.createComponent(JTextField.class, "1",
				GuiUtils.posOfLine(LEFT + IWIDTH + SPLITTER, TOP, IWIDTH, IHEIGHT, SPLITTER, 1)));
		add(SwingUtils.createComponent(JLabel.class, "Parser",
				GuiUtils.posOfLine(LEFT, TOP, IWIDTH, IHEIGHT, SPLITTER, 2)));
		add(SwingUtils.createComponent(JLabel.class, "Domain",
				GuiUtils.posOfLine(LEFT, TOP, IWIDTH, IHEIGHT, SPLITTER, 3)));
		add(SwingUtils.createComponent(JLabel.class, "Code",
				GuiUtils.posOfLine(LEFT, TOP, IWIDTH, IHEIGHT, SPLITTER, 4)));
	}

	private void loadRepository() {
	}

	@Test
	public static void main(String[] args) {
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
