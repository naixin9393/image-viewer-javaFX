package software.imageviewer.gui.command;

import software.imageviewer.gui.ImageChooser;
import software.imageviewer.gui.ImageDisplay;

public class ChooseImageCommand implements Command {
    private final ImageDisplay imageDisplay;
    private final ImageChooser imageChooser;

    public ChooseImageCommand(ImageChooser imageChooser, ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
        this.imageChooser = imageChooser;
    }

    @Override
    public void execute() {
        imageDisplay.image(imageChooser.choose());
        imageDisplay.display();
    }
}
