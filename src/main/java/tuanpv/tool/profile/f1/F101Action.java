package tuanpv.tool.profile.f1;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;

import tuanpv.tool.Constant;
import tuanpv.tool.domain.ActionInfo;
import tuanpv.tool.domain.ProfileAction;
import tuanpv.tool.utils.AppUtils;
import tuanpv.tool.utils.LogUtils;
import tuanpv.tool.utils.PoiUtils;

/**
 * Process the file Excel configuration
 * 
 * @author TuanPV
 */

@ActionInfo(value = "F101", description = "Process the file Excel configuration")
public class F101Action implements ProfileAction {
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {

		// process
		String fileConfigPath = map.get(AppUtils.keyOfSubArgs(Constant.KEY_EXTENSION)).toString();
		File fileConfig = new File(fileConfigPath);
		if (fileConfig.exists()) {
			load(map, fileConfig);
		}

		LogUtils.logOut(map);
		return map;
	}

	@SuppressWarnings("unused")
	private void load(Map<String, Object> map, File fileConfig) throws Exception {
		Workbook workbook = initWorkbook(fileConfig);

		// process main sheet
		Sheet sheet = workbook.getSheet(F1Const.SHEET_MAIN);
		processSheetKeys(map, sheet, F1Const.SHEET_MAIN_KEYS);

		// process message sheet
		sheet = workbook.getSheet(F1Const.SHEET_MESSAGE);
		processSheetKeys(map, sheet, F1Const.SHEET_MESSAGE_KEYS);
		processSheetMessage(map, sheet, F1Const.SHEET_MESSAGE_DATA_START, F1Const.SHEET_MESSAGE_DATA_KEYS);

		int screenIdx = 1;
		sheet = workbook.getSheet(sheetScreenName(screenIdx));
		while (sheet != null) {

			// TODO : need to implement
			Map<String, Object> scMap = new TreeMap<>();

			// load new sheet
			screenIdx = screenIdx + 1;
			sheet = workbook.getSheet(sheetScreenName(screenIdx));
		}
	}

	private void processSheetMessage(Map<String, Object> map, Sheet sheet, int[] starts, String[][] keys) {
		int rowIdx = starts[0];
		Row row = sheet.getRow(rowIdx);

		//
		List<Map<String, Object>> list = new ArrayList<>();
		while (row != null && StringUtils.isNotBlank(PoiUtils.parseCellValue(row.getCell(starts[1])).toString())) {
			rowIdx = rowIdx + 1;
			Map<String, Object> item = new HashMap<>();
			for (String[] strings : keys) {
				Cell cell = row.getCell(Integer.parseInt(strings[1]));
				String key = AppUtils.keyOfSubMsg(strings[0]);
				Object value = PoiUtils.parseCellValue(cell);
				item.put(key, value);
			}

			list.add(item);

			// load next row
			row = sheet.getRow(rowIdx);
		}

		map.put(AppUtils.keyOfSubData(Constant.KEY_MESSAGE), list);
	}

	private void processSheetKeys(Map<String, Object> map, Sheet sheet, String[][] keys) {
		for (String[] group : keys) {
			Cell cell = PoiUtils.getCellByAddress(sheet, group[1]);

			String key = AppUtils.keyOfSubData(group[0]);
			Object value = PoiUtils.parseCellValue(cell);
			map.put(key, value);
		}
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

	private String sheetScreenName(int id) {
		return String.format(F1Const.SHEET_SCREEN_FORMATTER, id);
	}
}
