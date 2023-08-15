package io.github.severnarch.archlib.api;

import io.github.severnarch.archlib.ArchLib;
import io.github.severnarch.archlib.exceptions.command.ArgumentNotFoundException;
import io.github.severnarch.archlib.objects.classes.command.CommandArgument;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Command {
    private final HashMap<String, CommandArgument> arguments;
    private String commandName;
    private final JavaPlugin parent;

    @SuppressWarnings("unused")
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

    public void build() {
        CommandExecutor executor = new CommandExecutor() {

            private CommandArgument getFinalArg(HashMap<String, CommandArgument> arguments, String[] cargs, Integer cindex) {
                if (arguments.size() != 0 && cargs.length != 0) {
                    for (String argN : arguments.keySet()) {
                        CommandArgument arg = arguments.get(argN);
                        if (cindex >= cargs.length) { cindex = cargs.length - 1; }
                        if (cindex < 0) { cindex = 0; }
                        if (Objects.equals(argN, cargs[cindex])) {
                            CommandArgument nextArg = getFinalArg(arg.getChildArguments(), cargs, cindex + 1);
                            return Objects.requireNonNullElse(nextArg, arg);
                        }
                    }
                }
                return null;
            }

            private CommandArgument getFinalArg(HashMap<String, CommandArgument> arguments, String[] cargs) {
                return getFinalArg(arguments, cargs, 0);
            }

            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, String[] args) {
                CommandArgument finalArg = getFinalArg(arguments, args);
                if (finalArg != null) {
                    finalArg.execute(sender, command, label, args);
                    return true;
                } else {
                    return false;
                }
            }
        };
        TabCompleter tabCompleter = new TabCompleter() {

            private HashMap<String, CommandArgument> getArgN(HashMap<String, CommandArgument> arguments, String[] cargs, Integer target, Integer cindex) {
                if (arguments.size() != 0 && cargs.length != 0) {
                    if (cindex.equals(target)) {
                        HashMap<String, CommandArgument> fills = new HashMap<>();
                        for (String argN : arguments.keySet()) {
                            if (cindex >= cargs.length) { cindex = cargs.length - 1; }
                            if (cindex < 0) { cindex = 0; }
                            System.out.println(cindex+", "+cargs.length+", "+argN);
                            if (argN.startsWith(cargs[cindex])) {
                                fills.put(argN, arguments.get(argN));
                            }
                        }
                        if (!fills.isEmpty()) {
                            return fills;
                        } else {
                            return null;
                        }
                    } else {
                        return getArgN(arguments, cargs, target, cindex + 1);
                    }
                } else {
                    return null;
                }
            }

            private HashMap<String, CommandArgument> getArgN(HashMap<String, CommandArgument> arguments, String[] cargs, Integer target) {
                return getArgN(arguments, cargs, target, 0);
            }

            @Nullable
            @Override
            public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
                HashMap<String, CommandArgument> targArg = getArgN(arguments, args, args.length);
                if (targArg != null) {
                    return targArg.keySet().stream().toList();
                } else {
                    return null;
                }
            }
        };

        PluginCommand command = parent.getCommand(commandName);
        if (command != null) {
            command.setExecutor(executor);
            command.setTabCompleter(tabCompleter);
        }
    }

    public String getName() {
        return this.commandName;
    }

    @SuppressWarnings("unused")
    public void setName(String newCommandName) {
        this.commandName = newCommandName;
    }

    public void addArgument(String argumentName, CommandArgument argument) {
        this.arguments.put(argumentName, argument);
    }

     @SuppressWarnings("unused")
    public void setArgument(String argumentName, CommandArgument argument) throws ArgumentNotFoundException {
        if (arguments.containsKey(argumentName)) {
            arguments.remove(argumentName);
            arguments.put(argumentName, argument);
        } else {
            throw new ArgumentNotFoundException(argumentName, this.commandName);
        }
    }

    @SuppressWarnings("unused")
    public void removeArgument(String argumentName) throws ArgumentNotFoundException {
        if (arguments.containsKey(argumentName)) {
            arguments.remove(argumentName);
        } else {
            throw new ArgumentNotFoundException(argumentName, this.commandName);
        }
    }

    @SuppressWarnings("unused")
    public CommandArgument getArgument(String argumentName) throws ArgumentNotFoundException {
        if (arguments.containsKey(argumentName)) {
            return arguments.get(argumentName);
        } else {
            throw new ArgumentNotFoundException(argumentName, this.commandName);
        }
    }
}
