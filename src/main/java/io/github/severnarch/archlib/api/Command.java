package io.github.severnarch.archlib.api;

import io.github.severnarch.archlib.ArchLib;
import io.github.severnarch.archlib.exceptions.command.ArgumentNotFoundException;
import io.github.severnarch.archlib.objects.classes.command.CommandArgument;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Command {
    private final HashMap<String, CommandArgument> arguments;
    private String commandName;
    private final JavaPlugin parent;

    private CommandExecutor executor;
    private TabCompleter tabCompleter;

    public Command(String commandName, JavaPlugin parent) {
        this.commandName = commandName;
        this.parent = parent;
        this.arguments = new HashMap<>();
    }

    public Command(String commandName) {
        this.commandName = commandName;
        this.parent = JavaPlugin.getPlugin(ArchLib.class);
        this.arguments = new HashMap<>();
    }

    public String getName() {
        return this.commandName;
    }

    public void setName(String newCommandName) {
        this.commandName = newCommandName;
    }

    public void addArgument(String argumentName, CommandArgument argument) {
        this.arguments.put(argumentName, argument);
    }
    public void setArgument(String argumentName, CommandArgument argument) throws ArgumentNotFoundException {
        if (arguments.containsKey(argumentName)) {
            arguments.remove(argumentName);
            arguments.put(argumentName, argument);
        } else {
            throw new ArgumentNotFoundException(argumentName, this.commandName);
        }
    }
    public void removeArgument(String argumentName) throws ArgumentNotFoundException {
        if (arguments.containsKey(argumentName)) {
            arguments.remove(argumentName);
        } else {
            throw new ArgumentNotFoundException(argumentName, this.commandName);
        }
    }
    public CommandArgument getArgument(String argumentName) throws ArgumentNotFoundException {
        if (arguments.containsKey(argumentName)) {
            return arguments.get(argumentName);
        } else {
            throw new ArgumentNotFoundException(argumentName, this.commandName);
        }
    }
}
