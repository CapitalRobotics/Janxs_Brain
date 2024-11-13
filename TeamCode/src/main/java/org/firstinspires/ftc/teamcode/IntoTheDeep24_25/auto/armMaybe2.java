package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class armMaybe2 extends LinearOpMode {
    // Position of the arm when it's down
    int armUpPosition = 30;

    // Position of the arm when it's lifted
    int armDownPosition = 150;
    // Find a motor in the hardware map named "Arm Motor"
    DcMotorEx armMotor = hardwareMap.get(DcMotorEx.class, "arm");
    Servo claw = hardwareMap.get(Servo.class, "bob");

    @Override
    public void runOpMode() throws InterruptedException {


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
            pidMaybe pid = new pidMaybe(0.004, 0, armMotor);
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
    public void claw() {
        if (gamepad2.left_bumper) {
            claw.setPosition(1);
        } else if (gamepad2.right_bumper) {
            claw.setPosition(-1);
        }
    }
        public int getPosition(){
            int pos = 0;
            if (gamepad2.a) {
                pos = armUpPosition;
            }

            // If the B button is pressed, lower the arm
            if (gamepad2.b) {
               pos = armDownPosition;
            }
            return pos;
        }
    }

