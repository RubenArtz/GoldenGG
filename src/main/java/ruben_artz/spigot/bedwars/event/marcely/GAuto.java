package ruben_artz.spigot.bedwars.event.marcely;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.Team;
import de.marcely.bedwars.api.event.arena.RoundEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import ruben_artz.spigot.bedwars.GMain;
import ruben_artz.spigot.bedwars.utils.ProjectUtils;

import java.util.Objects;

public class GAuto implements Listener {
    private final GMain plugin = GMain.getPlugin(GMain.class);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRoundEndEvent(RoundEndEvent event) {
        Arena arena = event.getArena();

        /*
        Send message to players who have not died
         */
        arena.getPlayers().forEach(player -> event.getWinners().forEach(winner -> {
            Team team = arena.getPlayerTeam(winner);

            if (winner != null && team != null && winner.hasPermission("BedWars.Golden.Auto")) {
                ProjectUtils.sendMessage(player, winner, Objects.requireNonNull(plugin.getConfig().getString("GOLDEN-GG.TEXT-AUTO-GG"))
                        .replace("{Player}", winner.getName())
                        .replace("{Level}", "")
                        .replace("{Team Color}", team.getBungeeChatColor() + "[" + team.getDisplayName().toUpperCase() + "]"));
            }
        }));

        /*
        Send the message to players who are spectators
         */
        arena.getSpectators().forEach(player -> event.getWinners().forEach(winner -> {
            Team team = arena.getPlayerTeam(winner);

            if (winner != null && team != null && winner.hasPermission("BedWars.Golden.Auto")) {

                ProjectUtils.sendMessage(player, winner, Objects.requireNonNull(plugin.getConfig().getString("GOLDEN-GG.TEXT-AUTO-GG"))
                        .replace("{Player}", winner.getName())
                        .replace("{Level}", "")
                        .replace("{Team Color}", team.getBungeeChatColor() + "[" + team.getDisplayName().toUpperCase() + "]"));
            }
        }));
    }
}