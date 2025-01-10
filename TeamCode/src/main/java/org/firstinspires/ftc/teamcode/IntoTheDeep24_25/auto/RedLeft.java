package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.pidMaybe;

@Disabled
@Autonomous(name = "RED LEFT", group = "Autonomous")
public class RedLeft extends LinearOpMode {

    private DcMotorEx frontRight, frontLeft, backRight, backLeft;
    private DcMotorEx armMotor;
    private Servo claw;

    // Arm positions
    private final int armUpPosition = 180;
    private final int armDownPosition = 30;

    // Movement durations
    private final int TURN_DURATION = 500; // Duration for 90-degree turns
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
        // Initialize hardware
        initializeHardware();

        // Wait for the game to start
        waitForStart();

        // Execute the autonomous path
        executePath();
    }

    private void initializeHardware() {
        // Drivetrain motor initialization
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");

        // Set motor directions
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);

        // Reset and set motors to run with encoders
        resetAndRunUsingEncoders(frontRight);
        resetAndRunUsingEncoders(backRight);
        resetAndRunUsingEncoders(frontLeft);
        resetAndRunUsingEncoders(backLeft);

        // Arm and claw initialization
        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        // Reset the arm motor encoder
        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    private void resetAndRunUsingEncoders(DcMotorEx motor) {
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        motor.setPower(0);
    }

    private void executePath() throws InterruptedException {
        turnLeft();
        moveForward(MOVE_28_INCHES);
        turnRight();
        moveForward(MOVE_6_INCHES);
        grabSample();
        turnRight();
        moveForward(MOVE_95_INCHES);
        turnRight();
        placeSample();
        turnRight();
        moveForward(MOVE_100_INCHES);
        turnRight();
        grabSample();
        turnRight();
        moveForward(MOVE_105_INCHES);
        placeSample();
        turnRight();
        moveForward(MOVE_85_INCHES);
        turnRight();
        moveForward(MOVE_36_INCHES);
        turnRight();
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
            frontRight.setVelocity(Speed * (clip((ly) - lx, -1, 1) - rx));
            frontLeft.setVelocity(Speed * (clip((ly) + lx, -1, 1) + rx));
            backRight.setVelocity(Speed * (clip((ly) + lx, -1, 1) - rx));
            backLeft.setVelocity(Speed * (clip((ly) - lx, -1, 1) + rx));
        } else {
            stopMotors();
        }
    }

    private void stopMotors() {
        frontRight.setVelocity(0);
        frontLeft.setVelocity(0);
        backRight.setVelocity(0);
        backLeft.setVelocity(0);
    }

    private double clip(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

    private void grabSample() {
        telemetry.addData("Action", "Grab Sample");
        telemetry.update();

        moveArm(armDownPosition);
        claw.setPosition(1); // Close claw
        sleep(1000); // Wait for claw to grab
    }

    private void placeSample() {
        telemetry.addData("Action", "Place Sample");
        telemetry.update();

        moveArm(armUpPosition);
        claw.setPosition(0); // Open claw
        sleep(1000); // Wait for claw to release
    }

    private void moveArm(int targetPosition) {
        armMotor.setTargetPosition(targetPosition);
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        //pidMaybe pid = new pidMaybe(0.004, 0, armMotor);
        while (opModeIsActive() && Math.abs(armMotor.getCurrentPosition() - targetPosition) > 5) {
            //double power = pid.calculatePower(targetPosition, armMotor.getCurrentPosition());
            double power = 0;
            armMotor.setPower(power);

            telemetry.addData("Arm Target", targetPosition);
            telemetry.addData("Arm Position", armMotor.getCurrentPosition());
            telemetry.addData("Arm Power", power);
            telemetry.update();
        }
        armMotor.setPower(0); // Stop motor
    }

    private void ascent() {
        telemetry.addData("Action", "Ascent");
        telemetry.update();

        int ascentPosition = 200;
        moveArm(ascentPosition);
        sleep(500);
    }
}