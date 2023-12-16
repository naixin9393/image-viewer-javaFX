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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import software.imageviewer.FileImageLoader;
import software.imageviewer.LinkedImage;
import software.imageviewer.gui.ImageChooser;
import software.imageviewer.gui.ImageDisplay;
import software.imageviewer.gui.command.*;

import java.io.File;
import java.util.Properties;

public class FXImageViewer extends Application {
    private final String buttonStyle = "-fx-opacity: 1;-fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-font-size: 20px";
    private final Properties properties = new Properties();
    private final CommandManager commandManager = ImageCommandManager.getInstance();
    private ImageDisplay imageDisplay;
    private MenuBar menuBar;
    private Stage mainStage;
    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        String propertiesFile = "application.properties";
        properties.load(FXImageViewer.class.getClassLoader().getResourceAsStream(propertiesFile));

        stage.setTitle(appTitle());
        stage.getIcons().add(appIcon());
        stage.setScene(createScene());
        stage.setWidth(1400);
        stage.setHeight(800);
        stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> menuBar.setVisible(!newValue));
        stage.setFullScreen(true);

        addCommands();
        loadImages();

        stage.show();
    }

    private void addCommands() {
        ImageCommandManager.getInstance()
                .add(PreviousImageCommand.class, new PreviousImageCommand(imageDisplay))
                .add(NextImageCommand.class, new NextImageCommand(imageDisplay))
                .add(ChooseImageCommand.class, new ChooseImageCommand(createImageChooser(), imageDisplay));
    }

    private String appTitle() {
        return properties.getProperty("app.title");
    }

    private Image appIcon() {
        return new Image(properties.getProperty("app.icon"));
    }

    private ImageChooser createImageChooser() {
        return new FXImageChooser();
    }

    private void loadImages() {
        String resourcesFolder = "src/main/resources";
        LinkedImage linkedImage = new FileImageLoader(new File(resourcesFolder)).load();
        imageDisplay.image(linkedImage);
        imageDisplay.display();
    }

    private Scene createScene() {
        scene = new Scene(new HBox(), 0, 0);
        scene.setRoot(createMainLayout());
        setKeymap();
        return scene;
    }

    private Parent createMainLayout() {
        VBox stackPane = new VBox();
        stackPane.setStyle("-fx-background-color: #000000");
        stackPane.getChildren().add(createMenuBar());
        stackPane.getChildren().add(imageDisplayLayout());
        return stackPane;
    }

    private Node createMenuBar() {
        this.menuBar = new FXMenuBar();
        return menuBar;
    }

    private Node imageDisplayLayout() {
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(createImageDisplay());
        stackPane.getChildren().add(buttonLayout());
        return stackPane;
    }

    private Node buttonLayout() {
        BorderPane layout = new BorderPane();
        layout.setLeft(createPreviousButton());
        layout.setRight(createNextButton());
        return layout;
    }

    private Node createImageDisplay() {
        FXImageDisplay imageDisplay = new FXImageDisplay(scene);
        this.imageDisplay = imageDisplay;
        return imageDisplay;
    }

    private Node createNextButton() {
        Button button = new Button("➡");
        button.setStyle("-fx-opacity: 0;");
        button.prefHeightProperty().bind(scene.heightProperty());
        button.setOnAction(e -> commandManager.execute(NextImageCommand.class));
        button.setOnMouseEntered(event -> button.setStyle(buttonStyle));
        button.setOnMouseExited(event -> button.setStyle("-fx-opacity: 0;"));
        return button;
    }

    private Node createPreviousButton() {
        Button button = new Button("⬅");
        button.setStyle("-fx-opacity: 0;");
        button.prefHeightProperty().bind(scene.heightProperty());
        button.setOnAction(e -> commandManager.execute(PreviousImageCommand.class));
        button.setOnMouseEntered(event -> button.setStyle(buttonStyle));
        button.setOnMouseExited(event -> button.setStyle("-fx-opacity: 0;"));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setKeymap() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                case LEFT:
                    commandManager.execute(PreviousImageCommand.class);
                    break;
                case D:
                case RIGHT:
                    commandManager.execute(NextImageCommand.class);
                    break;
                case F11:
                    mainStage.setFullScreen(!mainStage.isFullScreen());
                    break;
            }
        });
    }
}
