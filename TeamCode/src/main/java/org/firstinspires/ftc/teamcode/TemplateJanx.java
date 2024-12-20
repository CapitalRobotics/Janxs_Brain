package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TemplateJanx {
    HardwareMap hwMap = null;

    public DcMotorEx fr = null;
    public DcMotorEx br = null;
    public DcMotorEx fl = null;
    public DcMotorEx bl = null;

    public Servo claw;
    public Servo lc;
    public Servo rc;

    public DcMotorEx arm;
    public DcMotorEx elbow;

    //clockwise from front right
    public TemplateJanx(HardwareMap h) {
        hwMap = h;
    }

    public void wheelInit(String frontRight, String backRight, String backleft, String frontLeft) {
        //when you make this in teleop or autonomous, you can import hardware.DcMotor with the keyword hardwareMap
        fr = hwMap.get(DcMotorEx.class, frontRight);
        br = hwMap.get(DcMotorEx.class, backRight);
        fl = hwMap.get(DcMotorEx.class, frontLeft);
        bl = hwMap.get(DcMotorEx.class, backleft);

        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); //should it be brake?
        bl.setPower(0);
        fl.setPower(0);
        br.setPower(0);
        fr.setPower(0);


    }


    public void basicClaw(String armMotor, String clawMotor){
        arm = hwMap.get(DcMotorEx.class,armMotor);
        claw = hwMap.get(Servo.class,clawMotor);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm. setDirection(DcMotor.Direction.FORWARD);
    }

    //this is the initialization for an elbow arm with one servo claw
    public void elbow(String armMotor, String elbowMotor, String clawMotor){
        arm = hwMap.get(DcMotorEx.class,armMotor);
        elbow = hwMap.get(DcMotorEx.class, elbowMotor);
        claw = hwMap.get(Servo.class,clawMotor);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elbow.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elbow.setDirection(DcMotor.Direction.FORWARD);

    }

    //this is the initialization for an arm with a double servo claw
    public void doubleServo(String armMotor, String lcMotor, String rcMotor){
        arm = hwMap.get(DcMotorEx.class,armMotor);
        lc = hwMap.get(Servo.class,lcMotor);
        rc = hwMap.get(Servo.class,rcMotor);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setDirection(DcMotor.Direction.FORWARD);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void telscoping(){

    }

}

 /*    public void clawInit(String leftClaw, String rightClaw, String nodder) {
            nod = hwMap.get(DcMotorEx.class, nodder);
            nod.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            nod.setDirection(DcMotor.Direction.FORWARD);
            lc = hwMap.get(Servo.class, leftClaw);
            rc = hwMap.get(Servo.class, rightClaw);
        }

        public void armInit(String armextend, String armTurn, String left, String right) {
            sl = hwMap.get(DcMotorEx.class, left);
            sr = hwMap.get(DcMotorEx.class, right);
            turn = hwMap.get(DcMotorEx.class, armTurn);

            turn.setDirection(DcMotor.Direction.FORWARD);
            turn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            turn.setPower(0);

            sl.setDirection(DcMotor.Direction.FORWARD);
            sl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            sl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            sl.setPower(0);

            sr.setDirection(DcMotor.Direction.REVERSE);
            sr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            sr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            sr.setPower(0);
    }*/
