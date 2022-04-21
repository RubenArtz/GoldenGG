package ruben_artz.spigot.main;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ruben_artz.spigot.commands.GCommand;
import ruben_artz.spigot.events.GAuto;
import ruben_artz.spigot.events.GRemove;
import ruben_artz.spigot.events.GWinners;
import ruben_artz.spigot.events.GWrite;
import ruben_artz.spigot.utils.GUpdater;
import ruben_artz.spigot.utils.GUtils;

import java.util.*;
import java.util.logging.Level;

public final class GMain extends JavaPlugin {
    public ConfigManager configManager;
    public String latestVersion;
    public String version;
    public BedWars bedWars;
    public List<UUID> write = new ArrayList<>();

    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("BedWars1058")) {
            this.sendLogger(Level.SEVERE, "Error BedWars 1058 plugin is not installed, disabling plugin...");
            this.setEnabled(false);
            return;
        }
        getGolden();
        setRegister();
        setRegisterCommand();
        setUtils();
    }

    public void onDisable() {
        GUpdater.shutdown();
    }

    public void setRegisterCommand() {
        Objects.requireNonNull(this.getCommand("golden")).setExecutor(new GCommand());
    }

    public void setRegister() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        Arrays.asList(new GWrite(),
                new GWinners(),
                new GRemove(),
                new GAuto()).forEach(listener -> pluginManager.registerEvents(listener, this));
        this.bedWars = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(BedWars.class)).getProvider();
    }

    public void setUtils() {
        GUtils.setMetrics();
        GUpdater.launch();
    }

    public void sendLogger(final Level level, final String message) {
        Bukkit.getLogger().log(level, message);
    }

    public void getGolden() {
        if (Bukkit.getPluginManager().isPluginEnabled("BedWars1058")) {
            this.configManager = new ConfigManager(this, "config", "plugins/BedWars1058/Addons/GoldenGG");
            final YamlConfiguration yaml = this.configManager.getYml();
            yaml.addDefault("GOLDEN-GG.TEXT-COLOR", "&6");
            yaml.addDefault("GOLDEN-GG.TEXT-AUTO-GG", "{Level}{Team Color} %vault_prefix%{Player}&f: &6gg");
            yaml.options().copyDefaults(true);
            this.configManager.save();
        }
    }
}
