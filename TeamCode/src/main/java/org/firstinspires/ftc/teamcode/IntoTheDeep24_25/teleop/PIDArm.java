package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class PIDArm extends LinearOpMode {
    // Position of the arm when it's down
    // These are inverted
    int armUpPosition = 10;

    // Position of the arm when it's lifted
    int armDownPosition = 150;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx armMotor =  hardwareMap.get(DcMotorEx.class, "arm");

        // Reset the motor encoder so that it reads zero ticks
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Sets the starting position of the arm to the down position
        armMotor.setTargetPosition(armDownPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        while (opModeIsActive()) {
            // If the A button is pressed, raise the arm
            armMotor.setTargetPosition(getPosition());
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            pidMaybe pid = new pidMaybe(0.004, 0, armMotor); // these need fixing
            double power = pid.calculatePower(getPosition(), armMotor.getCurrentPosition());
            armMotor.setPower(power);

            // Get the current position of the armMotor
            double position = armMotor.getCurrentPosition();

            // Get the target position of the armMotor
            double desiredPosition = armMotor.getTargetPosition();

            // Show the position of the armMotor on telemetry
            telemetry.addData("Encoder Position", position);
            telemetry.addData("time", pid.getTime());
            telemetry.addData("Power", power);
            // Show the target position of the armMotor on telemetry
            telemetry.addData("Desired Position", desiredPosition);

            telemetry.update();
        }
    }

    public int getPosition(){
        int pos = 0;
        if (gamepad2.a) {
            pos = armDownPosition;
        } else {
            pos = armUpPosition;
        }
        return pos;
    }
}