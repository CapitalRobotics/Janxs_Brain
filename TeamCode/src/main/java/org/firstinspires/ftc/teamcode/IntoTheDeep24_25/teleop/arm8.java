package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.whateverElse;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.pidMaybe;
import org.firstinspires.ftc.teamcode.TemplateJanx;

@TeleOp(name = "teleop.exe")
public class arm8 extends OpMode{
    private static final int ARM_UP_POSITION = 60;
    private static final int ARM_DOWN_POSITION = 20;
    DcMotorEx arm,extender;
    // DcMotorEx frontLeft,frontRight,backLeft,backRight;
    TemplateJanx janx;
    Servo claw;
    Boolean pos = true;
    @Override
    public void init(){
        janx = new TemplateJanx(hardwareMap);
        janx.wheelInit("fr","br","bl","fl");
        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        extender = hardwareMap.get(DcMotorEx.class, "arm2");
        //ctrl hub 0
        claw = hardwareMap.get(Servo.class,"claw");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setTargetPosition(ARM_DOWN_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    @Override
    public void loop(){
        pidMaybe pid = new pidMaybe(0.004,0.000,0.1);
        double power = pid.calculatePower(getPosition(), arm.getCurrentPosition());
        arm.setTargetPosition(getPosition());
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        claw();
        extend(gamepad2.right_stick_y);
        janx.drive(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
        arm.setPower(power);
        telemetry.addData("claw",claw.getPosition());
        telemetry.addData("extend",extender.getPower());

    }

    public void extend(double x){
        if ((Math.abs(x) > 0.3)) {
            extender.setPower(x);
        } else {
            extender.setPower(0);
        }
    }

    public void test(){
        if(gamepad2.y){
            claw.setPosition(1);
        }
        else{
            claw.setPosition(0);
        }
    }
    public void claw(){
        if (gamepad2.left_trigger!=0) {
            claw.setPosition(1);
        } else if (gamepad2.right_trigger!=0) {
            claw.setPosition(-1);
        }
    }
    public int getPosition(){
        int pos = 0;
        if (gamepad2.a) {
            pos = ARM_DOWN_POSITION;
        }
        else{
            pos = ARM_UP_POSITION;
        }
        return pos;
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

