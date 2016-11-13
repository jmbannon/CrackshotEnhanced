package net.projectzombie.crackshotenhanced.main;

import net.projectzombie.crackshotenhanced.cs.guns.components.*;
import net.projectzombie.crackshotenhanced.cs.guns.weps.Guns;
import net.projectzombie.crackshotenhanced.cs.guns.crafting.Recipes;
import net.projectzombie.crackshotenhanced.cs.guns.skeleton.FirearmActions;
import net.projectzombie.crackshotenhanced.cs.guns.skeleton.SkeletonTypes;
import net.projectzombie.crackshotenhanced.cs.guns.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.windows.BlockBreakListener;
import net.projectzombie.crackshotenhanced.cs.guns.listeners.ShootListener;
import net.projectzombie.crackshotenhanced.cs.guns.listeners.ScopeZoomListener;
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
        this.OPexec = new OPCommandExec(this);
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
        boolean initialized = true;
        if (!isInitialized("Attachments", ProjectileAttachments.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("Barrels", Barrels.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("Bolts", Bolts.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("FireModes", FireModes.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("Magazines", Magazines.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("Sights", Sights.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("Stocks", Stocks.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("Modifier Sets", ModifierSets.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("Firearm Action", FirearmActions.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("Weapon Types", SkeletonTypes.getInstance().initialize()))
            initialized = false;
        if (!isInitialized("Gun Skeletons", GunSkeletons.getInstance().initialize()))
            initialized = false;
        
        if (!isInitialized("Guns", Guns.initialize())) {
            initialized = false;
        }
        return initialized;
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
            System.out.println("[Crackshot Enhanced] FATAL: Could not initialize " + toInitialize + ". Disabling plugin.");
            return false;
        }
    }
}
