package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    private Stage                  stage = null;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnClose;


    public void initialize(URL url, ResourceBundle rb) {



    }

    public void btnOkOnAction(ActionEvent actionEvent) {
        stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
