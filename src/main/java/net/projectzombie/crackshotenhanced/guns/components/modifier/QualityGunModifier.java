package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.crafting.CraftableType;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;

abstract public class QualityGunModifier extends GunModifier {

    private final Qualities.Quality quality;

    public QualityGunModifier(final int uniqueID,
                              final String name,
                              final int price,
                              final String color,
                              final Qualities.Quality quality,
                              final CraftableType type)
    {
        super(uniqueID, name, price, color, type);
        this.quality = quality;
    }

    public Qualities.Quality getQuality() { return quality; }
}
