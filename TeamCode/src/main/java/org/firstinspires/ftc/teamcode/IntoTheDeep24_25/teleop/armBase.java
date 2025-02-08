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
    private static final int ARM_UP_POSITION = 60;
    private static final int ARM_DOWN_POSITION = 150;
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
    }
    @Override
    public void loop(){
        claw();
        extend(gamepad2.right_stick_y);
        //test();
        //janx.drive(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
        setArm();
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(0.5);
        telemetry.addData("claw",claw.getPosition());
        telemetry.addData("extend",extender.getPower());

    }

    public void extend(double x){
//       if(Math.abs(x)>0.1){
//           if(x>0){
//               extender.setPower(1);
//           }
//           else if(x<0){
//               extender.setPower(-1);
//           }
//       }
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
