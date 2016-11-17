package net.projectzombie.crackshotenhanced.main;

import net.projectzombie.crackshotenhanced.guns.yaml_gen.YAMLGenerator;
import net.projectzombie.crackshotenhanced.windows.BlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OPCommandExec implements CommandExecutor
{
    final BlockBreakListener blockBreak;
    
    public OPCommandExec()
    {
        this.blockBreak = new BlockBreakListener();
    }
	
    @Override
    public boolean onCommand(CommandSender cs,
                         Command cmd,
                         String label,
                         String[] args)
    {
        if (!(cs instanceof Player) || !cs.isOp())
                return true;

        final Player sender = (Player)cs;

        if (cmd.getName().equalsIgnoreCase("bw") && args.length > 0)
        {
            Bukkit.broadcastMessage(args[0]);
            if (args[0].equalsIgnoreCase("yaml"))
                YAMLGenerator.generateDefaultWeapons();
            else
                this.commandList(sender);
        }
        else
            this.commandList(sender);

        return true;
    }
    
    public void commandList(Player sender)
    {
        sender.sendMessage("/bw yaml");
    }
}