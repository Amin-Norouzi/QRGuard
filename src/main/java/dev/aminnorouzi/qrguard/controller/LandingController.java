package dev.aminnorouzi.qrguard.controller;

import dev.aminnorouzi.qrguard.util.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LandingController {

    @FXML
    void switchToSignup(ActionEvent event) {
        ViewSwitcher.switchTo("signup-view");
    }

    @FXML
    void switchToDetection(ActionEvent event) {
        ViewSwitcher.switchTo("detection-view");
    }
}
