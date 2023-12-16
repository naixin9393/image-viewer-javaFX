package software.imageviewer.gui.javafx;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import software.imageviewer.gui.command.ChooseImageCommand;
import software.imageviewer.gui.command.ImageCommandManager;

public class FXMenuBar extends MenuBar {
    private final ImageCommandManager commandManager = ImageCommandManager.getInstance();
    public FXMenuBar() {
        this.getMenus().add(createFileMenu());

    }

    private Menu createFileMenu() {
        Menu menu = new Menu("File");
        menu.getItems().add(createOpenMenuItem());
        menu.getItems().add(createExitMenuItem());
        return menu;
    }

    private MenuItem createExitMenuItem() {
        MenuItem menuItem = new MenuItem("Exit");
        menuItem.setOnAction(e -> System.exit(0));
        return menuItem;
    }

    private MenuItem createOpenMenuItem() {
        MenuItem menuItem = new MenuItem("Open");
        menuItem.setOnAction(e -> commandManager.execute(ChooseImageCommand.class));
        return menuItem;
    }
}
