package ruben_artz.spigot.utils;

import org.bukkit.Bukkit;
import ruben_artz.spigot.main.GMain;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;

public class GUpdater {
    private static final GMain plugin = GMain.getPlugin(GMain.class);
    private static Integer task;

    @SuppressWarnings("deprecation")
    public static void launch() {
        GUpdater.task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, GUpdater::getUpdater, 0L, 360000L);
    }

    public static void shutdown() {
        if (GUpdater.task != null) {
            Bukkit.getScheduler().cancelTask(GUpdater.task);
        }
    }

    public static void getUpdater() {
        try {
            final HttpsURLConnection connection = (HttpsURLConnection)new URL("https://api.spigotmc.org/legacy/update.php?resource=95321").openConnection();
            final int time_out = 1250;
            connection.setConnectTimeout(time_out);
            connection.setReadTimeout(time_out);
            GUpdater.plugin.latestVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            if (GUpdater.plugin.latestVersion.length() <= 7 && !GUpdater.plugin.version.equals(GUpdater.plugin.latestVersion)) {
                GUpdater.plugin.sendLogger(Level.WARNING, "--------------------------------------------------------------------------------------");
                GUpdater.plugin.sendLogger(Level.WARNING, "You have an old version of the GoldenGG plugin.");
                GUpdater.plugin.sendLogger(Level.WARNING, "Please download the latest &e" + GUpdater.plugin.latestVersion + " version.");
                GUpdater.plugin.sendLogger(Level.WARNING, "--------------------------------------------------------------------------------------");
            }
        }
        catch (Exception exception) {
            GUpdater.plugin.sendLogger(Level.SEVERE, "We have problems establishing connection with spigotmc.org");
        }
    }
}
