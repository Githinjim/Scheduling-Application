/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Matthew
 */
public class FXMLDocumentController implements Initializable {
    FileChooser chooser = new FileChooser();
    @FXML
    private void handleButtonAction(ActionEvent event) {
        chooser.showOpenMultipleDialog(null);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chooser.getExtensionFilters().add(new ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx", ".xls"));
        chooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
        
    }    
    
}
