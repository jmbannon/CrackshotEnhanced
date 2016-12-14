/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.physical.weps;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.guns.utilities.Constants;
import net.projectzombie.crackshotenhanced.guns.weps.CrackshotGun;
import net.projectzombie.crackshotenhanced.guns.qualities.Condition;
import net.projectzombie.crackshotenhanced.guns.weps.GunID;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

/**
 *
 * @author Jesse Bannon
 * 
 * Class that represents the lore for a CrackshotGun.
 * 
 */
public class CrackshotGunLore extends HiddenGunInfo
{
    /** Color of titles within lore. */
    public static final ChatColor
        TITLE_COLOR = ChatColor.DARK_GREEN;
    
    /** Color of values within lore. */
    public static final ChatColor
        VALUE_COLOR = ChatColor.GRAY;
    
    /** Color of the dashed line in the lore. */
    public static final ChatColor
        LINE_COLOR = ChatColor.GRAY;
    
    /** Color of the text within the dashed line in the lore. */
    private static final ChatColor
        LINE_INFO_COLOR = ChatColor.RED;
    
    /** Gun modifier colors in the lore. */
    public static final ChatColor
        GUN_MOD_COLOR = ChatColor.GOLD;    

    /** Text within pre-shot CrackshotGun lores. */
    public static final String
        PRESHOT_VER = TITLE_COLOR + "Unviel using an identitying kit.";
    
    /** Beginning string of a line. */
    private static final String
        LINE_START = LINE_COLOR + "----- " + LINE_INFO_COLOR;
    
    /** Ending string of a line. */
    private static final String
        LINE_END = LINE_COLOR + " ------------------";
    
    /** Line to appear above all stats in CrackshotGun lore. */
    public static final String
        LINE_STATS = LINE_START + "Stats" + LINE_END;
    
    /** Line to appear above all modifiers in CrackshotGun lore. */
    public static final String
        LINE_MODS = LINE_START + "Mods" + LINE_END;
    
    /** Line to appear at very end of lore providing where to find more info. */
    public static final String
        LORE_END_INFO = LINE_INFO_COLOR + "Get more info using "
        + VALUE_COLOR + "/gunsmith info";
    
    
    /** Index where to set Stat line with appended HiddenInfo in lore. */
    private static final int STAT_LINE_HIDDENINFO_IDX = 0;
    
    /** Index where to set DPS information in lore. */
    private static final int DPS_IDX = 1;
    
    /** Index where to set Accuracy information in lore. */
    private static final int ACCURACY_IDX = 2;
    
    /** Index where to set Build information in lore. */
    private static final int BUILD_IDX = 3;
    
    /** Index where to set Condition information in lore. */
    private static final int CONDITION_IDX = 4;
    
    /** Index where to set ModifierAttributes line in lore. */
    private static final int MOD_LINE_IDX = 5;
    
    /** Lore to be set in an ItemStack. */
    private final List<String> lore;
    
    /**
     * Initializes CrackshotGunLore from an ItemMeta's
     * lore which is a List<String>.
     * 
     * @param lore Lore from ItemMeta.
     */
    public CrackshotGunLore(List<String> lore)
    {
        super(extractHiddenInfo(lore));
        this.lore = lore;
    }
    
    /**
     * Creates a pre-shot gun's lore from a GunID.
     * @param id Unique GunID to store within the lore.
     */
    public CrackshotGunLore(final GunID id)
    {
        super(id);
        this.lore = new ArrayList<>();
    }
    
    /**
     * Returns the lore.
     * @return Lore.
     */
    public List<String> getLore()
    {
        return lore;
    }
    
    
    @Override
    public void setGunID(final String newID)
    {
        super.setGunID(newID);
        setStatLineHiddenInfo();
    }
    
    @Override
    public void setDurability(final int dur)
    {
        super.setDurability(dur);
        this.setConditionLore(this.getGun().getCondition(dur));
        this.setStatLineHiddenInfo();
    }
    
    public void decrementDurability()
    {
        this.setDurability(super.getDurability() - 1);
    }
    
    /**
     * @return PostShot lore with Build value STOCK and an initial durability.
     */
    public CrackshotGunLore toPostShotLore()
    {
        return this.toPostShotLore(this.getGun().getInitialDurability());
    }
    
    /**
     * Creates an ItemStack lore (List<String>) for a Crackshot gun.
     * @param durability
     * @return 
     */
    public CrackshotGunLore toPostShotLore(final int durability)
    {
        final CrackshotGun gun = super.getGun();

        lore.clear();
        this.setStatLineHiddenInfo();
        this.setDPSInfo(gun.getDPS());
        this.setBulletSpread(gun);
        this.setQualityInfo();
        this.setDurability(durability);
        this.setGunModifierInfo(gun);
        lore.add(LORE_END_INFO);
        
        return this;
    }
    
    /**
     * 
     * @return Lore that contains a stat line with HiddenInfo appended to it,
     * followed by PRESHOT_VER in a new line.
     */
    public CrackshotGunLore toPreShotLore()
    {
        lore.clear();
        setStatLineHiddenInfo();
        lore.add(PRESHOT_VER);
        return this;
    }
    
