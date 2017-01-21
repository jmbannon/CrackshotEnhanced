package net.projectzombie.crackshotenhanced.guns.physical.crate;

import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.guns.utilities.HiddenLoreInfo;

public class GunCrateHiddenInfo extends HiddenLoreInfo {

    /** Index of the Quality ID. */
    private static final int QUALITY_ID_IDX = 1;

    public GunCrateHiddenInfo(final Qualities.Quality quality) {
        super(new String[] { String.valueOf(quality.getIndex()) });
    }

    public GunCrateHiddenInfo(final String encodedString) {
        super(encodedString);
    }

    public Qualities.Quality getQuality() {
        return Qualities.getInstance().get(super.getInfoInt(QUALITY_ID_IDX));
    }
}
