package dev.aminnorouzi.qrguard.util;

import dev.aminnorouzi.qrguard.controller.Consumable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewSwitcher {

    private static Stage stage;

    public static void initialize(Stage stage) {
        ViewSwitcher.stage = stage;

        ViewSwitcher.stage.setTitle("QRGuard");
        ViewSwitcher.stage.setResizable(false);
    }

    public static void switchTo(String view, Object data) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewSwitcher.class.getResource("/dev/aminnorouzi/qrguard/" + view + ".fxml"));
            Scene scene = new Scene(loader.load());

            if (data != null) {
                Consumable consumable = loader.getController();
                consumable.setData(data);
            }

            ViewSwitcher.stage.setScene(scene);
            ViewSwitcher.stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void switchTo(String view) {
        ViewSwitcher.switchTo(view, null);
    }
}
