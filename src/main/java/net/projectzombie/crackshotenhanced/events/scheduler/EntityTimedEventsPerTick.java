package net.projectzombie.crackshotenhanced.events.scheduler;

import net.projectzombie.crackshotenhanced.static_maps.EntityTimedEvents;

/**
 * Created by jb on 11/25/16.
 */
public class EntityTimedEventsPerTick implements Runnable {
    @Override
    public void run() {
        EntityTimedEvents.runAllEventsOnTick();
    }
}