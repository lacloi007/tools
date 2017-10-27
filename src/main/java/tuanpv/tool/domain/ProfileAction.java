package tuanpv.tool.domain;

import java.util.Map;

import org.springframework.context.ApplicationContext;

public interface ProfileAction {
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr) throws Exception;
}
