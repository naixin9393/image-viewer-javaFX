package software.imageviewer.gui.javafx;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
    private final String buttonStyle = "-fx-opacity: 1;-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px";
    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        stage.setTitle("Image Viewer");
        stage.getIcons().add(new javafx.scene.image.Image("icon.png"));
/*
        stage.setWidth(1400);
        stage.setHeight(800);
*/
        stage.setFullScreen(true);
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
        return new Scene(createMainLayout());
    }

    private Parent createMainLayout() {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(createImageDisplay());
        stackPane.getChildren().add(buttonLayout());
        stackPane.setStyle("-fx-background-color: #000000");
        return stackPane;
    }

    private Parent buttonLayout() {
        BorderPane layout = new BorderPane();
        layout.setLeft(createPreviousButton());
        layout.setRight(createNextButton());
        BorderPane.setAlignment(layout.getLeft(), javafx.geometry.Pos.CENTER);
        BorderPane.setAlignment(layout.getRight(), javafx.geometry.Pos.CENTER);
        return layout;
    }

    private Node createImageDisplay() {
        FXImageDisplay imageDisplay = new FXImageDisplay(mainStage);
        this.imageDisplay = imageDisplay;
        return imageDisplay;
    }

    private Node createNextButton() {
        Button button = new Button("➡");
        button.setStyle("-fx-opacity: 0;");
        button.setOnAction(e -> commands.get("next image").execute());
        button.setOnMouseEntered(event -> button.setStyle(buttonStyle));
        button.setOnMouseExited(event -> button.setStyle("-fx-opacity: 0;"));
        return button;
    }

    private Node createPreviousButton() {
        Button button = new Button("⬅");
        button.setStyle("-fx-opacity: 0;");
        button.setOnAction(e -> commands.get("previous image").execute());
        button.setOnMouseEntered(event -> button.setStyle(buttonStyle));
        button.setOnMouseExited(event -> button.setStyle("-fx-opacity: 0;"));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private ImageDisplay imageDisplay() {
        return this.imageDisplay;
    }
}
