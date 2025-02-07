package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TemplateJanx;

public class arm10 extends OpMode {
    private static final int ARM_UP_POSITION = 67;
    private static final int ARM_DOWN_POSITION = 15;
    private static final int LEVEL_1 = 67;
    private static final int LEVEL_2 = 127;
    DcMotorEx arm, extender;
    boolean flag;
    // DcMotorEx frontLeft,frontRight,backLeft,backRight;
    TemplateJanx janx;
    Servo claw;

    @Override
    public void init() {
        janx = new TemplateJanx(hardwareMap);
        janx.wheelInit("fr", "br", "bl", "fl");
        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        extender = hardwareMap.get(DcMotorEx.class, "arm2");
        flag = false;
        claw = hardwareMap.get(Servo.class, "claw");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setTargetPosition(ARM_DOWN_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        arm.setPower(gamepad2.right_stick_y);
        //we could also set hte target to current + x.
        telemetry.addData("current pos",arm.getCurrentPosition());
        telemetry.addData("target pos",arm.getTargetPosition());
    }
}
