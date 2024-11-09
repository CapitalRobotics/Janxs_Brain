package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "arm2")
public class ArmMove2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Position of the arm when it's lifted
        int armUpPosition = 2;

        // Position of the arm when it's down
        int armDownPosition = 118;

        // Find a motor in the hardware map named "Arm Motor"
        DcMotorEx armMotor = hardwareMap.get(DcMotorEx.class, "arm");


        // Reset the motor encoder so that it reads zero ticks
        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // Sets the starting position of the arm to the down position
        armMotor.setTargetPosition(armDownPosition);
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        waitForStart();

        while (opModeIsActive()) {
            int targetPos = 0;
            if (Math.abs(gamepad2.left_stick_y) > 0.1) {  // Add a small dead zone to prevent drift
                // Calculate the target position incrementally based on joystick input
                targetPos = armMotor.getCurrentPosition() + (int) (gamepad2.left_stick_y * -5);

                // Constrain target position to within the upper and lower bounds
                if (targetPos > armUpPosition) {
                    targetPos = armUpPosition;
                } else if (targetPos < armDownPosition) {
                    targetPos = armDownPosition;
                }

                // Set the motor to RUN_USING_ENCODER to allow smooth joystick control
                armMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

                // Set power based on joystick input for real-time movement
                armMotor.setPower(gamepad2.left_stick_y * -0.5);  // Adjust the scaling factor as needed
            } else {
                // Stop the motor if the joystick is in the neutral position
                armMotor.setPower(0);
            }

            // Display telemetry for debugging
            telemetry.addData("Encoder Position", armMotor.getCurrentPosition());
            telemetry.addData("Target Position", targetPos);
            telemetry.addData("Left Stick Y", gamepad2.left_stick_y);
            telemetry.update();
        }

            // Get the current position of the armMotor
            double position = armMotor.getCurrentPosition();

            // Get the target position of the armMotor
            double desiredPosition = armMotor.getTargetPosition();

            // Show the position of the armMotor on telemetry
            telemetry.addData("Encoder Position", position);

            // Show the target position of the armMotor on telemetry
            telemetry.addData("Desired Position", desiredPosition);

            telemetry.addData("Left Stick Y: ", gamepad2.left_stick_y);


            telemetry.update();
        }
    }
