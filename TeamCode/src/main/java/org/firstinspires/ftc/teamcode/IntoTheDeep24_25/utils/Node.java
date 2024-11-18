package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils;

public class Node implements Comparable<Node> {
    public int x, y;
    public double gCost, hCost;
    public Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gCost = Double.MAX_VALUE;
        this.hCost = 0;
        this.parent = null;
    }

    public double fCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.fCost(), other.fCost());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
