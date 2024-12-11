package ruben_artz.spigot.event.bedwars1058;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ruben_artz.spigot.GMain;
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
        if (player.hasPermission("BedWars.Golden") && plugin.getWrite().contains(player.getUniqueId()) && message.equalsIgnoreCase("gg")) {
            event.setMessage(message
                    .replace("gg", ProjectUtils.addColors(plugin.getConfig().getString("GOLDEN-GG.TEXT-COLOR")) + "gg")
                    .replace("GG", ProjectUtils.addColors(plugin.getConfig().getString("GOLDEN-GG.TEXT-COLOR")) + "GG"));
        }
    }
}