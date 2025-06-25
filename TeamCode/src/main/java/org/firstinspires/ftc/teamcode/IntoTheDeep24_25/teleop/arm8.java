package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.pidMaybe;
import org.firstinspires.ftc.teamcode.TemplateJanx;

@Disabled
@TeleOp(name = "teleop.exe")
public class arm8 extends OpMode{
    private static final int ARM_UP_POSITION = 67;
    private static final int ARM_DOWN_POSITION = 0;
    private static final int LEVEL_1 = 900;
    private static final int LEVEL_2 = 1700;
    DcMotorEx arm,extender,ascent;
    ElapsedTime time;
    pidMaybe pid;
    // DcMotorEx frontLeft,frontRight,backLeft,backRight;
    TemplateJanx janx;
    Servo claw;
    int x;
    @Override
    public void init(){
        time = new ElapsedTime();
        janx = new TemplateJanx(hardwareMap);

        janx.wheelInit("fr","br","bl","fl");
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        extender = hardwareMap.get(DcMotorEx.class, "extend");
        ascent = hardwareMap.get(DcMotorEx.class, "ascend");
        claw = hardwareMap.get(Servo.class,"claw");

        arm.setDirection(DcMotor.Direction.REVERSE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ascent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setTargetPosition(ARM_DOWN_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    @Override
    public void loop(){
        arm();
        claw();
        extend(gamepad2.left_stick_y);
        move(gamepad2.right_stick_y);
        janx.mecanum(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);

        telemetry.addData("current",arm.getCurrentPosition());
        telemetry.addData("target",arm.getTargetPosition());
//        telemetry.addData("PID",pid.coeff());
        telemetry.update();

    }
    public void arm()
    {
        pid = new pidMaybe(0.003,0.000,0.4);
        double power = pid.calculatePower(positioning(gamepad2.a,gamepad2.b,gamepad2.x), arm.getCurrentPosition(),time.seconds());
        arm.setTargetPosition(positioning(gamepad2.a,gamepad2.b,gamepad2.x));
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(power);
    }

    public void extend(double x){
        if ((Math.abs(x) > 0.3)) {
            extender.setPower(-x);
        } else {
            extender.setPower(0);
        }
    }
    public void claw(){
        //0.35
        if (gamepad2.left_trigger!=0) {
            //close
            claw.setPosition(1);
        } else if (gamepad2.right_trigger!=0) {
            //open
            claw.setPosition(0.25);
        }
    }
    public void move(double y)
    {
        if ((Math.abs(y) > 0.3)) {
            ascent.setPower(y);
        } else {
            ascent.setPower(0);
        }

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

//    public void test(){
//        if(gamepad2.y){
//            //0.35
//            claw.setPosition(1);
//        }
//        else{
//            claw.setPosition(0);
//        }
//    }
//    public int position(int x)
//    {
//        /**
//         *  if(gamepad2.x)
//         *         {
//         *             flag = !flag;
//         *             if(flag)
//         *             {
//         *                x++;
//         *                if(x>10)
//         *                {
//         *                  x = 3;
//         *                }
//         *             }
//         *         }
//         */
//        int pos = 0;
//        switch(x)
//        {
//            case(1):
//                pos = ARM_DOWN_POSITION;
//                break;
//            case(3):
//                pos = LEVEL_1;
//                break;
//            case(9):
//                pos = LEVEL_2;
//                break;
//        }
//        return pos;
//    }
//
//    public void setArm(){
//        if(gamepad2.a){
//            arm.setTargetPosition(ARM_UP_POSITION);
//        }
//        else if(gamepad2.b){
//            arm.setTargetPosition(ARM_DOWN_POSITION);
//        }
//        else{
//            arm.setTargetPosition(arm.getCurrentPosition());
//        }
//    }
//    public int getPosition(){
//        int pos = 0;
//        if (gamepad2.a) {
//            pos = ARM_DOWN_POSITION;
//        }
//        else{
//            pos = ARM_UP_POSITION;
//        }
//        return pos;
//    }
//
//
//    public void armTestOne(){
//        if(gamepad2.a){
//            arm.setPower(1);
//        }
//        else if (gamepad2.b) {
//            arm.setPower(-1);
//        }
//        else{
//            arm.setPower(0);
//        }
//    }
}