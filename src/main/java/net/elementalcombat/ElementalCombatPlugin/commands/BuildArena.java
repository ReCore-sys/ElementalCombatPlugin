package net.elementalcombat.ElementalCombatPlugin.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.elementalcombat.ElementalCombatPlugin.utils.Matrix3D;
import net.elementalcombat.ElementalCombatPlugin.utils.TerrainParts;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static net.elementalcombat.ElementalCombatPlugin.utils.noise.generateTerrain;
import static net.elementalcombat.ElementalCombatPlugin.utils.noise.noiseMap;


@CommandAlias("buildarena")
public class BuildArena extends BaseCommand {
    @Default
    public void onDefault(CommandSender player) {
        int[] start = {0, -63, 0};
        int[] end = {150, 138, 150};
        Matrix3D blocks = buildBlockList(start, end, Material.AIR);
        setBlocks(blocks);
        Matrix3D bottom_blocks = buildBlockList(new int[]{start[0], -64, start[2]}, new int[]{end[0], -64, end[2]}, Material.STONE_BRICKS);
        setBlocks(bottom_blocks);
        var walls = buildWalls(start, end);
        for (var wall : walls) {
            setBlocks(wall);
        }
        int[] terrain_start = {0, -20, 0};
        int[] terrain_end = {150, 0, 150};
        Matrix3D terrain = generateTerrain(terrain_start, terrain_end);
        terrain = placeBoulders(terrain);
        player.sendMessage("Starting to add terrain");
        setBlocks(terrain);
        Matrix3D dirtunderground = buildBlockList(new int[]{start[0], -63, start[2]}, new int[]{end[0], -20, end[2]}, Material.DIRT);
        setBlocks(dirtunderground);
        player.sendMessage("Added all terrain");
    }

    private void setBlocks(Matrix3D blocks) {
        int width = blocks.getWidth();
        int height = blocks.getHeight();
        int depth = blocks.getDepth();
        int[] start = blocks.getStart();
        int[] end = blocks.getEnd();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    Material block = blocks.get(x, y, z);
                    Objects.requireNonNull(Bukkit.getWorld("world")).getBlockAt(start[0] + x, start[1] + y, start[2] + z).setType(Objects.requireNonNullElse(block, Material.AIR));
                }
            }
        }
    }


    private Matrix3D buildBlockList(int[] start, int[] end, Material material) {

        Matrix3D blocks = new Matrix3D(end[0] - start[0], end[1] - start[1], end[2] - start[2]);
        blocks.setStart(start);
        blocks.setEnd(end);
        for (int x = 0; x < blocks.getWidth(); x++) {
            for (int y = 0; y < blocks.getHeight(); y++) {
                for (int z = 0; z < blocks.getDepth(); z++) {
                    blocks.set(x, y, z, material);
                }
            }
        }
        return blocks;
    }

    ArrayList<Matrix3D> buildWalls(int[] start, int[] end) {
        // given a start and end, build 4 matrixes that represent the walls
        // they will be 1 block thick and must be 1 block away from the edge of the arena
        Matrix3D left_wall_blocks = new Matrix3D(1, end[1] - start[1], end[2] - start[2]);
        left_wall_blocks.setStart(new int[]{start[0], start[1], start[2]});
        left_wall_blocks.setEnd(new int[]{start[0], end[1], end[2]});
        left_wall_blocks.fill(Material.QUARTZ_BRICKS);
        Matrix3D right_wall_blocks = new Matrix3D(1, end[1] - start[1], end[2] - start[2]);
        right_wall_blocks.setStart(new int[]{end[0], start[1], start[2]});
        right_wall_blocks.setEnd(new int[]{end[0], end[1], end[2]});
        right_wall_blocks.fill(Material.QUARTZ_BRICKS);
        Matrix3D front_wall_blocks = new Matrix3D(end[0] - start[0], end[1] - start[1], 1);
        front_wall_blocks.setStart(new int[]{start[0], start[1], start[2]});
        front_wall_blocks.setEnd(new int[]{end[0], end[1], start[2]});
        front_wall_blocks.fill(Material.QUARTZ_BRICKS);
        Matrix3D back_wall_blocks = new Matrix3D(end[0] - start[0], end[1] - start[1], 1);
        back_wall_blocks.setStart(new int[]{start[0], start[1], end[2]});
        back_wall_blocks.setEnd(new int[]{end[0], end[1], end[2]});
        back_wall_blocks.fill(Material.QUARTZ_BRICKS);


        return new ArrayList<>(Arrays.asList(left_wall_blocks, right_wall_blocks, front_wall_blocks, back_wall_blocks));

    }

    Matrix3D placeBoulders(Matrix3D terrain) {
        // get a random number of boulders
        int amount = (int) (Math.random() * 10);
        // get a random size for each boulder
        int size = (int) (Math.random() * 10);
        // get a random position for each boulder
        int[] position = {(int) (Math.random() * 150), (int) (Math.random() * 20) + 3, (int) (Math.random() * 150)};

        for (int i = 0; i < amount; i++) {
            Matrix3D boulder = new TerrainParts().Boulder(size);
            int rand_x = (int) (Math.random() * noiseMap.getWidth());
            int rand_z = (int) (Math.random() * noiseMap.getHeight());
            int rand_y = noiseMap.get(rand_x, rand_z);
            boulder.setStart(new int[]{rand_x, rand_y, rand_z});
            boulder.setEnd(new int[]{rand_x + boulder.getWidth(), rand_y + boulder.getHeight(), rand_z + boulder.getDepth()});
            for (int x = 0; x < boulder.getWidth(); x++) {
                for (int y = 0; y < boulder.getHeight(); y++) {
                    for (int z = 0; z < boulder.getDepth(); z++) {
                        if (boulder.get(x, y, z) != null) {
                            terrain.set(x + rand_x - 1, y + rand_y - 1, z + rand_z - 1, boulder.get(x, y, z));
                        }
                    }
                }
            }
        }
        return terrain;
    }
}