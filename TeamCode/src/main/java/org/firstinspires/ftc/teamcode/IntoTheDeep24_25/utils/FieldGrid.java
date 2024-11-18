package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils;

public class FieldGrid {
    private final int[][] grid;

    public FieldGrid(int rows, int cols) {
        this.grid = new int[rows][cols];
    }

    public void setObstacle(int x, int y) {
        grid[y][x] = 1;
    }

    public boolean isObstacle(int x, int y) {
        return grid[y][x] == 1;
    }

    public int[][] getGrid() {
        return grid;
    }
}