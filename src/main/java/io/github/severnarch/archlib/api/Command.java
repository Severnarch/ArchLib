package io.github.severnarch.archlib.api;

import io.github.severnarch.archlib.ArchLib;
import io.github.severnarch.archlib.objects.classes.command.CommandArgument;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class Command {
    private final CommandArgument[] arguments;
    private String commandName;
    private final JavaPlugin parent;

    private CommandExecutor executor;
    private TabCompleter tabCompleter;

    public Command(String commandName, JavaPlugin parent) {
        this.commandName = commandName;
        this.parent = parent;
        this.arguments = new CommandArgument[]{};
    }

    public Command(String commandName) {
        this.commandName = commandName;
        this.parent = JavaPlugin.getPlugin(ArchLib.class);
        this.arguments = new CommandArgument[]{};
    }

    public String getName() {
        return this.commandName;
    }

    public void setName(String newCommandName) {
        this.commandName = newCommandName;
    }
}
