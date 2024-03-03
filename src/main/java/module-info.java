module dev.aminnorouzi.qrguard {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencv;
    requires java.desktop;
    requires javafx.swing;
    requires com.google.zxing.javase;
    requires com.google.zxing;


    opens dev.aminnorouzi.qrguard.util to javafx.fxml;
    opens dev.aminnorouzi.qrguard.controller to javafx.fxml;
    exports dev.aminnorouzi.qrguard;
    exports dev.aminnorouzi.qrguard.controller;
}