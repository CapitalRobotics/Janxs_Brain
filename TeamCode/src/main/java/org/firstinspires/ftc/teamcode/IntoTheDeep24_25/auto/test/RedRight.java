package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
public class RedRight extends OpMode {
    autoTemplate2 auto;
    private static final int ARM_DOWN_POSITION = 15;
    private static final int LEVEL_1 = 67;
    private static final int LEVEL_2 = 127;
    private static final int speed = 1600;
    private static final int time = 2;
    public void init()
    {
        auto = new autoTemplate2(hardwareMap);
        auto.armInit("arm1","arm2","claw");
        auto.wheelInit("fr","br","bl","fl");
    }
    public void loop()
    {

    }
    public void turn(boolean right)
    {
        int turn = 33;
        if(right)
        {
            auto.drive(400,-turn,turn,3);
        }
        else
        {
            auto.drive(400,turn,-turn,3);
        }
    }


}
