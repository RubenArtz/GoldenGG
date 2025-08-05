package ruben_artz.spigot.bedwars.event.marcely;

import de.marcely.bedwars.api.event.arena.RoundEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ruben_artz.spigot.bedwars.GMain;

public class GWinners implements Listener {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    /*
    Adds the player to the list of UUID's
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRoundEndEvent(RoundEndEvent event) {
        event.getWinners().forEach(player -> {

            if (player != null && player.hasPermission("BedWars.Golden")) {
                plugin.getWrite().add(player.getUniqueId());
            }
        });
    }
}