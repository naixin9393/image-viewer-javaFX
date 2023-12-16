package software.imageviewer.gui.javafx;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import software.imageviewer.LinkedImage;
import software.imageviewer.gui.ImageDisplay;

import java.util.HashMap;
import java.util.Map;

public class FXImageDisplay extends ImageView implements ImageDisplay {
    private final Scene scene;
    private LinkedImage linkedImage;
    private final Map<String, Image> preloadedImages = new HashMap<>();

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
        Image displayedImage = preloadedImages.computeIfAbsent(linkedImage.url(), n -> new Image(linkedImage.url()));
        setFitHeight(Math.min(scene.getHeight(), linkedImage.height()));
        setFitWidth(Math.min(scene.getWidth(), linkedImage.width()));
        this.setImage(displayedImage);
        preloadNext();
    }

    private void preloadNext() {
        if (linkedImage.next() == null)
            return;
        if (preloadedImages.get(linkedImage.next().url()) != null)
            return;
        preloadedImages.put(linkedImage.next().url(), new Image(linkedImage.next().url()));
    }
}
