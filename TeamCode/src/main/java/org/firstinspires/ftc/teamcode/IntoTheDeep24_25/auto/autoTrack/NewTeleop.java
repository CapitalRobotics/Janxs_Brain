package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.autoTrack;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop.pidMaybe;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.templates.TemplateJanx;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

@TeleOp(name = "NEW TELEOP ---AutoTrack---")
public class NewTeleop extends OpMode {
    private static final int ARM_UP_POSITION = 67;
    private static final int ARM_DOWN_POSITION = 15;

    private DcMotorEx arm, extender;
    private TemplateJanx janx;
    private Servo claw;
    private JSONArray movementLog;
    private ElapsedTime logTimer;

    @Override
    public void init() {
        janx = new TemplateJanx(hardwareMap);
        janx.wheelInit("fr", "br", "bl", "fl");

        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        extender = hardwareMap.get(DcMotorEx.class, "arm2");
        claw = hardwareMap.get(Servo.class, "claw");

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setTargetPosition(ARM_DOWN_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        movementLog = new JSONArray();
        logTimer = new ElapsedTime();
        logTimer.reset();

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        pidMaybe pid = new pidMaybe(0.004, 0.0000, 0.15);
        double power = pid.calculatePower(getPosition(), arm.getCurrentPosition());
        arm.setTargetPosition(getPosition());
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        janx.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        arm.setPower(power);

        controlClaw();
        controlExtender(gamepad2.right_stick_y);

        if (logTimer.milliseconds() > 20) {
            recordMovement();
            logTimer.reset();
        }

        telemetry.addData("Arm Position", arm.getCurrentPosition());
        telemetry.addData("Claw Position", claw.getPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        saveMovementLog();
    }

    private void controlExtender(double input) {
        if (Math.abs(input) > 0.3) {
            extender.setPower(input);
        } else {
            extender.setPower(0);
        }
    }

    private void controlClaw() {
        if (gamepad2.left_trigger != 0) {
            claw.setPosition(1);
        } else if (gamepad2.right_trigger != 0) {
            claw.setPosition(-1);
        }
    }

    private int getPosition() {
        return gamepad2.a ? ARM_DOWN_POSITION : ARM_UP_POSITION;
    }

    private void recordMovement() {
        try {
            JSONObject movement = new JSONObject();
            movement.put("timestamp", System.nanoTime());
            movement.put("frontLeftVelocity", janx.fl.getVelocity());
            movement.put("frontRightVelocity", janx.fr.getVelocity());
            movement.put("backLeftVelocity", janx.bl.getVelocity());
            movement.put("backRightVelocity", janx.br.getVelocity());
            movement.put("armPosition", arm.getCurrentPosition());
            movement.put("clawPosition", claw.getPosition());
            movementLog.put(movement);
        } catch (JSONException e) {
            telemetry.addData("Error", "Failed to record movement: " + e.getMessage());
        }
    }

    private void saveMovementLog() {
        try (FileWriter file = new FileWriter("/sdcard/FIRST/movementLog.json")) {
            file.write(movementLog.toString(4));
            telemetry.addData("Status", "Movements saved to internal storage.");
        } catch (IOException | JSONException e) {
            telemetry.addData("Error", "Failed to save movements: " + e.getMessage());
        }
        telemetry.update();
    }
}