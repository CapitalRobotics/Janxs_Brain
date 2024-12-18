package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "RL1")
public class Ascent extends LinearOpMode {
    /* Declare OpMode members. */
    private DcMotorEx frontRight;
    private DcMotorEx backRight;
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx arm;
    private Servo claw;

    int armUpPosition = 20;
    boolean flag = false;
    int armDownPosition = 150;

    private ElapsedTime runtime = new ElapsedTime();

    // Encoder constants for the drivetrain
    static final double COUNTS_PER_MOTOR_REV = 28; // Rev motor ticks per revolution
    static final double DRIVE_GEAR_REDUCTION = 20.0; // External gearing
    static final double WHEEL_DIAMETER_INCHES = 4.0; // Diameter of wheels
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() {
        initial(); // Initialize hardware
        raiseArm(); // Raise the arm immediately

        // Wait for the game to start
        waitForStart();

        // Perform the sequence: Forward -> Turn Left -> Forward
        encoderDrive(DRIVE_SPEED, 44, 44, 5); // 1. Move forward 24 inches
        encoderDrive(TURN_SPEED, -12, 12, 4); // 2. Turn left
        encoderDrive(DRIVE_SPEED, 20, 20, 5); // 3. Move forward 12 inches

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000); // Pause to display the final telemetry message
    }


    /**
     * Method to perform a relative move, based on encoder counts.
     */
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {
            // Calculate new target positions
            newLeftTarget = backLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = backRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);

            // Set targets for all motors
            backLeft.setTargetPosition(newLeftTarget);
            frontLeft.setTargetPosition(newLeftTarget);
            backRight.setTargetPosition(newRightTarget);
            frontRight.setTargetPosition(newRightTarget);

            // Set motors to run to position
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Start motion
            runtime.reset();
            backLeft.setPower(Math.abs(speed));
            frontLeft.setPower(Math.abs(speed));
            backRight.setPower(Math.abs(speed));
            frontRight.setPower(Math.abs(speed));

            // Wait while motors are running or timeout occurs
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (backLeft.isBusy() && backRight.isBusy())) {
                telemetry.addData("Target L", newLeftTarget);
                telemetry.addData("Target R", newRightTarget);
                telemetry.addData("Current L", backLeft.getCurrentPosition());
                telemetry.addData("Current R", backRight.getCurrentPosition());
                telemetry.update();
            }
            while (opModeIsActive() && arm.isBusy()) {
                telemetry.addData("Raising Arm", "Current Position: %d", arm.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion
            backLeft.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            frontRight.setPower(0);

            // Reset motors to RUN_USING_ENCODER
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250); // Optional delay
        }
    }

    /**
     * Raise the arm to the "up" position.
     */
    public void raiseArm() {
        arm.setTargetPosition(150);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(0.5);

        //arm.setPower(0); // Stop the motor
    }

    /**
     * Initialize all hardware components.
     */
    public void initial() {
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        // Set arm motor to reset encoders and brake mode
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(20);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set drivetrain motor directions
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        // Set drivetrain motors to use encoders
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set brake behavior for all drivetrain motors
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Raise the arm immediately

        // Send telemetry message
        telemetry.addData("Initialization", "Complete");
        telemetry.update();
    }
}