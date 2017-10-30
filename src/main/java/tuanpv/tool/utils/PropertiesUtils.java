package tuanpv.tool.utils;

import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class PropertiesUtils {
	public static Map<String, Object> load2Sub(String path, Map<String, Object> map, String sub) throws Exception {
		return load2Sub(new File(path), map, sub);
	}

	public static Map<String, Object> load2Sub(File file, Map<String, Object> map, String sub) throws Exception {
		if (map == null)
			map = new TreeMap<>();

		if (file.exists()) {
			Properties properties = new Properties();
			properties.load(new FileReader(file));

			for (Object key : properties.keySet()) {
				String subValue = properties.getProperty(key.toString());

				// case input parameter is empty
				String subKey = key.toString();

				// case input parameter is not empty
				if (StringUtils.isNotBlank(sub))
					subKey = AppUtils.keyOfSub(sub, key.toString());

				map.put(subKey, subValue);
			}
		}

		return map;
	}
}
