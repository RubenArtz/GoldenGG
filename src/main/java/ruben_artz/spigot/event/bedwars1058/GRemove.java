package ruben_artz.spigot.event.bedwars1058;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ruben_artz.spigot.GMain;
import ruben_artz.spigot.utils.ProjectUtils;

public class GRemove implements Listener {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    /*
     * Removes all saved player UUIDs
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void writeAuto(GameEndEvent event) {
        ProjectUtils.syncTaskLater(180L, () -> event.getWinners().forEach(player ->
                plugin.getWrite().remove(player)));
    }
}