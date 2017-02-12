package net.projectzombie.crackshotenhanced.guns.attributes.modifier;

import net.projectzombie.crackshotenhanced.guns.attributes.AttributeSet;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.modifier.StatBuilder;

import java.util.ArrayList;

public class KnockbackSet extends AttributeSet<KnockbackSet.KnockbackAttributes> {

    public interface KnockbackAttributes extends ModifierAttributes {
        double getKnockbackMultiplier();
    }

    /**
     * We calculate knockback using a normalized vector with the direction of the damager to victim.
     * This value is the default multiplier of this knockback vector. Sends entities roughly 1 block back.
     */
    static private final double DEFAULT_KNOCKBACK_SCALAR = 0.5;

    private final double knockbackMultiplier;

    public KnockbackSet(final GunModifier[] modSet)
    {
        super("Knockback",
                modSet,
                KnockbackAttributes.class);

        this.knockbackMultiplier = super.getMultiplierSum(KnockbackAttributes::getKnockbackMultiplier);
    }

    public KnockbackSet(final GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }

    public double getKnockbackVelocityVectorScalar() {
        return DEFAULT_KNOCKBACK_SCALAR * knockbackMultiplier;
    }

    @Override
    public ArrayList<String> getGunStats()
    {
        final StatBuilder stats = new StatBuilder();
        stats.addMultiplierStatIfValid(this.knockbackMultiplier, "knockback");
        return stats.toArrayList();
    }

    @Override
    public ArrayList<String> getIndividualStats()
    {
        return this.getGunStats();
    }
}
