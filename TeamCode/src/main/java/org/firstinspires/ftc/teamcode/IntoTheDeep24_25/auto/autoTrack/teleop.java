package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.autoTrack;

import static com.qualcomm.robotcore.util.Range.clip;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.TemplateJanx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import java.io.OutputStream;
import java.io.IOException;

@Disabled
@TeleOp(name = "AUTO TRACK --- TeleOp")
public class teleop extends OpMode {
    private DcMotorEx frontRight, backRight, frontLeft, backLeft, armMotor;
    private Servo claw;
    private JSONArray movementLog;
    private ElapsedTime logTimer;

    private final int armUpPosition = 20;
    private final int armDownPosition = 150;
    private boolean armFlag = false;
    private boolean lastAState = false;

    // USB content URI
    private Uri usbUri;

    @Override
    public void init() {
        TemplateJanx janx = new TemplateJanx(hardwareMap);
        janx.wheelInit("frontRight", "backRight", "backLeft", "frontLeft");
        frontLeft = janx.fl;
        frontRight = janx.fr;
        backRight = janx.br;
        backLeft = janx.bl;

        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        claw = hardwareMap.get(Servo.class, "claw");

        armMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(armDownPosition);
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        movementLog = new JSONArray();
        logTimer = new ElapsedTime();
        logTimer.reset();

        //idk if this is right
        usbUri = Uri.parse("content://com.android.externalstorage.documents/document/B1BE-1722");

        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        mecanumDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        controlClaw();
        controlArm();

        if (logTimer.milliseconds() > 20) {
            recordMovement();
            logTimer.reset();
        }

        telemetry.addData("Arm Position", armMotor.getCurrentPosition());
        telemetry.addData("Claw Position", claw.getPosition());
        telemetry.update();
    }

    @Override
    public void stop() {
        saveMovementLog();
    }

    private void mecanumDrive(double LSY, double LSX, double RSX) {
        int speed = 1600;
        double lx = Math.pow(LSX, 3);
        double ly = -Math.pow(LSY, 3);
        double rx = -Math.pow(RSX, 3);

        if (LSX != 0 || LSY != 0 || RSX != 0) {
            frontRight.setVelocity(speed * (clip((ly) - lx, -1, 1) - rx));
            frontLeft.setVelocity(speed * (clip((ly) + lx, -1, 1) + rx));
            backRight.setVelocity(speed * (clip((ly) + lx, -1, 1) - rx));
            backLeft.setVelocity(speed * (clip((ly) - lx, -1, 1) + rx));
        } else {
            frontLeft.setVelocity(0);
            backLeft.setVelocity(0);
            frontRight.setVelocity(0);
            backRight.setVelocity(0);
        }
    }

    private void controlClaw() {
        if (gamepad2.left_trigger != 0) {
            claw.setPosition(1);
        } else if (gamepad2.right_trigger != 0) {
            claw.setPosition(-1);
        }
    }

    private void controlArm() {
        if (gamepad2.a && !lastAState) {
            armFlag = !armFlag;
        }
        lastAState = gamepad2.a;

        int targetPosition = armFlag ? armDownPosition : armUpPosition;
        armMotor.setTargetPosition(targetPosition);
        armMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        armMotor.setPower(0.5);
    }

    private void recordMovement() {
        try {
            JSONObject movement = new JSONObject();
            movement.put("timestamp", System.nanoTime()); // High-resolution timestamp
            movement.put("frontLeftVelocity", frontLeft.getVelocity());
            movement.put("frontRightVelocity", frontRight.getVelocity());
            movement.put("backLeftVelocity", backLeft.getVelocity());
            movement.put("backRightVelocity", backRight.getVelocity());
            movement.put("armPosition", armMotor.getCurrentPosition());
            movement.put("clawPosition", claw.getPosition());
            movementLog.put(movement);
        } catch (Exception e) {
            telemetry.addData("Error", "Failed to record movement: " + e.getMessage());
        }
    }

    private void saveMovementLog() {
        if (usbUri == null) {
            telemetry.addData("Error", "USB URI is not set.");
            telemetry.update();
            return;
        }

        try {
            Context context = hardwareMap.appContext;
            ContentResolver resolver = context.getContentResolver();
            OutputStream outputStream = resolver.openOutputStream(usbUri);

            if (outputStream != null) {
                outputStream.write(movementLog.toString(4).getBytes());
                outputStream.close();
                telemetry.addData("Status", "Movements saved to USB drive.");
            } else {
                telemetry.addData("Error", "Failed to open OutputStream.");
            }
        } catch (IOException | JSONException e) {
            telemetry.addData("Error", "Failed to save movements: " + e.getMessage());
        }
        telemetry.update();
    }
}
