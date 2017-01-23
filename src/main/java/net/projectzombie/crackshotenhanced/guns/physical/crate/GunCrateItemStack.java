package net.projectzombie.crackshotenhanced.guns.physical.crate;

import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.guns.physical.PhysicalItemStack;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.guns.utilities.Constants;
import net.projectzombie.crackshotenhanced.guns.utilities.ItemStackUtils;
import net.projectzombie.crackshotenhanced.yaml.ModifierConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GunCrateItemStack extends GunCrateLore implements PhysicalItemStack {

    /** TODO: Make this configurable. */
    static Material CRATE = Material.PURPUR_BLOCK;


    static GunCrateItemStack getRandomGunCrate() {
        return new GunCrateItemStack(Qualities.getInstance().getRandomQuality());
    }

    /**
     * Instantiates an unopened crate with provided quality.
     * @param quality Quality of items that are contained in the crate.
     */
    public GunCrateItemStack(final Qualities.Quality quality) {
        super(quality);
    }

    public GunCrateItemStack(final ItemStack item) {
        super((ItemStackUtils.hasLoreContents(item) && item.getType().equals(CRATE)) ?
                item.getItemMeta().getLore() : null);
    }

    private <T extends QualityGunModifier> void addQualitiesToArrayList(final ModifierConfig<T> modifiers,
                                                                        final ArrayList<QualityGunModifier> arrayList) {
        modifiers.getStream().filter(x -> x != null && x.getQuality() != null && x.getQuality().equals(super.getQuality())).forEach(arrayList::add);
    }

    @Override
    public ItemStack toItemStack() {
        final ItemStack toReturn = new ItemStack(CRATE);
        final ItemMeta meta = toReturn.getItemMeta();
        meta.setLore(this.generateLore());
        toReturn.setItemMeta(meta);
        return toReturn;
    }

    public ItemStack openCrate() {
        final ArrayList<QualityGunModifier> qualityList = new ArrayList<>();
        addQualitiesToArrayList(Barrels.getInstance(), qualityList);
        addQualitiesToArrayList(Bolts.getInstance(), qualityList);
        addQualitiesToArrayList(Magazines.getInstance(), qualityList);
        addQualitiesToArrayList(ProjectileAttachments.getSlotOneInstance(), qualityList);
        addQualitiesToArrayList(ProjectileAttachments.getSlotTwoInstance(), qualityList);
        addQualitiesToArrayList(ProjectileAttachments.getSlotThreeInstance(), qualityList);
        addQualitiesToArrayList(Sights.getInstance(), qualityList);
        addQualitiesToArrayList(Stocks.getInstance(), qualityList);
        addQualitiesToArrayList(GunSkeletons.getInstance(), qualityList);

        final int randomIndex = Constants.RANDOM.nextInt(qualityList.size());
        return qualityList.get(randomIndex).toItemStack();
    }

}
