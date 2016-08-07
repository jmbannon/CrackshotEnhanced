/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.physical.components;

import cs.guns.components.GunModifier;
import cs.guns.components.GunModifierType;
import cs.guns.components.modifiers.BoltAttributes;
import cs.guns.components.modifiers.BoltSet;
import cs.guns.components.modifiers.DurabilityAttributes;
import cs.guns.components.modifiers.DurabilitySet;
import cs.guns.components.modifiers.FireModeAttributes;
import cs.guns.components.modifiers.FireModeSet;
import cs.guns.components.modifiers.MagazineAttributes;
import cs.guns.components.modifiers.MagazineSet;
import cs.guns.components.modifiers.MotionAttributes;
import cs.guns.components.modifiers.MotionSet;
import cs.guns.components.modifiers.ProjectileAttributes;
import cs.guns.components.modifiers.ProjectileSet;
import cs.guns.components.modifiers.ScopeAttributes;
import cs.guns.components.modifiers.ScopeSet;
import cs.guns.components.modifiers.SilencerAttributes;
import cs.guns.components.modifiers.SilencerSet;
import cs.guns.modifiers.BaseDamageAttributes;
import cs.guns.modifiers.BaseDamageSet;
import cs.guns.modifiers.BleedoutAttributes;
import cs.guns.modifiers.BleedoutSet;
import cs.guns.modifiers.BulletSpreadAttributes;
import cs.guns.modifiers.BulletSpreadSet;
import cs.guns.modifiers.CritAttributes;
import cs.guns.modifiers.CritSet;
import cs.guns.modifiers.FireDamageAttributes;
import cs.guns.modifiers.FireDamageSet;
import cs.guns.modifiers.HeadshotAttributes;
import cs.guns.modifiers.HeadshotDamageSet;
import cs.guns.modifiers.IgniteAttributes;
import cs.guns.modifiers.IgniteSet;
import cs.guns.modifiers.ShrapnelDamageAttributes;
import cs.guns.modifiers.ShrapnelDamageSet;
import cs.guns.modifiers.StunAttributes;
import cs.guns.modifiers.StunSet;
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
        
        if (this instanceof BleedoutAttributes)     stats.addAll(new BleedoutSet(mod).getStat());
        if (this instanceof BoltAttributes)         stats.addAll(new BoltSet(mod).getStat());
        if (this instanceof BulletSpreadAttributes) stats.addAll(new BulletSpreadSet(mod).getStat());
        if (this instanceof CritAttributes)         stats.addAll(new CritSet(mod).getStat());
        if (this instanceof BaseDamageAttributes)   stats.addAll(new BaseDamageSet(mod).getStat());
        if (this instanceof HeadshotAttributes)     stats.addAll(new HeadshotDamageSet(mod).getStat());
        if (this instanceof DurabilityAttributes)   stats.addAll(new DurabilitySet(mod).getStat());
        if (this instanceof FireModeAttributes)     stats.addAll(new FireModeSet(mod).getStat());
        if (this instanceof IgniteAttributes)       stats.addAll(new IgniteSet(mod).getStat());
        if (this instanceof FireDamageAttributes)   stats.addAll(new FireDamageSet(mod).getStat());
        if (this instanceof MagazineAttributes)     stats.addAll(new MagazineSet(mod).getStat());
        if (this instanceof ProjectileAttributes)   stats.addAll(new ProjectileSet(mod).getStat());
        if (this instanceof MotionAttributes)       stats.addAll(new MotionSet(mod).getStat());
        if (this instanceof ShrapnelDamageAttributes) stats.addAll(new ShrapnelDamageSet(mod).getStat());
        if (this instanceof StunAttributes)         stats.addAll(new StunSet(mod).getStat());
        if (this instanceof SilencerAttributes)     stats.addAll(new SilencerSet(mod).getStat());
        if (this instanceof ScopeAttributes)         stats.addAll(new ScopeSet(mod).getStat());

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
