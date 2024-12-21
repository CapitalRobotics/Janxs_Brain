package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TemplateJanx;

public class RobotController {
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    public RobotController(HardwareMap hardwareMap) {
        TemplateJanx janx = new TemplateJanx(hardwareMap);
        janx.wheelInit("frontRight", "backRight", "backLeft", "frontLeft");
        frontLeft = janx.fl;
        frontRight = janx.fr;
        backLeft = janx.bl;
        backRight = janx.br;
    }

    public void moveForward(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    public void stop() {
        moveForward(0);
    }
}
