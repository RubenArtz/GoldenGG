package ruben_artz.spigot.utils;

import org.bukkit.Bukkit;
import ruben_artz.spigot.main.GMain;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;

public class Updater {
    private static final GMain plugin = GMain.getPlugin(GMain.class);
    private static Integer task;

    @SuppressWarnings("deprecation")
    public static void launch() {
        Updater.task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, Updater::getUpdater, 0L, 360000L);
    }

    public static void shutdown() {
        if (Updater.task != null) {
            Bukkit.getScheduler().cancelTask(Updater.task);
        }
    }

    public static void getUpdater() {
        try {
            final HttpsURLConnection connection = (HttpsURLConnection)new URL("https://api.spigotmc.org/legacy/update.php?resource=95321").openConnection();
            final int time_out = 1250;
            connection.setConnectTimeout(time_out);
            connection.setReadTimeout(time_out);
            Updater.plugin.latestVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            if (Updater.plugin.latestVersion.length() <= 7 && !Updater.plugin.version.equals(Updater.plugin.latestVersion)) {
                Updater.plugin.sendLogger(Level.WARNING, "--------------------------------------------------------------------------------------");
                Updater.plugin.sendLogger(Level.WARNING, "You have an old version of the GoldenGG plugin.");
                Updater.plugin.sendLogger(Level.WARNING, "Please download the latest &e" + Updater.plugin.latestVersion + " version.");
                Updater.plugin.sendLogger(Level.WARNING, "--------------------------------------------------------------------------------------");
            }
        }
        catch (Exception ignored) {}
    }
}
