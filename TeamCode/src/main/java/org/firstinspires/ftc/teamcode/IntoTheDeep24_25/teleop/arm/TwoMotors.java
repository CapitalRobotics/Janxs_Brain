package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop.arm;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@Disabled
@TeleOp
public class TwoMotors extends LinearOpMode {
    // Position of the arm when it's down
    private static final int ARM_UP_POSITION = 10;
    private static final int ARM_DOWN_POSITION = 150;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the arm motors
        DcMotorEx armMotor1 = hardwareMap.get(DcMotorEx.class, "arm1");
        DcMotorEx armMotor2 = hardwareMap.get(DcMotorEx.class, "arm2");

        // Reset the motor encoders so that they read zero ticks
        armMotor1.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armMotor2.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        armMotor1.setTargetPosition(ARM_DOWN_POSITION);
        armMotor2.setTargetPosition(ARM_DOWN_POSITION);

        // Set the motor modes to RUN_TO_POSITION
        armMotor1.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armMotor2.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        // Wait for the game to start
        waitForStart();

        while (opModeIsActive()) {
            // Check for button presses to set the target positions
            if (gamepad2.a) {
                armMotor1.setTargetPosition(ARM_UP_POSITION); // Move arm up
                armMotor2.setTargetPosition(ARM_UP_POSITION); // Move arm up
            } else if (gamepad2.b) {
                armMotor1.setTargetPosition(ARM_DOWN_POSITION); // Move arm down
                armMotor2.setTargetPosition(ARM_DOWN_POSITION); // Move arm down
            }

            // Apply a constant power to move both motors
            armMotor1.setPower(1.0);
            armMotor2.setPower(1.0);

            // Telemetry for debugging
            telemetry.addData("Motor 1 Position", armMotor1.getCurrentPosition());
            telemetry.addData("Motor 2 Position", armMotor2.getCurrentPosition());
            telemetry.addData("Target Position", armMotor1.getTargetPosition()); // Same for both motors
            telemetry.update();
        }
    }
}