package tuanpv.tool.profile.f2;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.ActionInfo;
import tuanpv.tool.domain.ProfileAction;

/**
 * Process the content of book and generate it to HTML, XML
 * 
 * @author TuanPV
 */

@ActionInfo(value = "F203", description = "Process the content of book and generate it to HTML, XML")
public class F203Action implements ProfileAction {
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {

		// process book main page

		// check type of book is 1
		String bookType = map.get(F2Utils.keyOfSubBook(Constant.KEY_TYPE)).toString();
		if (StringUtils.equals(F2Const.BOOK_TYPE_NORMAL, bookType)) {

		}

		return map;
	}
}