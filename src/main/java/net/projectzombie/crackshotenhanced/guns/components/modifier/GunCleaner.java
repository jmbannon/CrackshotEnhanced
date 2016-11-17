/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.modifier;

/**
 *
 * @author jesse
 */
public enum GunCleaner
{

    SOLVENT(0.10),
    LUBR_OIL(0.25),
    CLEANING_KIT(0.50);

    private final double durFix;
    
    private GunCleaner(double durFix)
    {
        this.durFix = durFix;
    }


    /**
     * Calculates an int based on Crackshot gun's maximum durability to be added
     * to a weapon's durability.
     * @param currentDurability
     * @param maximumDurability Maximum durability from a Crackshot gun skeleton.
     * @return Int to be added to a Crackshot gun's durability.
     */
    public int getDurabilityBoost(final int currentDurability,
                                  final int maximumDurability) 
    {
        final int fixedDurability = (int)(maximumDurability * (durFix));
        if (currentDurability + fixedDurability > maximumDurability)
        {
            return maximumDurability - currentDurability;
        }
        else
        {
            return fixedDurability;
        }
    }

    public String getDisplayName()
    {
        return null;
    }
    
}
