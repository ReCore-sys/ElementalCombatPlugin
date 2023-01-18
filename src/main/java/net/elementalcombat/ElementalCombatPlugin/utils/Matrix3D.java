package net.elementalcombat.ElementalCombatPlugin.utils;

import org.bukkit.Material;

public class Matrix3D {
    private final Material[][][] matrix;
    private final int width;
    private final int height;
    private final int depth;

    private int[] start = new int[3];
    private int[] end = new int[3];


    public Matrix3D(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.matrix = new Material[width][height][depth];
    }

    public void setStart(int[] start) {
        this.start = start;
    }

    public int[] getStart() {
        return start;
    }

    public void setEnd(int[] end) {
        this.end = end;
    }

    public int[] getEnd() {
        return end;
    }

    public Material get(int x, int y, int z) {
        return this.matrix[x][y][z];
    }

    public void set(int x, int y, int z, Material value) {
        this.matrix[x][y][z] = value;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getDepth() {
        return this.depth;
    }

    public void fill(Material material) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    this.matrix[x][y][z] = material;
                }
            }
        }
    }
}
