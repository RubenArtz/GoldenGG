package ruben_artz.spigot.bedwars.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import ruben_artz.spigot.bedwars.GMain;
import ruben_artz.spigot.bedwars.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GCommand implements CommandExecutor, TabCompleter {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    public boolean onCommand(final CommandSender sender, final @NotNull Command command, final @NotNull String label, final String[] array) {
        if (!sender.hasPermission("BedWars.Admin")) {
            sender.sendMessage(ProjectUtils.addColors("You do not have permissions to execute this command!"));
            return true;
        }
        if (array.length == 0) {
            sender.sendMessage(ProjectUtils.addColors("&aExecute the ( /golden reload ) command to reload the configuration."));
        } else if (array.length == 1) {
            if (array[0].equalsIgnoreCase("reload")) {
                plugin.saveDefaultConfig();
                plugin.reloadConfig();
                sender.sendMessage(ProjectUtils.addColors("&aThe configuration has been successfully reloaded!"));
                return false;
            }
            sender.sendMessage(ProjectUtils.addColors("&aExecute the ( /golden reload ) command to reload the configuration."));
        }
        return false;
    }

    public List<String> onTabComplete(final CommandSender commandSender, final @NotNull Command command, final @NotNull String s, final String[] strings) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        if (!commandSender.hasPermission("BedWars.Admin")) {
            return null;
        }
        if (strings.length == 1) {
            final String partialCommand = strings[0];
            commands.add("reload");
            StringUtil.copyPartialMatches(partialCommand, commands, completions);
            Collections.sort(completions);
            return completions;
        }
        return ImmutableList.of();
    }
}