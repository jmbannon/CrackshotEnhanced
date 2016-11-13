/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components;

import static net.projectzombie.crackshotenhanced.cs.guns.utilities.Constants.FORMATTER;
import org.bukkit.ChatColor;

/**
 *
 * @author jesse
 */
public class ModifierLoreBuilder
{
    public static final String STAT_COLOR = ChatColor.GRAY.toString() + ChatColor.ITALIC.toString();
    public static final String TITLE_COLOR = ChatColor.RED.toString();
    public static final String STAT_TYPE_SEPERATOR = ChatColor.GRAY + "--------------------------------";
    public static final String STAT_SEPERATOR = ChatColor.DARK_GRAY + "---------------";

    static
    public String toTitle(final String title)
    {
        return TITLE_COLOR + title;
    }
    
    static
    public String getMultiplierStat(final double multiplier,
                                     final String description)
    {
        return STAT_COLOR + "  " + numPercentage(multiplier) + " " + description;

    }
    
    static
    public String getValueStat(final double value,
                                final String description)
    {
        return STAT_COLOR + "  " + numValue(value) + " " + description;
    }
    
    static
    public String getBooleanStat(final boolean value,
                                  final String description)
    {
        if (value)
            return STAT_COLOR + "  " + description;
        else
            return null;
    }
    
    static
    private String numPercentage(final double num)
    {
        return getSign(num) + FORMATTER.format(num) + "%";
    }
    
    static
    private String numValue(final double num)
    {
        return getSign(num) + FORMATTER.format(num);
    }
    
    static
    private String getSign(final double num)
    {
        if (num > 0.0)
            return "+";
        else if (num == 0)
            return "";
        else
            return "-";
    }
}
