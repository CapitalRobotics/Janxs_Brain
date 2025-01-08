package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.whateverElse;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TemplateJanx;

@TeleOp(name = "armTest3")
public class armBase extends OpMode{
    private static final int ARM_UP_POSITION = 100;
    private static final int ARM_DOWN_POSITION = 150;
    DcMotorEx arm;
    DcMotorEx rotator;
    TemplateJanx janx;
    Servo claw;
    Boolean pos = true;
    @Override
    public void init(){
        janx = new TemplateJanx(hardwareMap);
        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        arm = hardwareMap.get(DcMotorEx.class, "arm2");
        //ctrl hub 0
        claw = hardwareMap.get(Servo.class,"claw");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    @Override
    public void loop(){
        claw(gamepad2.x);
        janx.drive(gamepad2.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
       setArm();
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);

    }

    public void claw(boolean flag){
        if(flag){
            pos = !pos;
        }
        if(pos){
            claw.setPosition(1);
        }
        if(!pos){
            claw.setPosition(-1);
        }
    }
    public void setArm(){
        if(gamepad2.a){
            arm.setTargetPosition(ARM_UP_POSITION);
        }
        else if(gamepad2.b){
            arm.setTargetPosition(ARM_DOWN_POSITION);
        }
        else{
            arm.setTargetPosition(arm.getCurrentPosition());
        }
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
