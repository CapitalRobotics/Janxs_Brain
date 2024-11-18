package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AStarRobotExecutor {
    private final Movement robot;
    private final Telemetry telemetry;

    public AStarRobotExecutor(Movement robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;
    }

    public void executePath(List<Node> path, int cellSize, double power) throws InterruptedException {
        telemetry.addData("Status", "Executing A* path...");
        telemetry.update();
        System.out.println("Executing A* path...");

        for (int i = 1; i < path.size(); i++) {
            Node current = path.get(i - 1);
            Node next = path.get(i);

            int dx = next.x - current.x;
            int dy = next.y - current.y;

            telemetry.addData("Current Position", "X: " + current.x + ", Y: " + current.y);
            telemetry.addData("Next Position", "X: " + next.x + ", Y: " + next.y);
            telemetry.addData("Direction", getDirection(dx, dy));
            telemetry.update();

            if (dx == 1 && dy == 0) {
                System.out.println("Move Right");
                //robot.moveRight(power, cellSize * 100);
            } else if (dx == -1 && dy == 0) {
                System.out.println("Move Left");
                //robot.moveLeft(power, cellSize * 100);
            } else if (dx == 0 && dy == 1) {
                System.out.println("Move Forward");
                //robot.moveForward(power, cellSize * 100);
            } else if (dx == 0 && dy == -1) {
                System.out.println("Move Backward");
                //robot.moveBackward(power, cellSize * 100);
            }

            if (next.equals(path.get(path.size() - 1))) {
                telemetry.addData("Status", "Final destination reached. Raising arm.");
                telemetry.update();
                //robot.armUp();
            }
        }

        telemetry.addData("Status", "Path execution complete.");
        telemetry.update();
        System.out.println("Path execution complete.");
    }

    private String getDirection(int dx, int dy) {
        if (dx == 1 && dy == 0) return "Right";
        if (dx == -1 && dy == 0) return "Left";
        if (dx == 0 && dy == 1) return "Forward";
        if (dx == 0 && dy == -1) return "Backward";
        return "Unknown";
    }
}