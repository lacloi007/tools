package tuanpv.tool.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;

public class PoiUtils {
	public static Cell getCellByAddress(Sheet sheet, String cellAddress) {
		CellReference cellRefer = new CellReference(cellAddress);
		return sheet.getRow(cellRefer.getRow()).getCell(cellRefer.getCol());
	}

	public static Object parseCellValue(Cell cell) {
		if (cell == null)
			return StringUtils.EMPTY;

		switch (cell.getCellTypeEnum()) {
		case STRING:
			return cell.getStringCellValue();

		case NUMERIC:
			return (int) cell.getNumericCellValue();

		case BOOLEAN:
			return cell.getBooleanCellValue();

		case FORMULA:
			switch (cell.getCachedFormulaResultTypeEnum()) {
			case NUMERIC:
				return cell.getNumericCellValue();

			case STRING:
				return cell.getRichStringCellValue();

			case BOOLEAN:
				return cell.getBooleanCellValue();

			default:
				return StringUtils.EMPTY;
			}

		default:
			return StringUtils.EMPTY;
		}
	}
}
