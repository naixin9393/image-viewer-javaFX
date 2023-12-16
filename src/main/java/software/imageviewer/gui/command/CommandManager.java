package software.imageviewer.gui.command;

public interface CommandManager {
    ImageCommandManager add(Class<? extends Command> commandClass, Command command);
    void execute(Class<? extends Command> commandClass);
}
