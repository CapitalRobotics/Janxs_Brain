package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;

import static com.qualcomm.robotcore.util.Range.clip;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.pidMaybe;
import org.firstinspires.ftc.teamcode.TemplateJanx;

@TeleOp(name = "letMeMoveItMoveIt")
public class teleOpFull extends OpMode {
    private DcMotorEx frontRight;
    private DcMotorEx backRight;
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx armMotor;
    private Servo claw;
    // Position of the arm when it's down
    int armUpPosition = 30;

    // Position of the arm when it's lifted
    int armDownPosition = 150;
    // Find a motor in the hardware map named "Arm Motor"

    @Override
    public void init() {
        DcMotorEx armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        Servo claw = hardwareMap.get(Servo.class, "claw");

        // Reset the motor encoder so that it reads zero ticks
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Sets the starting position of the arm to the down position
        armMotor.setTargetPosition(armDownPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        TemplateJanx janx = new TemplateJanx(hardwareMap);
        janx.wheelInit("frontRight","backRight","backLeft","frontLeft");
        frontLeft =  janx.fl;
        frontRight = janx.fr;
        backRight =  janx.br;
        backLeft =   janx.bl;

    }
    @Override
    public void loop() {
        mecanum(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        if (gamepad2.left_trigger!=0) {
            claw.setPosition(1);
        } else if (gamepad2.right_trigger!=0) {
            claw.setPosition(-1);
        }
        // If the A button is pressed, raise the arm
        armMotor.setTargetPosition(getPosition());
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pidMaybe pid = new pidMaybe(0.004, 0, armMotor);
        double power = pid.calculatePower(getPosition(), armMotor.getCurrentPosition());
        armMotor.setPower(power);



        // Show the position of the armMotor on telemetry
        telemetry.addData("Encoder Position", armMotor.getCurrentPosition());
        telemetry.addData("time", pid.getTime());
        telemetry.addData("Power", power);
        // Show the target position of the armMotor on telemetry
        telemetry.addData("Desired Position",armMotor.getTargetPosition());
        telemetry.update();

    }
    private int getPosition(){
        int pos = 0;
        if (gamepad2.a) {
            pos = armDownPosition;
        }
        else{
            pos = armUpPosition;
        }
        return pos;
    }
    private void mecanum(double LSY, double LSX, double RSX) {
        int Speed = 1600;
        double lx = Math.pow(LSX,3);
        double ly = -(Math.pow(LSY,3));
        double rx = Math.pow(RSX,3);
        //is RSX backwards? I may need to fix the canva
        if(LSX != 0 || LSY != 0 || RSX != 0){
            frontRight.setVelocity(Speed*(clip((ly)-lx,-1,1)-rx));
            frontLeft.setVelocity(Speed*(clip((ly)+lx,-1,1)+rx));
            backRight.setVelocity(Speed*(clip((ly)+lx,-1,1)-rx));
            backLeft.setVelocity(Speed*(clip((ly)-lx,-1,1)+rx));
        }
        else{
            frontLeft.setVelocity(0);
            backLeft.setVelocity(0);
            frontRight.setVelocity(0);
            backRight.setVelocity(0);
        }
    }
}
