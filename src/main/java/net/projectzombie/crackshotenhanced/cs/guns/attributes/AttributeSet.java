/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.attributes;

import net.projectzombie.crackshotenhanced.cs.guns.attributes.modifier.ModifierAttributes;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class AttributeSet<T extends ModifierAttributes>
{
    private final String name;
    private final ArrayList<T> attributes;

    public AttributeSet(final String name,
                        final GunModifier[] gunMods,
                        Class<T> t)
    {
        this.name = name;
        this.attributes = getModifiers(gunMods, t);
    }
    
    public String getName()
    {
        return name;
    }
    
    abstract public ArrayList<String> getStat();
    abstract public ArrayList<String> getStats();
    abstract public boolean hasStats();

    public final ArrayList<T> getModifiers(final GunModifier[] gunMods, Class<T> t)
    {
        final ArrayList<T> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod != null && t.isInstance(mod))
                mods.add((T)mod);
        }
        return mods;
    }

    public double getDoubleSum(ToDoubleFunction<? super T> toDoubleFunction) {
        return attributes.stream().mapToDouble(toDoubleFunction).sum();
    }

    /** Math.max(min, offset + sum) */
    public double getDoubleSum(double offset,
                               double min,
                               ToDoubleFunction<? super T> toDoubleFunction) {
        return Math.max(min, offset + attributes.stream().mapToDouble(toDoubleFunction).sum());
    }

    /** Math.max(0, 1.0 + sum) */
    public double getMultiplierSum(ToDoubleFunction<? super T> toDoubleFunction) {
        return getDoubleSum(1.0, 0.0, toDoubleFunction);
    }

    public int getIntSum(ToIntFunction<? super T> toIntFunction) {
        return attributes.stream().mapToInt(toIntFunction).sum();
    }

    public int getIntSum(int offset, int min, ToIntFunction<? super T> toIntFunction) {
        return Math.max(min, offset + attributes.stream().mapToInt(toIntFunction).sum());
    }

    public boolean getBoolean(Predicate<? super T> toBooleanFunction) {
        return attributes.stream().anyMatch(toBooleanFunction);
    }
    
}
