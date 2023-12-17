package software.imageviewer.gui.command;

import software.imageviewer.gui.AboutDialog;

public class OpenAboutDialogCommand implements Command {
    private final AboutDialog aboutDialog;

    public OpenAboutDialogCommand(AboutDialog aboutDialog) {
        this.aboutDialog = aboutDialog;
    }

    @Override
    public void execute() {
        aboutDialog.show();
    }
}
