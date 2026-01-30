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

import de.marcely.bedwars.api.event.arena.RoundEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import artzstudio.dev.goldengg.GMain;
import artzstudio.dev.goldengg.utils.ProjectUtils;

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