package io.github.severnarch.archlib.objects.classes.command;

import io.github.severnarch.archlib.api.Command;
import io.github.severnarch.archlib.exceptions.command.ArgumentNotFoundException;
import io.github.severnarch.archlib.objects.classes.ArgumentedRunnable;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class CommandArgument {
    private ArgumentedRunnable code;
    private final HashMap<String, CommandArgument> childArguments;
    private final Command parent;

    public CommandArgument(ArgumentedRunnable code, Command parent) {
        this.code = code;
        this.parent = parent;
        this.childArguments = new HashMap<>();
    }

    public void setCode(ArgumentedRunnable code) {
        this.code = code;
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        code.run(sender, command, label, args);
    }

    public void addChildArgument(String argumentName, CommandArgument argument) {
        this.childArguments.put(argumentName, argument);
    }

    public void setChildArgument(String argumentName, CommandArgument argument) throws ArgumentNotFoundException {
        if (childArguments.containsKey(argumentName)) {
            childArguments.remove(argumentName);
            childArguments.put(argumentName, argument);
        } else {
            throw new ArgumentNotFoundException(argumentName, parent.getName());
        }
    }
    public void removeChildArgument(String argumentName) throws ArgumentNotFoundException {
        if (childArguments.containsKey(argumentName)) {
            childArguments.remove(argumentName);
        } else {
            throw new ArgumentNotFoundException(argumentName, parent.getName());
        }
    }
    public CommandArgument getChildArgument(String argumentName) throws ArgumentNotFoundException {
        if (childArguments.containsKey(argumentName)) {
            return childArguments.get(argumentName);
        } else {
            throw new ArgumentNotFoundException(argumentName, parent.getName());
        }
    }
}
