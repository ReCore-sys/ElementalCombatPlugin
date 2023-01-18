package net.elementalcombat.ElementalCombatPlugin.utils;

public class Matrix2D {
    private final int[][] matrix;
    private final int width;
    private final int height;


    public Matrix2D(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new int[width][height];
    }

    public int get(int x, int y) {
        return this.matrix[x][y];
    }

    public void set(int x, int y, int value) {
        this.matrix[x][y] = value;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
