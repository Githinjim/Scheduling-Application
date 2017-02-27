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
import java.io.IOException;
import java.net.URL;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Matthew
 */
public class FXMLDocumentController implements Initializable {
	FileChooser fileChooser = new FileChooser();

	ArrayList<GraduateAssistant> gradList = new ArrayList<GraduateAssistant>();
	ArrayList<Class> classList = new ArrayList<Class>();
	ArrayList<Class> classes = new ArrayList<Class>();
	ArrayList<CellStyle> classStyleSheetList = new ArrayList<CellStyle>();
	private int uniqueIdentifierCounter = 0;

	@FXML
	TextArea resultsText;
	@FXML
	CheckBox saveBox;
	@FXML
	TextField hoursRequired;
	@FXML
	TextArea Help;

	@FXML
	/**
	 * Method for opening several Excel files and reading them in to populate GA
	 * data structures
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void selectGAs(ActionEvent event) throws Exception {
		List<File> list = fileChooser.showOpenMultipleDialog(null);
		resultsText.appendText("Loading Graduate Students\n");
		if (list != null) {
			for (File file : list) {
				FileInputStream fIP = new FileInputStream(file);
				Workbook workbook;
				try {
					workbook = new XSSFWorkbook(fIP);
					// workbook = WorkbookFactory.create(fIP);
				} catch (org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException e) {
					resultsText
							.appendText("File: \"" + file.getName() + "\" is not an excel file and was not read in.\n");
					continue;
				}

				Sheet datatypeSheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = datatypeSheet.iterator();
				try {
					workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue().equals(null);
					if (!workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue().equals("Phone Number:")) {
						resultsText.appendText("File \"" + file.getName()
								+ "\" could not be identified as a graduate assistant file.\n\n");
						continue;
					} // end if
					workbook.getSheetAt(0).getRow(2).getCell(1).getStringCellValue().equals(null);
					if (!workbook.getSheetAt(0).getRow(2).getCell(1).getStringCellValue().equals("Monday")) {
						resultsText.appendText("File \"" + file.getName()
								+ "\" could not be identified as a graduate assistant file.\n\n");
						continue;
					}
				} catch (NullPointerException e) {
					resultsText.appendText("File \"" + file.getName()
							+ "\" could not be identified as a graduate assistant file.\n\n");
					continue;
				}

				gradList.add(new GraduateAssistant());
				while (iterator.hasNext()) {
					Row currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator();

					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						// Conditional for the name field
						if (currentCell.getColumnIndex() == 1 && currentCell.getRowIndex() == 0) {
							// check if the name already exists
							boolean doesNameAlreadyExist = false;
							try {
								for (int gradSutdentNameIndexer = 0; gradSutdentNameIndexer < gradList
										.size(); gradSutdentNameIndexer++) {
									if (gradList.get(gradSutdentNameIndexer).getName()
											.equals(currentCell.getStringCellValue())) {
										doesNameAlreadyExist = true;
										break;
									}
								}
							} catch (NullPointerException e) {

							}
							// if the name already exists create a unique one,
							// else just assign the name
							if (doesNameAlreadyExist == true) {
								// find a number to append to the end of the
								// name to make it unique
								int extraNumber = 1;
								int index = 0;
								while (true) {

									String str = currentCell.getStringCellValue();
									str += extraNumber;
									try {
										if (gradList.get(index).getName().equals(str)) {
											index = 0;
											extraNumber += 1;
										}
										index++;
									} catch (NullPointerException e) {
										gradList.get(gradList.size() - 1)
												.setName(currentCell.getStringCellValue() + extraNumber);
										break;
									}

								} // end always true while
							} else {
								gradList.get(gradList.size() - 1).setName(currentCell.getStringCellValue());
							}
						}

						// Conditional for the phone number field
						else if (currentCell.getColumnIndex() == 1 && currentCell.getRowIndex() == 1) {
							gradList.get(gradList.size() - 1).setPhoneNumber(currentCell.getStringCellValue());
						}

						// conditional to check if the current cell is part of
						// the available times data
						else if (currentCell.getColumnIndex() >= 1 && currentCell.getColumnIndex() <= 5
								&& currentCell.getRowIndex() >= 3 && currentCell.getRowIndex() <= 15) {
							if (currentCell.getStringCellValue().equals("")) {
								// System.out.println("true");
								String timeOfTheDay = "";
								switch (currentCell.getRowIndex() - 3) {
								case 0:
									timeOfTheDay = "8am";
									break;
								case 1:
									timeOfTheDay = "9am";
									break;
								case 2:
									timeOfTheDay = "10am";
									break;
								case 3:
									timeOfTheDay = "11am";
									break;
								case 4:
									timeOfTheDay = "12pm";
									break;
								case 5:
									timeOfTheDay = "1pm";
									break;
								case 6:
									timeOfTheDay = "2pm";
									break;
								case 7:
									timeOfTheDay = "3pm";
									break;
								case 8:
									timeOfTheDay = "4pm";
									break;
								case 9:
									timeOfTheDay = "5pm";
									break;
								case 10:
									timeOfTheDay = "6pm";
									break;
								case 11:
									timeOfTheDay = "7pm";
									break;
								case 12:
									timeOfTheDay = "8pm";
									break;

								}// end switch case statement
								gradList.get(gradList.size() - 1).setAvailableAt(currentCell.getColumnIndex() - 1,
										timeOfTheDay);
							} // if conditional for setting availability of the
								// tiem of the day.
						} // end if conditional for the times section

						// conditional for the qualifications
						else if (currentCell.getRowIndex() >= 3 && currentCell.getRowIndex() <= 10
								&& currentCell.getColumnIndex() == 7) {
							if (!currentCell.getStringCellValue().isEmpty()) {
								gradList.get(gradList.size() - 1).addQualification(currentCell.getStringCellValue());
							}
						} // end if for the qualifications
					}
				}
				workbook.close();
			}
			resultsText.appendText("Graduate Students Loaded!\n\n");
		} // end if
		else {
			resultsText.appendText("No file(s) were selected for Graduate Students.\n\n");
		}

	}// end selectFiles method for the grad students

	@FXML
	/**
	 * Method for selecting offered classes and reading the data in.
	 * 
	 * @param event
	 * @throws Exception
	 */
	private void selectClasses(ActionEvent event) throws Exception {
		resultsText.appendText("Loading Classes.\n");
		List<File> classes = fileChooser.showOpenMultipleDialog(null);

		if (classes != null) {
			for (File file : classes) {
				FileInputStream fIP = new FileInputStream(file);
				Workbook workbook = new XSSFWorkbook(fIP);
				Sheet datatypeSheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = datatypeSheet.iterator();

				String professorName = "GEORGE"; // create a professor's Name

				while (iterator.hasNext()) // Iterate over rows
				{
					Row currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator(); //

					if (!(datatypeSheet.getRow(0).getCell(0).getStringCellValue().equals("Name:"))
							|| !(datatypeSheet.getRow(3).getCell(1).getStringCellValue().equals("Start Time"))) {
						resultsText.appendText(
								"File: \"" + file.getName() + "\"could not be recognized as a class sheet.\n\n");
						break;
					}

					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						// Get the professor -> This only happens once in the
						// entire file...
						if (currentRow.getRowNum() == 0 && currentCell.getColumnIndex() == 1) {
							professorName = currentCell.getStringCellValue(); // assign
																				// value
																				// to
						}

						else if (currentCell.getRowIndex() >= 4 && currentCell.getRowIndex() <= 15) {
							// Get the class number
							if (currentCell.getColumnIndex() == 0) {

								if (currentCell.getStringCellValue().isEmpty()) {
									break;
								} else {
									classList.add(new Class());
									classList.get(classList.size() - 1).setProfessor(professorName);
									classList.get(classList.size() - 1)
											.setClassNumber(currentCell.getStringCellValue());
									classList.get(classList.size() - 1).setUniqueIdentifier(uniqueIdentifierCounter);
									uniqueIdentifierCounter += 1;
								}

							}

							// Get the start time
							else if (currentCell.getColumnIndex() == 1) {
								classList.get(classList.size() - 1).setStartTime(currentCell.getStringCellValue());
							}

							// Get the end time
							else if (currentCell.getColumnIndex() == 2) {
								classList.get(classList.size() - 1).setEndTime(currentCell.getStringCellValue());
							}

							// Get the days of the week
							else if (currentCell.getColumnIndex() >= 3 && currentCell.getColumnIndex() <= 7) {
								if (currentCell.getStringCellValue().equals("Has Class")) {
									classList.get(classList.size() - 1)
											.addDayOfWeek((int) currentCell.getColumnIndex() - 3);
								}
							}
							// Get the prep time
							else if (currentCell.getColumnIndex() == 8) {
								classList.get(classList.size() - 1)
										.setPrepHours((int) currentCell.getNumericCellValue());
							}
						}
					}
				}
				workbook.close();
			}
			resultsText.appendText("Classes Loaded!\n\n");
		} else {
			resultsText.appendText("No Classes loaded.\n\n");
		}
	}

	@FXML
	public void handle(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		// Desktop desktop =Desktop.getDesktop();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			saveFile(file);
		}

	}

	private void saveFile(File file) {
		int rowCounter = 0;
		int columnCounter = 0;
		int numberOfGraduatesWritten = 0;
		int duplicateNameTracker = 0;
		final int ROW_OFFSET = 16;
		final int COLUMN_OFFSET = 8;

		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet worksheet = workbook.createSheet("POI Worksheet");

			XSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setWrapText(true);

			for (int i = 0; i < classList.size(); i++) {

				classStyleSheetList.add(workbook.createCellStyle());
				classStyleSheetList.get(i).setWrapText(true);
				classStyleSheetList.get(i).setBorderBottom(XSSFCellStyle.BORDER_THIN);
				classStyleSheetList.get(i).setBorderTop(XSSFCellStyle.BORDER_THIN);
				classStyleSheetList.get(i).setBorderRight(XSSFCellStyle.BORDER_THIN);
				classStyleSheetList.get(i).setBorderLeft(XSSFCellStyle.BORDER_THIN);
				classStyleSheetList.get(i).setBottomBorderColor(IndexedColors.BLACK.getIndex());
				classStyleSheetList.get(i).setTopBorderColor(IndexedColors.BLACK.getIndex());
				classStyleSheetList.get(i).setRightBorderColor(IndexedColors.BLACK.getIndex());
				classStyleSheetList.get(i).setLeftBorderColor(IndexedColors.BLACK.getIndex());
				// classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				classStyleSheetList.get(i).setFillPattern(CellStyle.SOLID_FOREGROUND);
				System.out.println("I is: " + i);

				switch (i) {

				case 1:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
					break;
				case 2:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.PINK.index);
					break;
				case 3:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
					break;
				case 4:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.ORANGE.index);
					break;
				case 5:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.GREEN.index);
					break;
				case 6:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.YELLOW.index);
					break;
				case 7:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.PLUM.index);
					break;
				case 8:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.RED.index);
					break;
				case 9:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.AQUA.index);
					break;
				case 10:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.LAVENDER.index);
					break;
				case 11:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.GOLD.index);
					break;
				case 12:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.ROSE.index);
					break;
				case 13:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.INDIGO.index);
					break;
				case 14:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.TAN.index);
					break;
				case 15:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.TURQUOISE.index);
					break;
				case 16:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.BLUE_GREY.index);
					break;
				case 17:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
					break;
				case 18:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.CORAL.index);
					break;
				case 19:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
					break;
				case 20:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.VIOLET.index);
					break;
				case 21:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
					break;
				case 22:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.SEA_GREEN.index);
					break;
				case 23:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.ORCHID.index);
					break;
				case 24:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.LIME.index);
					break;
				case 25:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
					break;
				case 26:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
					break;
				case 27:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
					break;
				case 28:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
					break;
				case 29:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.PALE_BLUE.index);
					break;
				case 30:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.MAROON.index);
					break;
				case 31:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.DARK_RED.index);
					break;
				case 32:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
					break;
				case 33:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
					break;
				default:
					classStyleSheetList.get(i).setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

				}// end switch

			} // end for loop

			CellStyle blackStyle;
			blackStyle = workbook.createCellStyle();
			blackStyle.setFillForegroundColor(HSSFColor.BLACK.index);
			blackStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			;

			// create the busy cell style
			CellStyle busyStyle;
			busyStyle = workbook.createCellStyle();
			busyStyle.setFillForegroundColor(HSSFColor.BLACK.index);
			busyStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			;

			busyStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			busyStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			busyStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			busyStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			busyStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			busyStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			busyStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			busyStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

			CellStyle rightBorder;
			rightBorder = workbook.createCellStyle();
			rightBorder.setBorderRight(XSSFCellStyle.BORDER_THIN);
			rightBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
			rightBorder.setWrapText(true);

			CellStyle rightAndBottomBorder;
			rightAndBottomBorder = workbook.createCellStyle();
			rightAndBottomBorder.setBorderRight(XSSFCellStyle.BORDER_THIN);
			rightAndBottomBorder.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			rightAndBottomBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
			rightAndBottomBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());
			rightAndBottomBorder.setWrapText(true);

			CellStyle classStyle = workbook.createCellStyle();
			classStyle.setWrapText(true);
			classStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			classStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			classStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			classStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			classStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			classStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			classStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			classStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			classStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			classStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			;

			CellStyle freeStyle = workbook.createCellStyle();
			freeStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			freeStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			freeStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			freeStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			freeStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			freeStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			freeStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			freeStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

			CellStyle IndividualTextStyle = workbook.createCellStyle();
			IndividualTextStyle.setAlignment(CellStyle.ALIGN_CENTER);
			IndividualTextStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			IndividualTextStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			IndividualTextStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			IndividualTextStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			IndividualTextStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			IndividualTextStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			IndividualTextStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			IndividualTextStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

			CellStyle justBorder = workbook.createCellStyle();
			justBorder.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			justBorder.setBorderTop(XSSFCellStyle.BORDER_THIN);
			justBorder.setBorderRight(XSSFCellStyle.BORDER_THIN);
			justBorder.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			justBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			justBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());
			justBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
			justBorder.setLeftBorderColor(IndexedColors.BLACK.getIndex());

			XSSFFont boldFont = workbook.createFont();
			boldFont.setBold(true);

			IndividualTextStyle.setFont(boldFont);

			// create an iterator of the graduate assistant
			Iterator<GraduateAssistant> itr = gradList.listIterator();
			GraduateAssistant currentGrad;

			// loop through all graduate assistant
			while (itr.hasNext()) {
				currentGrad = itr.next();

				rowCounter = ((numberOfGraduatesWritten / 2) * ROW_OFFSET);
				columnCounter = ((numberOfGraduatesWritten % 2) * COLUMN_OFFSET);

				// creating cells for times of the day
				for (int rowIndex = 0; rowIndex < 14; rowIndex++) {
					try {
						worksheet.getRow((short) rowIndex + rowCounter).equals(null);
					} catch (java.lang.NullPointerException e) {
						worksheet.createRow((short) rowIndex + rowCounter);
					}
					worksheet.getRow((short) rowIndex + rowCounter).createCell((short) 0 + columnCounter);

					worksheet.getRow(rowIndex + rowCounter).getCell(0 + columnCounter).setCellStyle(cellStyle);
				}

				// set the time value on the left hand side of the excel file
				worksheet.getRow(rowCounter).createCell(columnCounter).setCellValue(currentGrad.getName());
				worksheet.autoSizeColumn(columnCounter);
				worksheet.getRow(1 + rowCounter).getCell(0 + columnCounter).setCellValue("8:00 AM");
				worksheet.getRow(2 + rowCounter).getCell(0 + columnCounter).setCellValue("9:00 AM");
				worksheet.getRow(3 + rowCounter).getCell(0 + columnCounter).setCellValue("10:00 AM");
				worksheet.getRow(4 + rowCounter).getCell(0 + columnCounter).setCellValue("11:00 AM");
				worksheet.getRow(5 + rowCounter).getCell(0 + columnCounter).setCellValue("12:00 PM");
				worksheet.getRow(6 + rowCounter).getCell(0 + columnCounter).setCellValue("1:00 PM");
				worksheet.getRow(7 + rowCounter).getCell(0 + columnCounter).setCellValue("2:00 PM");
				worksheet.getRow(8 + rowCounter).getCell(0 + columnCounter).setCellValue("3:00 PM");
				worksheet.getRow(9 + rowCounter).getCell(0 + columnCounter).setCellValue("4:00 PM");
				worksheet.getRow(10 + rowCounter).getCell(0 + columnCounter).setCellValue("5:00 PM");
				worksheet.getRow(11 + rowCounter).getCell(0 + columnCounter).setCellValue("6:00 PM");
				worksheet.getRow(12 + rowCounter).getCell(0 + columnCounter).setCellValue("7:00 PM");
				worksheet.getRow(13 + rowCounter).getCell(0 + columnCounter).setCellValue("8:00 PM");

				// create the rows necessary
				for (int columnIndex = 1; columnIndex < 7; columnIndex++) {
					worksheet.getRow(0 + rowCounter).createCell((short) columnIndex + columnCounter);

					worksheet.getRow(0 + rowCounter).getCell(columnIndex + columnCounter).setCellStyle(cellStyle);

				}

				// set the days of the week at the top of the excel file
				worksheet.getRow(0 + rowCounter).getCell(1 + columnCounter).setCellValue("Monday");
				worksheet.getRow(0 + rowCounter).getCell(2 + columnCounter).setCellValue("Tuesday");
				worksheet.getRow(0 + rowCounter).getCell(3 + columnCounter).setCellValue("Wednesday");
				worksheet.getRow(0 + rowCounter).getCell(4 + columnCounter).setCellValue("Thursday");
				worksheet.getRow(0 + rowCounter).getCell(5 + columnCounter).setCellValue("Friday");
				worksheet.getRow(0 + rowCounter).getCell(6 + columnCounter).setCellValue("Prep Hours:");
				worksheet.autoSizeColumn(1 + columnCounter);
				worksheet.autoSizeColumn(2 + columnCounter);
				worksheet.autoSizeColumn(3 + columnCounter);
				worksheet.autoSizeColumn(4 + columnCounter);
				worksheet.autoSizeColumn(5 + columnCounter);
				worksheet.autoSizeColumn(6 + columnCounter);

				// loop through all class the graduate student is going to be
				// assisting
				for (int classIndex = 0; classIndex < currentGrad.getAssignedClasses().size(); classIndex++) {
					Class currentClass = currentGrad.getAssignedClasses().get(classIndex);
					String startTime = currentClass.getStartTime();
					String endTime = currentClass.getEndTime();
					ArrayList<Integer> dayList = currentClass.getDaysOfWeek();

					// loop through the list of days for the current class
					for (int z = 0; z < dayList.size(); z++) {
						int startInt = stringToHour(startTime);
						int endInt = stringToHour(endTime);
						while (startInt < endInt) {
							try {
								worksheet.getRow(startInt + 1 + rowCounter).getCell(dayList.get(z) + 1 + columnCounter)
										.equals(null);
							} catch (java.lang.NullPointerException e) {
								worksheet.getRow(startInt + 1 + rowCounter)
										.createCell(dayList.get(z) + 1 + columnCounter);
							}
							worksheet.getRow(startInt + 1 + rowCounter).getCell(dayList.get(z) + 1 + columnCounter)
									.setCellValue(currentClass.getClassNumber() + "\n" + currentClass.getProfessor());
							worksheet.getRow(startInt + 1 + rowCounter).getCell(dayList.get(z) + 1 + columnCounter)
									.setCellStyle(classStyleSheetList.get(currentClass.getUniqueIdentifier()));
							worksheet.autoSizeColumn(dayList.get(z) + 1 + columnCounter);
							startInt += 1;

						} // end while

					} // end for loop for the number of days in the class

					// loop set all remaining busy days to black
					for (int day = 1; day < 6; day++) {
						for (int j = 1; j < 14; j++) {
							String time = "";
							time = intToHour(j);
							if (!currentGrad.isAvailable(day - 1, time)) {
								try {
									worksheet.getRow(j + rowCounter).getCell(day + columnCounter).equals(null);
								} catch (java.lang.NullPointerException e) {

									worksheet.getRow(j + rowCounter).createCell(day + columnCounter);
									worksheet.getRow(j + rowCounter).getCell(day + columnCounter)
											.setCellStyle(busyStyle);

								}

							} // end if for if is available at the given time
							else {
								try {
									worksheet.getRow(j + rowCounter).getCell(day + columnCounter).equals(null);
								} catch (java.lang.NullPointerException e) {

									worksheet.getRow(j + rowCounter).createCell(day + columnCounter);
									worksheet.getRow(j + rowCounter).getCell(day + columnCounter)
											.setCellStyle(freeStyle);

								}
							}
						} // end inner for loop
					} // end outer for loop

					// Add the extra hours for grading etc. if needed for the
					// current class
					if (currentClass.getPrepHours() > 0) {
						for (int rowIndexer = (1 + rowCounter); rowIndexer < (14 + rowCounter); rowIndexer++) {

							try {
								worksheet.getRow(rowIndexer).getCell(6 + columnCounter).getStringCellValue().equals("");

							} catch (java.lang.NullPointerException e) {
								worksheet.getRow(rowIndexer).createCell(6 + columnCounter);
								worksheet.getRow(rowIndexer).getCell(6 + columnCounter).setCellValue(
										currentClass.getClassNumber() + "\nHours:" + currentClass.getPrepHours());
								worksheet.getRow(rowIndexer).getCell(6 + columnCounter)
										.setCellStyle(classStyleSheetList.get(currentClass.getUniqueIdentifier()));
								worksheet.autoSizeColumn(6 + columnCounter);
								break;
							}

						}
					}

				} // end for loop

				if (numberOfGraduatesWritten % 10 == 0) {
					resultsText.appendText("Algorithm Still running please do not close.\n");
				}
				numberOfGraduatesWritten += 1;

				// ---------Here is where we write to the individual
				// sheets---------
				workbook.createSheet(currentGrad.getName());

				for (int rowIndex = 0; rowIndex < 25; rowIndex++) {
					try {
						workbook.getSheet(currentGrad.getName()).getRow((short) rowIndex).equals(null);
					} catch (java.lang.NullPointerException e) {
						workbook.getSheet(currentGrad.getName()).createRow((short) rowIndex);
					}
					workbook.getSheet(currentGrad.getName()).getRow((short) rowIndex).createCell((short) 0);

					workbook.getSheet(currentGrad.getName()).getRow(rowIndex).getCell(0).setCellStyle(cellStyle);
				}

				// workbook.getSheet(currentGrad.getName()).getRow(rowCounter).createCell(columnCounter);

				// set the time value on the left hand side of the excel file
				// workbook.getSheet(currentGrad.getName()).getRow(rowCounter).createCell(columnCounter).setCellValue(currentGrad.getName());
				workbook.getSheet(currentGrad.getName()).autoSizeColumn(columnCounter);
				workbook.getSheet(currentGrad.getName()).getRow(2).getCell(0).setCellValue("8:00 AM");
				workbook.getSheet(currentGrad.getName()).getRow(2).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(3).getCell(0).setCellValue("9:00 AM");
				workbook.getSheet(currentGrad.getName()).getRow(3).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(4).getCell(0).setCellValue("10:00 AM");
				workbook.getSheet(currentGrad.getName()).getRow(4).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(5).getCell(0).setCellValue("11:00 AM");
				workbook.getSheet(currentGrad.getName()).getRow(5).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(6).getCell(0).setCellValue("12:00 PM");
				workbook.getSheet(currentGrad.getName()).getRow(6).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(7).getCell(0).setCellValue("1:00 PM");
				workbook.getSheet(currentGrad.getName()).getRow(7).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(8).getCell(0).setCellValue("2:00 PM");
				workbook.getSheet(currentGrad.getName()).getRow(8).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(9).getCell(0).setCellValue("3:00 PM");
				workbook.getSheet(currentGrad.getName()).getRow(9).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(10).getCell(0).setCellValue("4:00 PM");
				workbook.getSheet(currentGrad.getName()).getRow(10).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(11).getCell(0).setCellValue("5:00 PM");
				workbook.getSheet(currentGrad.getName()).getRow(11).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(12).getCell(0).setCellValue("6:00 PM");
				workbook.getSheet(currentGrad.getName()).getRow(12).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(13).getCell(0).setCellValue("7:00 PM");
				workbook.getSheet(currentGrad.getName()).getRow(13).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(14).getCell(0).setCellValue("8:00 PM");
				workbook.getSheet(currentGrad.getName()).getRow(14).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).autoSizeColumn(0);

				// create the rows necessary
				for (int columnIndex = 1; columnIndex < 13; columnIndex++) {
					workbook.getSheet(currentGrad.getName()).getRow(1).createCell((short) columnIndex);
					workbook.getSheet(currentGrad.getName()).getRow(0).createCell((short) columnIndex);

					workbook.getSheet(currentGrad.getName()).getRow(1).getCell(columnIndex).setCellStyle(cellStyle);

				}

				// set the days of the week at the top of the excel file
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(1).setCellValue("Monday");
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(1).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(2).setCellValue("Tuesday");
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(2).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(3).setCellValue("Wednesday");
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(3).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(4).setCellValue("Thursday");
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(4).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(5).setCellValue("Friday");
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(5).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).autoSizeColumn(1);
				workbook.getSheet(currentGrad.getName()).autoSizeColumn(2);
				workbook.getSheet(currentGrad.getName()).autoSizeColumn(3);
				workbook.getSheet(currentGrad.getName()).autoSizeColumn(4);

				// loop through all class the graduate student is going to be
				// assisting
				for (int classIndex = 0; classIndex < currentGrad.getAssignedClasses().size(); classIndex++) {
					Class currentClass = currentGrad.getAssignedClasses().get(classIndex);
					String startTime = currentClass.getStartTime();
					String endTime = currentClass.getEndTime();
					ArrayList<Integer> dayList = currentClass.getDaysOfWeek();

					// loop through the list of days for the current class
					for (int z = 0; z < dayList.size(); z++) {
						// boolean isTheEndTime = false;
						int startInt = stringToInt(startTime);
						int endInt = stringToInt(endTime);
						while (startInt < endInt) {
							try {
								workbook.getSheet(currentGrad.getName()).getRow(startInt + 2)
										.getCell(dayList.get(z) + 1).equals(null);
							} catch (java.lang.NullPointerException e) {
								workbook.getSheet(currentGrad.getName()).getRow(startInt + 2)
										.createCell(dayList.get(z) + 1);
							}
							workbook.getSheet(currentGrad.getName()).getRow(startInt + 2).getCell(dayList.get(z) + 1)
									.setCellValue(currentClass.getClassNumber() + "\n" + currentClass.getProfessor());
							workbook.getSheet(currentGrad.getName()).getRow(startInt + 2).getCell(dayList.get(z) + 1)
									.setCellStyle(classStyle);
							workbook.getSheet(currentGrad.getName()).autoSizeColumn(dayList.get(z) + 1);
							startInt += 1;

						} // end while

					} // end for loop for the number of days in the class

				} // end for loop

				// set the stuff that is always in the excel file
				workbook.getSheet(currentGrad.getName()).getRow(0).getCell(0).setCellValue("Health Sciences");
				workbook.getSheet(currentGrad.getName()).getRow(0).getCell(0).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(0, 0, 0, 1));

				workbook.getSheet(currentGrad.getName()).getRow(0).getCell(2)
						.setCellValue("Integrative Human Physiology");
				workbook.getSheet(currentGrad.getName()).getRow(0).getCell(2).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(0, 0, 2, 4));

				workbook.getSheet(currentGrad.getName()).getRow(0).getCell(5)
						.setCellValue("GA Timesheet for: " + currentGrad.getName());
				workbook.getSheet(currentGrad.getName()).getRow(0).getCell(5).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).autoSizeColumn(5);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(0, 0, 5, 7));

				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(6)
						.setCellValue("Additional hours:\n (prep times, grading, \n research etc.)");
				workbook.getSheet(currentGrad.getName()).autoSizeColumn(6);
				workbook.getSheet(currentGrad.getName()).getRow(1).getCell(6).setCellStyle(classStyle);
				// workbook.getSheet(currentGrad.getName()).addMergedRegion(new
				// CellRangeAddress(1,2,6,6));

				workbook.getSheet(currentGrad.getName()).getRow(16).createCell(4);
				workbook.getSheet(currentGrad.getName()).getRow(16).createCell(5);
				workbook.getSheet(currentGrad.getName()).getRow(16).createCell(6);
				workbook.getSheet(currentGrad.getName()).getRow(16).getCell(4).setCellValue("Total Hours worked: ");
				workbook.getSheet(currentGrad.getName()).getRow(16).getCell(4).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(16).getCell(5).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(16).getCell(6).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(16, 16, 4, 6));

				workbook.getSheet(currentGrad.getName()).getRow(17).createCell(0);
				workbook.getSheet(currentGrad.getName()).getRow(17).createCell(1);
				workbook.getSheet(currentGrad.getName()).getRow(17).createCell(2);
				workbook.getSheet(currentGrad.getName()).getRow(17).createCell(3);
				workbook.getSheet(currentGrad.getName()).getRow(17).createCell(4);
				workbook.getSheet(currentGrad.getName()).getRow(17).createCell(5);
				workbook.getSheet(currentGrad.getName()).getRow(17).createCell(6);
				workbook.getSheet(currentGrad.getName()).getRow(17).getCell(0)
						.setCellValue("Strike out days not worked for example due to vacation or holidays!");
				workbook.getSheet(currentGrad.getName()).getRow(17).getCell(0).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).getRow(17).getCell(1).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).getRow(17).getCell(2).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).getRow(17).getCell(3).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).getRow(17).getCell(4).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).getRow(17).getCell(5).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).getRow(17).getCell(6).setCellStyle(IndividualTextStyle);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(17, 17, 0, 6));

				workbook.getSheet(currentGrad.getName()).getRow(18).createCell(0);
				workbook.getSheet(currentGrad.getName()).getRow(18).getCell(0).setCellValue(
						"This timesheet shows assigned GA duties initialing at a time slot the instructor of record "
								+ "declares that the GA worked these hours.  By signing this form the student certifies that these hours are a true and accurate record of all time worked during the week.");
				workbook.getSheet(currentGrad.getName()).getRow(18).getCell(0).setCellStyle(cellStyle);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(18, 20, 0, 6));

				workbook.getSheet(currentGrad.getName()).getRow(21).createCell(0);
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(0).setCellValue("Employee Signature: ");
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(21).createCell(1);
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(1).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(21).createCell(2);
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(2).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(21, 21, 0, 2));

				workbook.getSheet(currentGrad.getName()).getRow(21).createCell(3);
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(3).setCellValue("Date Signed: ");
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(3).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(21).createCell(4);
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(4).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(21, 21, 3, 4));

				workbook.getSheet(currentGrad.getName()).getRow(21).createCell(5);
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(5).setCellValue("Week of (Daterange): ");
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(5).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(21).createCell(6);
				workbook.getSheet(currentGrad.getName()).getRow(21).getCell(6).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(21, 21, 5, 6));

				workbook.getSheet(currentGrad.getName()).getRow(22).createCell(0);
				workbook.getSheet(currentGrad.getName()).getRow(22).getCell(0).setCellValue("Supervisor Signature ");
				workbook.getSheet(currentGrad.getName()).getRow(22).getCell(0).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(22).createCell(1);
				workbook.getSheet(currentGrad.getName()).getRow(22).getCell(1).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(22).createCell(2);
				workbook.getSheet(currentGrad.getName()).getRow(22).getCell(2).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(22).createCell(3);
				workbook.getSheet(currentGrad.getName()).getRow(22).getCell(3).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(22).createCell(4);
				workbook.getSheet(currentGrad.getName()).getRow(22).getCell(4).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(22, 22, 0, 4));

				workbook.getSheet(currentGrad.getName()).getRow(22).createCell(5);
				workbook.getSheet(currentGrad.getName()).getRow(22).getCell(5).setCellValue("Date: ");
				workbook.getSheet(currentGrad.getName()).getRow(22).getCell(5).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).getRow(22).createCell(6);
				workbook.getSheet(currentGrad.getName()).getRow(22).getCell(6).setCellStyle(justBorder);
				workbook.getSheet(currentGrad.getName()).addMergedRegion(new CellRangeAddress(22, 22, 5, 6));

				// set extra columns to a black background
				for (int rowIndexer = 15; rowIndexer < 17; rowIndexer++) {
					if (rowIndexer == 15) {
						for (int columnIndexer = 0; columnIndexer < 7; columnIndexer++) {
							workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).createCell(columnIndexer);
							workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).getCell(columnIndexer)
									.setCellStyle(blackStyle);
						}
					}
					if (rowIndexer == 16) {
						for (int columnIndexer = 0; columnIndexer < 4; columnIndexer++) {
							workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).createCell(columnIndexer);
							workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).getCell(columnIndexer)
									.setCellStyle(blackStyle);
						}
					}
				}

				// loop though all cells in the individual sheets and set
				// borders if there
				// are no borders (just to make the sheet asthetically pleasing)
				// first loop through just calendar
				for (int rowIndexer = 2; rowIndexer < 15; rowIndexer++) {
					for (int columnIndexer = 1; columnIndexer < 6; columnIndexer++) {
						try {
							workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).getCell(columnIndexer)
									.equals(null);
						} catch (NullPointerException e) {
							workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).createCell(columnIndexer);
							workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).getCell(columnIndexer)
									.setCellStyle(justBorder);
							;
						} // end try / catch
					} // end inner for
				} // end outer for

				// set right borders for additional prep hour boxes
				for (int rowIndexer = 2; rowIndexer < 14; rowIndexer++) {
					try {
						workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).getCell(6).equals(null);
					} catch (NullPointerException e) {
						workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).createCell(6);
						workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).getCell(6)
								.setCellStyle(rightBorder);
					} // end try / catch
				}

				workbook.getSheet(currentGrad.getName()).getRow(14).createCell(6);
				workbook.getSheet(currentGrad.getName()).getRow(14).getCell(6).setCellStyle(rightAndBottomBorder);

				// set prep hours
				for (int classIndex = 0; classIndex < currentGrad.getAssignedClasses().size(); classIndex++) {

					if (currentGrad.getAssignedClasses().get(classIndex).getPrepHours() > 0) {
						// System.out.println(currentGrad.getName() + " has prep
						// hours for " +
						// currentGrad.getAssignedClasses().get(classIndex).getClassNumber());
						for (int rowIndexer = 2; rowIndexer < 15; rowIndexer++) {
							if (workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).getCell(6)
									.getStringCellValue().equals("")) {
								workbook.getSheet(currentGrad.getName()).getRow(rowIndexer).getCell(6)
										.setCellValue(currentGrad.getAssignedClasses().get(classIndex).getClassNumber()
												+ "\nHours:" + currentGrad.getAssignedClasses().get(classIndex)
														.getPrepHours());

								break;
							}
						}
					}

				} // end prep hours loop

			} // end while loop

			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			resultsText.appendText("File has been saved!");
			if (saveBox.isSelected()) {
				// open excel file if checkbox is checked
				Desktop dt = Desktop.getDesktop();
				dt.open(new File(file.getAbsolutePath()));
				resultsText.appendText("Saved File will open shortly on your Desktop as an Excel Spreed Sheet");

			}
		} catch (FileNotFoundException e) {
			resultsText.appendText(
					"Error when saving file: Please check to make sure the file you are saving to is not already open.  Then try to save file again.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// end save file method

	private static int stringToInt(String stringDay) {
		switch (stringDay) {
		case "8am":
			return 0;
		case "9am":
			return 1;

		case "10am":
			return 2;

		case "11am":
			return 3;

		case "12pm":
			return 4;

		case "1pm":
			return 5;

		case "2pm":
			return 6;

		case "3pm":
			return 7;

		case "4pm":
			return 8;

		case "5pm":
			return 9;

		case "6pm":
			return 10;

		case "7pm":
			return 11;

		case "8pm":
			return 12;
		default:
			return -1;
		}// end switch
	}// end method intToDay

	private static String intToHour(int intHour) {
		switch (intHour) {
		case 1:
			return "8am";
		case 2:
			return "9am";

		case 3:
			return "10am";

		case 4:
			return "11am";

		case 5:
			return "12pm";

		case 6:
			return "1pm";

		case 7:
			return "2pm";

		case 8:
			return "3pm";

		case 9:
			return "4pm";

		case 10:
			return "5pm";

		case 11:
			return "6pm";

		case 12:
			return "7pm";

		case 13:
			return "8pm";
		default:
			return "";
		}// end switch
	}// end method intToDay

	private static int stringToHour(String stringDay) {
		switch (stringDay) {
		case "8am":
			return 0;
		case "9am":
			return 1;
		case "10am":
			return 2;
		case "11am":
			return 3;
		case "12pm":
			return 4;
		case "1pm":
			return 5;
		case "2pm":
			return 6;
		case "3pm":
			return 7;
		case "4pm":
			return 8;
		case "5pm":
			return 9;
		case "6pm":
			return 10;
		case "7pm":
			return 11;
		case "8pm":
			return 12;
		default:
			return -1;
		}// end switch
	}// end method intToDay

	@FXML
	private void runAlgorithm(ActionEvent event) {
		resultsText.appendText("Starting to assign TAs\n");
		double totalHours = 0;
		double workedHours = -1;
		int iterations = 0;
		int MAX_HOURS = Integer.parseInt(hoursRequired.getText());

		Algorithm alg = new Algorithm(gradList, classList, MAX_HOURS);

		ArrayList<Class> unassigned = null;

		while (totalHours != workedHours) {
			totalHours = 0;
			workedHours = 0;

			alg.reset();
			alg.initializeGraph();
			unassigned = alg.createInitialSolution();

			// Assign unassigned classes
			for (Class weekly : unassigned) {
				alg.partialAssignment(weekly);
			}

			totalHours = alg.getClassHours();
			workedHours = alg.getStudentHours();
			iterations++;

			// Attempt 2000 times
			if (iterations > 2000) {
				break;
			}
		}

		resultsText.appendText("-----Partial Assignments-----\n");
		for (Class weekly : unassigned) {
			System.out.println(weekly + "\t" + weekly.getAssignedGA());
			if (weekly.getAssignedGA().size() > 0) {
				resultsText.appendText(weekly.getClassNumber() + " has a partial assignment\n");
			}
		}
		resultsText.appendText("\n");

		resultsText.appendText("-----Unassigned Classes-----\n");
		for (Class weekly : unassigned) {
			if (weekly.getAssignedGA().size() == 0) {
				resultsText.appendText(weekly.getClassNumber() + " is unassigned\n");
			}
		}
		resultsText.appendText("\n");

		for (GraduateAssistant ga : gradList) {
			System.out.println(ga.getName() + " is working " + ga.getHoursAssigned());
		}
		System.out.print(totalHours);
		System.out.print(" " + workedHours);
		System.out.print("\t " + iterations);
		System.out.println();

		resultsText.appendText("Finished Assigning TAs\n");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx"));
	}

}
