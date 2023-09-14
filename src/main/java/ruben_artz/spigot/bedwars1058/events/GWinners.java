package ruben_artz.spigot.bedwars1058.events;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ruben_artz.spigot.GMain;

public class GWinners implements Listener {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    /*
     * Adds the player to the list of UUID's
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerWin(GameEndEvent event) {
        event.getWinners().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null && player.hasPermission("BedWars.Golden")) {

                plugin.getWrite().add(player.getUniqueId());
            }
        });
    }
}