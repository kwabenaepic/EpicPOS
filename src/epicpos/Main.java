/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package epicpos;

import beans.Employee;
import beans.EmployeeLogins;
import controller.DashboardController;
import controller.LoginController;
import controller.PosClientController;
import controller.ProgressFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tables.EmployeeLoginsManager;
import tables.EmployeeManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private static Main application;
    private Stage stage;
    private Employee loggedUser;
    public String sessionId;

    public Main() {
        application = this;
    }
//
//    public void gotoHome() {
//        try {
//            HomeController posClient = (HomeController) posClient("Home.fxml");
//            posClient.setApp(this);
//        } catch (Exception ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void gotoLogin() {
        try {
            LoginController loginFrm = (LoginController) login();
            loginFrm.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoPosClient() {
        try {
            PosClientController posClient = (PosClientController) posClient();
            posClient.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void gotoProgressForm() {
//        try {
//            ProgressFormController bounce = (ProgressFormController) bounce();
//            bounce.setApp(this);
//        } catch (Exception ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private Initializable login() throws IOException {
        stage.close();
        this.stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream("/view/login.fxml");
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/view/login.fxml"));
        AnchorPane page;

        try {
            page = loader.load(in);
        } finally {
            in.close();
        }

        // Make scene occupy full screen
//      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//      Scene scene = new Scene(page, screenBounds.getWidth(), screenBounds.getHeight());
        Scene scene = new Scene(page);

        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.sizeToScene();
        this.stage.show();

//      stage.setMaximized(true);
        return (Initializable) loader.getController();
    }

//    private Initializable bounce() throws IOException {
////        stage.close();
//
//        this.stage = new Stage();
//        FXMLLoader loader = new FXMLLoader();
//        InputStream in = Main.class.getResourceAsStream("/view/ProgressForm.fxml");
//        loader.setBuilderFactory(new JavaFXBuilderFactory());
//        loader.setLocation(Main.class.getResource("/view/ProgressForm.fxml"));
//        AnchorPane page;
//
//        try {
//            page = loader.load(in);
//        } finally {
//            in.close();
//        }
//
//        // Make scene occupy full screen
////      Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
////      Scene scene = new Scene(page, screenBounds.getWidth(), screenBounds.getHeight());
//        Scene scene = new Scene(page);
//
//        stage.setScene(scene);
//        stage.initStyle(StageStyle.TRANSPARENT);
//        stage.sizeToScene();
//        this.stage.show();
//
////      stage.setMaximized(true);
//        return (Initializable) loader.getController();
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);

//      launch(args);
    }

    private Initializable posClient() throws IOException {
        stage.close();
        this.stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream("/view/PosClient.fxml");
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/view/PosClient.fxml"));
        AnchorPane page;

        try {
            page = loader.load(in);
        } finally {
            in.close();
        }

        // Make scene occupy full screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(page, screenBounds.getWidth(), screenBounds.getHeight());
//      Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.sizeToScene();
        stage.show();
        stage.setOnHiding(event -> {
//            userLogout();
//            gotoLogin();
        });
        stage.setMaximized(true);
        return loader.getController();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            stage = primaryStage;
//          gotoBounce();
//            gotoPosClient();
            gotoLogin();
//            gotoDashboard();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean userLogging(String username, String password) {
        loggedUser = EmployeeManager.validate(username, password);
        return true;
    }

    public void userLogout() {
        EmployeeLogins bean = new EmployeeLogins();
        try {
            boolean result = EmployeeLoginsManager.updateLogout(this.sessionId);
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        loggedUser = null;
        gotoLogin();
    }

    public Employee getLoggedUser() {
        return loggedUser;
    }

    public Stage getPrimaryStage() {
        return stage;
    }

    public static Main getInstance() {
        return application;
    }

    private Initializable dashboard() throws IOException {
        stage.close();
        this.stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream("/view/Dashboard.fxml");
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/view/Dashboard.fxml"));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }

        // Make scene occupy full screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(page, screenBounds.getWidth(), screenBounds.getHeight());
//      Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.sizeToScene();
        stage.show();
        stage.setOnHiding(event -> {
//            userLogout();
        });
        stage.setMaximized(true);
        return loader.getController();
    }

    public void gotoDashboard() {
        try {
            DashboardController dashboardFrm = (DashboardController) dashboard();
            dashboardFrm.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
