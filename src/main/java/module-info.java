module image.viewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens software.imageviewer.gui.javafx to javafx.fxml;

    exports software.imageviewer.gui.javafx;
    exports software.imageviewer.gui.command;
    exports software.imageviewer.gui;
    exports software.imageviewer;
}