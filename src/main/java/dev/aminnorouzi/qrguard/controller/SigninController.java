package dev.aminnorouzi.qrguard.controller;

import dev.aminnorouzi.qrguard.model.User;
import dev.aminnorouzi.qrguard.service.UserService;
import dev.aminnorouzi.qrguard.util.DependencyInjector;
import dev.aminnorouzi.qrguard.util.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

public class SigninController implements Consumable {

    private final UserService auth = DependencyInjector.getInstance(UserService.class);

    @FXML
    private PasswordField passwordField;

    private String email;

    @Override
    public void setData(Object data) {
        email = (String) data;
    }

    @FXML
    void login(ActionEvent event) {
        try {
            String password = passwordField.getText();
            User user = auth.get(email);

            if (auth.authenticate(user, password)) {
                ViewSwitcher.switchTo("home-view", user);
            } else {
                passwordField.setStyle("-fx-border-color: red;");
            }
        } catch (RuntimeException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }

    @FXML
    void switchToLanding(ActionEvent event) {
        ViewSwitcher.switchTo("landing-view");
    }
}
