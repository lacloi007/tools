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
import tuanpv.tool.utils.PropertiesUtils;

/**
 * Process the file Excel configuration
 * 
 * @author TuanPV
 */

@ActionInfo(value = "F102", description = "Process the file Excel configuration")
public class F102Action implements ProfileAction {
	@Override
	public Map<String, Object> execute(ApplicationContext context, Map<String, Object> map, Map<String, String> attr)
			throws Exception {

		// load definition file
		String pathDefine = AppUtils.pathOfEtcFile(map, F1Const.FILE_DEFINITION);
		File fileDefine = new File(pathDefine);
		if (!fileDefine.exists())
			throw new Exception(pathDefine + " is not exist.");

		// load the properties file into MAP
		PropertiesUtils.load2Key(fileDefine, map, F1Const.KEY_DEFINE);

		// process
		String fileConfigPath = map.get(AppUtils.keyOfSubArgs(Constant.KEY_EXTENSION)).toString();
		File fileConfig = new File(fileConfigPath);
		if (!fileConfig.exists())
			throw new Exception(fileConfigPath + " is not exist.");

		// create Workbook
		Workbook workbook = initWorkbook(fileConfig);

		// process file
		process(workbook, map, fileConfig);

		LogUtils.logOut(map);
		return map;
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

	@SuppressWarnings("unchecked")
	private void process(Workbook workbook, Map<String, Object> map, File fileConfig) {
		Map<String, Object> definition = (Map<String, Object>) map.get(F1Const.KEY_DEFINE);

		String[] loaders = F1Utils.loadArray(definition, F1Const.KEY_LIST);
		for (String loaderCode : loaders) {
			int idx = 1;
			Sheet sheet = null;
			while ((sheet = getSheet(workbook, definition, loaderCode, idx)) != null) {
				// process cells
				processCells(map, sheet, definition, loaderCode, idx);

				// process rows
				processRows(map, sheet, definition, loaderCode, idx);

				// increase INDEX
				idx++;
			}
		}
	}

	private Sheet getSheet(Workbook workbook, Map<String, Object> definition, String loaderCode, int idx) {
		String format = F1Utils.loadKey(definition, loaderCode, F1Const.KEY_SHEET, F1Const.KEY_FORMAT);
		if (StringUtils.isBlank(format) && idx > 1)
			return null;

		String name = StringUtils.isBlank(format) ? F1Utils.loadKey(definition, loaderCode, F1Const.KEY_SHEET)
				: String.format(format, idx);

		return workbook.getSheet(name);
	}

	@SuppressWarnings("unchecked")
	private void processCells(Map<String, Object> map, Sheet sheet, Map<String, Object> definition, String loaderCode,
			int index) {

		Map<String, Object> data = map.containsKey(F1Const.KEY_LOADER)
				? (Map<String, Object>) map.get(F1Const.KEY_LOADER) : new TreeMap<>();

		String cellSub = getSub(definition, loaderCode, F1Const.KEY_CELL, index);
		if (StringUtils.isNotBlank(cellSub)) {
			String[] names = F1Utils.loadArray(definition, loaderCode, F1Const.KEY_CELL, F1Const.KEY_NAMES);
			String[] cells = F1Utils.loadArray(definition, loaderCode, F1Const.KEY_CELL, F1Const.KEY_ITEMS);
			for (int idx = 0; idx < cells.length; idx++) {
				Cell cell = PoiUtils.getCellByAddress(sheet, cells[idx]);

				String key = AppUtils.keyOfSub(cellSub, names[idx]);
				Object value = F1Utils.parseCellValue(cell);
				data.put(key, value);
			}
		}

		map.put(F1Const.KEY_LOADER, data);
	}

	@SuppressWarnings("unchecked")
	private void processRows(Map<String, Object> map, Sheet sheet, Map<String, Object> definition, String loaderCode,
			int index) {
		Map<String, Object> data = map.containsKey(F1Const.KEY_LOADER)
				? (Map<String, Object>) map.get(F1Const.KEY_LOADER) : new TreeMap<>();

		List<Map<String, Object>> list = new ArrayList<>();
		String dataKey = getSub(definition, loaderCode, F1Const.KEY_ROW, index);
		String sub = F1Utils.loadKey(definition, loaderCode, F1Const.KEY_ROW, F1Const.KEY_SUB);
		if (StringUtils.isNotBlank(dataKey)) {
			String[] starts = F1Utils.loadArray(definition, loaderCode, F1Const.KEY_ROW, "start");
			String[] names = F1Utils.loadArray(definition, loaderCode, F1Const.KEY_ROW, "names");
			String[] cols = F1Utils.loadArray(definition, loaderCode, F1Const.KEY_ROW, "cols");

			int rowIdx = Integer.parseInt(starts[0]);
			int colIdx = Integer.parseInt(starts[1]);
			Row row = sheet.getRow(rowIdx);

			while (row != null && StringUtils.isNotBlank(PoiUtils.parseCellValue(row.getCell(colIdx)).toString())) {
				rowIdx = rowIdx + 1;
				Map<String, Object> item = new HashMap<>();
				for (int i = 0; i < cols.length; i++) {
					Cell cell = row.getCell(Integer.parseInt(cols[i]));
					Object value = F1Utils.parseCellValue(cell);
					item.put(AppUtils.keyOfSub(sub, names[i]), value);
				}

				list.add(item);

				// load next row
				row = sheet.getRow(rowIdx);
			}
		}

		if (!list.isEmpty())
			data.put(dataKey, list);
		map.put(F1Const.KEY_LOADER, data);
	}

	private String getSub(Map<String, Object> definition, String loaderCode, String loaderType, int index) {
		String format = F1Utils.loadKey(definition, loaderCode, loaderType, F1Const.KEY_SUB, F1Const.KEY_FORMAT);
		if (StringUtils.isBlank(format) && index > 1)
			return StringUtils.EMPTY;

		String name = StringUtils.isBlank(format) ? F1Utils.loadKey(definition, loaderCode, loaderType, F1Const.KEY_SUB)
				: String.format(format, index);

		return name;
	}
}
