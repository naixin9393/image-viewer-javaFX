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
import software.imageviewer.gui.command.Command;
import software.imageviewer.gui.command.NextImageCommand;
import software.imageviewer.gui.command.PreviousImageCommand;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FXImageViewer extends Application {
    private ImageDisplay imageDisplay;
    private final Map<String, Command> commands = new HashMap<>();
    private final String ResourcesFolder = "src/main/resources";
    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        stage.setTitle("Image Viewer");
        stage.setWidth(500);
        stage.setHeight(500);
        stage.setScene(createScene());

        loadImages();
        addCommands();
        stage.show();
    }

    private void addCommands() {
        addCommand("previous image", new PreviousImageCommand(imageDisplay()));
        addCommand("next image", new NextImageCommand(imageDisplay()));
    }

    private void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    private void loadImages() {
        Image image = new FileImageLoader(new File(ResourcesFolder)).load();
        imageDisplay.image(image);
        imageDisplay.display();
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
        FXImageDisplay imageDisplay = new FXImageDisplay(mainStage);
        this.imageDisplay = imageDisplay;
        return imageDisplay;
    }

    private Node createNextButton() {
        Button button = new Button(">");
        button.setOnAction(e -> commands.get("next image").execute());
        return button;
    }

    private Node createPreviousButton() {
        Button button = new Button("<");
        button.setOnAction(e -> commands.get("previous image").execute());
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ImageDisplay imageDisplay() {
        return this.imageDisplay;
    }
}
