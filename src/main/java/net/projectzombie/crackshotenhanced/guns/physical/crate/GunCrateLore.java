package net.projectzombie.crackshotenhanced.guns.physical.crate;

import net.projectzombie.crackshotenhanced.guns.physical.PhysicalLore;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.guns.utilities.ItemStackUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class GunCrateLore extends GunCrateHiddenInfo implements PhysicalLore {

    private static final int HIDDEN_LORE_IDX = 0;

    private static final ChatColor LINE_COLOR = ChatColor.GRAY;
    private static final ChatColor LINE_INFO_COLOR = ChatColor.RED;
    private static final String STATS_LINE = LINE_COLOR + "------------------------------";

    public GunCrateLore(final Qualities.Quality quality) {
        super(quality);
    }

    public GunCrateLore(final List<String> lore) {
        super(extractHiddenLoreInfo(lore));
    }

    @Override
    public List<String> generateLore()
    {
        final ArrayList<String> lore = new ArrayList<>();
        if (!this.isValid())
            return null;

        lore.add(HIDDEN_LORE_IDX, STATS_LINE + this.getHiddenInfo());
        lore.add(super.getQuality().getDisplayName(false) + " Crate");
        return lore;
    }


    static
    private String extractHiddenLoreInfo(final List<String> lore) {
        return ItemStackUtils.hasLoreContents(lore) ? lore.get(HIDDEN_LORE_IDX).replace(STATS_LINE, "") : null;
    }
}
