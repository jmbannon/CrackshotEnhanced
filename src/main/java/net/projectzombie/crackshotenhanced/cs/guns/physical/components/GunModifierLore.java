/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.physical.components;

import net.projectzombie.crackshotenhanced.cs.guns.attributes.modifier.*;
import net.projectzombie.crackshotenhanced.cs.guns.attributes.skeleton.*;
import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.crafting.GunModifierType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;

/**
 *
 * @author jb
 */
public class GunModifierLore extends HiddenGunModifierInfo
{
    private static final int HIDDEN_LORE_IDX = 0;
    
    private static final ChatColor LINE_COLOR = ChatColor.GRAY;
    private static final ChatColor LINE_INFO_COLOR = ChatColor.RED;
    private static final String STATS_LINE = LINE_COLOR + "----- " + LINE_INFO_COLOR + "Stats" + LINE_COLOR + " ------------------";

    public GunModifierLore(final GunModifierType type,
                           final int id)
    {
        super(type, id);
    }
    
    public GunModifierLore(final List<String> lore)
    {
        super(extractHiddenLoreInfo(lore));
    }
    
    public ArrayList<String> generateLore()
    {
        final GunModifier mod = super.getGunModifier();
        final ArrayList<String> stats = new ArrayList<>();
        if (!this.isValid() || mod == null || mod.isNull())
            return null;


        
        if (this instanceof BleedoutSet.BleedoutAttributes)     stats.addAll(new BleedoutSet(mod).getStat());
        if (this instanceof BoltSet.BoltAttributes)         stats.addAll(new BoltSet(mod).getStat());
        if (this instanceof BulletSpreadSet.BulletSpreadAttributes) stats.addAll(new BulletSpreadSet(mod).getStat());
        if (this instanceof CritSet.CritAttributes)         stats.addAll(new CritSet(mod).getStat());
        if (this instanceof BaseDamageSet.BaseDamageAttributes)   stats.addAll(new BaseDamageSet(mod).getStat());
        if (this instanceof HeadshotDamageSet.HeadshotAttributes)     stats.addAll(new HeadshotDamageSet(mod).getStat());
        if (this instanceof DurabilitySet.DurabilityAttributes)   stats.addAll(new DurabilitySet(mod).getStat());
        if (this instanceof FireModeSet.FireModeAttributes)     stats.addAll(new FireModeSet(mod).getStat());
        if (this instanceof IgniteSet.IgniteAttributes)       stats.addAll(new IgniteSet(mod).getStat());
        if (this instanceof FireDamageSet.FireDamageAttributes)   stats.addAll(new FireDamageSet(mod).getStat());
        if (this instanceof MagazineSet.MagazineAttributes)     stats.addAll(new MagazineSet(mod).getStat());
        if (this instanceof ProjectileSet.ProjectileAttributes)   stats.addAll(new ProjectileSet(mod).getStat());
        if (this instanceof MotionSet.MotionAttributes)       stats.addAll(new MotionSet(mod).getStat());
        if (this instanceof ShrapnelDamageSet.ShrapnelDamageAttributes) stats.addAll(new ShrapnelDamageSet(mod).getStat());
        if (this instanceof StunSet.StunAttributes)         stats.addAll(new StunSet(mod).getStat());
        if (this instanceof SilencerSet.SilencerAttributes)     stats.addAll(new SilencerSet(mod).getStat());
        if (this instanceof SightSet.SightAttributes)         stats.addAll(new SightSet(mod).getStat());

        if (!stats.isEmpty())
        {
            Collections.shuffle(stats);
            stats.add(0, buildStatLore());
            return stats;
        }
        else
        {
            return null;
        }
    }
    
    private String buildStatLore()
    {
        return STATS_LINE + super.getHiddenInfo();
    }
    
    static
    private boolean hasLoreContents(final List<String> lore)
    {
        return lore != null
            && lore.size() > HIDDEN_LORE_IDX;
    }
    
    static
    private String extractHiddenLoreInfo(final List<String> lore)
    {
        if (hasLoreContents(lore))
        {
            return lore.get(HIDDEN_LORE_IDX).replace(STATS_LINE, "");
        }
        else
        {
            return null;
        }
    }
    
}
