package ruben_artz.spigot;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ruben_artz.spigot.commands.GCommand;
import ruben_artz.spigot.utils.ProjectUtils;
import ruben_artz.spigot.utils.Updater;

import java.util.*;

public final class GMain extends JavaPlugin {
    private final PluginManager pluginManager = Bukkit.getPluginManager();
    public String latestVersion;
    @Getter String version;
    @Getter String prefix = "&8[&9GoldenGG&8]&f ";
    @Getter List<UUID> write = new ArrayList<>();

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
            final int supportedAPIVersion = 24;
            final String supportedVersionName = "5.3.1";

            try {
                Class<?> apiClass = Class.forName("de.marcely.bedwars.api.BedwarsAPI");
                int apiVersion = (int) apiClass.getMethod("getAPIVersion").invoke(null);

                if (apiVersion < supportedAPIVersion)
                    throw new IllegalStateException();
            } catch(Exception e) {
                sendConsole(getPrefix() + "&cSorry, your installed version of MBedwars is not supported. Please install at least v" + supportedVersionName);
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }

            Arrays.asList(
                    new ruben_artz.spigot.marcely.events.GWrite(),
                    new ruben_artz.spigot.marcely.events.GAuto(),
                    new ruben_artz.spigot.marcely.events.GRemove(),
                    new ruben_artz.spigot.marcely.events.GWinners())

            .forEach((listener -> pluginManager.registerEvents(listener, this)));
        }

        /*
        BedWars 1058
         */

        else if (ProjectUtils.isPluginEnabled("BedWars1058")) {
            Arrays.asList(
                    new ruben_artz.spigot.bedwars1058.events.GWrite(),
                    new ruben_artz.spigot.bedwars1058.events.GAuto(),
                    new ruben_artz.spigot.bedwars1058.events.GRemove(),
                    new ruben_artz.spigot.bedwars1058.events.GWinners())

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
        this.sendConsole("&7         Developed by &c"+ getDescription().getAuthors());
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
