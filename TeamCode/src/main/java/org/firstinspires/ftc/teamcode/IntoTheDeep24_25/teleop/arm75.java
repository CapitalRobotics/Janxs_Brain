package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.TemplateJanx;


@TeleOp(name = "hi")
public class arm75 extends OpMode{
    private static final int ARM_UP_POSITION = 67;
    private static final int ARM_DOWN_POSITION = 15;
    DcMotorEx arm,extender;
    // DcMotorEx frontLeft,frontRight,backLeft,backRight;
    TemplateJanx janx;
    Servo claw;
    Boolean pos = true;
    @Override
    public void init(){
        janx = new TemplateJanx(hardwareMap);
        janx.wheelInit("fr","br","bl","fl");
        claw = hardwareMap.get(Servo.class,"claw");
//        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        arm.setTargetPosition(ARM_DOWN_POSITION);
//        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    @Override
    public void loop(){
        claw();
       // extend(gamepad2.right_stick_y);
        janx.drive(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
        //arm.setPower(0);

//        telemetry.addData("pos",arm.getCurrentPosition());
//        telemetry.addData("pos",arm.getTargetPosition());
        telemetry.update();

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
