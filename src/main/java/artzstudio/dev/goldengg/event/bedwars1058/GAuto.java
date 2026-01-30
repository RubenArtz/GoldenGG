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

package artzstudio.dev.goldengg.event.bedwars1058;

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
import artzstudio.dev.goldengg.GMain;
import artzstudio.dev.goldengg.utils.ProjectUtils;

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