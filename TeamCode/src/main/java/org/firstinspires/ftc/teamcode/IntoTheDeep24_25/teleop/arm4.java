package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import com.arcrobotics.ftclib.controller.wpilibcontroller.ArmFeedforward;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.PID2;

@TeleOp(name = "the one with ftclib")
public class arm4 extends OpMode{
    DcMotorEx arm;
    ArmFeedforward ff;
    int target = 60;
    @Override
    public void init(){
       ff = new ArmFeedforward(3,10,5,2);
        //the name of this arm may not be extender
        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        arm.setTargetPosition(target);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    @Override
    public void loop(){
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(ff.calculate(60,20,2));


    }
}
