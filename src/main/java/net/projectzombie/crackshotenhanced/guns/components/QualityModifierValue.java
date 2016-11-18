package net.projectzombie.crackshotenhanced.guns.components;

import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.yaml.ModifierValue;

/**
 * Created by jb on 11/17/16.
 */
public class QualityModifierValue extends ModifierValue {

    private final Qualities.Quality quality;
    public QualityModifierValue(final int index,
                                final String name,
                                final Qualities.Quality quality) {
        super(index, name);
        this.quality = quality;
    }

    public Qualities.Quality getQuality() { return quality; }
}
