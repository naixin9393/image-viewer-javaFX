package software.imageviewer;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.image.Image;

public class FileImageLoader implements ImageLoader {
    private final List<String> imageExtensions = List.of("jpeg", "jpg", "png");
    private final List<Image> images;

    public FileImageLoader(File directory) {
        this.images = toImages(directory.listFiles(withImageExtension()));
    }

    private List<Image> toImages(File[] files) {
        return Arrays.stream(files)
                .map(file -> new Image(file.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public LinkedImage load() {
        return new MyLinkedImage(0);
    }

    class MyLinkedImage implements LinkedImage {
        private String url;
        private final MyLinkedImage previous;
        private MyLinkedImage next;
        private int width;
        private int height;

        public MyLinkedImage(int i) {
            this.previous = null;
            setFields(i);
        }

        private MyLinkedImage(MyLinkedImage previous, int i) {
            this.previous = previous;
            setFields(i);
        }

        private void setFields(int i) {
            this.next = i + 1 == images.size() ? null : new MyLinkedImage(this, i + 1);
            Image image = images.get(i);
            this.url = image.getUrl();
            this.width = (int) image.getWidth();
            this.height = (int) image.getHeight();
        }

        @Override
        public String url() {
            return this.url;
        }

        @Override
        public LinkedImage next() {
            return this.next;
        }

        @Override
        public LinkedImage previous() {
            return this.previous;
        }

        @Override
        public int height() {
            return this.height;
        }

        @Override
        public int width() {
            return this.width;
        }
    }

    private FileFilter withImageExtension() {
        return f -> isImageFile(f.getName());
    }

    private boolean isImageFile(String name) {
        return imageExtensions.stream()
                .anyMatch(name::endsWith);
    }
}
