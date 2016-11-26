package net.projectzombie.crackshotenhanced.entities;

import net.projectzombie.crackshotenhanced.entities.CSEEntity;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

/**
 * Created by jb on 11/25/16.
 */
public class CSELivingEntity<T extends LivingEntity> extends CSEEntity<T> {

    private final double defaultSpeed;

    public CSELivingEntity(final T livingEntity) {
        super(livingEntity);
        this.defaultSpeed = livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
    }

    @Override public T toBukkit() { return super.toBukkit(); }

    public double getDefaultSpeed() { return defaultSpeed; }
    public double getSpeed() { return super.toBukkit().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue(); }

    public void setSpeed(final double speed) {
        super.toBukkit().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
    }
    public void restoreDefaultSpeed() { setSpeed(getDefaultSpeed()); }


}
