package tuanpv.tool.profile.f2;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.context.ApplicationContext;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.ActionInfo;
import tuanpv.tool.domain.ProfileAction;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.LogUtils;

/**
 * Clone structure of kindle book
 * 
 * @author TuanPV
 */

@ActionInfo(value = "F204", description = "Convert book by tool [kindlegen]")
public class F204Action implements ProfileAction {

	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {

		// if file destination is exist, bypass generating
		String nameMobi = AppUtils.join(FilenameUtils.EXTENSION_SEPARATOR_STR,
				map.get(F2Utils.keyOfSubBook(Constant.KEY_CODE)).toString(), Constant.FILE_TYPE_MOBI);
		String pathKindle = AppUtils.pathOfFile(F2Utils.pathOfKindle(map), nameMobi);
		File fileDest = new File(pathKindle);
		if (fileDest.exists())
			return map;

		String pathExternal = AppUtils.pathOfExternal();
		String exeName = getExecution();

		// return if EXE file is empty
		if (StringUtils.isBlank(exeName))
			return map;

		// return if EXE file is not exist
		String pathExe = AppUtils.pathOfFile(pathExternal, exeName);
		File fileExe = new File(pathExe);
		if (!fileExe.exists() || !fileExe.isFile())
			return map;

		// return if OPF file is not exist
		String pathOpf = AppUtils.pathOfFile(F2Utils.pathOfBook(map), F2Const.VM_BOOK_OPF);
		File fileOpf = new File(pathOpf);
		if (!fileOpf.exists() || !fileOpf.isFile())
			return map;

		try {
			// create variable store directory of external
			File directory = new File(pathExternal);

			// create process builder for running kindlegen
			ProcessBuilder builder = new ProcessBuilder(fileExe.getAbsolutePath(), fileOpf.getAbsolutePath());
			builder.directory(directory);

			// add log to console if system is in debug mode
			if (Constant.IS_DEBUG) {
				builder.redirectOutput(Redirect.INHERIT);
				builder.redirectError(Redirect.INHERIT);
				builder.redirectInput(Redirect.INHERIT);
			}

			// log debug
			LogUtils.debug("kindlegen", "start convert file " + pathOpf);

			// start parsing
			builder.start().waitFor();

			// log debug
			LogUtils.debug("kindlegen", "finish convert to directory " + F2Utils.pathOfBook(map));

			// start move file
			moveFile(map, fileDest);

			// log debug
			LogUtils.debug("kindlegen", "finish moving to " + pathKindle);
		} catch (Exception e) {
			throw e;
		}

		return map;
	}

	private void moveFile(Map<String, Object> map, File fileDest) throws IOException {
		String pathMobi = AppUtils.pathOfFile(F2Utils.pathOfBook(map), F2Const.FILE_BOOK_MOBI);

		File fileScr = new File(pathMobi);
		if (fileScr.exists())
			FileUtils.moveFile(new File(pathMobi), fileDest);
	}

	private String getExecution() {

		if (SystemUtils.IS_OS_WINDOWS)
			return "kindlegen.exe";

		if (SystemUtils.IS_OS_MAC_OSX)
			return "kindlegen-mac";

		if (SystemUtils.IS_OS_LINUX)
			return "kindlegen-unix";

		return StringUtils.EMPTY;
	}
}