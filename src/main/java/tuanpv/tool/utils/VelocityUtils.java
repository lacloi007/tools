package tuanpv.tool.utils;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

public class VelocityUtils {
	public static void addStringTemplate(String path) throws Exception {
		addStringTemplate(new File(path));
	}

	public static void addStringTemplate(File file) throws Exception {
		if (file.exists()) {
			Properties props = new Properties();
			props.load(new FileReader(file));
			StringResourceRepository repo = StringResourceLoader.getRepository();
			for (Object key : props.keySet())
				repo.putStringResource(key.toString(), props.getProperty(key.toString()));
		}
	}
}
