package net.elementalcombat.ElementalCombatPlugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("move")
public class Move extends BaseCommand {
    @Subcommand("top")
    public void top(Player player) {
        World world = player.getWorld();
        player.teleport(new Location(world, -1, 138, -1));
    }

    @Subcommand("bottom")
    public void bottom(Player player) {
        World world = player.getWorld();
        player.teleport(new Location(world, 1, -63, 1));
    }

}