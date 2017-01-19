/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
        List<File> list =
                fileChooser.showOpenMultipleDialog(null);
            if (list != null) {
                for (File file : list) {
                    FileInputStream fIP = new FileInputStream(file);
                    XSSFWorkbook workbook = new XSSFWorkbook(fIP);
                    XSSFSheet spreadsheet = workbook.createSheet();
                    XSSFRow row = spreadsheet.createRow(0);
                    Cell cell = row.createCell(0);
                    cell.setCellValue("test");
                    
                    FileOutputStream fOP = new FileOutputStream(file);
                    workbook.write(fOP);
                    
                    fOP.close();
                	
                }
            }
    }
    
    @FXML
    private void runAlgorithm(ActionEvent event) {
    	// Fake creation of GA
    	GraduateAssistant student = new GraduateAssistant("bob", "123-456-7890");
    	student.setAvailableAt(0, "8am");
    	student.setAvailableAt(2, "8am");
    	student.setAvailableAt(4, "8am");
    	
    	
    	String startTime = "8am";
    	String endTime = "9am";
    	
    	// Fake creation of Class
    	Class myClass = new Class();
    	myClass.setClassNumber("123");
    	myClass.addDayOfWeek(0);
    	myClass.addDayOfWeek(2);
    	myClass.addDayOfWeek(4);
    	myClass.setStartTime(startTime);
    	myClass.setEndTime(endTime);
    	
    	Class test = new Class();
    	test.setClassNumber("456");
    	test.addDayOfWeek(1);
    	test.setStartTime(startTime);
    	test.setEndTime(endTime);
      	
    	Scheduler schedule = new Scheduler();
    	schedule.addClass(myClass);
    	schedule.addClass(test);
    	schedule.addGradStudent(student);
    	
    	schedule.initializeGraph();
    	schedule.createInitialSolution();
    	
    	System.out.println(myClass.getAssignedGA().getName());
    	
    	// Demo of determining if the student is available for a certain class.
    	//System.out.println(student.isAvailable(myClass.getDaysOfWeek(), myClass.getStartTime(), myClass.getEndTime()));
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx", ".xls"));
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
        
    }    
    
}
