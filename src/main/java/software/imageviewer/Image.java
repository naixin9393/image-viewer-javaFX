package software.imageviewer;

public interface Image {
    String name();
    Image next();
    Image previous();
    int height();
    int width();
}
