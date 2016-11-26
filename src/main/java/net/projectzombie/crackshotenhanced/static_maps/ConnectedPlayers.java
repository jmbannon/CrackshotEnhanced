package net.projectzombie.crackshotenhanced.static_maps;

import net.projectzombie.crackshotenhanced.entities.CSEPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by jb on 11/25/16.
 */
public class ConnectedPlayers {
    private ConnectedPlayers() { /* Do nothing. */ }

    static private final HashMap<UUID, CSEPlayer> CONNECTED_PLAYERS = new HashMap<>();

    static public boolean contains(final UUID uuid) {
        return CONNECTED_PLAYERS.containsKey(uuid);
    }

    static public boolean contains(final Player player) {
        return CONNECTED_PLAYERS.containsKey(player.getUniqueId());
    }

    static public void add(final Player player) {
        CONNECTED_PLAYERS.put(player.getUniqueId(), new CSEPlayer(player));
    }

    static public CSEPlayer remove(final Player player) {
        return CONNECTED_PLAYERS.remove(player.getUniqueId());
    }

    static public CSEPlayer get(final UUID uuid) {
        return CONNECTED_PLAYERS.get(uuid);
    }

    static public CSEPlayer get(final Player player) {
        return CONNECTED_PLAYERS.get(player.getUniqueId());
    }

    static public CSEPlayer[] getAll() {
        final Collection<CSEPlayer> collection = CONNECTED_PLAYERS.values();
        return collection.toArray(new CSEPlayer[collection.size()]);
    }
}
