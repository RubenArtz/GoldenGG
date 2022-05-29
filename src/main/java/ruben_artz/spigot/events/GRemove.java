package ruben_artz.spigot.events;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ruben_artz.spigot.main.GMain;

public class GRemove implements Listener {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    /*
    * Removes all saved player UUIDs
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void removeGolden(GameStateChangeEvent event) {
        IArena arena = event.getArena();
        if (event.getNewState().equals(GameState.restarting)) {
            arena.getPlayers().forEach(player -> plugin.write.remove(player.getUniqueId()));
        }
    }
}