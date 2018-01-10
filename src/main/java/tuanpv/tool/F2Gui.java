package tuanpv.tool;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tuanpv.tool.domain.ProfileAction;
import tuanpv.tool.model.Action;
import tuanpv.tool.model.Profile;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.XmlUtils;

/**
 * Run book generate buy GUI
 * 
 * @author TuanPV
 */
public class F2Gui {
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext(Constant.FILE_APPLICATION_CONTEXT);

		Map<String, String> parameter = null;
		try {
			parameter = AppUtils.parseArguments(args);
			List<Profile> profiles = XmlUtils.parseProfile(AppUtils.pathOfFileConfig(Constant.FILE_PROFILE));
			for (Profile profile : profiles) {
				if (StringUtils.equals(parameter.get(AppUtils.keyOfSubArgs(Constant.KEY_TYPE)).toString(),
						profile.id)) {
					// initialize the data input for Profile Action
					Map<String, Object> map = new TreeMap<>(parameter);
					for (Action action : profile.acts) {

						// run action
						Object object = null;
						if (StringUtils.isNotBlank(action.bean) && context.containsBean(action.bean))
							object = context.getBean(action.bean);

						if (object == null && StringUtils.isNotBlank(action.clazz))
							object = AppUtils.getClassByName(action.clazz);

						if (object != null && object instanceof ProfileAction)
							map = ((ProfileAction) object).execute(context, map, new TreeMap<>(action.attr));
					}

					return;
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			System.exit(1);
		}
	}
}
