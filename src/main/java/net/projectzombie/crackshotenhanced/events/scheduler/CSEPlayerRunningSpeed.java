package net.projectzombie.crackshotenhanced.events.scheduler;

import net.projectzombie.crackshotenhanced.static_maps.ConnectedPlayers;
import net.projectzombie.crackshotenhanced.entities.CSEPlayer;

/**
 * Created by jb on 11/17/16.
 */
public class CSEPlayerRunningSpeed implements Runnable {
    @Override
    public void run() {
        for (CSEPlayer player : ConnectedPlayers.getAll()) {
            player.adjustRunningSpeed();
        }
    }
}
