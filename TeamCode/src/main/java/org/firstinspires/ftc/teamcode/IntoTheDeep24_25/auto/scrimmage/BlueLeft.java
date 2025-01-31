package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.scrimmage;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "BLUE LEFT", group = "Autonomous")
public class BlueLeft extends LinearOpMode {

    private DcMotorEx frontRight, frontLeft, backRight, backLeft;
    private DcMotorEx armMotor = null;
    private Servo claw = null;

    // Arm positions
    private final int armUpPosition = 30;
    private final int armDownPosition = 150;

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

        // BLUE LEFT Path
        // Turn left
        mecanum(0, 0, -1); // RSX = -1 for left rotation
        sleep(500); // Adjust timing for 90-degree turn

        // Move forward 28 inches
        mecanum(1, 0, 0); // Forward
        sleep(1000); // Adjust timing for 28 inches

        // Turn right
        mecanum(0, 0, 1); // RSX = 1 for right rotation
        sleep(500); // Adjust timing for 90-degree turn

        // Move forward 6 inches
        mecanum(1, 0, 0); // Forward
        sleep(300); // Adjust timing for 6 inches

        // Grab Sample
        grabSample();

        // Turn left
        mecanum(0, 0, -1); // RSX = -1 for left rotation
        sleep(500); // Adjust timing for 90-degree turn

        // Place Basket
        placeSample();

        // Turn right
        mecanum(0, 0, 1); // RSX = 1 for right rotation
        sleep(500); // Adjust timing for 90-degree turn

        // Strafe left a little
        mecanum(0, -1, 0); // LSX = -1 for left strafe
        sleep(300); // Adjust timing for small strafe

        // Move forward
        mecanum(1, 0, 0); // Forward
        sleep(1000); // Adjust timing for desired distance

        // Grab Sample
        grabSample();

        // Turn
        mecanum(0, 0, 1); // RSX = 1 for right rotation
        sleep(500); // Adjust timing for 90-degree turn

        // Place Sample
        placeSample();

        // Turn left
        mecanum(0, 0, -1); // RSX = -1 for left rotation
        sleep(500); // Adjust timing for 90-degree turn

        // Move forward
        mecanum(1, 0, 0); // Forward
        sleep(1000); // Adjust timing for desired distance

        // Strafe left
        mecanum(0, -1, 0); // LSX = -1 for left strafe
        sleep(500); // Adjust timing for full strafe

        // Level 1 Ascent
        level1Ascent();
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
        claw.setPosition(-1);
        sleep(1000); // Allow time for claw to release
    }

    // Move arm to a specific position
    private void moveArm(int targetPosition) {
        armMotor.setTargetPosition(targetPosition);
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        //pidMaybe pid = new pidMaybe(0.004, 0, armMotor);
        while (opModeIsActive() && Math.abs(armMotor.getCurrentPosition() - targetPosition) > 5) {
            //double power = pid.calculatePower(targetPosition, armMotor.getCurrentPosition());
            double power = 0;
            armMotor.setPower(power);

            // Debug telemetry
            telemetry.addData("Arm Target", targetPosition);
            telemetry.addData("Arm Position", armMotor.getCurrentPosition());
            telemetry.addData("Arm Power", power);
            telemetry.update();
        }
        armMotor.setPower(0); // Stop motor when done
    }

    // Level 1 Ascent
    private void level1Ascent() {
        int level1AscentPosition = 200; // Adjust this value for Level 1 height
        moveArm(level1AscentPosition);
        sleep(500); // Ensure time for arm to stabilize
    }
}