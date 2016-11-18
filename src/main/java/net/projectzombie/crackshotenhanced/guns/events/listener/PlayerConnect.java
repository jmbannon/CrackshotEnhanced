package net.projectzombie.crackshotenhanced.guns.events.listener;

import net.projectzombie.crackshotenhanced.guns.utilities.CSEPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by jb on 11/17/16.
 */
public class PlayerConnect implements Listener {

    static private final HashMap<UUID, CSEPlayer> CONNECTED_PLAYERS = new HashMap<>();
    static private final PlayerConnect LISTENER = new PlayerConnect();

    static public PlayerConnect getListener() { return LISTENER; }
    private PlayerConnect() { /* Do nothing */ }

    static public CSEPlayer[] getPlayers() {
        final Collection<CSEPlayer> collection = CONNECTED_PLAYERS.values();
        return collection.toArray(new CSEPlayer[collection.size()]);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void playerJoin(PlayerJoinEvent e)
    {
        final Player player = e.getPlayer();
        final UUID playerUUID = player.getUniqueId();
        if (!CONNECTED_PLAYERS.containsKey(playerUUID)) {
            CONNECTED_PLAYERS.put(playerUUID, new CSEPlayer(player));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void playerExit(PlayerQuitEvent e)
    {
        final Player player = e.getPlayer();
        final UUID playerUUID = player.getUniqueId();
        if (CONNECTED_PLAYERS.containsKey(playerUUID)) {
            CONNECTED_PLAYERS.remove(playerUUID);
        }
    }
}
