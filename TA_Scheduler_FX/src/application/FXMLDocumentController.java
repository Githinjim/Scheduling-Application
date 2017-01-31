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
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
    @FXML TextArea resultsText;
    
   
	@FXML
	private void selectFiles(ActionEvent event) throws Exception {
		List<File> list =
                fileChooser.showOpenMultipleDialog(null);
            if (list != null) {
                for (File file : list) {
                    FileInputStream fIP = new FileInputStream(file);
                    //XSSFWorkbook workbook = new XSSFWorkbook(fIP);
                    Workbook workbook = new XSSFWorkbook(fIP);
                    //XSSFSheet spreadsheet = workbook.createSheet();
                    //XSSFRow row = spreadsheet.createRow(0);
                    //Cell cell = row.createCell(0);
                    //cell.setCellValue("test");
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
                    		
                    		//Conditional for the phonenumber field
                    		if(currentCell.getColumnIndex() == 1 && currentCell.getRowIndex() == 1){
                    			gradList.get(gradList.size() - 1).setPhoneNumber(currentCell.getStringCellValue());
                    		}
                    		
                    		//conditional to check if the current cell is part of the available times data
                    		if(currentCell.getColumnIndex() >= 1 && currentCell.getColumnIndex() <= 5 && currentCell.getRowIndex() >= 3 && currentCell.getRowIndex() <= 15){
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
                    			}//if conditional for setting avalablity of the tiem of the day.
                    		}//end if conditional for the times section
                    		
                    		//conditional for the qualifications
                    		if(currentCell.getRowIndex() >= 3 && currentCell.getRowIndex() <= 10 && currentCell.getColumnIndex() == 7){
                    			
                    			if(currentCell.getNumericCellValue() != Cell.CELL_TYPE_NUMERIC){
                    				String qualificationToAdd = "";
                    				int theint = (int) (currentCell.getNumericCellValue() / 1);
                    				switch(Integer.toString(theint)){
                    	
                    				case "1":
                    					qualificationToAdd = "Fitness Lab Supervision";
                    					break;
                    				case "2":
                    					qualificationToAdd = "Gross Anatomy Lecture and Lab";
                    					break;
                    				case "3":
                    					qualificationToAdd = "Open Anatomy Cadaver Lab Study Session";
                    					break;
                    				case "4":
                    					qualificationToAdd = "Physiology lecture & Lab";
                    					break;
                    				case "5":
                    					qualificationToAdd = "Physiology of exercise, lecture and lab";
                    					break;
                    				case "6":
                    					qualificationToAdd = "Assesment and treatment of athletic injuries";
                    					break;
                    				case "7":
                    					qualificationToAdd = "Fitness assessment and exercise Prescription";
                    					break;
                    				case "8":
                    					qualificationToAdd = "Introduction to Biomechanics";
                    					break;
                    				case "9":
                    					qualificationToAdd = "Clinical Physiology";
                    					break;
                    				case "10":
                    					qualificationToAdd = "Clinical Biomechanics, lecture and lab";
                    					break;
                    				case "11":
                    					qualificationToAdd = "Sports Biomechanics lecture and lab";
                    					break;
                    				case "12":
                    					qualificationToAdd = "Sports Nutrition";
                    					break;
                    					
                    				
                    				}//end switch
                    				gradList.get(gradList.size() - 1).addQualification(qualificationToAdd);
                    			}//end if for if the qualifications is empty
                    			
                    		}//end if for the qualifications
                    		
                    		
                    		
                    		/**
                    		if (currentCell.getCellTypeEnum() == CellType.STRING) {
                                System.out.print(currentCell.getStringCellValue() + "--");
                            } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                                System.out.print(currentCell.getNumericCellValue() + "--");
                            }
                    		*/
                    	}
                    }
                    
                    //System.out.println(workbook.getName(name));

                    //FileOutputStream fOP = new FileOutputStream(file);
                    //workbook.write(fOP);

                    //fOP.close();
                	
                }
            }//end if
            else{
            	resultsText.appendText("No files were selected for the Graduate Students\n");
            }
            
            //just some test code to test if the data population is working
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
    }//end selectFiles method for the grad students

	 @FXML 
	private void selectClasses(ActionEvent event) throws Exception {
		resultsText.appendText("Loading Classes.\n");
		List<File> classes = fileChooser.showOpenMultipleDialog(null);

		if (classes != null) {
			for (File file : classes) {
				FileInputStream fIP = new FileInputStream(file);
				Workbook workbook = new XSSFWorkbook(fIP);
				Sheet datatypeSheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = datatypeSheet.iterator();

				String professorName = "";

				while (iterator.hasNext()) {
					Row currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator();

					if (currentRow.getRowNum() == 0) 
					{
						Cell currentCell = currentRow.getCell(1);
						professorName = currentCell.getStringCellValue();
					} 
					else 
					{
						while (cellIterator.hasNext()) 
						{
							Cell currentCell = cellIterator.next();
							if (currentCell.getColumnIndex() == 0 && currentCell.getRowIndex() >= 4
									&& currentCell.getRowIndex() <= 15) 
							{
								// Determine if there is another class that
								// needs to be created
								if (currentCell.getStringCellValue().isEmpty()) 
								{
									break;
								} 
								else 
								{
									classList.add(new Class());
									classList.get(classes.size() - 1).setClassNumber(currentCell.getStringCellValue());
									classList.get(classes.size() - 1).setProfessor(professorName);
								}
							}
							// conditional to set Start Time
							else if (currentCell.getColumnIndex() == 1 && currentCell.getRowIndex() >= 4
									&& currentCell.getRowIndex() <= 15) 
							{
								classList.get(classes.size() - 1).setStartTime(currentCell.getStringCellValue());
							}

							// conditional to set End Time
							else if (currentCell.getColumnIndex() == 2 && currentCell.getRowIndex() >= 4
									&& currentCell.getRowIndex() <= 15) 
							{
								classList.get(classes.size() - 1).setEndTime(currentCell.getStringCellValue());
							}

							// conditional to set Prep Time
							else if (currentCell.getColumnIndex() == 8 && currentCell.getRowIndex() >= 4
									&& currentCell.getRowIndex() <= 15) 
							{
								classList.get(classes.size() - 1).setPrepHours((int) currentCell.getNumericCellValue());
							}

							// conditional to set Day of Week class is available
							else if (currentCell.getColumnIndex() >= 3 && currentCell.getColumnIndex() <= 7
									&& currentCell.getRowIndex() >= 4 && currentCell.getRowIndex() <= 15) 
							{
								if (currentCell.getStringCellValue().equals("Has Class")) 
								{
									classList.get(classes.size() - 1)
											.addDayOfWeek((int) currentCell.getColumnIndex() - 3);
								}
							}
						}
					}
				}
			}
		               // testing purposes
		               resultsText.appendText("Classes Loaded!\n");
		               System.out.println(classList.get(classes.size()-1).getClassNumber());
		               System.out.println(classList.get(classes.size()-1).getStartTime());
		               System.out.println(classList.get(classes.size()-1).getEndTime());
		               System.out.println(classList.get(classes.size()-1).getProfessor());
		               System.out.println(classList.get(classes.size()-1).getDaysOfWeek());
		               System.out.println(classList.size());
		} 
		else {
			resultsText.appendText("No Classes loaded.\n");
		}
	}

	
	
	public void handle(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		//Desktop desktop =Desktop.getDesktop();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)", "*.xls");
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

//		// Fake creation of Class
//		Class class1 = new Class();
//		class1.setClassNumber("123");
//		class1.addDayOfWeek(0);
//		class1.setStartTime(startTime);
//		class1.setEndTime(endTime);
//
//		Class class2 = new Class();
//		class2.setClassNumber("456");
//		class2.addDayOfWeek(1);
//		class2.setStartTime(startTime);
//		class2.setEndTime(endTime);
//
//		Algorithm schedule = new Algorithm();
//		schedule.addClass(class1);
//		schedule.addClass(class2);
//		schedule.addGradStudent(student1);
//		schedule.addGradStudent(student2);
//
//		schedule.initializeGraph();
//		schedule.createInitialSolution();
//
//		System.out.println(class2.getAssignedGA().getName());
//		 System.out.println(student1.getAssignedClasses().get(0).getClassNumber());

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
