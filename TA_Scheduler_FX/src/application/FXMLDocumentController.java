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
    	
    	//System.out.println(class2.getAssignedGA().getName());
    	//System.out.println(student1.getAssignedClasses().get(0).getClassNumber());
    	
    	// Demo of determining if the student is available for a certain class.
    	//System.out.println(student.isAvailable(myClass.getDaysOfWeek(), myClass.getStartTime(), myClass.getEndTime()));
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx", ".xls"));
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
        
    }    
    
}
