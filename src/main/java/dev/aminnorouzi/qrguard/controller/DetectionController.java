package dev.aminnorouzi.qrguard.controller;

import dev.aminnorouzi.qrguard.service.CameraService;
import dev.aminnorouzi.qrguard.util.DependencyInjector;
import dev.aminnorouzi.qrguard.util.ViewSwitcher;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class DetectionController implements Initializable {

    private final CameraService camera = DependencyInjector.getInstance(CameraService.class);

    @FXML
    private ImageView cameraView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> camera.open(cameraView)
                .thenAccept(data ->
                        Platform.runLater(() -> ViewSwitcher.switchTo("signin-view", data)))
        );
    }

    @FXML
    void switchToLanding(MouseEvent event) {
        camera.stop();
        ViewSwitcher.switchTo("landing-view");
    }
}
