/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.physical.weps;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.qualities.Build;
import net.projectzombie.crackshotenhanced.cs.guns.weps.Guns;
import net.projectzombie.crackshotenhanced.cs.guns.qualities.Condition;
import net.projectzombie.crackshotenhanced.cs.guns.weps.GunID;

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
        setStatLineHiddenInfo();
    }
    
    @Override
    public int decrementDurability()
    {
        final int decDur = super.decrementDurability();
        setStatLineHiddenInfo();
        return decDur;
    }
    
    @Override
    public void setBuild(final Build build)
    {
        super.setBuild(build);
        setStatLineHiddenInfo();
    }
    
    /**
     * @return PostShot lore with Build value STOCK and an initial durability.
     */
    public CrackshotGunLore toPostShotLore()
    {
        return this.toPostShotLore(Build.STOCK, this.getGun().getInitialDurability());
    }
    
    /**
     * Creates an ItemStack lore (List<String>) for a Crackshot gun.
     * @param build
     * @param durability
     * @return 
     */
    public CrackshotGunLore toPostShotLore(final Build build,
                                           final int durability)
    {
        final Guns.CrackshotGun gun = super.getGun();

        lore.clear();
        this.setStatLineHiddenInfo();
        this.setDPSInfo(gun.getDPS());
        this.setAccuracyInfo(gun, build, durability);
        this.setBuildInfo(build);
        this.setConditionLore(gun.getCondition(durability));
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
    private void setGunModifierInfo(final Guns.CrackshotGun gun)
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
    
    /**
     * Sets lore with LINE_STATS appended with HiddenInfo.
     */
    private void setStatLineHiddenInfo()
    {
        if (STAT_LINE_HIDDENINFO_IDX >= lore.size()) {
            lore.add(STAT_LINE_HIDDENINFO_IDX, LINE_STATS + this.getHiddenInfo());
        } else {
            lore.set(STAT_LINE_HIDDENINFO_IDX, LINE_STATS + this.getHiddenInfo());
        }
    }
    
    /**
     * Sets lore DPS.
     * 
     * @param dps Damage per second to set in lore.
     */
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
     * TODO: Sets the accuracy information in the lore.
     * 
     * @param gun CrackshotGun to evaluate accuracy rating.
     * @param build Current Build of the CrackshotGun.
     * @param durability Current durability of the CrackshotGun.
     */
    private void setAccuracyInfo(final Guns.CrackshotGun gun,
                                 final Build build,
                                 final int durability)
    {
        lore.add(ACCURACY_IDX, buildLoreString("Accuracy: ", "WIP"));
    }
    
    /**
     * Sets the string that represents a CrackshotGun's condition information.
     * @param condition Current condition of the gun to set in the lore.
     */
    private void setConditionLore(final Condition condition)
    {
        lore.add(CONDITION_IDX, buildLoreString(Condition.getTitle(), condition.toString()));
    }
    
    
    /**
     * Sets the build info in the lore.
     * @param build  Current build of the gun.
     */
    private void setBuildInfo(final Build build)
    {
        lore.add(BUILD_IDX, buildLoreString(Build.getTitle(), build.getDisplayName()));
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
