package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "x")
public class all extends OpMode {
    int speed,sec;
    autoTemplate2 janx;
    private static final int ARM_DOWN_POSITION = 15;
    private static final int LEVEL_1 = 67;
    private static final int LEVEL_2 = 127;
        public void init()
        {
            janx = new autoTemplate2(hardwareMap);
            janx.armInit("arm1","arm2","claw");
            janx.wheelInit("fr","br","bl","fl");

        }
        public void loop() {
            speed = 2400;
            sec = 3;
            telemetry.addData("power",janx.fl.getPower());
           // janx.moveArm(LEVEL_1,sec);
            janx.drive(speed,24,24,sec);
            janx.turn(true);
            janx.moveArm(ARM_DOWN_POSITION);
            janx.claw(false);
            janx.moveArm(LEVEL_2);
            janx.claw(true);
            janx.turn(false);
            janx.drive(speed,-24,24,sec);
            telemetry.update();
        }
        public void stop()
        {
            janx.fl.setPower(0);
            janx.fr.setPower(0);
            janx.bl.setPower(0);
            janx.br.setPower(0);
        }

}
