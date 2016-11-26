package net.projectzombie.crackshotenhanced.entities;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.UUID;

/**
 * Created by jb on 11/25/16.
 */
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

    static private boolean isWater(final Block block)
    {
        final Material type = block.getType();
        return type.equals(Material.WATER) || type.equals(Material.STATIONARY_WATER);
    }

}
