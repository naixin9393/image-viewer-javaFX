package software.imageviewer.gui.javafx;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import software.imageviewer.Drawable;
import software.imageviewer.Image;
import software.imageviewer.gui.ImageDisplay;


public class FXImageDisplay extends ImageView implements ImageDisplay {
    private final Stage stage;
    private Image image;

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
        Drawable scaledDrawable = image.drawable();
        scaledDrawable = scaledDrawable.resize((int) stage.getWidth(), (int) stage.getHeight());
        setFitWidth(scaledDrawable.width());
        setFitHeight(scaledDrawable.height());
        this.setImage(new javafx.scene.image.Image(image.name()));
    }
}
