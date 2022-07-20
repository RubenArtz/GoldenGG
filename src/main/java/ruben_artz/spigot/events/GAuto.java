package ruben_artz.spigot.events;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import com.andrei1058.bedwars.api.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ruben_artz.spigot.main.GMain;
import ruben_artz.spigot.utils.ProjectUtils;

public class GAuto implements Listener {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void writeAuto(GameEndEvent event) {
        IArena arena = event.getArena();
        /*
        * Send message to players who have not died
         */
        arena.getPlayers().forEach(player -> event.getWinners().forEach(w -> {
            Player winner = Bukkit.getPlayer(w);
           if (winner != null && winner.hasPermission("BEDWARS1058.GOLDEN.AUTO")) {
               ITeam team = arena.getTeam(winner);
               ProjectUtils.sendMessage(player, winner, plugin.configManager.getString("GOLDEN-GG.TEXT-AUTO-GG")
                       .replaceAll("§", "&")
                       .replace("{Player}", winner.getName())
                       .replace("{Level}", plugin.bedWars.getLevelsUtil().getLevel(winner))
                       .replace("{Team Color}", team.getColor().chat() + "[" + team.getDisplayName(Language.getPlayerLanguage(winner)).toUpperCase() + "]"));
               plugin.write.remove(player.getUniqueId());
               plugin.write.remove(winner.getUniqueId());
           }
        }));
        /*
         * Send the message to players who are spectators
         */
        arena.getSpectators().forEach(player -> event.getWinners().forEach(w -> {
            Player winner = Bukkit.getPlayer(w);
            if (winner != null && winner.hasPermission("BEDWARS1058.GOLDEN.AUTO")) {
                ITeam team = arena.getTeam(winner);
                ProjectUtils.sendMessage(player, winner, plugin.configManager.getString("GOLDEN-GG.TEXT-AUTO-GG")
                        .replaceAll("§", "&")
                        .replace("{Player}", winner.getName())
                        .replace("{Level}", plugin.bedWars.getLevelsUtil().getLevel(winner))
                        .replace("{Team Color}", team.getColor().chat() + "[" + team.getDisplayName(Language.getPlayerLanguage(winner)).toUpperCase() + "]"));
                plugin.write.remove(player.getUniqueId());
                plugin.write.remove(winner.getUniqueId());
            }
        }));
    }
}