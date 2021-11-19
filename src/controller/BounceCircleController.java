/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import epicpos.Bounce;
import epicpos.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Epic
 */
public class BounceCircleController implements Initializable {

    @FXML
    private Circle circle1;
    @FXML
    private Circle circle2;
    @FXML
    private Circle circle3;

        private Main application;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        new Bounce(circle1).setCycleCount(4).setDelay(Duration.valueOf("500ms")).play();
                new Bounce(circle2).setCycleCount(10).setDelay(Duration.valueOf("1000ms")).play();
                        new Bounce(circle3).setCycleCount(4).setDelay(Duration.valueOf("1100ms")).play();
    }    
    
        public void setApp(Main application) {
        this.application = application;
    }
}
