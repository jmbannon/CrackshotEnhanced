package net.projectzombie.crackshotenhanced.static_maps;

import net.projectzombie.crackshotenhanced.entities.CSELivingEntity;
import net.projectzombie.crackshotenhanced.guns.attributes.attributeproperties.TimedEvent;
import net.projectzombie.crackshotenhanced.guns.attributes.attributeproperties.TimedEventPerTick;
import net.projectzombie.crackshotenhanced.main.Main;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by jb on 11/25/16.
 */
public class EntityTimedEvents {

    private EntityTimedEvents() { /* Do nothing. */ }

    static private final HashMap<UUID, EntityTimedEventsInfo> ENTITY_EVENT_MAP = new HashMap<>();

    static private class TimedEventInfo {

        private final TimedEvent event;
        private int ticksRemaining;

        private TimedEventInfo(final TimedEvent event) {
            this.event = event;
            this.ticksRemaining = event.getDurationInTicks();
        }

        private void decrementTicksRemaining() { --ticksRemaining; }
        private boolean isStart() { return event.getDurationInTicks() == ticksRemaining; }
        private boolean isEnd() { return ticksRemaining <= 0; }

    }

    static private class EntityTimedEventsInfo {

        private final CSELivingEntity entity;
        private LinkedList<TimedEventInfo> events;

        private EntityTimedEventsInfo(final CSELivingEntity entity,
                                      final TimedEvent event) {
            Main.info("Creating new timed event info");
            this.entity = entity;
            this.events = new LinkedList<>();
            this.events.add(new TimedEventInfo(event));
        }

        private void addTimedEvent(final TimedEvent event) {
            Main.info("Adding new timed event");
            this.events.addLast(new TimedEventInfo(event));
        }

        private void applyEventsOnTick() {
            final Iterator<TimedEventInfo> iterator = events.iterator();
            TimedEventInfo eventInfo;
            while (iterator.hasNext()) {
                eventInfo = iterator.next();
                if (eventInfo.event.canStop(entity)) {
                    iterator.remove();
                    continue;
                } else if (eventInfo.isEnd()) {
                    eventInfo.event.onEnd(entity);
                    iterator.remove();
                    continue;
                }
                if (eventInfo.isStart()) {
                    Main.info("Starting event!!!");
                    eventInfo.event.onStart(entity);
                }
                if (eventInfo.event instanceof TimedEventPerTick) {
                    ((TimedEventPerTick) eventInfo.event).applyEventPerTick(entity);
                }
                eventInfo.decrementTicksRemaining();
            }
        }
    }

    static public void add(final CSELivingEntity entity,
                           final TimedEvent event) {
        final UUID uuid = entity.getUniqueId();
        if (ENTITY_EVENT_MAP.containsKey(uuid)) {
            EntityTimedEventsInfo info = ENTITY_EVENT_MAP.get(uuid);
            info.addTimedEvent(event);
            ENTITY_EVENT_MAP.put(uuid, info);
        } else {
            ENTITY_EVENT_MAP.put(uuid, new EntityTimedEventsInfo(entity, event));
        }
    }

    static public void runAllEventsOnTick() {
        ENTITY_EVENT_MAP.values().forEach(EntityTimedEventsInfo::applyEventsOnTick);
    }

}
