package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.tests;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp
public class ArmMoveSmooth extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Position of the arm when it's down
        int armUpPosition = 30;

        // Position of the arm when it's lifted
        int armDownPosition = 170;

        // Find a motor in the hardware map named "Arm Motor"
        DcMotor armMotor = hardwareMap.dcMotor.get("arm");

        // Reset the motor encoder so that it reads zero ticks
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Sets the starting position of the arm to the down position
        armMotor.setTargetPosition(armDownPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Initialize Butter class with parameters
        Butter butter = new Butter(1.0, 0.2, 50, 5);

        waitForStart();

        while (opModeIsActive()) {
            // If the A button is pressed, raise the arm
            if (gamepad2.a) {
                armMotor.setTargetPosition(armUpPosition);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            // If the B button is pressed, lower the arm
            if (gamepad2.b) {
                armMotor.setTargetPosition(armDownPosition);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            // Get the current position of the armMotor
            int currentPosition = armMotor.getCurrentPosition();

            // Get the target position of the armMotor
            int targetPosition = armMotor.getTargetPosition();

            // Calculate power using Butter algorithm
            double power = butter.calculatePower(currentPosition, targetPosition);
            armMotor.setPower(power);

            // Show the position of the armMotor on telemetry
            telemetry.addData("Encoder Position", currentPosition);

            // Show the target position of the armMotor on telemetry
            telemetry.addData("Desired Position", targetPosition);

            telemetry.update();
        }
    }
}