package tuanpv.swing.utils;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SwingUtils {
	private static final boolean IS_DEBUG = true;
	private static final Border BORDER = BorderFactory.createLineBorder(Color.BLUE, 1);

	public static JMenu createMenu(String title, int key) {
		JMenu menu = new JMenu(title);
		menu.setMnemonic(key);
		return menu;
	}

	public static JMenuItem createMenuItem(String title, String action, int key, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.setMnemonic(key);
		menuItem.setActionCommand(action);
		menuItem.addActionListener(listener);
		return menuItem;
	}

	public static void border(JComponent component) {
		if (IS_DEBUG)
			component.setBorder(BORDER);
	}

	public static JComponent createComponent(Class<?> clazz, String title, Integer[] size) {
		try {
			Object object = clazz.newInstance();
			if (object instanceof JLabel) {
				JLabel component = (JLabel) object;
				component.setText(title);
			}

			if (object instanceof JTextField) {
				JTextField component = (JTextField) object;
				component.setToolTipText(title);
			}

			if (object instanceof JComponent) {
				JComponent component = (JComponent) object;
				applyComponent(component, size);
				return component;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static JLabel createLabel(String title, Integer[] size) {
		JLabel label = new JLabel(title);
		applyComponent(label, size);
		return label;
	}

	public static JTextField createTextField(String title, Integer[] size) {
		JTextField textField = new JTextField();
		textField.setToolTipText(title);
		applyComponent(textField, size);
		return textField;
	}

	public static void applyComponent(JComponent component, Integer[] size) {
		component.setBounds(size[0], size[1], size[2], size[3]);
		border(component);
	}

	public static Integer[] icalcNewLine(Integer[] size, int def) {
		size[0] = def;
		size[1] = size[1] + size[3] + size[4];
		return size;
	}

	public static Integer[] icalcRight(Integer[] size) {
		size[0] = size[0] + size[2] + size[4];
		return size;
	}
}
