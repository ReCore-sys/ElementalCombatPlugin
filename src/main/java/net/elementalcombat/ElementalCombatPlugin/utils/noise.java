package net.elementalcombat.ElementalCombatPlugin.utils;

import org.bukkit.Material;

import java.time.Clock;

public class noise {
    public static Matrix3D generateTerrain(int[] start, int[] end) {
        int width = end[0] - start[0];
        int height = end[1] - start[1];
        int depth = end[2] - start[2];
        Matrix3D matrix = new Matrix3D(width, height, depth);
        matrix.setStart(start);
        matrix.setEnd(end);
        noisefunctions noise = new noisefunctions();
        noise.OpenSimplexNoise(Clock.systemUTC().instant().getEpochSecond());
        Matrix2D noiseMap = new Matrix2D(width, depth);
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < depth; z++) {
                double nx = (double) x / (double) width * 2d;
                double nz = (double) z / (double) depth * 2d;
                double value = noise.eval(nx, nz);
                // normalize to 0...height
                double normalizedValue = (value + 1.0) / 2.0 * height;
                noiseMap.set(x, z, (int) normalizedValue);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    if (y < noiseMap.get(x, z)) {
                        matrix.set(x, y, z, Material.GRASS_BLOCK);
                    } else if (y < 5) {
                        matrix.set(x, y, z, Material.WATER);
                    } else {
                        matrix.set(x, y, z, Material.AIR);

                    }
                }
            }
        }
        return matrix;


    }
}
