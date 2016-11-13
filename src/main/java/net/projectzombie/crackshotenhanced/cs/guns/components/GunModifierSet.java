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
    
    public GunModifierSet(final String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    abstract public ArrayList<String> getStat();
    abstract public ArrayList<String> getStats();
    abstract public boolean hasStats();
    
}
