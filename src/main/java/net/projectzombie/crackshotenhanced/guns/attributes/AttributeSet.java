/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes;

import net.projectzombie.crackshotenhanced.guns.attributes.modifier.ModifierAttributes;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;

import java.util.ArrayList;
import java.util.Arrays;
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

    /** @return Stats applicable to a single GunModifier. */
    abstract public ArrayList<String> getIndividualStats();

    public ArrayList<String> getIndividualStats(int spaceIndentation) {
        if (spaceIndentation < 0) throw new IllegalArgumentException("Space indentation must be > 0");
        if (spaceIndentation == 0) {
            return getIndividualStats();
        } else {
            final ArrayList<String> toRet = new ArrayList<>();
            final char[] spaces = new char[spaceIndentation];
            Arrays.fill(spaces, ' ');
            final String spaceStr = new String(spaces);
            getIndividualStats().forEach(s -> toRet.add(spaceStr + s));
            return toRet;
        }
    }

    /** @return Stats applicable to an assembled gun. */
    abstract public ArrayList<String> getGunStats();

    public boolean hasStats() { return !attributes.isEmpty(); };

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
