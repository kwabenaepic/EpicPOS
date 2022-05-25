/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Business;
import beans.Employee;
import epicpos.Preferences;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tables.BusinessManager;
import tables.EmployeeManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
//import library.assistant.data.model.MailServerInfo;
//import library.assistant.database.DataHelper;
//import library.assistant.database.DatabaseHandler;
//import library.assistant.database.export.DatabaseExporter;
//import library.assistant.ui.mail.TestMailController;
//import library.assistant.util.LibraryAssistantUtil;
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class SettingsController implements Initializable {
    @FXML
    private TextField tfUploadImage;
    @FXML
    private TextField serverName;
    @FXML
    private TextField smtpPort;
    @FXML
    private Button btnUpload;
    @FXML
    private TextField tfCompanyName;
    @FXML
    private TextField tfContactOne;
    @FXML
    private TextField tfContactTwo;
    @FXML
    private TextField tfLocation;
    @FXML
    private TextArea taAddress;

    @FXML
    private ImageView picBoxImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initDefaultValues();
    }

    @FXML
    private void handleSaveButtonAction(ActionEvent event) throws Exception {


        Preferences preferences = Preferences.getPreferences();
        preferences.setCompanyName(tfCompanyName.getText());
        preferences.setContactOne(tfContactOne.getText());
        preferences.setContactTwo(tfContactTwo.getText());
        preferences.setLocation(tfLocation.getText());
        preferences.setAddress(taAddress.getText());

        Preferences.writePreferenceToFile(preferences);
        saveEmployeeInfo();

    }

//    private Stage getStage() {
////        return ((Stage) nDaysWithoutFine.getScene().getWindow());
//    }
    private void initDefaultValues() {
        Preferences preferences = Preferences.getPreferences();
        tfCompanyName.setText(String.valueOf(preferences.getCompanyName()));
        tfContactOne.setText(String.valueOf(preferences.getContactOne()));
        tfContactTwo.setText(String.valueOf(preferences.getContactTwo()));
        tfLocation.setText(String.valueOf(preferences.getLocation()));
        taAddress.setText(String.valueOf(preferences.getAddress()));

//        String passHash = String.valueOf(preferences.getPassword());
//        password.setText(passHash.substring(0, Math.min(passHash.length(), 10)));
//        loadMailServerConfigurations();
    }

//    private MailServerInfo readMailSererInfo() {
//        try {
//            MailServerInfo mailServerInfo
//                    = new MailServerInfo(serverName.getText(), Integer.parseInt(smtpPort.getText()), emailAddress.getText(), emailPassword.getText(), sslCheckbox.isSelected());
//            if (!mailServerInfo.validate() || !LibraryAssistantUtil.validateEmailAddress(emailAddress.getText())) {
//                throw new InvalidParameterException();
//            }
//            return mailServerInfo;
//        } catch (Exception exp) {
//            AlertMaker.showErrorMessage("Invalid Entries Found", "Correct input and try again");
//            LOGGER.log(Level.WARN, exp);
//        }
//        return null;
//    }
    private void loadMailServerConfigurations() {
//        MailServerInfo mailServerInfo = DataHelper.loadMailServerInfo();
//        if (mailServerInfo != null) {
//            LOGGER.log(Level.INFO, "Mail server info loaded from DB");
//            serverName.setText(mailServerInfo.getMailServer());
//            smtpPort.setText(String.valueOf(mailServerInfo.getPort()));
//            emailAddress.setText(mailServerInfo.getEmailID());
//            emailPassword.setText(mailServerInfo.getPassword());
//            sslCheckbox.setSelected(mailServerInfo.getSslEnabled());
//        }
    }

    @FXML
    private void handleDatabaseExportAction(ActionEvent event) {
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setTitle("Select Location to Create Backup");
//        File selectedDirectory = directoryChooser.showDialog(getStage());
//        if (selectedDirectory == null) {
//            AlertMaker.showErrorMessage("Export cancelled", "No Valid Directory Found");
//        } else {
//            DatabaseExporter databaseExporter = new DatabaseExporter(selectedDirectory);
//            progressSpinner.visibleProperty().bind(databaseExporter.runningProperty());
//            new Thread(databaseExporter).start();
//        }
    }

    public void handleUploadButtonAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");

        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image         = SwingFXUtils.toFXImage(bufferedImage, null);

                 picBoxImageView.setImage(image);
                tfUploadImage.setText(file.getAbsolutePath());

            } catch (IOException ex) {
                Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void saveEmployeeInfo() throws Exception {
        Business bean = new Business();

        bean.setName(tfCompanyName.getText());
        bean.setMobile(tfContactOne.getText());
        bean.setPhone(tfContactTwo.getText());
        bean.setLocation(tfLocation.getText());
        bean.setAddress(taAddress.getText());

        boolean result = BusinessManager.insert(bean);
        if (result == true) {
            Notifications notification = Notifications.create()
                    .hideAfter(Duration.seconds(6))
                    .title("Company Information")
                    .text("Business Information Added Successfully!")
                    .graphic(null)
                    .darkStyle()
                    .position(Pos.CENTER);
            notification.show();
//            clearFields();
        }
    }
}
