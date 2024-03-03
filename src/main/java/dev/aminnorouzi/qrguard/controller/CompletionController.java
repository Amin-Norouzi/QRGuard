package dev.aminnorouzi.qrguard.controller;

import dev.aminnorouzi.qrguard.model.User;
import dev.aminnorouzi.qrguard.service.QrCodeService;
import dev.aminnorouzi.qrguard.util.DependencyInjector;
import dev.aminnorouzi.qrguard.util.ViewSwitcher;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class CompletionController implements Initializable, Consumable {

    private final QrCodeService qrcode = DependencyInjector.getInstance(QrCodeService.class);

    @FXML
    private ImageView qrcodeView;

    private User user;
    private BufferedImage code;
    private Thread thread;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        thread = new Thread(() -> {
            try {
                BufferedImage generated = qrcode.generate(user.getEmail());
                code = generated;

                WritableImage image = SwingFXUtils.toFXImage(generated, null);
                qrcodeView.setImage(image);
            } catch (RuntimeException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(exception.getMessage());
                alert.show();
            }
        });

        thread.setDaemon(true);
    }

    @Override
    public void setData(Object data) {
        user = (User) data;
        thread.start();
    }

    @FXML
    void save(ActionEvent event) {
        try {
            qrcode.save(code);
            ViewSwitcher.switchTo("landing-view");
        } catch (RuntimeException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(exception.getMessage());
            alert.show();
        }
    }
}
