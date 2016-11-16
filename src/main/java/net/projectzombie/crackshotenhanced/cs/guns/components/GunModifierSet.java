/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.components;

import java.util.ArrayList;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class GunModifierSet<T extends Modifier>
{
    private final String name;
    private final Class<T> t;
    
    public GunModifierSet(final String name, Class<T> t)
    {
        this.name = name;
        this.t = t;
    }
    
    public String getName()
    {
        return name;
    }
    
    abstract public ArrayList<String> getStat();
    abstract public ArrayList<String> getStats();
    abstract public boolean hasStats();

    public final ArrayList<T> getModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<T> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod != null && t.isInstance(mod))
                mods.add((T)mod);
        }
        return mods;
    }
    
}
