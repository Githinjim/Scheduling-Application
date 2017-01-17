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
    private void handleButtonAction(ActionEvent event) throws Exception {
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx", ".xls"));
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
        
    }    
    
}
