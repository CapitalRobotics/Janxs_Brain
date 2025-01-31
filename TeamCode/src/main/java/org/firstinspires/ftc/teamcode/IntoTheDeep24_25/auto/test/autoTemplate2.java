package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.test;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.pidMaybe;
import org.firstinspires.ftc.teamcode.TemplateJanx;

public class autoTemplate2 extends TemplateJanx{
    private ElapsedTime runtime = new ElapsedTime();
    static final double COUNTS_PER_MOTOR_REV = 1440;    // 288- core hex motor
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
        pid = new pidMaybe(0.004,0.0000,0.15);
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

    public void drive(double speed, double leftInches, double rightInches, int timeout) {
        int flTarget = fl.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        int blTarget = bl.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
        int brTarget = br.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
        int frTarget = fr.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
        while(runtime.seconds()<timeout)
        {
            fl.setTargetPosition(flTarget);
            fr.setTargetPosition(frTarget);
            bl.setTargetPosition(blTarget);
            br.setTargetPosition(brTarget);

            fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            fr.setPower(speed);
            br.setPower(speed);
            bl.setPower(speed);
            fl.setPower(speed);
        }
        fr.setPower(0);
        br.setPower(0);
        bl.setPower(0);
        fl.setPower(0);
    }

    public void moveArm(int targetPos,int timeout)
    {
        while(runtime.seconds()<timeout)
        {
            arm.setTargetPosition(targetPos);
            double power = pid.calculatePower(targetPos, arm.getCurrentPosition());
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setPower(power);
        }

    }

    public void claw(boolean open)
    {
        if(open)
        {
            claw.setPosition(0);
        }
        if(!open)
        {
            claw.setPosition(0.35);
        }
    }

    public void turn(boolean right)
    {
        int turn = 32;
        if(right)
        {
            drive(1400,turn,-turn,5);
        }
        else
        {
            drive(1400,-turn,turn,5);
        }
    }

//    public int getPosition(int x)
//    {
//        int pos = 0;
//        switch(x)
//        {
//            case(1):
//                pos = ARM_DOWN_POSITION;
//                break;
//            case(2):
//                pos = LEVEL_1;
//                break;
//            case(3):
//                pos = LEVEL_2;
//                break;
//        }
//        return pos;
//    }
}
