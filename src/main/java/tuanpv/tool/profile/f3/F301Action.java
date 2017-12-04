package tuanpv.tool.profile.f3;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import tuanpv.tool.domain.ActionInfo;
import tuanpv.tool.domain.ProfileAction;
import tuanpv.tool.utils.LogUtils;

/**
 * Process the book download configuration
 * 
 * @author TuanPV
 */
@ActionInfo(value = "F301", description = "Load configuration of notification")
public class F301Action implements ProfileAction {
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {

		LogUtils.logOut(map);
		return map;
	}
}