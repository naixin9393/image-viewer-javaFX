package software.imageviewer;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileImageLoader implements ImageLoader {
    private final List<String> imageExtensions = List.of("jpeg", "jpg", "png");
    private final List<javafx.scene.image.Image> images;

    public FileImageLoader(File directory) {
        this.images = toImages(directory.listFiles(withImageExtension()));
    }

    private List<javafx.scene.image.Image> toImages(File[] files) {
        return Arrays.stream(files)
                .map(file -> new javafx.scene.image.Image(file.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Image load() {
        return imageAt(0);
    }

    private Image imageAt(int i) {
        if (images.isEmpty()) return null;
        return new Image() {
            @Override
            public String name() {
                return images.get(i).getUrl();
            }

            @Override
            public Image next() {
                return i + 1 == images.size() ? null : imageAt(i + 1);
            }

            @Override
            public Image previous() {
                return i == 0 ? null : imageAt(i - 1);
            }

            @Override
            public int height() {
                return (int) images.get(i).getHeight();
            }

            @Override
            public int width() {
                return (int) images.get(i).getWidth();
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
