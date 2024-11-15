package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop.pidMaybe;

@Autonomous(name = "BLUE RIGHT", group = "Autonomous")
public class BlueRight extends LinearOpMode {

    private DcMotorEx frontRight, frontLeft, backRight, backLeft;
    private DcMotorEx armMotor = null;
    private Servo claw = null;

    // Arm positions
    private final int armUpPosition = 30;
    private final int armDownPosition = 180;

    // Constants for movement
    private final int TURN_DURATION = 500;
    private final int MOVE_28_INCHES = 1000;
    private final int MOVE_6_INCHES = 300;
    private final int MOVE_95_INCHES = 3000;
    private final int MOVE_100_INCHES = 3000;
    private final int MOVE_105_INCHES = 3300;
    private final int MOVE_85_INCHES = 2700;
    private final int MOVE_36_INCHES = 1200;
    private final int MOVE_5_INCHES = 200;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize drive motors
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        backRight = hardwareMap.get(DcMotorEx.class, "br");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");

        // Set directions for correct movement
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);

        // Initialize arm and claw
        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        waitForStart();

        // BLUE RIGHT Path
        executePath();
    }

    private void executePath() throws InterruptedException {
        turnRight();
        moveForward(MOVE_28_INCHES);
        turnLeft();
        moveForward(MOVE_6_INCHES);
        grabSample();
        turnLeft();
        moveForward(MOVE_95_INCHES);
        turnLeft();
        placeSample();
        turnLeft();
        moveForward(MOVE_100_INCHES);
        turnLeft();
        grabSample();
        turnLeft();
        moveForward(MOVE_105_INCHES);
        placeSample();
        turnLeft();
        moveForward(MOVE_85_INCHES);
        turnLeft();
        moveForward(MOVE_36_INCHES);
        turnLeft();
        moveForward(MOVE_5_INCHES);
        ascent();
    }

    private void turnLeft() throws InterruptedException {
        mecanum(0, 0, -1);
        sleep(TURN_DURATION);
    }

    private void turnRight() throws InterruptedException {
        mecanum(0, 0, 1);
        sleep(TURN_DURATION);
    }

    private void moveForward(int duration) throws InterruptedException {
        mecanum(1, 0, 0);
        sleep(duration);
    }

    private void mecanum(double LSY, double LSX, double RSX) {
        int Speed = 1600;
        double lx = Math.pow(LSX, 3);
        double ly = -(Math.pow(LSY, 3));
        double rx = Math.pow(RSX, 3);

        if (LSX != 0 || LSY != 0 || RSX != 0) {
            // Set velocity for each motor
            frontRight.setVelocity(Speed * (clip((ly) - lx, -1, 1) - rx));
            frontLeft.setVelocity(Speed * (clip((ly) + lx, -1, 1) + rx));
            backRight.setVelocity(Speed * (clip((ly) + lx, -1, 1) - rx));
            backLeft.setVelocity(Speed * (clip((ly) - lx, -1, 1) + rx));
        } else {
            // Stop all motors if no input
            frontRight.setVelocity(0);
            frontLeft.setVelocity(0);
            backRight.setVelocity(0);
            backLeft.setVelocity(0);
        }
    }

    private double clip(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    // Grab sample using the arm and claw
    private void grabSample() {
        telemetry.addData("Action", "Grab Sample");
        telemetry.update();

        // Move arm down
        moveArm(armDownPosition);

        // Close claw
        claw.setPosition(1);
        sleep(1000); // Allow time for claw to grab
    }

    // Place sample using the arm and claw
    private void placeSample() {
        telemetry.addData("Action", "Place Sample");
        telemetry.update();

        // Move arm up
        moveArm(armUpPosition);

        // Open claw
        claw.setPosition(0);
        sleep(1000); // Allow time for claw to release
    }

    // Move arm to a specific position
    private void moveArm(int targetPosition) {
        armMotor.setTargetPosition(targetPosition);
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        pidMaybe pid = new pidMaybe(0.004, 0, armMotor);
        while (opModeIsActive() && Math.abs(armMotor.getCurrentPosition() - targetPosition) > 5) {
            double power = pid.calculatePower(targetPosition, armMotor.getCurrentPosition());
            armMotor.setPower(power);

            // Debug telemetry
            telemetry.addData("Arm Target", targetPosition);
            telemetry.addData("Arm Position", armMotor.getCurrentPosition());
            telemetry.addData("Arm Power", power);
            telemetry.update();
        }
        armMotor.setPower(0); // Stop motor when done
    }

    // Raise the arm to its ascent position
    private void ascent() {
        int ascentPosition = 200;
        telemetry.addData("Action", "Ascent");
        telemetry.update();
        moveArm(ascentPosition);
        sleep(500);
    }
}