package net.projectzombie.crackshotenhanced.guns.events.scheduler;

import net.projectzombie.crackshotenhanced.guns.events.listener.PlayerConnect;
import net.projectzombie.crackshotenhanced.guns.utilities.CSEPlayer;

/**
 * Created by jb on 11/17/16.
 */
public class CSEPlayerRunningSpeed implements Runnable {
    @Override
    public void run() {
        for (CSEPlayer player : PlayerConnect.getPlayers()) {
            player.adjustRunningSpeed();
        }
    }
}
