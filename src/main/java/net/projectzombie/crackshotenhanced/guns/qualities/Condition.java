/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.qualities;

import net.projectzombie.crackshotenhanced.main.Main;
import org.bukkit.ChatColor;

/**
 * @author Jesse Bannon
 * 
 * Condition tiers for CrackshotGuns. Display information in the weapon lore.
 */
public enum Condition
{
    NEW     (.90, 1.10, ChatColor.GREEN,     "Factory New"),
    MINT    (.70, 1.05, ChatColor.DARK_AQUA, "Mint"),
    NORM    (.40, 1.0, ChatColor.YELLOW,     "Standard"),
    WORN    (.25, 0.9, ChatColor.GOLD,       "Worn"),
    SCARRED (.10, 0.8, ChatColor.GRAY,       "Scarred"),
    RUSTY   (.0,  0.65, ChatColor.RED,       "Rusty"),
    BROKEN  (-1,  0.0, ChatColor.DARK_RED,   "Broken"),
    PRESHOT (-2,  0.0, ChatColor.DARK_RED,   "ERROR");
    
    public static final String TITLE = "Condition: ";
    
    private final double multiplier;
    private final double ratioDurability;
    private final String displayName;

    /**
     * Creates a weapon tier of the value (higher -> better) and the display
     * information for the weapon lore.
     * 
     * @param multiplier Multiplier of the tier (higher is better).
     * @param color Color of the condition string in the lore.
     * @param displayName Condition of the weapon to display in the lore.
     */
     Condition(final double ratioDurability,
              final double multiplier,
              final ChatColor color,
              final String displayName)
    {
        this.ratioDurability = ratioDurability;
        this.multiplier = multiplier;
        this.displayName = color + displayName;
    }
    
    public static String getTitle()     { return TITLE;     }
    public double getMultiplier()       { return multiplier; }
    public String getDisplayName()      { return displayName;  }
    @Override public String toString()  { return displayName; }
    
    /**
     * Gets the condition based on the ratio between currentDurability
     * and maxDurability.
     * @param currentDurability
     * @param maxDurability
     * @return 
     */
    static public Condition getCondition(final double currentDurability,
                                         final double maxDurability)
    {
        Main.info("CURR DUR: " + currentDurability);
        Main.info("MAX DUR: " + maxDurability);
        final double durRatio = currentDurability/maxDurability;
        Main.info("RATIO: " + durRatio);
        for (Condition cond : Condition.values())
        {
            if (durRatio >= cond.ratioDurability)
            {
                return cond;
            }
        }
        return Condition.BROKEN;
    }
}
