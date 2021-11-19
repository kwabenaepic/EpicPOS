/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class GetCashPaidController implements Initializable {

    @FXML
    private Button btnOk;
    @FXML
    private Button btnClose;
    private Stage stage = null;
    @FXML
    private TextField tfCashPaid;
    
    private Double cashPaid;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
   
//            tfCashPaid.focusedProperty().addListener(new ChangeListener<Boolean>() {
////            NumberFormat nf = NumberFormat.getCurrencyInstance();
//
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
//                if (newPropertyValue) {
//                    sum.add(a);
//                    cashPaid = Double.parseDouble(tfCashPaid.getText());
//                    System.out.println("Textfield on focus: " + cashPaid);
//                }  
//            }
//        });
    }    

    @FXML
    private void btnOkOnAction(ActionEvent event) {
          cashPaid = Double.parseDouble(tfCashPaid.getText());
             System.out.println("Textfield on focus: " +  showDialogCash());
//             showDialog();
//             stage = (Stage) btnClose.getScene().getWindow();
//             stage.close();
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void tfCashPaidOnAction(ActionEvent event) {
           
        
    }
    
        private String showDialogCash() {
        String qty = null;
        TextInputDialog dialog = new TextInputDialog("Add Quantity");
        dialog.setTitle("Add Quantity");
//        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Enter Quantity: ");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            qty = result.get();

        }
        return qty;
    }
    
}
