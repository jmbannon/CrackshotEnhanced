package net.projectzombie.crackshotenhanced.main;

import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.ModifierSets;
import net.projectzombie.crackshotenhanced.guns.weps.Guns;
import net.projectzombie.crackshotenhanced.guns.crafting.Recipes;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.FirearmActions;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.SkeletonTypes;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.windows.BlockBreakListener;
import net.projectzombie.crackshotenhanced.guns.listeners.ShootListener;
import net.projectzombie.crackshotenhanced.guns.listeners.ScopeZoomListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Plugin PLUGIN = null;

    private BlockBreakListener windowListener;
    private ScopeZoomListener scopeListener;
    private OPCommandExec OPexec;
    private GunSmithCommandExec gunsmithExec;
    private ShootListener shootListener;
    private Recipes recipes;

    static public Plugin getPlugin() {
        return PLUGIN;
    }

    @Override
    public void onEnable()
    {
        PLUGIN = this;
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        if (!initializeGuns())
            return;
        
        this.windowListener = new BlockBreakListener();
        this.OPexec = new OPCommandExec();
        this.getCommand("bw").setExecutor(OPexec);
        this.getServer().getPluginManager().registerEvents(windowListener, this);

        this.gunsmithExec = new GunSmithCommandExec();
        this.scopeListener = new ScopeZoomListener();
        this.shootListener = new ShootListener();
        this.recipes = new Recipes();
        
        this.getCommand("gunsmith").setExecutor(gunsmithExec);
        this.getCommand("gs").setExecutor(gunsmithExec);

        this.getServer().getPluginManager().registerEvents(scopeListener, this);
        this.getServer().getPluginManager().registerEvents(shootListener, this);
        this.getServer().getPluginManager().registerEvents(recipes, this);

        Recipes.initializeCraftingRecipes();
        this.getLogger().info("CrackshotEnhanced enabled!");

    }

    @Override
    public void onDisable()
    {
        if (this.scopeListener != null)
            this.scopeListener.disable();
        this.getLogger().info("CrackshotEnhanced disabled.");
    }
    
    private boolean initializeGuns()
    {
        if (!isInitialized("Attachments", ProjectileAttachments.getInstance().size()))
            return false;
        if (!isInitialized("Barrels", Barrels.getInstance().size()))
            return false;
        if (!isInitialized("Bolts", Bolts.getInstance().size()))
            return false;
        if (!isInitialized("FireModes", FireModes.getInstance().size()))
            return false;
        if (!isInitialized("Magazines", Magazines.getInstance().size()))
            return false;
        if (!isInitialized("Sights", Sights.getInstance().size()))
            return false;
        if (!isInitialized("Stocks", Stocks.getInstance().size()))
            return false;
        if (!isInitialized("ModifierAttributes Sets", ModifierSets.getInstance().size()))
            return false;
        if (!isInitialized("Firearm Action", FirearmActions.getInstance().size()))
            return false;
        if (!isInitialized("Weapon Types", SkeletonTypes.getInstance().size()))
            return false;
        if (!isInitialized("Gun Skeletons", GunSkeletons.getInstance().size()))
            return false;
        
        if (!isInitialized("Guns", Guns.initialize()))
            return false;
        return true;
    }
    
    private boolean isInitialized(final String toInitialize,
                                  final int returned)
    {
        if (returned >= 0)
        {
            System.out.println("[Crackshot Enhanced] Initialized " + returned + " " + toInitialize);
            return true;
        }
        else
        {
            System.out.println("[Crackshot Enhanced] FATAL: Could not size " + toInitialize + ". Disabling plugin.");
            return false;
        }
    }
}
