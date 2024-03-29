package controller;

//import com.jfoenix.controls.JFXButton;

import beans.Employee;
import epicpos.Main;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author KwabenaEpic
 */
public class DashboardController implements Initializable {

    @FXML
    private Button btnHome;
    @FXML
    private StackPane holderPane;
    AnchorPane about, home, inventory, employees, sales, reports, widgets, controls, stockSummaryAmbient, meterReconciliation, settings;
    @FXML
    private AnchorPane acMain;
    @FXML
    private AnchorPane acDashBord;
    @FXML
    private ImageView imgStoreBtn;
    @FXML
    private ImageView imgSellBtn;
    @FXML
    private ImageView imgSettingsBtn;
    @FXML
    private BorderPane appContent;
    @FXML
    private AnchorPane acHead;
    private Label lblUsrNamePopOver;
//    private Label lblFullName;
    private Label lblUsrName;
    @FXML
    private Label lblUserId;

    @FXML
    private ImageView imgAboutBtn1;
    @FXML
    private Button btnMeterReconciliation;
    @FXML
    private Button btnAbout111;
    @FXML
    private ImageView imgAboutBtn111;

    private Main application;
    @FXML
    private Button btnInventory;
    @FXML
    private Button btnEmployees;
    @FXML
    private ImageView ivUserImage;
//    private Employee loggedUser;
    @FXML
    private Label lblHour;
    @FXML
    private Label dateLocal1;
    @FXML
    private Label lblMinutes;
    @FXML
    private Label dateLocal11;
    @FXML
    private Label lblSeconds;
    @FXML
    private Label dateLocal;
    @FXML
    private Button btnSales;
    @FXML
    private Button btnCustomers;
    private Integer currentMonth;
    @FXML
    private Button btnReports;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnSettings;
    @FXML
    private Label staffNameLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        disPlayDateTime();
        displayDate();
        try {
            home = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
            inventory = FXMLLoader.load(getClass().getResource("/view/Inventory.fxml"));
            employees = FXMLLoader.load(getClass().getResource("/view/Employees.fxml"));
            sales = FXMLLoader.load(getClass().getResource("/view/SalesReports.fxml"));
            reports = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
            settings = FXMLLoader.load(getClass().getResource("/view/Settings.fxml"));
            about = FXMLLoader.load(getClass().getResource("/view/About.fxml"));
//            meterReconciliation = FXMLLoader.load(getClass().getResource("/view/MeterReconciliation.fxml"));
//            controls = FXMLLoader.load(getClass().getResource("/views/Controls.fxml"));
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        application.getPrimaryStage().getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void disPlayDateTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Calendar cal = Calendar.getInstance();
            int second = cal.get(Calendar.SECOND);
            int minute = cal.get(Calendar.MINUTE);
            int hour = cal.get(Calendar.HOUR);

            lblHour.setText(hour + "");
            lblMinutes.setText(minute + "");
            lblSeconds.setText(second + "");

        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void displayDate() {
        String s;
        Format formatter;
        Date date = new Date();
        // 29-Jan-02
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
        s = formatter.format(date);
        dateLocal.setText(s);
    }

    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void btnAboutOnClick(ActionEvent event) {
//        setNode(about);
        FXMLLoader fXMLLoader = new FXMLLoader();

        fXMLLoader.setLocation(getClass().getResource("/view/About.fxml"));

        // initializing the controller
//      DailyStockReceiptsController dailystockReceiptsController = new DailyStockReceiptsController();
//      fXMLLoader.setController(dailystockReceiptsController);
        try {
            fXMLLoader.load();

            Parent parent = fXMLLoader.getRoot();
            Scene scene = new Scene(parent);

            scene.setFill(new Color(0, 0, 0, 0));

//            AddEmployeeController addEmployeeController = fXMLLoader.getController();
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void acMain(KeyEvent event) {
    }

    @FXML
    private void acMainOnMouseMove(MouseEvent event) {
    }

//    private void btnCalculatorOnClick(ActionEvent event) {
//        setNode(calculator);
//    }
    @FXML
    private void btnSwitchHome(ActionEvent event) {
        setNode(home);
    }

    public void setApp(Main application) {
        this.application = application;

        Employee loggedUser = application.getLoggedUser();
        staffNameLbl.setText(loggedUser.getFirstName().concat(" " + loggedUser.getLastName()));
//        lblFullName.setText(loggedUser.getFirstName().concat(" " + loggedUser.getLastName()));
//        lblUsrName.setText(loggedUser.getFirstName());
//        lblUsrNamePopOver.setText(loggedUser.getFirstName());
//        if (loggedUser.getImage() != null) {
//            ByteArrayInputStream byteArrayInputStream = null;
//            try {
////                byteArrayInputStream = new ByteArrayInputStream(loggedUser.getImage().getBytes(1, (int) loggedUser.getImage().length()));
//            } catch (SQLException ex) {
//                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
//            }
////            ivUserImage.setImage(new Image(byteArrayInputStream));
//        } else {
//
//          ivUserImage.setVisible(true);
//        }
    }

    private void btnSwitchStockSummaryAmbient(ActionEvent event) {
        setNode(stockSummaryAmbient);
    }

    @FXML
    private void btnSwitchMeterReconciliation(ActionEvent event) {
//        setNode(meterReconciliation);
    }

    @FXML
    private void btnSwitchInventory(ActionEvent event) {
        setNode(inventory);
    }

    @FXML
    private void btnSwitchEmployees(ActionEvent event) {
        setNode(employees);
    }

    private void btnLogOutOnAction(ActionEvent event) {
        application.userLogout();
    }

    @FXML
    private void btnSwitchSales(ActionEvent event) {
        setNode(sales);
    }

    @FXML
    private void btnSwitchCustomers(ActionEvent event) {
    }

    @FXML
    private void btnSwitchReports(ActionEvent event) {
        setNode(reports);
    }

    @FXML
    private void btnLogoutOnAction(ActionEvent event) {
//        application.getPrimaryStage().close();
        application.userLogout();
    }

    @FXML
    private void btnSettingsOnClick(ActionEvent event) {
        setNode(settings);
    }

    @FXML
    private void btnPosOnAction(ActionEvent event) {
        application.gotoPosClient();
    }

}
