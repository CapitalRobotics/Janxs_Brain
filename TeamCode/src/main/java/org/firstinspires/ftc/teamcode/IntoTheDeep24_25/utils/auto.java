package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TemplateJanx;

public class auto {
    HardwareMap h = null;
    DcMotorEx fr,fl,br,bl;
    DcMotorEx arm;
    Servo claw;
    public auto(HardwareMap hwMap){
        TemplateJanx janx = new TemplateJanx(hwMap);
        janx.wheelInit("frontRight","backRight","backLeft","frontLeft");
        fl =  janx.fl;
        fr = janx.fr;
        br =  janx.br;
        bl =   janx.bl;
        DcMotorEx armMotor = h.get(DcMotorEx.class, "arm");
        Servo claw = h.get(Servo.class, "claw");

        // Reset the motor encoder so that it reads zero ticks
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Sets the starting position of the arm to the down position
        armMotor.setTargetPosition(2);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }


}
