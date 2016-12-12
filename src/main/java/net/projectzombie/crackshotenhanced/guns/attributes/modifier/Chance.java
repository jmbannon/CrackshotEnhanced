/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;

import java.util.Random;
import java.util.function.ToDoubleFunction;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class Chance<T extends ModifierAttributes> extends AttributeSet<T>
{
    private final double chance;
    
    public Chance(final String name,
                  final GunModifier[] gunMods,
                  ToDoubleFunction<? super T> chanceSum,
                  final Class<T> t)
    {
        super(name, gunMods, t);
        this.chance = super.getDoubleSum(0, 0, chanceSum);
    }
    
    public double getChance() { return chance; }
    
    @Override
    public boolean hasStats()
    {
        return super.hasStats() && chance > 0;
    }
    
    public boolean rollDice()
    {
        return new Random().nextDouble() <= chance;
    }
    
}
