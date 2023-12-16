package software.imageviewer.gui.javafx;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import software.imageviewer.LinkedImage;
import software.imageviewer.gui.ImageDisplay;

public class FXImageDisplay extends ImageView implements ImageDisplay {
    private final Scene scene;
    private LinkedImage linkedImage;

    public FXImageDisplay(Scene scene) {
        setPreserveRatio(true);
        this.scene = scene;
        scene.widthProperty().addListener((observable, oldValue, newValue) -> display());
        scene.heightProperty().addListener((observable, oldValue, newValue) -> display());
    }

    @Override
    public LinkedImage image() {
        return this.linkedImage;
    }

    @Override
    public void image(LinkedImage linkedImage) {
        this.linkedImage = linkedImage;
    }

    @Override
    public void display() {
        Image displayedImage = new Image(linkedImage.url());
        setFitHeight(Math.min(scene.getHeight(), displayedImage.getHeight()));
        setFitWidth(Math.min(scene.getWidth(), displayedImage.getWidth()));
        this.setImage(displayedImage);
        setPreserveRatio(true);
    }
}
