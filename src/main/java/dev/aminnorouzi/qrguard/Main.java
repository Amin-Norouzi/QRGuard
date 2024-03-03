package dev.aminnorouzi.qrguard;

import dev.aminnorouzi.qrguard.util.ViewSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ViewSwitcher.initialize(stage);
        ViewSwitcher.switchTo("landing-view");
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}