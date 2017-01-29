package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * Michael Githinji
 */

public class FileReader extends Class {

	public static void main(String[] args) throws IOException {
		ArrayList<Class> classess = readFileUsingPOI();
		for (Class cl : classess) {
			System.out.println(cl.toString());
		}

	}

	private static ArrayList<Class> readFileUsingPOI() throws IOException {
		ArrayList<Class> classess = new ArrayList<Class>();

		ClassLoader classLoader = FileReader.class.getClassLoader();
		String excelFilePath = "Excel.xls";
		FileInputStream inputStream = new FileInputStream(new File(classLoader.getResource(excelFilePath).getFile()));

		Workbook workbk = new XSSFWorkbook(inputStream);
		org.apache.poi.ss.usermodel.Sheet sheet = workbk.getSheetAt(0);

		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();

			// Not creating country object for header
			if (nextRow.getRowNum() == 0)
				continue;

			Class classObj = new Class();
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				int columnIndex = cell.getColumnIndex();
				switch (columnIndex + 1) {
				case 1:
					classObj.setAssignedGA(cell.getStringCellValue());
					break;
				case 2:
					classObj.setClassNumber(cell.getNumericCellValue());
					break;
				case 3:
					classObj.setDaysOfWeek(cell.getStringCellValue());
					break;
				case 4:
					classObj.setEndTime(cell.getStringCellValue());
					break;
				case 5:
					classObj.setProfessor(cell.getStringCellValue());
					break;
				case 6:
					classObj.setQualifications(cell.getStringCellValue());
					break;
				case 7:
					classObj.setStartTime(cell.getStringCellValue());
					break;

				default:
				}

			}
			classess.add(classObj);
		}
		workbk.close();
		inputStream.close();

		return classess;
	}
}