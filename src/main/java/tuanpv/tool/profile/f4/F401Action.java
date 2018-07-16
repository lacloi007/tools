package tuanpv.tool.profile.f4;

import java.io.File;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.ActionInfo;
import tuanpv.tool.domain.ProfileAction;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.LogUtils;

/**
 * Process the book download configuration
 * 
 * @author TuanPV
 */
@ActionInfo(value = "F401", description = "Process the picture book download configuration")
public class F401Action implements ProfileAction {
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {

		// process
		String fileBookProperties = map.get(AppUtils.keyOfSubArgs(Constant.KEY_EXTENSION)).toString();
		File fileBook = new File(fileBookProperties);
		if (fileBook.exists()) {
		}

		LogUtils.logOut(map);
		return map;
	}
}