/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.attributes.skeleton;

import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;
import net.projectzombie.crackshotenhanced.guns.components.modifier.ModifierLoreBuilder;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

/**
 *
 * @author jb
 */
public class SilencerSet extends AttributeSet<SilencerSet.SilencerAttributes>
{
    public interface SilencerAttributes extends SkeletonAttributes
    {
        boolean isSilencer();
    }

    private final boolean isSilenced;
    
    public SilencerSet(final GunModifier[] gunMods)
    {
        super("Silencer", gunMods, SilencerAttributes.class);
        this.isSilenced = super.getBoolean(SilencerAttributes::isSilencer);
    }
    
    public SilencerSet(GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    @Override
    public ArrayList<String> getGunStats()
    {
        return getIndividualStats();
    }
    
    @Override
    public ArrayList<String> getIndividualStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addBooleanStatIfTrue(isSilenced, "Silenced");
        return stats.toArrayList();
    }
}
