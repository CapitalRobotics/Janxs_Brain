package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class JanxsMovements {
    private DcMotor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;

    public JanxsMovements(DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack) {
        this.leftFrontDrive = leftFront;
        this.rightFrontDrive = rightFront;
        this.leftBackDrive = leftBack;
        this.rightBackDrive = rightBack;
    }

    // utility method to set power to all motors
    private void setMotorPowers(double lfPower, double rfPower, double lbPower, double rbPower) {
        leftFrontDrive.setPower(lfPower);
        rightFrontDrive.setPower(rfPower);
        leftBackDrive.setPower(lbPower);
        rightBackDrive.setPower(rbPower);
    }

    //stop all motors
    public void stop() {
        setMotorPowers(0, 0, 0, 0);
    }

    // move forward with a specific power
    public void moveForward(double power) {
        power = Range.clip(power, -1.0, 1.0);
        setMotorPowers(power, power, power, power);
    }

    // move backward with a specific power
    public void moveBackward(double power) {
        moveForward(-power);
    }

    //  strafe left with a specific power
    public void strafeLeft(double power) {
        power = Range.clip(power, -1.0, 1.0);
        setMotorPowers(-power, power, power, -power);
    }

    // strafe right with a specific power
    public void strafeRight(double power) {
        strafeLeft(-power);
    }

    // turn Janx to the left (counterclockwise) with a specific power
    public void turnLeft(double power) {
        power = Range.clip(power, -1.0, 1.0);
        setMotorPowers(-power, power, -power, power);
    }

    // turn Janx to the right (clockwise) with a specific power
    public void turnRight(double power) {
        turnLeft(-power);
    }

    // move diagonally left-forward
    public void diagonalLeftForward(double power) {
        power = Range.clip(power, -1.0, 1.0);
        setMotorPowers(power, 0, 0, power);
    }

    // move diagonally right-forward
    public void diagonalRightForward(double power) {
        power = Range.clip(power, -1.0, 1.0);
        setMotorPowers(0, power, power, 0);
    }

    // move diagonally left-backward
    public void diagonalLeftBackward(double power) {
        diagonalLeftForward(-power);
    }

    // move diagonally right-backward
    public void diagonalRightBackward(double power) {
        diagonalRightForward(-power);
    }

    // rotate to a specific angle using IMU (if we end up using sensors)
    public void rotateToAngle(double targetAngle, double currentAngle, double turnPower) {
        double error = targetAngle - currentAngle;
        double power = Range.clip(error * 0.01, -1, 1); // proportional control for turning
        if (Math.abs(error) > 2.0) {  // only turn if error is greater than 2 degrees
            setMotorPowers(-power, power, -power, power);
        } else {
            stop(); // stop if within tolerance
        }
    }

    // drive towards a target based on AprilTag detection (if needed)
    public void driveToTag(double distanceError, double bearingError, double yawError) {
        double forwardPower = Range.clip(distanceError * 0.02, -1.0, 1.0);
        double turnPower = Range.clip(bearingError * 0.01, -1.0, 1.0);
        double strafePower = Range.clip(yawError * 0.015, -1.0, 1.0);

        // apply power to each motor
        setMotorPowers(forwardPower - strafePower - turnPower,
                forwardPower + strafePower + turnPower,
                forwardPower + strafePower - turnPower,
                forwardPower - strafePower + turnPower);
    }
}