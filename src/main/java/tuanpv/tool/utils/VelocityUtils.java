package tuanpv.tool.utils;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.springframework.context.ApplicationContext;

import tuanpv.tool.Constant;

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

	public static String generateString(ApplicationContext context, String code, Map<String, Object> param)
			throws Exception {
		VelocityEngine engine = context.getBean(VelocityEngine.class);
		return generateString(engine, code, param);
	}

	public static String generateString(VelocityEngine engine, String code, Map<String, Object> param)
			throws Exception {

		// create velocity context
		VelocityContext context = createVelocityContext(param);

		return generateString(engine, code, context);
	}

	public static String generateString(VelocityEngine engine, String code, VelocityContext context) throws Exception {

		// get template
		Template template = engine.getTemplate(code);

		// merge source
		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		return writer.toString();
	}

	public static VelocityContext createVelocityContext(Map<String, Object> param) {
		VelocityContext context = new VelocityContext();
		for (String key : param.keySet())
			context.put(key, param.get(key));
		return context;
	}

	public static void generateFile(ApplicationContext context, Map<String, Object> config, Map<String, Object> content,
			String pathOfVM, String pathOfFile) throws Exception {
		generateFile(context.getBean(VelocityEngine.class), config, content, pathOfVM, pathOfFile);
	}

	public static void generateFile(VelocityEngine engine, Map<String, Object> config, Map<String, Object> content,
			String pathOfVM, String pathOfFile) throws Exception {

		// get real path of VM file
		String srcVmPath = AppUtils.join(File.separator, AppUtils.pathOfTemplate(config),
				pathOfVM + FilenameUtils.EXTENSION_SEPARATOR_STR + Constant.FILE_TYPE_VM);

		// check the source is exist or not
		File fileSourceVm = new File(srcVmPath);
		if (fileSourceVm.exists() && fileSourceVm.isFile()) {

			// create velocity context
			VelocityContext context = createVelocityContext(content);

			// parse template file
			Template template = engine.getTemplate(srcVmPath, Constant.ENCODE);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);

			// write content to new file
			File fileDest = new File(pathOfFile);
			FileUtils.write(fileDest, writer.toString(), Constant.ENCODE);

			// log
			LogUtils.debug("generate-html", pathOfFile);
		}
	}
}
