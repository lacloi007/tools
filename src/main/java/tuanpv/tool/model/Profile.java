package tuanpv.tool.model;

import java.util.ArrayList;
import java.util.List;

public class Profile {
	public String id;
	public List<Action> acts;

	public Profile(String id) {
		this.id = id;
	}

	public void addAction(Action act) {
		if (acts == null)
			acts = new ArrayList<>();
		acts.add(act);
	}

	public void addActionBean(String bean) {
		Action action = new Action();
		action.bean = bean;

		this.addAction(action);
	}

	public void addActionClass(String clazz) {
		Action action = new Action();
		action.clazz = clazz;

		this.addAction(action);
	}
}
