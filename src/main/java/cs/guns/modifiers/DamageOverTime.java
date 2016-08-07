/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.modifiers;

import cs.guns.components.GunModifierSet;
import java.util.ArrayList;
import cs.guns.components.Modifier;
import static cs.guns.utilities.Constants.TPS;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class DamageOverTime<T extends Modifier> extends GunModifierSet<T>
{
    
    final double totalDPS;
    final double totalDuration;
    final double durationValue;
    final double durationMultiplier;
    
    public DamageOverTime(final String name,
                          final double totalDPS,
                          final double durationValue,
                          final double durationMultiplier)
    {
        super(name);
        this.totalDPS = totalDPS;
        this.totalDuration = Math.max(0, durationValue * durationMultiplier);
        
        this.durationValue = durationValue;
        this.durationMultiplier = durationMultiplier;
    }
    
    @Override
    public boolean hasStats()
    {
        return totalDPS > 0 && totalDuration > 0;
    }
    
    public double getTotalDPS() { return totalDPS;       }
    public double getTotalDPT() { return totalDPS / TPS; }
    
    public double getTotalDurationInSeconds() { return totalDuration;       }
    public double getTotalDurationInTicks()   { return totalDuration / TPS; }
    public double getDurationValue()          { return durationValue;       }
    public double getDurationMultiplier()     { return durationMultiplier;  }
    
}
