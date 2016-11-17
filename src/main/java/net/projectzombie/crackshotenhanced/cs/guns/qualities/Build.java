/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.qualities;

import org.bukkit.ChatColor;

/**
 *
 * @author Jesse Bannon
 * 
 * This enumeration represents different build qualities of a CrackshotGun,
 * each one in ascending order giving it better stats i.e. bullet spread,
 * durability, etc.
 */
public enum Build
{
    PRESHOT  (0.0,  ChatColor.DARK_RED,     "ERROR"),
    STOCK    (1.0,  ChatColor.YELLOW,       "Stock"),
    TEMPERED (0.96, ChatColor.GREEN,        "Tempered"),
    MODIFIED (0.91, ChatColor.DARK_GREEN,   "Modified"),
    ENHANCED (0.85, ChatColor.AQUA,         "Enhanced"),
    SUPERIOR (0.78, ChatColor.DARK_AQUA,    "Superior"),
    RARE     (0.71, ChatColor.LIGHT_PURPLE, "Rare"),
    UNIQUE   (0.65, ChatColor.GOLD,         "Unique");
    
    private static final String TITLE = "Build: ";
    
    private final double scalar;
    private final String displayName;
    
    Build(final double scalar,
          final ChatColor color,
          final String value)
    {
        this.scalar = scalar;
        this.displayName = color + value;
    }
    
    /** @return Returns the title, "Build: ". */
    public static String getTitle()     { return TITLE;     }
    
    /** @return Display name of the Build. */
    public String getDisplayName()      { return displayName;     }
    
    /** @return The bullet-spread scalar of the Build. */
    public double getScalar()           { return scalar;    }
    
    /** @return The enumeration name of the build defined in the .java file. */
    @Override
    public String toString() { return super.name(); }
}
