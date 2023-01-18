package net.elementalcombat.ElementalCombatPlugin.utils;

import org.bukkit.Material;

public class TerrainParts {

    public Matrix3D Boulder(int size) {
        Matrix3D matrix = new Matrix3D(size, size, size);
        // Generate an orb of stone
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    matrix.set(x, y, z, Material.STONE);
                }
            }
        }
        return matrix;
    }
}
