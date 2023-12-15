package software.imageviewer;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class FileImageLoader implements ImageLoader {
    private final List<String> imageExtensions = List.of("jpeg", "jpg", "png");
    private final File[] imageFiles;

    public FileImageLoader(File directory) {
        this.imageFiles = directory.listFiles(withImageExtension());
    }

    @Override
    public Image load() {
        return imageAt(0);
    }

    private Image imageAt(int i) {
        if (imageFiles == null || imageFiles.length == 0) return null;
        return new Image() {
            @Override
            public String name() {
                return imageFiles[i].getName();
            }

            @Override
            public Drawable drawable() {
                javafx.scene.image.Image image = readImage();
                return new Drawable((int) image.getWidth(), (int) image.getHeight());
            }

            private javafx.scene.image.Image readImage() {
                return new javafx.scene.image.Image(String.valueOf(imageFiles[0].getName()));
            }

            @Override
            public Image next() {
                return i + 1 == imageFiles.length ? null : imageAt(i + 1);
            }

            @Override
            public Image previous() {
                return i == 0 ? null : imageAt(i - 1);
            }
        };
    }

    private FileFilter withImageExtension() {
        return f -> isImageFile(f.getName());
    }

    private boolean isImageFile(String name) {
        return imageExtensions.stream()
                .anyMatch(name::endsWith);
    }
}
