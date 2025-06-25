package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.pidMaybe;
import org.firstinspires.ftc.teamcode.Template;
import org.firstinspires.ftc.teamcode.TemplateJanx;
@TeleOp(name = "teleop")
public class arm11 extends OpMode {
    private static final int ARM_DOWN_POSITION = 35;
    private static final int LEVEL_1 = 650;
    private static final int LEVEL_2 = 800;
    private static final int HORIZONTAL = 300;
    int flag;
    DcMotorEx arm, extender,ascent;
    ElapsedTime time;
    Servo claw;
    // DcMotorEx frontLeft,frontRight,backLeft,backRight;
    TemplateJanx janx;
    pidMaybe pid;

    @Override
    public void init() {
        janx = new TemplateJanx(hardwareMap);
        time = new ElapsedTime();
        flag = 0;

        janx.wheelInit("fr","br","bl","fl");
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        extender = hardwareMap.get(DcMotorEx.class, "extend");
        claw = hardwareMap.get(Servo.class,"claw");
        ascent = hardwareMap.get(DcMotorEx.class, "ascend");

        ascent.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ascent.setDirection(DcMotor.Direction.REVERSE);
        ascent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ascent.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ascent.setTargetPosition(0);
        ascent.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setTargetPosition(ARM_DOWN_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }

    @Override
    public void loop() {
//        arm();
//       arm1();
        try {
            arm2(gamepad2.x,gamepad2.a,gamepad2.b);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        extend(gamepad2.left_stick_y);
        //move(gamepad1.a);
        move1(gamepad1.a);
        claw();
        janx.mecanum(-gamepad1.left_stick_x,-gamepad1.left_stick_y,gamepad1.right_stick_x);
        telemetry.addData("current pos",arm.getCurrentPosition());
        telemetry.addData("target pos", arm.getTargetPosition());
        telemetry.addData("flag",flag);
    }
    public void move(boolean y)
    {
        if (y) {
            ascent.setPower(1);
        }
        else
        {
            ascent.setPower(0);
        }
    }
    public void move1(boolean y)
    {
        if (y) {
            ascent.setTargetPosition(ascent.getCurrentPosition() + 800);;
        }
        ascent.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        ascent.setPower(1);
       // telemetry.addData("rope pos",ascent.getTargetPosition());
    }


    public void arm()
    {
        if(gamepad2.a)
        {
            arm.setTargetPosition(ARM_DOWN_POSITION);
        }
        else if(gamepad2.b)
        {
            arm.setTargetPosition(LEVEL_2);
        }
        else if(gamepad2.x)
        {
            arm.setTargetPosition(LEVEL_1);
        }
        else
        {
            arm.setTargetPosition(ARM_DOWN_POSITION);
        }
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
    }
    public void arm1()
    {
        arm.setTargetPosition(positioning(gamepad2.a,gamepad2.b,gamepad2.x));
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);
    }
    public void arm2(boolean f,boolean stop, boolean h) throws InterruptedException {
        if(f)
        {
            flag++;
            sleep(250);
        }
        if(stop)
        {
            flag = 0;
        }
        if(h)
        {
            flag = 1;
        }
        if(flag == 0)
        {
            arm.setTargetPosition(ARM_DOWN_POSITION);
        }
        else if(flag == 1)
        {
            arm.setTargetPosition(HORIZONTAL);
        }
        else if(flag%2 == 1)
        {
            arm.setTargetPosition(LEVEL_1);
        }
        else if(flag%2 == 0)
        {
            arm.setTargetPosition(LEVEL_2);
        }
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
    public void extend(double y){
        if ((Math.abs(y) > 0.3)) {
            extender.setPower(-y);
        } else {
            extender.setPower(0);
        }
    }
}
