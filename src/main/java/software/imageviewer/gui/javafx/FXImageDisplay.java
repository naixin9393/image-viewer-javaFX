package software.imageviewer.gui.javafx;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import software.imageviewer.Image;
import software.imageviewer.gui.ImageDisplay;

import java.util.HashMap;
import java.util.Map;

public class FXImageDisplay extends ImageView implements ImageDisplay {
    private final Stage stage;
    private Image image;
    private final Map<String, javafx.scene.image.Image> preloadedImages = new HashMap<>();


    public FXImageDisplay(Stage stage) {
        setPreserveRatio(true);
        this.stage = stage;
    }

    @Override
    public Image image() {
        return this.image;
    }

    @Override
    public void image(Image image) {
        this.image = image;
    }

    @Override
    public void display() {
        javafx.scene.image.Image displayedImage = preloadedImages.computeIfAbsent(image.name(), n -> new javafx.scene.image.Image(image.name()));
        setFitHeight(Math.min(stage.getHeight(), image.height()));
        setFitWidth(Math.min(stage.getWidth(), image.width()));
        this.setImage(displayedImage);
        preloadNext();
    }

    private void preloadNext() {
        if (image.next() == null)
            return;
        if (preloadedImages.get(image.next().name()) != null)
            return;
        preloadedImages.put(image.next().name(), new javafx.scene.image.Image(image.next().name()));
    }
}
