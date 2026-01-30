/*
 *
 *  * Copyright (c) 2026 RubenArtz and Artz Studio.
 *  *
 *  * This file is part of GoldenGG.
 *  *
 *  * GoldenGG is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * GoldenGG is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with GoldenGG.  If not, see https://www.gnu.org/licenses/.
 *
 */

package artzstudio.dev.goldengg.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import artzstudio.dev.goldengg.GMain;

import java.util.Objects;

public class ProjectUtils {
    private static final GMain plugin = GMain.getPlugin(GMain.class);

    public static String addColors(String input) {
        if ((input == null) || (input.isEmpty())) return input;
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static boolean isPluginEnabled(final String args) {
        return Bukkit.getPluginManager().getPlugin(args) != null && Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(args)).isEnabled();
    }

    public static String setPlaceholders(Player player, String text) {
        if (isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(player, text);
        }
        return text;
    }

    public static void sendMessage(final Player everyone, final Player winner, final String message) {
        everyone.sendMessage(addColors(setPlaceholders(winner, message)));
        plugin.sendConsole(addColors(setPlaceholders(winner, message)));
    }

    public static void setMetrics() {
        final Metrics metrics = new Metrics(plugin, 12442);
        metrics.addCustomChart(new SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));
    }

    public static String getName() {
        return ProjectUtils.isPluginEnabled("BedWars1058") ? "BedWars1058" : (ProjectUtils.isPluginEnabled("MBedwars") ? "MBedwars" : "None");
    }

    public static void syncTaskLater(long delay, Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
    }
}