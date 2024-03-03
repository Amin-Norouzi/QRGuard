package dev.aminnorouzi.qrguard.controller;

import dev.aminnorouzi.qrguard.model.User;
import dev.aminnorouzi.qrguard.util.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable, Consumable {

    @FXML
    private Label label;

    private User user;
    private Thread thread;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thread = new Thread(() -> label.setText("Hi " + user.getFullName() + "!"));
        thread.setDaemon(true);
    }

    @Override
    public void setData(Object data) {
        user = (User) data;
        thread.start();
    }

    @FXML
    protected void switchToLanding() {
        ViewSwitcher.switchTo("landing-view");
    }
}