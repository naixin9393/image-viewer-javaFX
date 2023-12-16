package software.imageviewer.gui.javafx;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class FXMenuBar extends MenuBar {
    public FXMenuBar() {
        this.getMenus().add(createFileMenu());
    }

    private Menu createFileMenu() {
        Menu menu = new Menu("File");
        menu.getItems().add(createOpenMenuItem());
        return menu;
    }

    private MenuItem createOpenMenuItem() {
        MenuItem menuItem = new MenuItem("Open");
        //menuItem.setOnAction(e -> commands.get("open").execute());
        return menuItem;
    }
}
