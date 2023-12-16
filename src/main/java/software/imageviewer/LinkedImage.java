package software.imageviewer;

public interface LinkedImage {
    String url();
    LinkedImage next();
    LinkedImage previous();
}
