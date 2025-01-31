package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "blue left")
public class BlueLeft extends OpMode {
    autoTemplate2 auto;
    private static final int ARM_DOWN_POSITION = 15;
    private static final int LEVEL_1 = 67;
    private static final int LEVEL_2 = 75;
    public void init()
    {
        auto = new autoTemplate2(hardwareMap);
        auto.armInit("arm1","arm2","claw");
        auto.wheelInit("fr","br","bl","fl");
    }
    public void loop()
    {
        auto.turn(false);
        auto.drive(1600,28,28,4);
        auto.turn(true);
        auto.drive(1600,6,6,4);
        auto.moveArm(ARM_DOWN_POSITION,5);
        auto.claw(false);
        auto.moveArm(LEVEL_1,5);

    }
}
