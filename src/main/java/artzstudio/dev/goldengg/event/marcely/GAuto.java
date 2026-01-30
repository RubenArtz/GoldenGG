/*
 *
 *  * Copyright (c) 2026 RubenArtz and Artz Studio.
 *  *
 *  * This file is part of GoldenGG.
 *  *
 *  * GoldenGG is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * GoldenGG is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with GoldenGG.  If not, see https://www.gnu.org/licenses/.
 *
 */

package artzstudio.dev.goldengg.event.marcely;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.arena.Team;
import de.marcely.bedwars.api.event.arena.RoundEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import artzstudio.dev.goldengg.GMain;
import artzstudio.dev.goldengg.utils.ProjectUtils;

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