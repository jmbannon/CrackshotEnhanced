package net.projectzombie.crackshotenhanced.guns.attributes.attributeproperties;

import net.projectzombie.crackshotenhanced.entities.CSELivingEntity;

/**
 * Created by jb on 11/25/16.
 */
public interface TimedEvent {
    int getDurationInTicks();
    void onStart(final CSELivingEntity entity);
    void onEnd(final CSELivingEntity entity);

    boolean canStop(final CSELivingEntity victim);
}
