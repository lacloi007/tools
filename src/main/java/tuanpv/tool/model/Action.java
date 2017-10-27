package tuanpv.tool.model;

import java.util.HashMap;
import java.util.Map;

public class Action {
	public String bean;
	public String clazz;
	public Map<String, String> attr;

	public Action() {
		this.attr = new HashMap<>();
	}

	public void addAttr(String key, String value) {
		this.attr.put(key, value);
	}
}
