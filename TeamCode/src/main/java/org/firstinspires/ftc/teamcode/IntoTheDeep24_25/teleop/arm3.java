package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import static com.qualcomm.robotcore.util.Range.clip;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.PID2;
import org.firstinspires.ftc.teamcode.TemplateJanx;
import com.qualcomm.robotcore.util.ElapsedTime;
public class arm3 extends OpMode{
    ElapsedTime time = new ElapsedTime();
    int target = 500;
    PID2 pid = new PID2(target);
    public DcMotorEx arm;
    /**
     *  I REALLY DONT KNOW IF ITS GOING TO WORK SO I HIGHLY RECOMMEND PROTECTIVE MEASURES.
     *
     *  xoxo,
     *  gossip girl
     */
    @Override
    public void init(){
        arm = hardwareMap.get(DcMotorEx.class, "extender");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    @Override
    public void loop(){
        arm.setTargetPosition(target);
        double power = pid.returnPower(arm.getCurrentPosition(),time);
        //this number should be getting smaller and smaller as it gets to the target
        telemetry.addData("P",pid.returnP());
        //in theory this one should be adding up a lot if the arm is not moving.
        telemetry.addData("I",pid.returnI());
        //this number should be very very small.
        telemetry.addData("D",pid.returnD());
        telemetry.addData("POWER",power);
      //  arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
        //after 2 seconds of testing it should shut off
        if(time.milliseconds() == 2000){
            arm.setPower(0);
        }
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pid.reset(time);
    }
}