    /************************************************************
     * 
     *  PRIVATE FUNCTIONS
     *
     ************************************************************/
    
    /**
     * Sets each GunModifier's in the lore if the GunModifier is not null.
     * @param gun CrackshotGun to add GunModifier info into lore.
     */
    private void setGunModifierInfo(final CrackshotGun gun)
    {
        lore.add(MOD_LINE_IDX, LINE_MODS);
        addGunModifierLine("Attachment One", gun.getAttachmentOneMod());
        addGunModifierLine("Attachment Two", gun.getAttachmentTwoMod());
        addGunModifierLine("Attachment Three", gun.getAttachmentThreeMod());
        addGunModifierLine("Barrel", gun.getBarrelMod());
        addGunModifierLine("Bolt", gun.getBoltMod());
        addGunModifierLine("Fire Mode", gun.getFireModeMod());
        addGunModifierLine("Magazine", gun.getMagazineMod());
        addGunModifierLine("Sight", gun.getScopeMod());
        addGunModifierLine("Stock", gun.getStockMod());
    }
    
    /**
     * Adds a line to the lore specifying info regarding the GunModifier only
     * if the GunModifier is not null.
     * @param title Title of the GunModifier.
     * @param mod ModifierAttributes to display name of.
     */
    private void addGunModifierLine(final String title,
                                    final GunModifier mod)
    {
        if (!mod.isNull()) {
            lore.add(getGunModifierLine(title, mod));
        }
    }
    
    /**
     * Returns a string in the form of "Title: Name" where Title
     * is the color of GUN_MOD_COLOR and Name is the name of the GunModifier.
     * @param title Title of the GunModifier ie type.
     * @param mod GunModifier to retrieve name from.
     * @return String in the form of "Title: Name".
     */
    private String getGunModifierLine(final String title,
                                      final GunModifier mod)
    {
        return GUN_MOD_COLOR + title + ": " + mod.getDisplayName(true);
    }
    
    /**
     * 
     * @param lore Lore to check.
     * @return Returns true if lore contains at least one line.
     */
    static
    private boolean hasLoreContents(List<String> lore)
    {
        return lore != null && lore.size() > 0;
    }

    /** Adds the info to lore if lore size is <= infoIndex, otherwise sets the info to lore. */
    private void setInfo(final int infoIndex, final String info) {
        if (infoIndex >= lore.size()) {
            lore.add(infoIndex, info);
        } else {
            lore.set(infoIndex, info);
        }
    }

    /** Sets lore with LINE_STATS appended with HiddenInfo. */
    private void setStatLineHiddenInfo()
    {
        setInfo(STAT_LINE_HIDDENINFO_IDX, LINE_STATS + this.getHiddenInfo());
    }
    
    /** @param dps Damage per second to set in lore. */
    private void setDPSInfo(final Double dps)
    {
        lore.add(DPS_IDX, buildLoreString("DPS: ", String.valueOf(dps)));
    }
    
    /**
     * If lore contains more than one line it returns the HiddenLore contents.
     * Otherwise returns null.
     * 
     * @param lore Lore to extract HiddenLore from.
     * @return HidenLore if it exists, null otherwise.
     */
    static
    private String extractHiddenInfo(final List<String> lore)
    {
        if (hasLoreContents(lore))
            return lore.get(STAT_LINE_HIDDENINFO_IDX).replace(LINE_STATS, "");
        else
            return null;
    }
    
    /**
     * If the gun can zoom it displays zoom bullet spread. Otherwise displays standing bullet spread.
     * @param gun CrackshotGun to evaluate bullet spread.
     */
    private void setBulletSpread(final CrackshotGun gun)
    {
        final double bulletSpreadToDisplay = gun.canZoom() ?
                gun.getZoomedBulletSpread() : gun.getStandingBulletSpread();
        setInfo(ACCURACY_IDX, buildLoreString("Bullet Spread: ", Constants.FORMATTER.format(bulletSpreadToDisplay)));
    }
    
    /**
     * Sets the string that represents a CrackshotGun's condition information.
     * @param condition Current condition of the gun to set in the lore.
     */
    private void setConditionLore(final Condition condition)
    {
        setInfo(CONDITION_IDX, buildLoreString(Condition.getTitle(), condition.toString()));
    }
    
    
    /**
     * Sets the quality info in the lore.
     */
    private void setQualityInfo()
    {
        setInfo(BUILD_IDX, buildLoreString(Qualities.getInstance().getTitle(), super.getGun().getQuality().getName()));
    }
    
    static
    private String buildLoreString(final String title,
                                   final ChatColor valueColor,
                                   final String value)
    {
        final StringBuilder stb = new StringBuilder();
        stb.append(TITLE_COLOR);
        stb.append(title);
        stb.append(valueColor);
        stb.append(value);
        return stb.toString();
    }
    
    static
    private String buildLoreString(final String title,
                                   final String value)
    {
        return buildLoreString(title, VALUE_COLOR, value);
    }
}
