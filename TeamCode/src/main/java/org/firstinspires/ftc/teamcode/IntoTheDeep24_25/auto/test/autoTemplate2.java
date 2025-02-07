package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.test;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.pidMaybe;
import org.firstinspires.ftc.teamcode.TemplateJanx;

@Disabled
public class autoTemplate2 extends TemplateJanx{
    private ElapsedTime runtime = new ElapsedTime();
    static final double COUNTS_PER_MOTOR_REV = 288;    // 288- core hex motor
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    pidMaybe pid;
    HardwareMap h;
    DcMotorEx extender, arm;
    Servo claw;

    public autoTemplate2(HardwareMap h) {
        super(h);
        this.h = h;
        runtime.reset();
        pid = new pidMaybe(0.004,0.0001,0.15);
    }

    public void armInit(String a, String e, String c) {
        arm = h.get(DcMotorEx.class, a);
        claw = h.get(Servo.class, c);
        extender = h.get(DcMotorEx.class, e);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setTargetPosition(15);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void wheelInit(String frontRight, String backRight, String backleft, String frontLeft) {
        super.wheelInit(frontRight, backRight, backleft, frontLeft);
        fr.setTargetPosition(fr.getCurrentPosition());
        fl.setTargetPosition(fl.getCurrentPosition());
        br.setTargetPosition(br.getCurrentPosition());
        bl.setTargetPosition(bl.getCurrentPosition());
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void drive(double speed, double leftInches, double rightInches) {
        int flTarget = fl.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        int blTarget = bl.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        int brTarget = br.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
        int frTarget = fr.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);

            fl.setTargetPosition(flTarget);
            fr.setTargetPosition(frTarget);
            bl.setTargetPosition(blTarget);
            br.setTargetPosition(brTarget);

            fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            fr.setPower(1);
            br.setPower(1);
            bl.setPower(1);
            fl.setPower(1);
        sleep(250);
    }

    public void stop()
    {
        fr.setPower(0);
        br.setPower(0);
        bl.setPower(0);
        fl.setPower(0);
    }
    public void turn(boolean right)
    {
        int turn = 33;
        if(right)
        {
            drive(400,-turn,turn,3);
        }
        else
        {
            drive(400,turn,-turn,3);
        }
    }

    public void moveArm(int targetPos)
    {
     arm.setTargetPosition(targetPos);
     //double power = pid.calculatePower(targetPos, arm.getCurrentPosition(),time);
     arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     arm.setPower(1);
     // arm.setPower(power);
    }

    public void open()
    {
        while(runtime.seconds()<2) {
            claw.setPosition(0);
        }
    }
    public void close()
    {
        while(runtime.seconds()<2) {
            claw.setPosition(1);
        }
    }
    public void claw(boolean open,int time)
    {
        while(runtime.seconds()<time) {
            if (open) {
                claw.setPosition(0);
            }
            if (!open) {
                claw.setPosition(1);
            }
        }
    }


    /*
    int flTarget = 0,brTarget = 0,frTarget = 0,blTarget = 0;
        int turn = 33;
        if(right)
        {
            flTarget = fl.getCurrentPosition() + (int) (-turn * COUNTS_PER_INCH);
            blTarget = bl.getCurrentPosition() + (int) (-turn * COUNTS_PER_INCH);
            brTarget = br.getCurrentPosition() + (int) (turn * COUNTS_PER_INCH);
            frTarget = fr.getCurrentPosition() + (int) (turn * COUNTS_PER_INCH);
        }
        if(!right)
        {
            flTarget = fl.getCurrentPosition() + (int) (turn * COUNTS_PER_INCH);
            blTarget = bl.getCurrentPosition() + (int) (turn * COUNTS_PER_INCH);
            brTarget = br.getCurrentPosition() + (int) (-turn * COUNTS_PER_INCH);
            frTarget = fr.getCurrentPosition() + (int) (-turn * COUNTS_PER_INCH);
        }

        while(runtime.seconds()<6)
        {
            fl.setTargetPosition(flTarget);
            fr.setTargetPosition(frTarget);
            bl.setTargetPosition(blTarget);
            br.setTargetPosition(brTarget);

            fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            fr.setPower(1);
            br.setPower(1);
            bl.setPower(1);
            fl.setPower(1);
        }
        fr.setPower(0);
        br.setPower(0);
        bl.setPower(0);
        fl.setPower(0);
     */
    public void strafe(boolean left)
    {
        if(left)
        {

        }
    }
}
