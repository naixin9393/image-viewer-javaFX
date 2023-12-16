package software.imageviewer.gui.command;

import java.util.HashMap;
import java.util.Map;

public class ImageCommandManager implements CommandManager {
    private static final ImageCommandManager instance = new ImageCommandManager();

    private ImageCommandManager() {}

    public static ImageCommandManager getInstance() {
        return instance;
    }

    private final Map<Class<? extends Command>, Command> commands = new HashMap<>();

    @Override
    public ImageCommandManager add(Class<? extends Command> commandClass, Command command) {
        this.commands.put(commandClass, command);
        return instance;
    }

    @Override
    public void execute(Class<? extends Command> commandClass) {
        this.commands.get(commandClass).execute();
    }
}
