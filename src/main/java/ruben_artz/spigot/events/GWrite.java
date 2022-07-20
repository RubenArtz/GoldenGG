package ruben_artz.spigot.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ruben_artz.spigot.main.GMain;
import ruben_artz.spigot.utils.ProjectUtils;

public class GWrite implements Listener {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    /*
     * Allows the player to write ( gg, GG ) in color
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (player.hasPermission("BEDWARS1058.GOLDEN") && plugin.write.contains(player.getUniqueId()) && message.equalsIgnoreCase("gg")) {
            event.setMessage(message
                    .replace("gg", ProjectUtils.addColors(plugin.configManager.getString("GOLDEN-GG.TEXT-COLOR")) + "gg")
                    .replace("GG", ProjectUtils.addColors(plugin.configManager.getString("GOLDEN-GG.TEXT-COLOR")) + "GG"));
            plugin.write.remove(player.getUniqueId());
        }
    }
}
