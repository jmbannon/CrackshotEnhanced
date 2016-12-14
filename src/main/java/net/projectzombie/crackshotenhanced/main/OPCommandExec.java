package net.projectzombie.crackshotenhanced.main;

import net.projectzombie.crackshotenhanced.guns.yaml_gen.YAMLGenerator;
import net.projectzombie.crackshotenhanced.windows.BlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class OPCommandExec implements CommandExecutor
{

    public OPCommandExec() { /* Do nothing. */ }
	
    @Override
    public boolean onCommand(CommandSender cs,
                         Command cmd,
                         String label,
                         String[] args)
    {
        if (!cs.isOp())
            return true;

        if (cmd.getName().equalsIgnoreCase("cse") && args.length > 0)
        {
            if (args[0].equalsIgnoreCase("yaml"))
                YAMLGenerator.generateDefaultWeapons();
            else
                this.commandList(cs);
        }
        else
            this.commandList(cs);

        return true;
    }
    
    public void commandList(CommandSender sender)
    {
        sender.sendMessage("/cse yaml");
    }
}