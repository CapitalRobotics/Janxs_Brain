package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.whateverElse;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "armTest3")
public class happynewyear extends OpMode{
    private static final int ARM_UP_POSITION = 100;
    private static final int ARM_DOWN_POSITION = 150;
    DcMotorEx arm;
    @Override
    public void init(){
        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setPIDFCoefficients(1);
    }
    @Override
    public void loop(){
        if(gamepad2.a){
            arm.setTargetPosition(ARM_UP_POSITION);
        }
        else if(gamepad2.b){
            arm.setTargetPosition(ARM_DOWN_POSITION);
        }
        else{
            arm.setTargetPosition(arm.getCurrentPosition());
        }
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);

    }
    public void armTestOne(){
        if(gamepad2.a){
            arm.setPower(1);
        }
        else if (gamepad2.b) {
            arm.setPower(-1);
        }
        else{
            arm.setPower(0);
        }
    }

}
