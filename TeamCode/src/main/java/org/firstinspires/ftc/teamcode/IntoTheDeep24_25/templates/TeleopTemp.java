package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.templates;

import static com.qualcomm.robotcore.util.Range.clip;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.pidMaybe;
import org.firstinspires.ftc.teamcode.TemplateJanx;
@Disabled
public class TeleopTemp extends OpMode {
    // Drivetrain motors
    private DcMotorEx frontRight;
    private DcMotorEx backRight;
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;

    // Arm and claw
    private DcMotorEx armMotor;
    private Servo claw;

    // Arm positions
    private final int armUpPosition = 20;
    private final int armDownPosition = 150;
    private boolean armFlag = false;

    // Speed constant
    private final int Speed = 1600;

    @Override
    public void init() {
        // Initialize arm and claw
        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        // Reset the arm motor encoder
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Initialize drivetrain motors using TemplateJanx
        TemplateJanx janx = new TemplateJanx(hardwareMap);
        janx.wheelInit("frontRight", "backRight", "backLeft", "frontLeft");
        frontLeft = janx.fl;
        frontRight = janx.fr;
        backRight = janx.br;
        backLeft = janx.bl;

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Drivetrain control
        mecanum(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        // Arm and claw control
        armControl();
        clawControl();

        // Telemetry for debugging
        telemetry.addData("Arm Position", armMotor.getCurrentPosition());
        telemetry.addData("Target Position", armMotor.getTargetPosition());
        telemetry.addData("Claw Position", claw.getPosition());
        telemetry.update();
    }

    private void mecanum(double LSY, double LSX, double RSX) {
        // Cubed inputs for fine control
        double lx = Math.pow(LSX, 3);
        double ly = -Math.pow(LSY, 3);
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
        frontLeft.setVelocity(0);
        backLeft.setVelocity(0);
        frontRight.setVelocity(0);
        backRight.setVelocity(0);
    }

    private void armControl() {
        // Toggle arm position with gamepad2 A button
        if (gamepad2.a && !lastAState) {
            armFlag = !armFlag;
        }
        lastAState = gamepad2.a;

        int targetPosition = armFlag ? armDownPosition : armUpPosition;
        armMotor.setTargetPosition(targetPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // PID control for arm motor
        pidMaybe pid = new pidMaybe(0.004, 0, armMotor);
        double power = pid.calculatePower(targetPosition, armMotor.getCurrentPosition());
        armMotor.setPower(power);
    }

    private void clawControl() {
        // Open or close claw with triggers
        if (gamepad2.left_trigger > 0) {
            claw.setPosition(1); // Close claw
        } else if (gamepad2.right_trigger > 0) {
            claw.setPosition(-1); // Open claw
        }
    }

    // State tracking for toggle logic
    private boolean lastAState = false;
}