package ruben_artz.spigot.marcely.events;

import de.marcely.bedwars.api.event.arena.RoundEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ruben_artz.spigot.GMain;
import ruben_artz.spigot.utils.ProjectUtils;

public class GRemove implements Listener {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    /*
    Removes all saved player UUIDs
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRoundEndEvent(RoundEndEvent event) {

        ProjectUtils.syncTaskLater(180L, () -> event.getWinners().forEach(player ->
                plugin.getWrite().remove(player.getUniqueId())));
    }
}