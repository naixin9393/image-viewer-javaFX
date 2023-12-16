package software.imageviewer.gui.javafx;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import software.imageviewer.FileImageLoader;
import software.imageviewer.LinkedImage;
import software.imageviewer.gui.ImageChooser;
import software.imageviewer.gui.ImageDisplay;
import software.imageviewer.gui.command.Command;
import software.imageviewer.gui.command.NextImageCommand;
import software.imageviewer.gui.command.ChooseImageCommand;
import software.imageviewer.gui.command.PreviousImageCommand;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FXImageViewer extends Application {
    private final Map<String, Command> commands = new HashMap<>();
    private final String buttonStyle = "-fx-opacity: 1;-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px";
    private final MenuBar menuBar = new MenuBar();
    private ImageDisplay imageDisplay;
    private Stage mainStage;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        stage.setTitle("Image Viewer");
        stage.getIcons().add(new Image("icon.png"));
        stage.setWidth(1400);
        stage.setHeight(800);
        stage.setScene(createScene());
        stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> menuBar.setVisible(!newValue));
        stage.setFullScreen(true);

        loadImages();
        addCommands();
        stage.show();
    }

    private void addCommands() {
        addCommand("previous image", new PreviousImageCommand(imageDisplay()));
        addCommand("next image", new NextImageCommand(imageDisplay()));
        addCommand("open", new ChooseImageCommand(createImageChooser(), imageDisplay()));
    }

    private ImageChooser createImageChooser() {
        return new FXImageChooser();
    }

    private void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    private void loadImages() {
        String resourcesFolder = "src/main/resources";
//        String resourcesFolder = "C:/Users/naixin9393/Desktop";
        LinkedImage linkedImage = new FileImageLoader(new File(resourcesFolder)).load();
        imageDisplay.image(linkedImage);
        imageDisplay.display();
    }

    private Scene createScene() {
        this.scene = new Scene(new HBox(), 0, 0);
        this.scene.setRoot(createMainLayout());
        setKeymap();
        return scene;
    }

    private Parent createMainLayout() {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(createImageDisplay());
        stackPane.getChildren().add(outerLayout());
        stackPane.setStyle("-fx-background-color: #000000");
        return stackPane;
    }


    private Node createMenuBar() {
        return new FXMenuBar();
    }

    private Parent outerLayout() {
        BorderPane layout = new BorderPane();
        layout.setTop(createMenuBar());
        layout.setLeft(createPreviousButton());
        layout.setRight(createNextButton());
        return layout;
    }

    private Node createImageDisplay() {
        FXImageDisplay imageDisplay = new FXImageDisplay(this.scene);
        this.imageDisplay = imageDisplay;
        return imageDisplay;
    }

    private Node createNextButton() {
        Button button = new Button("➡");
        button.setStyle("-fx-opacity: 0;");
        button.prefHeightProperty().bind(scene.heightProperty());
        button.setOnAction(e -> commands.get("next image").execute());
        button.setOnMouseEntered(event -> button.setStyle(buttonStyle));
        button.setOnMouseExited(event -> button.setStyle("-fx-opacity: 0;"));
        return button;
    }

    private Node createPreviousButton() {
        Button button = new Button("⬅");
        button.setStyle("-fx-opacity: 0;");
        button.prefHeightProperty().bind(mainStage.heightProperty());
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

    private void setKeymap() {
        this.scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                case LEFT:
                    commands.get("previous image").execute();
                    break;
                case D:
                case RIGHT:
                    commands.get("next image").execute();
                    break;
                case F11:
                    mainStage.setFullScreen(!mainStage.isFullScreen());
                    break;
            }
        });
    }
}
