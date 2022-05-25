package controller;

import beans.Employee;
import beans.EmployeeLogins;
import com.jfoenix.controls.JFXToggleButton;
import epicpos.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tables.EmployeeLoginsManager;
import tables.EmployeeManager;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.controlsfx.dialog.Dialog;
//import org.controlsfx.dialog.Dialogs;
/**
 * Login Controller.
 */
public class LoginController implements Initializable {

    private Stage stage = null;
    @FXML
    Label errorMessage;
    private ResultSet rs;
    private Main application;
    private Employee loggedUser;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField tfUsername;
    @FXML
    private Button btnClose;
    private InetAddress ip;

    private static int sessionID = 0;
    private String currentSessionId;
    @FXML
    private JFXToggleButton tsConnection;

    @FXML
    private void btnCloseOnAction() {
        stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            this.ip = InetAddress.getLocalHost();
//          System.out.println(ip.getHostName());
        } catch (UnknownHostException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentSessionId = generateSessionID();
    }

    @FXML
    public void processLogin(ActionEvent event) throws SQLException {
        if (application == null) {
        } else {
            if (isValidCondition()) {

                Employee bean = EmployeeManager.validate(tfUsername.getText(), tfPassword.getText());

                if (bean.getPassword() == null || bean.getUsername() == null) {
                    System.out.println("Been is null");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Incorrect Username / Password");
                    String s = "Username/Password is Incorrect";
                    alert.setContentText(s);
                    alert.showAndWait();
                    tfUsername.requestFocus();
                    tfPassword.setText("");
                    errorMessage.setText("Username/Password is incorrect");
                } else {
                    if (!application.userLogging(tfUsername.getText(), tfPassword.getText())) {
                        errorMessage.setText("Username/Password is incorrect");
                    }

                    String timeStamp;
                    Format formatter;
                    Date date = new Date();

                    formatter = new SimpleDateFormat("YYYY-MM-DD");
                    timeStamp = formatter.format(date);
                    EmployeeLogins beans = new EmployeeLogins();
//                    beans.setDate(timeStamp);
                    beans.setUsername(bean.getUsername());
                    beans.setSessionId(ip.getHostName());
                    beans.setStatus(1);
                    beans.setSalesOutletId(ip.getHostName());
                    application.sessionId = ip.getHostName();
                    try {
                        EmployeeLoginsManager.insertLogin(beans);
                    } catch (Exception ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    application.gotoPosClient();
                }
            }
        }
    }

    public void setApp(Main application) {
        this.application = application;
    }

    private boolean isValidCondition() {
        boolean validCondition = true;

        if (tfUsername.getText().trim().isEmpty() || tfPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Enter Valid Text");
            String s = "Text should be at least 5 characters long. " + "Enter valid username and password.";
            alert.setContentText(s);
            alert.showAndWait();
            validCondition = false;
            tfUsername.requestFocus();
        } else {
            validCondition = true;
        }

        return validCondition;
    }

    public static int ticketGenerator() {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((100 - 1) + 1) + 1;
        return randomNum;
    }

    private String generateSessionID() {
//        sessionID++;
        return (ip.getHostName() + ticketGenerator());
    }
}
