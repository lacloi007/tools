package tuanpv.tool.profile.f2;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import tuanpv.tool.domain.ProfileAction;

/**
 * Process the content of book and generate it to HTML, XML
 * 
 * @author TuanPV
 */

@Component(value = "F203")
public class F203Action implements ProfileAction {
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {

		return map;
	}
}
