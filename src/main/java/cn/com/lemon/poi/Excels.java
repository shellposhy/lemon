package cn.com.lemon.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import static cn.com.lemon.base.Preassert.checkArgument;

/**
 * Static utility methods pertaining to {@code Excel} primitives.
 *
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Excels {

	private Excels() {
	}

	/**
	 * The suffix xls {@code String} value.
	 */
	public static final String XLS_SUFFIX = "xls";

	/**
	 * The suffix xlsx {@code String} value.
	 */
	public static final String XLSX_SUFFIX = "xlsx";

	/**
	 * Returns the values from provided excel file to {@code List}.
	 * 
	 * @param file
	 *            the {@MultipartFile} excel file
	 * @param isFirstRow
	 *            if the value {@code true} Usually, excel file first row is
	 *            info,we don't need.
	 * @return {@code List} the excel file value
	 * 
	 */
	public static List<String[]> read(MultipartFile file, boolean isFirstRow) {
		checkArgument(!file.isEmpty());
		List<String[]> result = new ArrayList<String[]>();
		String fileName = file.getOriginalFilename();
		try {
			Workbook workbook = workbook(file.getInputStream(), fileName.substring(fileName.lastIndexOf(".") + 1));
			data(result, workbook, isFirstRow);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static List<String[]> read(File file, boolean isFirstRow) {
		checkArgument(file.exists() && file.isFile());
		List<String[]> result = new ArrayList<String[]>();
		String fileName = file.getName();
		try {
			Workbook workbook = workbook(new FileInputStream(file), fileName.substring(fileName.lastIndexOf(".") + 1));
			data(result, workbook, isFirstRow);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	// The private utilities
	private static void data(List<String[]> result, Workbook workbook, boolean isFirstRow) {
		checkArgument(null != workbook);
		for (int rows = 0; rows < workbook.getNumberOfSheets(); rows++) {
			Sheet sheet = workbook.getSheetAt(rows);
			if (null == sheet)
				continue;
			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			if (isFirstRow)
				firstRowNum += 1;
			for (int num = firstRowNum; num <= lastRowNum; num++) {
				Row row = sheet.getRow(num);
				if (row == null) {
					continue;
				}
				int firstCellNum = row.getFirstCellNum();
				int lastCellNum = row.getPhysicalNumberOfCells();
				String[] cells = new String[row.getPhysicalNumberOfCells()];
				for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
					Cell cell = row.getCell(cellNum);
					cells[cellNum] = value(cell);
				}
				result.add(cells);
			}
		}
	}

	private static Workbook workbook(InputStream inputStream, String suffix) {
		checkArgument(suffix != null && suffix.length() > 0);
		if (suffix.equals(XLS_SUFFIX))
			try {
				return new HSSFWorkbook(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		else
			try {
				return new XSSFWorkbook(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	}

	public static String value(Cell cell) {
		String value = "";
		if (cell == null) {
			return value;
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			value = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			value = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			value = String.valueOf(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BLANK:
			value = "";
			break;
		case Cell.CELL_TYPE_ERROR:
			value = "非法字符";
			break;
		default:
			value = "未知类型";
			break;
		}
		return value;
	}
}
