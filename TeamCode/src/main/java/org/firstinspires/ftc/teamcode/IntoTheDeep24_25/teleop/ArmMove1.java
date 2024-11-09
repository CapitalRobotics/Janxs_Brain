//package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//
//@TeleOp(name = "ArmMove1.5 with PID")
//public class ArmMove1 extends LinearOpMode {
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        // Define ar/**/m boundaries
//        int armUpPosition = 30;
//        int armDownPosition = 150;
//
//        // Initialize motor
//        DcMotorEx armMotor = hardwareMap.get(DcMotorEx.class, "arm");
//        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Using encoder for continuous control
//
//        // Initialize PID controller with chosen kP, kI, kD values
//        PIDTemplate pid = new PIDTemplate(0.1, 0.01, 0.005);  // Example values, adjust as needed
//        double targetPosition = armDownPosition;  // Default start position
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            // Set the target position based on gamepad input
//            if (gamepad2.a) {
//                targetPosition = armUpPosition;  // Raise the arm
//            } else if (gamepad2.b) {
//                targetPosition = armDownPosition;  // Lower the arm
//            }
//            pid.setSetpoint(targetPosition);  // Set the desired position for the PID controller
//
//            // Calculate PID output to control motor power
//            int currentPosition = armMotor.getCurrentPosition();
//            double command = pid.update(targetPosition,currentPosition);
//
//            // Set the motor power based on the PID output
//            armMotor.setPower(0.5);
//
//            // Telemetry for monitoring
//            telemetry.addData("Current Position", currentPosition);
//            telemetry.addData("Target Position", targetPosition);
//            telemetry.addData("Motor Power (PID Output)", pidOutput);
//            telemetry.update();
//        }
//    }
//}