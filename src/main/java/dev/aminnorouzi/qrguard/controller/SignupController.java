package dev.aminnorouzi.qrguard.controller;

import dev.aminnorouzi.qrguard.model.User;
import dev.aminnorouzi.qrguard.service.UserService;
import dev.aminnorouzi.qrguard.util.DependencyInjector;
import dev.aminnorouzi.qrguard.util.ViewSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {

    private final UserService auth = DependencyInjector.getInstance(UserService.class);
//    private final ViewSwitcher switcher = DependencyInjector.getInstance(ViewSwitcher.class);

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    void signup(ActionEvent event) {
        try {
            String fullName = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            User user = auth.save(fullName, email, password);

            Platform.runLater(() -> ViewSwitcher.switchTo("completion-view", user));
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
