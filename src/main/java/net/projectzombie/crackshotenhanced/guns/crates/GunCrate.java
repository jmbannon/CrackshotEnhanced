package net.projectzombie.crackshotenhanced.guns.crates;

import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.guns.utilities.Constants;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GunCrate {

    private final Qualities.Quality quality;

    public GunCrate(final Qualities.Quality quality) {
        this.quality = quality;
        quality.getIndex();
    }

    private <T extends QualityGunModifier> void addQualitiesToArrayList(final ModifierConfig<T> modifiers,
                                                                        final ArrayList<QualityGunModifier> arrayList) {
        modifiers.getStream().filter(x -> x.getQuality().equals(this.quality)).forEach(arrayList::add);
    }

    public ItemStack getRandomItemStack() {
        final ArrayList<QualityGunModifier> qualityList = new ArrayList<>();
        addQualitiesToArrayList(Barrels.getInstance(), qualityList);
        addQualitiesToArrayList(Bolts.getInstance(), qualityList);
        addQualitiesToArrayList(Magazines.getInstance(), qualityList);
        addQualitiesToArrayList(ProjectileAttachments.getSlotOneInstance(), qualityList);
        addQualitiesToArrayList(ProjectileAttachments.getSlotTwoInstance(), qualityList);
        addQualitiesToArrayList(ProjectileAttachments.getSlotThreeInstance(), qualityList);
        addQualitiesToArrayList(Sights.getInstance(), qualityList);
        addQualitiesToArrayList(Stocks.getInstance(), qualityList);

        final int randomIndex = Constants.RANDOM.nextInt(qualityList.size());
        return qualityList.get(randomIndex).toItemStack();
    }


}
