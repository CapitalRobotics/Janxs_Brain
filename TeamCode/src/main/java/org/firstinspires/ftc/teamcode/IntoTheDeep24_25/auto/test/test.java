package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class test extends LinearOpMode {
    int speed,sec;
    private static final int ARM_DOWN_POSITION = 15;
    private static final int LEVEL_1 = 67;
    private static final int LEVEL_2 = 127;
    @Override
    public void runOpMode() throws InterruptedException {
        autoTemplate2 janx = new autoTemplate2(hardwareMap);
        janx.wheelInit("fr","br","bl","fl");
        janx.armInit("arm1","arm2","claw");
        speed = 2400;
        sec = 5;
        waitForStart();
        janx.moveArm(LEVEL_2,sec);
        janx.drive(speed,24,24,sec);
        janx.turn(true);
        janx.moveArm(ARM_DOWN_POSITION,sec);
        janx.claw(false);
        janx.moveArm(LEVEL_1,sec);
        janx.claw(true);
        janx.turn(false);
        janx.drive(speed,-24,24,sec);
    }
}
