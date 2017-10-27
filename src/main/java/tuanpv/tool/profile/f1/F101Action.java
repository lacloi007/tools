package tuanpv.tool.profile.f1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.ProfileAction;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.LogUtils;

/**
 * Process the file Excel configuration
 * 
 * @author TuanPV
 */

@Component(value = "F101")
public class F101Action implements ProfileAction {
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {
		LogUtils.logOut(map);

		// process
		String fileConfigPath = map.get(AppUtils.keyOfSubArgs(Constant.KEY_EXTENSION)).toString();
		File fileConfig = new File(fileConfigPath);
		if (fileConfig.exists()) {
			load(fileConfig);
		}

		return map;
	}

	private void load(File fileConfig) throws Exception {
		Workbook workbook = initWorkbook(fileConfig);
	}

	private Workbook initWorkbook(File fileConfig) throws Exception {
		String fileExtension = FilenameUtils.getExtension(fileConfig.getName()).toLowerCase();
		FileInputStream inputStream = new FileInputStream(fileConfig);

		Workbook workbook = null;
		switch (fileExtension) {
		case Constant.FILE_TYPE_XLS:
			workbook = new HSSFWorkbook(inputStream);
			break;
		case Constant.FILE_TYPE_XLSX:
			workbook = new XSSFWorkbook(inputStream);
			break;
		}

		return workbook;
	}
}
