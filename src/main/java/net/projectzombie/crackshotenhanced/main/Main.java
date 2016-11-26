package net.projectzombie.crackshotenhanced.main;

import net.projectzombie.crackshotenhanced.events.scheduler.EntityTimedEventsPerTick;
import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.ModifierSets;
import net.projectzombie.crackshotenhanced.events.listener.OnHitListener;
import net.projectzombie.crackshotenhanced.events.listener.PlayerConnect;
import net.projectzombie.crackshotenhanced.events.scheduler.CSEPlayerRunningSpeed;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.guns.crafting.Recipes;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.FirearmActions;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.SkeletonTypes;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.windows.BlockBreakListener;
import net.projectzombie.crackshotenhanced.events.listener.ShootListener;
import net.projectzombie.crackshotenhanced.events.listener.ScopeZoomListener;
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
    private OnHitListener onHitListener;

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
        this.onHitListener = new OnHitListener();
        this.recipes = new Recipes();
        
        this.getCommand("gunsmith").setExecutor(gunsmithExec);
        this.getCommand("gs").setExecutor(gunsmithExec);

        this.getServer().getPluginManager().registerEvents(scopeListener, this);
        this.getServer().getPluginManager().registerEvents(shootListener, this);
        this.getServer().getPluginManager().registerEvents(recipes, this);
        this.getServer().getPluginManager().registerEvents(onHitListener, this);
        this.getServer().getPluginManager().registerEvents(PlayerConnect.getListener(), this);
        this.getServer().getScheduler().runTaskTimer(this, new CSEPlayerRunningSpeed(), 10L, 10L);
        this.getServer().getScheduler().runTaskTimer(this, new EntityTimedEventsPerTick(), 1L, 1L);

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
        return isInitialized("Qualities", Qualities.getInstance().size())
           && isInitialized("Attachments", ProjectileAttachments.getInstance().size())
           && isInitialized("Barrels", Barrels.getInstance().size())
           && isInitialized("Bolts", Bolts.getInstance().size())
           && isInitialized("FireModes", FireModes.getInstance().size())
           && isInitialized("Magazines", Magazines.getInstance().size())
           && isInitialized("Sights", Sights.getInstance().size())
           && isInitialized("Stocks", Stocks.getInstance().size())
           && isInitialized("ModifierAttributes Sets", ModifierSets.getInstance().size())
           && isInitialized("Firearm Action", FirearmActions.getInstance().size())
           && isInitialized("Weapon Types", SkeletonTypes.getInstance().size())
           && isInitialized("Gun Skeletons", GunSkeletons.getInstance().size());
    }
    
    private boolean isInitialized(final String toInitialize,
                                  final int returned)
    {
        if (returned > 0)
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
