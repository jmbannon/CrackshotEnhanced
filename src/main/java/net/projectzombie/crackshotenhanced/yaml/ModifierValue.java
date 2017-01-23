/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.yaml;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Jesse Bannon
 */
public abstract class ModifierValue
{
    private final String key;
    private final String name;
    private final int index;
    
    public ModifierValue(final String key,
                         final int index,
                         final String name)
    {
        this.key = key;
        this.name = name;
        this.index = index;
    }
    
    public int getIndex()
    {
        return index;
    }
    
    public String getName()
    {
        return name;
    }

    public String getKey() {
        return key;
    }
    
    @Override
    public String toString()
    {
        return name;
    }

    public void serializeInfoToYaml(final YamlConfiguration yml) { /* Do nothing. */ }
}
