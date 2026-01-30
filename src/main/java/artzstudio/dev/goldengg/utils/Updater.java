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

import org.bukkit.Bukkit;
import artzstudio.dev.goldengg.GMain;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Updater {
    private static final GMain plugin = GMain.getPlugin(GMain.class);
    private static Integer task;

    @SuppressWarnings("deprecation")
    public static void launch() {
        task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, Updater::getUpdater, 0L, 360000L);
    }

    public static void shutdown() {
        if (task != null) {
            Bukkit.getScheduler().cancelTask(task);
        }
    }

    public static void getUpdater() {
        try {
            final HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=95321").openConnection();
            final int time_out = 1250;
            connection.setConnectTimeout(time_out);
            connection.setReadTimeout(time_out);
            plugin.latestVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
            if (plugin.latestVersion.length() <= 7 && !plugin.getVersion().equals(plugin.latestVersion)) {
                plugin.sendConsole("--------------------------------------------------------------------------------------");
                plugin.sendConsole("You have an old version of the GoldenGG plugin.");
                plugin.sendConsole("Please download the latest &e" + plugin.latestVersion + " version.");
                plugin.sendConsole("--------------------------------------------------------------------------------------");
            }
        } catch (Exception ignored) {
        }
    }
}
