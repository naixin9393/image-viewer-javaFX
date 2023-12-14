package software.imageviewer;

public interface Image {
    String name();
    Drawable drawable();
    Image next();
    Image previous();
}
