package net.projectzombie.crackshotenhanced.guns.attributes.attributeproperties;

import net.projectzombie.crackshotenhanced.entities.CSELivingEntity;

/**
 * Created by jb on 11/25/16.
 */
public interface TimedEventPerTick extends TimedEvent {
    void applyEventPerTick(final CSELivingEntity victim);
}
