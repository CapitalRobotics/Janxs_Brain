package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

public class Butter {
    private double maxPower;
    private double minPower;
    private double slowDownDistance;
    private int tolerance;

    // Constructor to initialize the Butter algorithm parameters
    public Butter(double maxPower, double minPower, double slowDownDistance, int tolerance) {
        this.maxPower = maxPower;
        this.minPower = minPower;
        this.slowDownDistance = slowDownDistance;
        this.tolerance = tolerance;
    }

    // Butter algorithm to calculate motor power for smooth movement
    public double calculatePower(int currentPos, int targetPos) {
        // Calculate the distance to the target
        int distanceToTarget = Math.abs(targetPos - currentPos);

        // If within tolerance, stop the motor
        if (distanceToTarget <= tolerance) {
            return 0;
        }

        // Calculate power based on the distance to target
        double power;
        if (distanceToTarget > slowDownDistance) {
            power = maxPower;  // Use max power if far from the target
        } else {
            // Ramp down power smoothly as it approaches the target
            power = minPower + (maxPower - minPower) * (distanceToTarget / slowDownDistance);
        }

        // Set the direction of power based on the target position relative to the current position
        return (targetPos > currentPos) ? power : -power;
    }
}