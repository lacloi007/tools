package tuanpv.tool.profile.f2;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;

import tuanpv.tool.domain.ActionInfo;
import tuanpv.tool.domain.ProfileAction;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.LogUtils;

/**
 * Clone structure of kindle book
 * 
 * @author TuanPV
 */

@ActionInfo(value = "F202", description = "Clone structure of kindle book")
public class F202Action implements ProfileAction {
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {

		// process
		String pathSrc = AppUtils.pathOfStructure(map);
		String pathDst = F2Utils.pathOfBook(map);
		FileUtils.copyDirectory(new File(pathSrc), new File(pathDst));

		// log data
		LogUtils.logOut("path-src", pathSrc);
		LogUtils.logOut("path-dst", pathDst);
		return map;
	}
}