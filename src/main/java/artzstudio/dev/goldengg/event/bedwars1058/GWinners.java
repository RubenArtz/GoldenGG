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

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import artzstudio.dev.goldengg.GMain;

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