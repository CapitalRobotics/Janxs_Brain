package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.autoTrack;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TemplateJanx;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;

@Autonomous(name = "NEW AUTO --- AutoTrack")
public class NewAuto extends com.qualcomm.robotcore.eventloop.opmode.LinearOpMode {
    private DcMotorEx frontRight, backRight, frontLeft, backLeft, arm, extender;
    private Servo claw;

    @Override
    public void runOpMode() throws InterruptedException {
        TemplateJanx janx = new TemplateJanx(hardwareMap);
        janx.wheelInit("fr", "br", "bl", "fl");
        frontLeft = janx.fl;
        frontRight = janx.fr;
        backRight = janx.br;
        backLeft = janx.bl;

        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        extender = hardwareMap.get(DcMotorEx.class, "arm2");
        claw = hardwareMap.get(Servo.class, "claw");

        telemetry.addData("Status", "Initialized. Waiting for start...");
        telemetry.update();

        waitForStart();

        JSONArray movementLog = loadMovementLog();
        if (movementLog == null) {
            telemetry.addData("Error", "Failed to load movement log.");
            telemetry.update();
            return;
        }

        telemetry.addData("Status", "Replaying movements...");
        telemetry.update();

        for (int i = 0; i < movementLog.length() && opModeIsActive(); i++) {
            try {
                JSONObject movement = movementLog.getJSONObject(i);

                frontLeft.setVelocity(movement.getDouble("frontLeftVelocity"));
                frontRight.setVelocity(movement.getDouble("frontRightVelocity"));
                backLeft.setVelocity(movement.getDouble("backLeftVelocity"));
                backRight.setVelocity(movement.getDouble("backRightVelocity"));

                arm.setTargetPosition(movement.getInt("armPosition"));
                arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
                arm.setPower(0.5);

                extender.setPower(movement.optDouble("extenderPower", 0));
                claw.setPosition(movement.getDouble("clawPosition"));

                Thread.sleep(50); // Adjust delay as needed
            } catch (Exception e) {
                telemetry.addData("Error", "Failed to replay movement at index " + i);
                telemetry.update();
            }
        }

        stopMotors();
        telemetry.addData("Status", "Replay complete!");
        telemetry.update();
    }

    private JSONArray loadMovementLog() {
        try (FileReader file = new FileReader("/sdcard/FIRST/movementLog.json")) {
            StringBuilder jsonBuilder = new StringBuilder();
            int character;
            while ((character = file.read()) != -1) {
                jsonBuilder.append((char) character);
            }
            return new JSONArray(jsonBuilder.toString());
        } catch (Exception e) {
            telemetry.addData("Error", "Failed to load JSON: " + e.getMessage());
            telemetry.update();
            return null;
        }
    }

    private void stopMotors() {
        frontLeft.setVelocity(0);
        backLeft.setVelocity(0);
        frontRight.setVelocity(0);
        backRight.setVelocity(0);
        arm.setPower(0);
        extender.setPower(0);
    }
}
