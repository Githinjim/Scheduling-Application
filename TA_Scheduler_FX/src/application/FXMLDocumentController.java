/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
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

    @FXML TextArea resultsText;
    
	@FXML
	/**
	 * Method for opening several Excel files and reading them in to populate GA data structures
	 * @param event
	 * @throws Exception
	 */
	private void selectGAs(ActionEvent event) throws Exception {
		List<File> list =
                fileChooser.showOpenMultipleDialog(null);
            if (list != null) {
                for (File file : list) {
                	FileInputStream fIP = new FileInputStream(file);
                	Workbook workbook;
                	try{
                		workbook = new XSSFWorkbook(fIP);
                		//workbook = WorkbookFactory.create(fIP);
                	}catch(org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException e){
                		resultsText.appendText("File: \"" + file.getName() + "\" is not an excel file and was not read in.\n");
                		continue;
                	}
                    
                    Sheet datatypeSheet = workbook.getSheetAt(0);
                    Iterator<Row> iterator = datatypeSheet.iterator();
                    try{
                    	workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue().equals(null);
                    	if(!workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue().equals("Phone Number:")){
                    		resultsText.appendText("File \"" + file.getName() + "\" could not be identified as a graduate assistant file.\n");
                    		continue;
                    	}//end if
                    	workbook.getSheetAt(0).getRow(2).getCell(1).getStringCellValue().equals(null);
                    	if(!workbook.getSheetAt(0).getRow(2).getCell(1).getStringCellValue().equals("Monday")){
                    		resultsText.appendText("File \"" + file.getName() + "\" could not be identified as a graduate assistant file.\n");
                    		continue;
                    	}
                    }catch(NullPointerException e){
                    	resultsText.appendText("File \"" + file.getName() + "\" could not be identified as a graduate assistant file.\n");
                    	continue;
                    }	
                    
                    
                    gradList.add(new GraduateAssistant());
                    while(iterator.hasNext()){
                    	Row currentRow = iterator.next();
                    	Iterator<Cell> cellIterator = currentRow.iterator();
                    	
                    	while(cellIterator.hasNext()){
                    		Cell currentCell = cellIterator.next();
                    		//Conditional for the name field
                    		if(currentCell.getColumnIndex() == 1 && currentCell.getRowIndex() == 0){
                    			gradList.get(gradList.size() - 1).setName(currentCell.getStringCellValue());
                    		}
                    		
                    		//Conditional for the phone number field
                    		else if(currentCell.getColumnIndex() == 1 && currentCell.getRowIndex() == 1){
                    			gradList.get(gradList.size() - 1).setPhoneNumber(currentCell.getStringCellValue());
                    		}
                    		
                    		//conditional to check if the current cell is part of the available times data
                    		else if(currentCell.getColumnIndex() >= 1 && currentCell.getColumnIndex() <= 5 && currentCell.getRowIndex() >= 3 && currentCell.getRowIndex() <= 15){
                    			if(currentCell.getStringCellValue().equals("")){
                    				//System.out.println("true");
                    				String timeOfTheDay = "";
                    				switch(currentCell.getRowIndex() - 3){
                    					case 0: timeOfTheDay = "8am";
                    							break;
                    					case 1: timeOfTheDay = "9am";
            									break;
                    					case 2: timeOfTheDay = "10am";
    											break;
                    					case 3: timeOfTheDay = "11am";
    											break;
                    					case 4: timeOfTheDay = "12pm";
    											break;
                    					case 5: timeOfTheDay = "1pm";
    											break;
                    					case 6: timeOfTheDay = "2pm";
    											break;
                    					case 7: timeOfTheDay = "3pm";
    											break;
                    					case 8: timeOfTheDay = "4pm";
    											break;
                    					case 9: timeOfTheDay = "5pm";
    											break;
                    					case 10: timeOfTheDay = "6pm";
    											break;
                    					case 11: timeOfTheDay = "7pm";
    											break;
                    					case 12: timeOfTheDay = "8pm";
    											break;
                    						
                    				}//end switch case statement
                    				gradList.get(gradList.size() - 1).setAvailableAt(currentCell.getColumnIndex() - 1, timeOfTheDay);
                    			}//if conditional for setting availability of the tiem of the day.
                    		}//end if conditional for the times section
                    		
                    		//conditional for the qualifications
                    		else if(currentCell.getRowIndex() >= 3 && currentCell.getRowIndex() <= 10 && currentCell.getColumnIndex() == 7){
                    			if (!currentCell.getStringCellValue().isEmpty())
                    			{
                    				gradList.get(gradList.size() - 1).addQualification(currentCell.getStringCellValue());
                    			}                    			
                    		}//end if for the qualifications
                    	}
                    }
                    workbook.close();
                }
                resultsText.appendText("End reading in Graduate Assistant files.\n");
            }//end if
            else{
            	resultsText.appendText("No files were selected for the Graduate Students.\n");
            }
            
    }//end selectFiles method for the grad students

	
	
	
	@FXML
	/**
	 * Method for selecting offered classes and reading the data in.
	 * @param event
	 * @throws Exception
	 */
	private void selectClasses(ActionEvent event) throws Exception {
		resultsText.appendText("Loading Classes.\n");
		List<File> classes = fileChooser.showOpenMultipleDialog(null);

		if (classes != null) 
		{
			for (File file : classes) 
			{
				FileInputStream fIP = new FileInputStream(file);
				Workbook workbook = new XSSFWorkbook(fIP);
				Sheet datatypeSheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = datatypeSheet.iterator();

				String professorName = "GEORGE"; // create a professor's Name
				
				while (iterator.hasNext()) // Iterate over rows
				{
					Row currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator(); // 
					
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						// Get the professor -> This only happens once in the
						// entire file...
						if (currentRow.getRowNum() == 0 && currentCell.getColumnIndex() == 1) 
						{
							professorName = currentCell.getStringCellValue(); // assign value to
						}

						else if (currentCell.getRowIndex() >= 4 && currentCell.getRowIndex() <= 15) 
						{
							// Get the class number
							if (currentCell.getColumnIndex() == 0) 
							{
								if (currentCell.getStringCellValue().isEmpty()) 
								{
									break;
								} 
								else 
								{
									classList.add(new Class());
									classList.get(classList.size() -1).setProfessor(professorName);
									classList.get(classList.size() - 1).setClassNumber(currentCell.getStringCellValue());
								}
							}

							// Get the start time
							else if (currentCell.getColumnIndex() == 1)
							{
								classList.get(classList.size() - 1).setStartTime(currentCell.getStringCellValue());
							}

							// Get the end time
							else if (currentCell.getColumnIndex() == 2)
							{
								classList.get(classList.size() - 1).setEndTime(currentCell.getStringCellValue());
							}

							// Get the days of the week
							else if (currentCell.getColumnIndex() >= 3 && currentCell.getColumnIndex() <= 7)
							{
								if (currentCell.getStringCellValue().equals("Has Class"))
								{
									classList.get(classList.size() - 1).addDayOfWeek((int)currentCell.getColumnIndex() - 3);
								}
							}
							// Get the prep time
							else if (currentCell.getColumnIndex() == 8)
							{
								classList.get(classList.size() - 1).setPrepHours((int)currentCell.getNumericCellValue());
							}
						}
					}
				}
				workbook.close();
			}		               
			resultsText.appendText("Classes Loaded!\n");               
		} 
		else 
		{
			resultsText.appendText("No Classes loaded.\n");
		}
		
		// Checking results for correctness
		System.out.println("-----Reading in Classes-----");
		for (Class weeklyClass : classList)
		{
			System.out.println("Class Name: " + weeklyClass.getClassNumber());
			System.out.println("Professor: " + weeklyClass.getProfessor());
			System.out.println("Start Time: " + weeklyClass.getStartTime());
			System.out.println("End Time: " + weeklyClass.getEndTime());
			System.out.println("Days of Week: " + weeklyClass.getDaysOfWeek());
			System.out.println("Prep Hour: " + weeklyClass.getPrepHours());
		}
		System.out.println();
	}
	
	
	public void handle(ActionEvent event) {
		System.out.println("in the save file method");
		
		FileChooser fileChooser = new FileChooser();
		//Desktop desktop =Desktop.getDesktop();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel files (*.xls)", "*.xls");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			saveFile(file);
			System.out.println("File Saved");
		}
		
	}

	private void saveFile(File file) {
		int rowCounter = 0;
		int columnCounter = 0;
		int numberOfGraduatesWritten = 0;
		final int ROW_OFFSET = 16;
		final int COLUMN_OFFSET = 7;

		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("POI Worksheet");
			
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			cellStyle.setWrapText(true);
			
			//create the busy cell style
			CellStyle busyStyle;
			busyStyle = workbook.createCellStyle();
			busyStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
			busyStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);;

			busyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			busyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			busyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			busyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			busyStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			busyStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			busyStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			busyStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			
			//busyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			CellStyle classStyle = workbook.createCellStyle();
			classStyle.setWrapText(true);
			classStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			classStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			classStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			classStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			classStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			classStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			classStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			classStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			
			
			CellStyle freeStyle = workbook.createCellStyle();
			freeStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			freeStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			freeStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			freeStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			freeStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			freeStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			freeStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			freeStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			
			
			
			//create an iterator of the graduate assistant
			Iterator<GraduateAssistant> itr = gradList.listIterator();
			GraduateAssistant currentGrad;
			
			//loop through all graduate assistant
			while(itr.hasNext()){
				currentGrad = itr.next();
				
				rowCounter = ((numberOfGraduatesWritten / 2) * ROW_OFFSET);
				columnCounter = ((numberOfGraduatesWritten % 2) * COLUMN_OFFSET);
				
				
				//creating cells for times of the day
				for(int rowIndex = 0; rowIndex < 14; rowIndex++){
					try{
						worksheet.getRow((short) rowIndex + rowCounter).equals(null);
					}
					catch(java.lang.NullPointerException e){
						worksheet.createRow((short) rowIndex + rowCounter);
					}
					worksheet.getRow((short) rowIndex + rowCounter).createCell((short) 0 + columnCounter);
					
					worksheet.getRow(rowIndex + rowCounter).getCell(0 + columnCounter).setCellStyle(cellStyle);
				}
				
				//set the time value on the left hand side of the excel file
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
				
				//create the rows necessary
				for(int columnIndex = 1; columnIndex < 6; columnIndex++){
					worksheet.getRow(0 + rowCounter).createCell((short) columnIndex + columnCounter);
					
					worksheet.getRow(0 + rowCounter).getCell(columnIndex + columnCounter).setCellStyle(cellStyle);

				}
				
				//set the days of the week at the top of the excel file
				worksheet.getRow(0 + rowCounter).getCell(1 + columnCounter).setCellValue("Monday");
				worksheet.getRow(0 + rowCounter).getCell(2 + columnCounter).setCellValue("Tuesday");
				worksheet.getRow(0 + rowCounter).getCell(3 + columnCounter).setCellValue("Wednesday");
				worksheet.getRow(0 + rowCounter).getCell(4 + columnCounter).setCellValue("Thursday");
				worksheet.getRow(0 + rowCounter).getCell(5 + columnCounter).setCellValue("Friday");
				worksheet.autoSizeColumn(1 + columnCounter);
				worksheet.autoSizeColumn(2 + columnCounter);
				worksheet.autoSizeColumn(3 + columnCounter);
				worksheet.autoSizeColumn(4 + columnCounter);
				worksheet.autoSizeColumn(5 + columnCounter);
				
				
				
				//loop through all class the graduate student is going to be assisting
				for(int classIndex = 0; classIndex < currentGrad.getAssignedClasses().size(); classIndex++){
					Class currentClass = currentGrad.getAssignedClasses().get(classIndex);
					String startTime = currentClass.getStartTime();
					String endTime = currentClass.getEndTime();
					ArrayList<Integer> dayList = currentClass.getDaysOfWeek();
					
					//loop through the list of days for the current class
					for(int z = 0; z < dayList.size(); z++){
						boolean isTheEndTime = false;
						int startInt = stringToInt(startTime);
						int endInt = stringToInt(endTime);
						while(startInt < endInt){
							try{
								worksheet.getRow(startInt + 1 + rowCounter).getCell(dayList.get(z) + 1 + columnCounter).equals(null);
							}catch(java.lang.NullPointerException e){
								worksheet.getRow(startInt + 1 + rowCounter).createCell(dayList.get(z) + 1 + columnCounter);
							}
							worksheet.getRow(startInt + 1 + rowCounter).getCell(dayList.get(z) + 1 + columnCounter).setCellValue(currentClass.getClassNumber() + "\n" + currentClass.getProfessor());
							worksheet.getRow(startInt + 1 + rowCounter).getCell(dayList.get(z) + 1 + columnCounter).setCellStyle(classStyle);
							worksheet.autoSizeColumn(dayList.get(z) + 1 + columnCounter);
							startInt += 1;
							
						}//end while
						
					}//end for loop for the number of days in the class
					
					//loop set all remaining busy days to black
					for(int day = 1; day < 6; day++){
						for(int j = 1; j < 14; j++){
							String time = "";
							switch(j){
								case 1:
									time = "8am";
									break;
								case 2:
									time = "9am";
									break;
								case 3:
									time = "10am";
									break;
								case 4:
									time = "11am";
									break;
								case 5:
									time = "12pm";
									break;
								case 6:
									time = "1pm";
									break;
								case 7:
									time = "2pm";
									break;
								case 8:
									time = "3pm";
									break;
								case 9:
									time = "4pm";
									break;
								case 10:
									time = "5pm";
									break;
								case 11:
									time = "6pm";
									break;
								case 12:
									time = "7pm";
									break;
								case 13:
									time = "8pm";
									break;
							}//end switch
							
							if(!currentGrad.isAvailable(day - 1, time)){
								try{
									worksheet.getRow(j + rowCounter).getCell(day + columnCounter).equals(null);
								}catch(java.lang.NullPointerException e){
									
									worksheet.getRow(j + rowCounter).createCell(day + columnCounter);
									worksheet.getRow(j + rowCounter).getCell(day + columnCounter).setCellStyle(busyStyle);
									
								}
								
							}//end if for if is available at the given time
							else{
								try{
									worksheet.getRow(j + rowCounter).getCell(day + columnCounter).equals(null);
								}catch(java.lang.NullPointerException e){
									
									worksheet.getRow(j + rowCounter).createCell(day + columnCounter);
									worksheet.getRow(j + rowCounter).getCell(day + columnCounter).setCellStyle(freeStyle);
									
								}
							}
						}//end inner for loop
					}//end outer for loop
					
				}//end for loop	
				
				if(numberOfGraduatesWritten % 10 == 0){
					resultsText.appendText("Algorithm Still running please do not close.");
				}
				numberOfGraduatesWritten += 1;
			}//end while loop
			

			//worksheet.getRow(1).getCell(0).setCellStyle(cellStyle);
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			resultsText.appendText("File has been saved!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}//end save file method

	private static String intToDay(int intDay){
		switch(intDay){
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
			return null;
		}//end switch
	}//end method intToDay


	private static int stringToInt(String stringDay){
		switch(stringDay){
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
		}//end switch
	}//end method intToDay


	@FXML
	private void runAlgorithm(ActionEvent event) {
		
		double totalHours = 0;
		double workedHours = -1;
		int iterations = 0;
		
		Algorithm alg = new Algorithm(gradList, classList);
		
		ArrayList<Class> unassigned = null;
		
		while(totalHours != workedHours)
		{
			totalHours = 0;
			workedHours = 0;
			
			alg.reset();
			alg.initializeGraph();
			unassigned = alg.createInitialSolution();
			
			for (Class weekly : unassigned)
			{
				alg.partialAssignment(weekly);
			}
			
			totalHours = alg.getClassHours();
			workedHours = alg.getStudentHours();
			iterations++;
			
			// Attempt 2000 times
			if (iterations > 2000)
			{
				break;
			}
		}
		
		for (GraduateAssistant ga : gradList)
		{
			System.out.println(ga.getName() + " is working " + ga.getHoursAssigned());
		}
		System.out.print(totalHours);
		System.out.print(" " + workedHours);
		System.out.print("\t " + iterations);
		System.out.println();	
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx", ".xls"));

	}

}
