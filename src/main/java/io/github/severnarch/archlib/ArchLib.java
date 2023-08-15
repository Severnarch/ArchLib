package io.github.severnarch.archlib;

import io.github.severnarch.archlib.api.Command;
import io.github.severnarch.archlib.objects.classes.ArgumentedRunnable;
import io.github.severnarch.archlib.objects.classes.command.CommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ArchLib extends JavaPlugin {

    public List<Plugin> getSupportingPlugins() {
        List<Plugin> supportingPlugins = new java.util.ArrayList<>(List.of());
        for (Plugin plugin : JavaPlugin.getPlugin(ArchLib.class).getServer().getPluginManager().getPlugins()) {
            for (String pluginDepend : plugin.getDescription().getDepend()) {
                if (pluginDepend.equalsIgnoreCase("archlib")) {
                    supportingPlugins.add(plugin);
                }
            }
        }
        return supportingPlugins;
    }

    @Override
    public void onEnable() {
        Command mainCommand = new Command("archlib");
        CommandArgument mainCommandVersionArg = new CommandArgument(new ArgumentedRunnable() {
            @Override public void run(Object... args) {
                if (args.length == 4) {
                    CommandSender sender = (CommandSender) args[0];

                    sender.sendMessage("ArchLib is running on version "+JavaPlugin.getPlugin(ArchLib.class).getDescription().getVersion());
                }
            }
        }, mainCommand);
        CommandArgument mainCommandPluginsArg = new CommandArgument(new ArgumentedRunnable() {
            @Override public void run(Object... args) {
                if (args.length == 4) {
                    CommandSender sender = (CommandSender) args[0];

                    sender.sendMessage("ArchLib is currently being used by "+getSupportingPlugins().size()+" plugins.");
                }
            }
        }, mainCommand);
        mainCommandPluginsArg.addChildArgument("list", new CommandArgument(new ArgumentedRunnable() {
            @Override public void run(Object... args) {
                CommandSender sender = (CommandSender) args[0];
                String listString = "";
                for (Plugin supportedPlugin : getSupportingPlugins()) {
                    listString = listString.concat(" - "+supportedPlugin.getName()+"\n");
                }
                sender.sendMessage("ArchLib is currently being used by the following "+getSupportingPlugins().size()+" plugins:\n "+listString.strip());
            }
        }, mainCommand));
        mainCommand.addArgument("version", mainCommandVersionArg);
        mainCommand.addArgument("plugins", mainCommandPluginsArg);
        mainCommand.build();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
