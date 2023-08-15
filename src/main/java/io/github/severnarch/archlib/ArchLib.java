package io.github.severnarch.archlib;

import io.github.severnarch.archlib.api.Command;
import io.github.severnarch.archlib.objects.classes.ArgumentedRunnable;
import io.github.severnarch.archlib.objects.classes.command.CommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ArchLib extends JavaPlugin {

    @Override
    public void onEnable() {
        Command mainCommand = new Command("archlib");
        CommandArgument mainCommandVersionArg = new CommandArgument(new ArgumentedRunnable() {
            @Override
            public void run(Object... args) {
                /*
                Arguments provided to a CommandArgument:
                    @NotNull CommandSender sender | args[0]
                    @NotNull org.bukkit.command.Command command | args[1]
                    @NotNull String label | args[2]
                             String[] args | args[3]
                 */
                if (args.length == 4) {
                    CommandSender sender = (CommandSender) args[0];
                    org.bukkit.command.Command command = (org.bukkit.command.Command) args[1];
                    String label = (String) args[2];
                    String[] arguments = (String[]) args[3];

                    sender.sendMessage("ArchLib is running on version "+JavaPlugin.getPlugin(ArchLib.class).getDescription().getVersion());
                } else {
                    getLogger().log(Level.SEVERE, "CommandArgument was passed an amount of arguments not equal to 4.");
                }
            }
        }, mainCommand);
        mainCommand.addArgument("version", mainCommandVersionArg);
        mainCommand.build();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
