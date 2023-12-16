package software.imageviewer.gui.javafx;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import software.imageviewer.LinkedImage;
import software.imageviewer.gui.ImageDisplay;

import java.util.HashMap;
import java.util.Map;

public class FXImageDisplay extends ImageView implements ImageDisplay {
    private final Stage stage;
    private LinkedImage linkedImage;
    private final Map<String, Image> preloadedImages = new HashMap<>();

    public FXImageDisplay(Stage stage) {
        setPreserveRatio(true);
        this.stage = stage;
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
        setFitHeight(Math.min(stage.getHeight(), linkedImage.height()));
        setFitWidth(Math.min(stage.getWidth(), linkedImage.width()));
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
