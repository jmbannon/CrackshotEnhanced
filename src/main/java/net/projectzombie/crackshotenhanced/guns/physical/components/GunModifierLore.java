/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.physical.components;

import net.projectzombie.crackshotenhanced.guns.attributes.modifier.*;
import net.projectzombie.crackshotenhanced.guns.attributes.skeleton.*;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.crafting.GunModifierType;

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

        if (this instanceof BleedoutSet.BleedoutAttributes)     stats.addAll(new BleedoutSet(mod).getIndividualStats());
        if (this instanceof BoltSet.BoltAttributes)         stats.addAll(new BoltSet(mod).getIndividualStats());
        if (this instanceof BulletSpreadSet.BulletSpreadAttributes) stats.addAll(new BulletSpreadSet(mod).getIndividualStats());
        if (this instanceof CritSet.CritAttributes)         stats.addAll(new CritSet(mod).getIndividualStats());
        if (this instanceof BaseDamageSet.BaseDamageAttributes)   stats.addAll(new BaseDamageSet(mod).getIndividualStats());
        if (this instanceof HeadshotDamageSet.HeadshotAttributes)     stats.addAll(new HeadshotDamageSet(mod).getIndividualStats());
        if (this instanceof DurabilitySet.DurabilityAttributes)   stats.addAll(new DurabilitySet(mod).getIndividualStats());
        if (this instanceof FireModeSet.FireModeAttributes)     stats.addAll(new FireModeSet(mod).getIndividualStats());
        if (this instanceof IgniteSet.IgniteAttributes)       stats.addAll(new IgniteSet(mod).getIndividualStats());
        if (this instanceof FireDamageSet.FireDamageAttributes)   stats.addAll(new FireDamageSet(mod).getIndividualStats());
        if (this instanceof MagazineSet.MagazineAttributes)     stats.addAll(new MagazineSet(mod).getIndividualStats());
        if (this instanceof ProjectileSet.ProjectileAttributes)   stats.addAll(new ProjectileSet(mod).getIndividualStats());
        if (this instanceof MotionSet.MotionAttributes)       stats.addAll(new MotionSet(mod).getIndividualStats());
        if (this instanceof ShrapnelDamageSet.ShrapnelDamageAttributes) stats.addAll(new ShrapnelDamageSet(mod).getIndividualStats());
        if (this instanceof StunSet.StunAttributes)         stats.addAll(new StunSet(mod).getIndividualStats());
        if (this instanceof SilencerSet.SilencerAttributes)     stats.addAll(new SilencerSet(mod).getIndividualStats());
        if (this instanceof SightSet.SightAttributes)         stats.addAll(new SightSet(mod).getIndividualStats());

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
