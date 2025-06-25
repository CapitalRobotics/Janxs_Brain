package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.pidMaybe;
import org.firstinspires.ftc.teamcode.TemplateJanx;

@Disabled
@TeleOp(name = "hi")
public class arm75 extends OpMode{
    private static final int ARM_DOWN_POSITION = 0;
    private static final int LEVEL_1 = 500;
    private static final int LEVEL_2 = 3000;
    DcMotorEx arm;
    // DcMotorEx frontLeft,frontRight,backLeft,backRight;
    TemplateJanx janx;
    Servo claw;
    Boolean pos = true;
    pidMaybe pid;
    ElapsedTime time;

    @Override
    public void init(){
        arm = hardwareMap.get(DcMotorEx.class,"bob");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setTargetPosition(ARM_DOWN_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        time = new ElapsedTime();
    }
    @Override
    public void loop(){
        armTwo();
        telemetry.addData("pos: ", arm.getCurrentPosition());
        telemetry.addData("target: ", arm.getTargetPosition());

    }
    public void armTwo()
    {
        if(gamepad1.a)
        {
            arm.setTargetPosition(LEVEL_1);
        }
        else if(gamepad1.b)
        {
            arm.setTargetPosition(LEVEL_2);
        }
        else if(gamepad1.x)
        {
            arm.setTargetPosition(ARM_DOWN_POSITION);
        }
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
    }
    public void armTest()
    {
        if(Math.abs(gamepad1.left_stick_y)>0.1){
            if(gamepad1.left_stick_y>0){
                arm.setPower(1);
            }
            if(gamepad1.left_stick_y<0){
                arm.setPower(-1);
            }
        }
    }
    public void armOne()
    {
        pid = new pidMaybe(0.003,0.000,0.4);
        double power = pid.calculatePower(positioning(gamepad2.a,gamepad2.b,gamepad2.x), arm.getCurrentPosition(),time.seconds());
        arm.setTargetPosition(positioning(gamepad2.a,gamepad2.b,gamepad2.x));
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
    }
    public int positioning(boolean a, boolean b, boolean x)
    {
        int pos = 0;
        if(a){
            pos = ARM_DOWN_POSITION;
        }
        else if(x){
            pos = LEVEL_2;
        }
        else if(b)
        {
            pos = LEVEL_1;
        }
        return pos;
    }
}
