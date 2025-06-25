package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop.arm;

import static com.qualcomm.robotcore.util.Range.clip;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.PID2;

import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@TeleOp(name = "the one that doesn't really work")
public class arm3 extends OpMode{
    ElapsedTime time = new ElapsedTime();
    int target = 89;
    PID2 pid;
    public DcMotorEx arm;
    /**
     *  I REALLY DONT KNOW IF ITS GOING TO WORK SO I HIGHLY RECOMMEND PROTECTIVE MEASURES.
     *
     *  xoxo,
     *  gossip girl
     */
    @Override
    public void init(){
        //the name of this arm may not be extender
        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        arm.setTargetPosition(target);
        pid = new PID2(arm.getTargetPosition());
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    @Override
    public void loop(){
        double power = pid.returnPower(arm.getCurrentPosition(),time);
//        if(arm.getCurrentPosition()>90){
//            arm.setPower(0);
//            pid.reset(time);
              //
//            arm.setPower(power);
//        }
        //this number should be getting smaller and smaller as it gets to the target
        telemetry.addData("P",pid.returnP());
        //in theory this one should be adding up a lot if the arm is not moving.
        telemetry.addData("I",pid.returnI());
        //this number should be very very small.
        telemetry.addData("D",pid.returnD());
        telemetry.addData("power",power);
        telemetry.addData("position",arm.getCurrentPosition());
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(power);
       // pid.reset(time);
    }
}
