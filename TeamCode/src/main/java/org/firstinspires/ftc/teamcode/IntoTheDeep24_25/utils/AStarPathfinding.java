package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils;

import java.util.*;

public class AStarPathfinding {
    private final int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public List<Node> findPath(Node start, Node goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        HashSet<Node> closedSet = new HashSet<>();

        start.gCost = 0;
        start.hCost = heuristic(start, goal);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goal)) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                FieldGrid grid = null;
                if (!isValid(grid, newX, newY) || closedSet.contains(new Node(newX, newY))) {
                    continue;
                }

                Node neighbor = new Node(newX, newY);
                double tentativeGCost = current.gCost + 1;

                if (tentativeGCost < neighbor.gCost) {
                    neighbor.gCost = tentativeGCost;
                    neighbor.hCost = heuristic(neighbor, goal);
                    neighbor.parent = current;

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private boolean isValid(FieldGrid grid, int x, int y) {
        int[][] field = grid.getGrid();
        return x >= 0 && y >= 0 && x < field[0].length && y < field.length && !grid.isObstacle(x, y);
    }

    private double heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}
