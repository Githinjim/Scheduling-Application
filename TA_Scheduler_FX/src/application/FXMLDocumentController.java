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
                    Workbook workbook = new XSSFWorkbook(fIP);
                    Sheet datatypeSheet = workbook.getSheetAt(0);
                    Iterator<Row> iterator = datatypeSheet.iterator();
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
            }//end if
            else{
            	resultsText.appendText("No files were selected for the Graduate Students\n");
            }
            
            //just some test code to test if the data population is working
            System.out.println("-----Reading in Grad Students-----");
            for(int i = 0; i < gradList.size(); i ++){
            	System.out.println("Grad Student: " + gradList.get(i).getName());
            	System.out.println("Has phone number: " + gradList.get(i).getPhoneNumber());
            	System.out.println("They have qualifications of:");
            	for(int j = 0; j < gradList.get(i).getQualifications().size(); j++){
            		System.out.println(gradList.get(i).getQualifications().get(j));
            	}
            	System.out.println("Is available on Monday at 3pm: " + gradList.get(i).isAvailable(0, "3pm"));
            	System.out.println("Is available on Wednesday at 2pm: " + gradList.get(i).isAvailable(2, "2pm"));
            }
            System.out.println();
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

	private void saveFile(File file) {
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
			workbook.close();
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
		Algorithm alg = new Algorithm(gradList, classList);
		alg.initializeGraph();
		alg.createInitialSolution();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx", ".xls"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));

	}

}
