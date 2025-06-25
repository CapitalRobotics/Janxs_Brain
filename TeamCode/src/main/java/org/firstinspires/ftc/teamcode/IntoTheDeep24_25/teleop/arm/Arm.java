package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop.arm;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
@Disabled
@TeleOp
public class Arm extends LinearOpMode {
    // Position of the arm when it's down
    private static final int ARM_UP_POSITION = 10;
    private static final int ARM_DOWN_POSITION = 150;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize the arm motor
        DcMotorEx armMotor = hardwareMap.get(DcMotorEx.class, "arm");

        // Reset the motor encoder so that it reads zero ticks
        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // Set the motor mode to RUN_TO_POSITION
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        // Wait for the game to start
        waitForStart();

        while (opModeIsActive()) {
            // Check for button presses to set the target position
            if (gamepad2.a) {
                armMotor.setTargetPosition(ARM_UP_POSITION); // Move arm up
            } else if (gamepad2.b) {
                armMotor.setTargetPosition(ARM_DOWN_POSITION); // Move arm down
            }

            // Apply a constant power to move the motor
            armMotor.setPower(1.0);

            // Telemetry for debugging
            telemetry.addData("Current Position", armMotor.getCurrentPosition());
            telemetry.addData("Target Position", armMotor.getTargetPosition());
            telemetry.update();
        }
    }
}