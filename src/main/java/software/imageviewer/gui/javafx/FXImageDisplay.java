package software.imageviewer.gui.javafx;

import javafx.scene.image.ImageView;
import software.imageviewer.Image;
import software.imageviewer.gui.ImageDisplay;


public class FXImageDisplay extends ImageView implements ImageDisplay {
    private Image image;

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
        this.setImage(new javafx.scene.image.Image(image.name()));
    }
}
