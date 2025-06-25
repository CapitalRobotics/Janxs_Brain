package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop.arm;

import static com.qualcomm.robotcore.util.Range.clip;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.pidMaybe;
import org.firstinspires.ftc.teamcode.TemplateJanx;
@Disabled
public class arm10 extends OpMode {
    private static final int ARM_DOWN_POSITION = 0;
    private static final int LEVEL_1 = 500;
    private static final int LEVEL_2 = 3000;
    DcMotorEx arm, extender,fr,fl;
    ElapsedTime time;
    Servo claw;
    // DcMotorEx frontLeft,frontRight,backLeft,backRight;
    TemplateJanx janx;
    pidMaybe pid;

    @Override
    public void init() {
        time = new ElapsedTime();
        fr = hardwareMap.get(DcMotorEx.class, "fr");
        fl = hardwareMap.get(DcMotorEx.class, "fl");

        fr.setDirection(DcMotor.Direction.FORWARD);
        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fl.setPower(0);
        fr.setPower(0);

        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        extender = hardwareMap.get(DcMotorEx.class, "arm2");
        claw = hardwareMap.get(Servo.class,"claw");
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setTargetPosition(ARM_DOWN_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void loop() {
        arm();
        claw();
        mechanum(gamepad1.left_stick_x,gamepad1.left_stick_y,gamepad1.right_stick_x);
        extend(gamepad2.left_stick_y);
        telemetry.addData("current pos",arm.getCurrentPosition());
        telemetry.addData("target pos",arm.getTargetPosition());
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

    public void mechanum(double LSX, double LSY, double RSX)
    {
        int Speed = 1600;
        double lx = Math.pow(LSX,3);
        double ly = (Math.pow(LSY,3));
        double rx = -Math.pow(RSX,3);
        //is RSX backwards? I may need to fix the canva
        if(LSX != 0 || LSY != 0 || RSX != 0){
            fr.setVelocity(Speed*(clip((-ly)-lx,-1,1)-rx));
            fl.setVelocity(Speed*(clip((ly)+lx,-1,1)+rx));

        }
        else{
            fl.setVelocity(0);
            fr.setVelocity(0);
        }
    }
}
