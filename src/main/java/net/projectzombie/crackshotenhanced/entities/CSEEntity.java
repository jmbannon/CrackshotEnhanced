package net.projectzombie.crackshotenhanced.entities;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class CSEEntity<T extends Entity> {


    private final T entity;
    public CSEEntity(final T entity) {
        this.entity = entity;
    }

    public T toBukkit() {
        return entity;
    }

    public boolean isInWater()
    {
        return isWater(this.entity.getLocation().getBlock());
    }

    public UUID getUniqueId() {
        return entity.getUniqueId();
    }

    /** @return True if the entity is not null. False otherwise. */
    public boolean isValid() { return entity != null; }

    static private boolean isWater(final Block block)
    {
        final Material type = block.getType();
        return type.equals(Material.WATER) || type.equals(Material.STATIONARY_WATER);
    }

}
