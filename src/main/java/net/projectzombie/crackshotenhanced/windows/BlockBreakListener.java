package net.projectzombie.crackshotenhanced.windows;

import org.bukkit.event.Listener;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import com.shampaggon.crackshot.events.WeaponHitBlockEvent;
import net.projectzombie.blockbuffer.buffer.Buffer;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BlockBreakListener implements Listener
{

    private static GlassFormations formations;

    public BlockBreakListener()
    {
        BlockBreakListener.formations = new GlassFormations();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void meleeBlockBreakEvent(BlockBreakEvent event)
    {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();

        if (player.getGameMode() == GameMode.SURVIVAL)
        {
            if (isBreakableBlock(block))
            {
                chunckBreak(block);
                if (player.getItemInHand().getType() == Material.AIR)
                {
                    player.damage(0.5);
                }
            } else if (isBreakableLight(block))
            {
                storeAndBreakBlock(block);
                if (block.getType() != Material.TORCH
                        && player.getItemInHand().getType() == Material.AIR)
                {
                    player.damage(0.5);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void projectileBlockBreakEvent(WeaponHitBlockEvent event)
    {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        
        if (isBreakableBlock(block))
        {
            chunckBreak(block);
        }
        else if (isBreakableLight(block))
        {
            storeAndBreakBlock(block);
        } 
        else if (isTorch(block))
        {
            block.setType(Material.AIR);
            block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.GLASS.getId());
        }
        else if (!block.getType().equals(Material.SNOW))
        {
            block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getTypeId());
        }
        else // is snow
        {
            Entity proj = event.getProjectile();
            Location loc = proj.getLocation();
            Vector norm = proj.getVelocity().normalize().multiply(1.0);
            Material tempMat;
            int i = 0;
            
            do
            {
                loc = new Location(block.getWorld(), loc.getX() + norm.getX(), loc.getY() + norm.getY(), loc.getZ() + norm.getZ());
                ++i;
            } 
            while (!loc.getBlock().getType().equals(Material.SNOW) && i < 5);
            
            for (i = 0; i < 40; i++)
            {
                loc = new Location(block.getWorld(), loc.getX() + norm.getX(), loc.getY() + norm.getY(), loc.getZ() + norm.getZ());
                tempMat = loc.getBlock().getType();
                if (!(tempMat.equals(Material.SNOW) || tempMat.equals(Material.AIR)))
                {
                    if (loc.getBlock().getRelative(BlockFace.UP).getType().equals(Material.SNOW))
                        block.getWorld().playEffect(loc.getBlock().getRelative(BlockFace.UP).getLocation(), Effect.STEP_SOUND, Material.SNOW.getId());
                    else
                        block.getWorld().playEffect(loc, Effect.STEP_SOUND, tempMat.getId());
                    break;
                }
            }
                
        }
    }

    public void storeAndBreakBlock(final Block theBlock)
    {
        Buffer.writeToBrokeBuffer(theBlock);
        theBlock.getWorld().playEffect(theBlock.getLocation(), Effect.STEP_SOUND, Material.GLASS.getId());
        theBlock.setType(Material.AIR);
    }

    public boolean isTorch(final Block block)
    {
        return block.getType().equals(Material.TORCH);
    }

    public boolean isBreakableBlock(final Block block)
    {
        final Material blockMaterial = block.getType();
        return (blockMaterial.equals(Material.GLASS)
                || blockMaterial.equals(Material.THIN_GLASS));
    }

    public boolean isBreakableLight(final Block block)
    {
        final Material blockMaterial = block.getType();
        return (blockMaterial.equals(Material.GLOWSTONE)
                || blockMaterial.equals(Material.REDSTONE_LAMP_ON)
                || blockMaterial.equals(Material.BEACON)
                || blockMaterial.equals(Material.REDSTONE_LAMP_OFF));
    }

    public void checkFormation(final Block glassBlock,
            final GlassFormations.GlassOffset[] offsets) {
        Block glassBlockOffset;
        for (GlassFormations.GlassOffset offset : offsets) {
            glassBlockOffset = glassBlock.getRelative(offset.getX(),
                    offset.getY(),
                    offset.getZ());
            if (isBreakableBlock(glassBlockOffset)) {
                storeAndBreakBlock(glassBlockOffset);
            }
        }
    }

    public void chunckBreak(final Block glassBlock)
    {
        this.storeAndBreakBlock(glassBlock);
        this.checkFormation(glassBlock, formations.getConstantCheck());
        this.checkFormation(glassBlock, formations.getRandomCheck());
    }
}
