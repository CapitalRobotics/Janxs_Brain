package org.firstinspires.ftc.teamcode.IntoTheDeep24_25;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TemplateJanx;
public class TemplateJanx2024 extends TemplateJanx {
    HardwareMap hwMap = null;
    public DcMotorEx arm;
    public DcMotorEx rotator;
    public TemplateJanx2024(HardwareMap h) {
        super(h);
        hwMap = h;
    }

    public void carl(String armMotor, String rotatorMotor){
        arm = hwMap.get(DcMotorEx.class,armMotor);
        rotator = hwMap.get(DcMotorEx.class, rotatorMotor);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotator.setDirection(DcMotor.Direction.FORWARD);
    }

}
