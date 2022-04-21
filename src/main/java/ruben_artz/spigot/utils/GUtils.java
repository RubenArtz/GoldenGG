package ruben_artz.spigot.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ruben_artz.spigot.main.GMain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUtils {
    private static final GMain plugin = GMain.getPlugin(GMain.class);
    private static final Pattern HEX_PATTERN_ONE = Pattern.compile("#([A-Fa-f\\d]{6})");
    private static final Pattern HEX_PATTERN_TWO = Pattern.compile("\\{#[a-fA-F\\d]{6}}");

    public static String addColors(final String message) {
        if (isVersion_1_16_To_1_19()) {
            return ChatColor.translateAlternateColorCodes('&', setHexColors(message));
        } else {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
    }

    public static String setPlaceholders(final Player p, final String text) {
        if (isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(p, text);
        }
        return text;
    }

    private static String getHex(String message) {
        Matcher matcher = HEX_PATTERN_ONE.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 32);
        while (matcher.find()) {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, "§x§" + group.charAt(0) + '§' + group.charAt(1) + '§' + group.charAt(2) + '§' + group.charAt(3) + '§' + group.charAt(4) + '§' + group.charAt(5));
        }
        return matcher.appendTail(buffer).toString();
    }

    private static String setHexColors(String msg) {
        Matcher match = HEX_PATTERN_TWO.matcher(msg);
        while (match.find()) {
            String hex = msg.substring(match.start(), match.end());
            msg = org.apache.commons.lang.StringUtils.replace(msg, hex, "" + net.md_5.bungee.api.ChatColor.of(hex.replace("{", "").replace("}", "")));
            match = HEX_PATTERN_TWO.matcher(msg);
        }
        return ChatColor.translateAlternateColorCodes('&', getHex(msg));
    }

    public static boolean isPluginEnabled(final String args) {
        return Bukkit.getPluginManager().getPlugin(args) != null && Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(args)).isEnabled();
    }

    public static void sendMessage(final Player everyone, final Player winner, final String message) {
        everyone.sendMessage(addColors(setPlaceholders(winner, message)));
    }

    public static void addGolden(final Player player) {
        plugin.write.add(player.getUniqueId());
    }

    public static void setMetrics() {
        final Metrics metrics = new Metrics(plugin, 12442);
        metrics.addCustomChart(new SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));
    }

    /*
    * It will check if they are using versions 1.16-1.19.
     */
    public static boolean isVersion_1_16_To_1_19() {
        return Bukkit.getVersion().contains("1.16")
                || (Bukkit.getVersion().contains("1.17"))
                || (Bukkit.getVersion().contains("1.18"))
                || (Bukkit.getVersion().contains("1.19"));
    }
}