package tuanpv.tool.profile.f2;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.ActionInfo;
import tuanpv.tool.domain.ProfileAction;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.LogUtils;
import tuanpv.tool.utils.PropertiesUtils;
import tuanpv.tool.utils.VelocityUtils;

/**
 * Process the book download configuration
 * 
 * @author TuanPV
 */
@ActionInfo(value = "F201", description = "Process the book download configuration")
public class F201Action implements ProfileAction {
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {

		// process
		String fileBookProperties = map.get(AppUtils.keyOfSubArgs(Constant.KEY_EXTENSION)).toString();
		File fileBook = new File(fileBookProperties);
		if (fileBook.exists()) {
			PropertiesUtils.load2Sub(fileBook, map, F2Const.SUB_BOOK);

			// load domain configuration
			String fileDomainProperties = AppUtils.pathOfEtcFile(map,
					F2Utils.nameOfDomain(map.get(F2Utils.keyOfSubBook(F2Const.KEY_DOMAIN)).toString()));
			map.put(F2Utils.keyOfSubBook(Constant.KEY_CONFIG),
					PropertiesUtils.load2Sub(fileDomainProperties, null, StringUtils.EMPTY));

			// load repository for Velocity
			String fileDomainRepoProp = AppUtils.pathOfEtcFile(map,
					F2Utils.nameOfRepository(map.get(F2Utils.keyOfSubBook(F2Const.KEY_DOMAIN)).toString()));
			VelocityUtils.addStringTemplate(fileDomainRepoProp);
		}

		LogUtils.logOut(map);
		return map;
	}
}