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

package artzstudio.dev.goldengg;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import artzstudio.dev.goldengg.commands.GCommand;
import artzstudio.dev.goldengg.event.bedwars1058.GAuto;
import artzstudio.dev.goldengg.event.bedwars1058.GRemove;
import artzstudio.dev.goldengg.event.bedwars1058.GWinners;
import artzstudio.dev.goldengg.event.bedwars1058.GWrite;
import artzstudio.dev.goldengg.utils.ProjectUtils;
import artzstudio.dev.goldengg.utils.Updater;

import java.util.*;

public final class GMain extends JavaPlugin {
    private final PluginManager pluginManager = Bukkit.getPluginManager();
    public String latestVersion;

    @Getter
    String version = getDescription().getVersion();
    @Getter
    String prefix = "&8[&9Golden GG&8]&f ";
    @Getter
    List<UUID> write = new ArrayList<>();

    public void onEnable() {
        if (ProjectUtils.isPluginEnabled("MBedwars") && ProjectUtils.isPluginEnabled("BedWars1058")) {
            sendConsole(getPrefix() + "&cSorry we cannot run the plugin if you have more than one BedWars plugin installed.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        setRegisterCommand();
        setUtils();

        /*
        Marcely's Bedwars
         */
        if (ProjectUtils.isPluginEnabled("MBedwars")) {
            final int supportedAPIVersion = 114;
            final String supportedVersionName = "5.4.15";

            try {
                Class<?> apiClass = Class.forName("de.marcely.bedwars.api.BedwarsAPI");
                int apiVersion = (int) apiClass.getMethod("getAPIVersion").invoke(null);

                if (apiVersion < supportedAPIVersion)
                    throw new IllegalStateException();
            } catch (Exception e) {
                sendConsole(getPrefix() + "&cSorry, your installed version of MBedwars is not supported. Please install at least v" + supportedVersionName);
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            Arrays.asList(
                            new artzstudio.dev.goldengg.event.marcely.GWrite(),
                            new artzstudio.dev.goldengg.event.marcely.GAuto(),
                            new artzstudio.dev.goldengg.event.marcely.GRemove(),
                            new artzstudio.dev.goldengg.event.marcely.GWinners())

                    .forEach((listener -> pluginManager.registerEvents(listener, this)));
        }

        /*
        BedWars 1058
         */

        else if (ProjectUtils.isPluginEnabled("BedWars1058")) {
            Arrays.asList(
                            new GWrite(),
                            new GAuto(),
                            new GRemove(),
                            new GWinners())

                    .forEach((listener -> pluginManager.registerEvents(listener, this)));
        }

        this.sendWelcome();
    }

    public void onDisable() {
        Updater.shutdown();
    }

    public void setRegisterCommand() {
        Objects.requireNonNull(this.getCommand("golden")).setExecutor(new GCommand());
    }

    public void setUtils() {
        saveDefaultConfig();
        reloadConfig();

        ProjectUtils.setMetrics();
        Updater.launch();
    }

    public void sendConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(ProjectUtils.addColors(message));
    }

    private void sendWelcome() {
        this.sendConsole("");
        this.sendConsole("");
        this.sendConsole("&9  $$$$$$\\   $$$$$$\\  $$\\       $$$$$$$\\  $$$$$$$$\\ $$\\   $$\\ ");
        this.sendConsole("&9 $$  __$$\\ $$  __$$\\ $$ |      $$  __$$\\ $$  _____|$$$\\  $$ |");
        this.sendConsole("&9 $$ /  \\__|$$ /  $$ |$$ |      $$ |  $$ |$$ |      $$$$\\ $$ |");
        this.sendConsole("&9 $$ |$$$$\\ $$ |  $$ |$$ |      $$ |  $$ |$$$$$\\    $$ $$\\$$ |");
        this.sendConsole("&9 $$ |\\_$$ |$$ |  $$ |$$ |      $$ |  $$ |$$  __|   $$ \\$$$$ |");
        this.sendConsole("&9 $$ |  $$ |$$ |  $$ |$$ |      $$ |  $$ |$$ |      $$ |\\$$$ |");
        this.sendConsole("&9 \\$$$$$$  | $$$$$$  |$$$$$$$$\\ $$$$$$$  |$$$$$$$$\\ $$ | \\$$ |");
        this.sendConsole("&9  \\______/  \\______/ \\________|\\_______/ \\________|\\__|  \\__|");
        this.sendConsole("&9");
        this.sendConsole("&9              $$$$$$\\   $$$$$$\\ ");
        this.sendConsole("&9             $$  __$$\\ $$  __$$\\ ");
        this.sendConsole("&9             $$ /  $$ |$$ /  $$ |");
        this.sendConsole("&9             $$ |  $$ |$$ |  $$ | ");
        this.sendConsole("&9             \\$$$$$$$ |\\$$$$$$$ |");
        this.sendConsole("&9              \\____$$ | \\____$$ |");
        this.sendConsole("&9             $$\\   $$ |$$\\   $$ |");
        this.sendConsole("&9             \\$$$$$$  |\\$$$$$$  |");
        this.sendConsole("&9              \\______/  \\______/");
        this.sendConsole("");
        this.sendConsole("&7         Developed by &c" + getDescription().getAuthors());
        this.sendConsole(getPrefix() + "&aVersion: &c" + getVersion() + " &ais loading...");
        this.sendConsole(getPrefix() + "&aServer: &c" + Bukkit.getVersion());
        this.sendConsole(getPrefix() + "&aLoading necessary files...");
        this.sendConsole(getPrefix());
        this.sendConsole(getPrefix() + "&aPlugin support: &c" + ProjectUtils.getName());
        this.sendConsole(getPrefix());
        this.sendConsole(getPrefix() + "&aStarting plugin...");
        this.sendConsole("");
        this.sendConsole("");
    }
}
