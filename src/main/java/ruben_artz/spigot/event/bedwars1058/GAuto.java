package ruben_artz.spigot.event.bedwars1058;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ruben_artz.spigot.GMain;
import ruben_artz.spigot.utils.ProjectUtils;

import java.util.Objects;

public class GAuto implements Listener {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void writeAuto(GameEndEvent event) {
        IArena arena = event.getArena();
        BedWars bedWars = Objects.requireNonNull(Bukkit.getServicesManager().getRegistration(BedWars.class)).getProvider();

        /*
         * Send message to players who have not died
         */
        arena.getPlayers().forEach(player -> event.getWinners().forEach(w -> {
            Player winner = Bukkit.getPlayer(w);
            if (winner != null && winner.hasPermission("BedWars.Golden.Auto")) {
                ITeam team = arena.getTeam(winner);
                ProjectUtils.sendMessage(player, winner, Objects.requireNonNull(plugin.getConfig().getString("GOLDEN-GG.TEXT-AUTO-GG"))
                        .replaceAll("§", "&")
                        .replace("{Player}", winner.getName())
                        .replace("{Level}", bedWars.getLevelsUtil().getLevel(winner))
                        .replace("{Team Color}", team.getColor().chat() + "[" + team.getDisplayName(Language.getPlayerLanguage(winner)).toUpperCase() + "]"));
                plugin.getWrite().remove(player.getUniqueId());
                plugin.getWrite().remove(winner.getUniqueId());
            }
        }));
        /*
         * Send the message to players who are spectators
         */
        arena.getSpectators().forEach(player -> event.getWinners().forEach(w -> {
            Player winner = Bukkit.getPlayer(w);
            if (winner != null && winner.hasPermission("BedWars.Golden.Auto")) {
                ITeam team = arena.getTeam(winner);
                ProjectUtils.sendMessage(player, winner, Objects.requireNonNull(plugin.getConfig().getString("GOLDEN-GG.TEXT-AUTO-GG"))
                        .replaceAll("§", "&")
                        .replace("{Player}", winner.getName())
                        .replace("{Level}", bedWars.getLevelsUtil().getLevel(winner))
                        .replace("{Team Color}", team.getColor().chat() + "[" + team.getDisplayName(Language.getPlayerLanguage(winner)).toUpperCase() + "]"));
                plugin.getWrite().remove(player.getUniqueId());
                plugin.getWrite().remove(winner.getUniqueId());
            }
        }));
    }
}