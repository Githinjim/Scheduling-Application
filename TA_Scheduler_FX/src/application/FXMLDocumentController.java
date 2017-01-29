/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Matthew
 */
public class FXMLDocumentController implements Initializable {
	FileChooser fileChooser = new FileChooser();
	private Desktop desktop = Desktop.getDesktop();

	@FXML
	private void selectFiles(ActionEvent event) throws Exception {
		List<File> list = fileChooser.showOpenMultipleDialog(null);
		if (list != null) {
			for (File file : list) {
				FileInputStream fIP = new FileInputStream(file);
				XSSFWorkbook workbook = new XSSFWorkbook(fIP);
				XSSFSheet spreadsheet = workbook.createSheet();
				XSSFRow row = spreadsheet.createRow(0);
				Cell cell = row.createCell(0);
				cell.setCellValue("Test");

				FileOutputStream fOP = new FileOutputStream(file);
				workbook.write(fOP);

				fOP.close();
			}
		}
	}

	public void handle(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		//Desktop desktop =Desktop.getDesktop();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel files (*.xls)", "*.xls");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			SaveFile(file);
			System.out.println("File Saved");
		}
	}

	@SuppressWarnings("unused")
	private void SaveFile(File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("POI Worksheet");

			// index from 0,0... cell A1 is cell(0,0)
			HSSFRow row1 = worksheet.createRow((short) 0);

			HSSFCell cellA1 = row1.createCell((short) 0);
			cellA1.setCellValue("Hello");
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellA1.setCellStyle(cellStyle);

			HSSFCell cellB1 = row1.createCell((short) 1);
			cellB1.setCellValue("Goodbye");
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellB1.setCellStyle(cellStyle);

			HSSFCell cellC1 = row1.createCell((short) 2);
			cellC1.setCellValue(true);

			HSSFCell cellD1 = row1.createCell((short) 3);
			cellD1.setCellValue(new Date());
			cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
			cellD1.setCellStyle(cellStyle);

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	@FXML
	private void runAlgorithm(ActionEvent event) {
		// Fake creation of GAs
		GraduateAssistant student1 = new GraduateAssistant("bob", "123-456-7890");
		student1.setAvailableAt(0, "8am");

		GraduateAssistant student2 = new GraduateAssistant("foo", "bar");
		student2.setAvailableAt(0, "8am");
		student2.setAvailableAt(1, "8am");

		String startTime = "8am";
		String endTime = "9am";

		// Fake creation of Class
		Class class1 = new Class();
		class1.setClassNumber("123");
		class1.addDayOfWeek(0);
		class1.setStartTime(startTime);
		class1.setEndTime(endTime);

		Class class2 = new Class();
		class2.setClassNumber("456");
		class2.addDayOfWeek(1);
		class2.setStartTime(startTime);
		class2.setEndTime(endTime);

		Algorithm schedule = new Algorithm();
		schedule.addClass(class1);
		schedule.addClass(class2);
		schedule.addGradStudent(student1);
		schedule.addGradStudent(student2);

		schedule.initializeGraph();
		schedule.createInitialSolution();

		System.out.println(class2.getAssignedGA().getName());
		 System.out.println(student1.getAssignedClasses().get(0).getClassNumber());

		// Demo of determining if the student is available for a certain class.
		// System.out.println(student.isAvailable(myClass.getDaysOfWeek(),
		// myClass.getStartTime(), myClass.getEndTime()));
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx", ".xls"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));

	}

}
