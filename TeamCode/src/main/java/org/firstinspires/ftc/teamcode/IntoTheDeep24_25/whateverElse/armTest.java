package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.whateverElse;

import static com.qualcomm.robotcore.util.Range.clip;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.TemplateJanx2024;

/**
 * both on gamepad 2 (start +b!)
 left stick controls arm. right stick controls rotation.
 */

@TeleOp(name = "armTest")
public class  armTest extends LinearOpMode {
    private DcMotorEx arm; //this is port 0.
    private DcMotorEx rotator; //this guy is port 2, moves it up and down

    @Override
    public void runOpMode() throws InterruptedException {
        TemplateJanx2024 janx = new TemplateJanx2024(hardwareMap);
        //janx. janx.wheelInit("frontRight", "backRight", "backLeft", "frontLeft");
        janx.carl("rotator","extender");
        arm = janx.arm;
        rotator = janx.rotator;
        waitForStart();

        while (opModeIsActive()) {
            armTest(gamepad2.left_stick_y,gamepad2.right_stick_y);

        }
    }
    public void armTest(double LSY, double RSY){
        if(LSY>0){
            arm.setPower(1);
        }
        else if(LSY<0){
            arm.setPower(1);
        }
        else{
            arm.setPower(0);
        }
        if(RSY>0){
            rotator.setPower(1);
        }
        else if(RSY<0){
            rotator.setPower(1);
        }
        else{
            rotator.setPower(0);
        }

    }
}
