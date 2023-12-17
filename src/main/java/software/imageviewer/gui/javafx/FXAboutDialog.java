package software.imageviewer.gui.javafx;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import software.imageviewer.gui.AboutDialog;

import java.io.IOException;
import java.util.Properties;

public class FXAboutDialog implements AboutDialog {
    private final Properties properties = new Properties();
    private final Stage dialog;

    public FXAboutDialog() {
        try {
            this.properties.load(FXAboutDialog.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.dialog = new Stage();
        dialog.setTitle("About");
        dialog.setScene(createScene());
        dialog.setWidth(300);
        dialog.setHeight(150);
        dialog.getIcons().add(new Image(properties.getProperty("app.icon")));
    }

    private Scene createScene() {
        return new Scene(createMainLayout());
    }

    private Parent createMainLayout() {
        VBox vBox = new VBox();
        vBox.getChildren().add(createInformationLayout());
        vBox.getChildren().add(createButtonLayout());
        return vBox;
    }

    private Node createInformationLayout() {
        HBox hBox = new HBox();
        hBox.getChildren().add(createImageView());
        hBox.getChildren().add(createTextArea());
        return hBox;
    }

    private Node createButtonLayout() {
        HBox hBox = new HBox();
        hBox.setSpacing(30);
        hBox.setAlignment(javafx.geometry.Pos.CENTER);
        hBox.getChildren().add(createCopyButton());
        hBox.getChildren().add(createCloseButton());
        return hBox;
    }

    private Node createCopyButton() {
        Button button = new Button("Copy");
        button.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(aboutMessage());
            clipboard.setContent(content);
        });
        return button;
    }

    private Node createCloseButton() {
        Button button = new Button("Close");
        button.setOnAction(e -> dialog.close());
        return button;
    }

    private Node createTextArea() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setFocusTraversable(false);
        textArea.setText(aboutMessage());
        return textArea;
    }

    private String aboutMessage() {
        return properties.getProperty("app.name") + "\n" +
                properties.getProperty("app.author") + "\n" +
                properties.getProperty("app.author.url") + "\n";
    }

    private Node createImageView() {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(properties.getProperty("app.icon")));
        imageView.setFitHeight(60);
        imageView.setFitWidth(60);
        return imageView;
    }

    @Override
    public void show() {
        dialog.showAndWait();
    }
}
