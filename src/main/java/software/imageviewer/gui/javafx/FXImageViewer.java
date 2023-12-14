package software.imageviewer.gui.javafx;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import software.imageviewer.FileImageLoader;
import software.imageviewer.Image;
import software.imageviewer.gui.ImageDisplay;

import java.io.File;

public class FXImageViewer extends Application {
    private ImageDisplay imageDisplay;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Image Viewer");
        stage.setWidth(500);
        stage.setHeight(500);

        stage.setScene(createScene());

        stage.show();
    }

    private Scene createScene() {
        return new Scene(createLayout());
    }

    private Parent createLayout() {
        HBox layout = new HBox();
        layout.getChildren().add(createPreviousButton());
        layout.getChildren().add(createImageDisplay());
        layout.getChildren().add(createNextButton());
        return layout;
    }

    private Node createImageDisplay() {
        FXImageDisplay imageDisplay = new FXImageDisplay();
        this.imageDisplay = imageDisplay;
        Image image = new FileImageLoader(new File("src/main/resources")).load();
        imageDisplay.image(image);
        imageDisplay.display();
        return imageDisplay;
    }

    private Node createNextButton() {
        Button button = new Button(">");
        button.setOnAction(event -> {
            // TODO
        });
        return button;
    }

    private Node createPreviousButton() {
        Button button = new Button("<");
        button.setOnAction(event -> {
            // TODO
        });
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ImageDisplay imageDisplay() {
        return this.imageDisplay;
    }
}
