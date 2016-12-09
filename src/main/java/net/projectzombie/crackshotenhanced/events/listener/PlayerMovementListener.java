package net.projectzombie.crackshotenhanced.events.listener;

import net.projectzombie.crackshotenhanced.static_maps.ConnectedPlayers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by jb on 12/8/16.
 */
public class PlayerMovementListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        ConnectedPlayers.get(event.getPlayer()).onPlayerMove(event);
    }
}
