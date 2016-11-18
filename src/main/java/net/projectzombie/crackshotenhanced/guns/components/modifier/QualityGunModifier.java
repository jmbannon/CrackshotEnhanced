package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;

/**
 * Created by jb on 11/17/16.
 */
abstract public class QualityGunModifier extends GunModifier {

    private final Qualities.Quality quality;
    public QualityGunModifier(final int uniqueID,
                              final String name,
                              final String material,
                              final int materialData,
                              final int price,
                              final String color,
                              final Qualities.Quality quality)
    {
        super(uniqueID, name, material, materialData, price, color);
        this.quality = quality;
    }

    public Qualities.Quality getQuality() { return quality; }

}
