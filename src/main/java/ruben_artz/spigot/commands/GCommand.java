package ruben_artz.spigot.commands;

import com.google.common.collect.ImmutableList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import ruben_artz.spigot.main.GMain;
import ruben_artz.spigot.utils.GUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GCommand implements CommandExecutor, TabCompleter {
    private static final GMain plugin = GMain.getPlugin(GMain.class);

    public boolean onCommand(final CommandSender sender, final @NotNull Command command, final @NotNull String label, final String[] array) {
        if (!sender.hasPermission("BEDWARS1058.ADMIN")) {
            sender.sendMessage(GUtils.addColors("You do not have permissions to execute this command!"));
            return true;
        }
        if (array.length == 0) {
            sender.sendMessage(GUtils.addColors("&aExecute the ( /golden reload ) command to reload the configuration."));
        } else if (array.length == 1) {
            if (array[0].equalsIgnoreCase("reload")) {
                GCommand.plugin.configManager.reload();
                sender.sendMessage(GUtils.addColors("&aThe configuration has been successfully reloaded!"));
                return false;
            }
            sender.sendMessage(GUtils.addColors("&aExecute the ( /golden reload ) command to reload the configuration."));
        }
        return false;
    }

    public List<String> onTabComplete(final CommandSender commandSender, final @NotNull Command command, final @NotNull String s, final String[] strings) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();
        if (!commandSender.hasPermission("BEDWARS1058.ADMIN")) {
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