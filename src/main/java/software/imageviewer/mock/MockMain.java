package software.imageviewer.mock;

import software.imageviewer.gui.javafx.FXImageViewer;

public class MockMain {
    public static void main(String[] args) {
        FXImageViewer.main(args);
/*
        try {
            FlatCarbonIJTheme.setup();
        } catch (Exception e) {
            System.out.println("Error setting native LAF: " + e);
        }
        SwingMainFrame mainFrame = new SwingMainFrame();
        Image image = new FileImageLoader(new File("src/main/resources")).load();
        mainFrame.imagePanel().image(image);
        mainFrame.imagePanel().display();
        mainFrame.add("next image", new NextImageCommand(mainFrame.imagePanel()));
        mainFrame.add("previous image", new PreviousImageCommand(mainFrame.imagePanel()));
        mainFrame.setVisible(true);
*/
    }
}
