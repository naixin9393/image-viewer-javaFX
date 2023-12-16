package software.imageviewer.gui.javafx;

import javafx.stage.FileChooser;

import software.imageviewer.FileImageLoader;
import software.imageviewer.LinkedImage;
import software.imageviewer.gui.ImageChooser;

import java.io.File;

public class FXImageChooser implements ImageChooser {
    @Override
    public LinkedImage choose() {
        File file = new FileChooser().showOpenDialog(null);
        return new FileImageLoader(file).load();
    }
}
