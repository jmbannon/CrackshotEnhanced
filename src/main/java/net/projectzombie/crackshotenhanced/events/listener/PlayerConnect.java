package net.projectzombie.crackshotenhanced.events.listener;

import net.projectzombie.crackshotenhanced.static_maps.ConnectedPlayers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by jb on 11/17/16.
 */
public class PlayerConnect implements Listener {

    static private final PlayerConnect LISTENER = new PlayerConnect();

    static public PlayerConnect getListener() { return LISTENER; }
    private PlayerConnect() { /* Do nothing */ }

    @EventHandler(priority = EventPriority.HIGH)
    public void playerJoin(PlayerJoinEvent e)
    {
        final Player player = e.getPlayer();
        if (!ConnectedPlayers.contains(player)) {
            ConnectedPlayers.add(player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void playerExit(PlayerQuitEvent e)
    {
        final Player player = e.getPlayer();
        if (ConnectedPlayers.contains(player)) {
            ConnectedPlayers.remove(player);
        }
    }
}
