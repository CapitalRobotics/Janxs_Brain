package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name = "park")
public class park extends OpMode {
    autoTemplate2 auto;
    private static final int ARM_DOWN_POSITION = 15;
    private static final int LEVEL_1 = 67;
    private static final int LEVEL_2 = 127;
    private static final int speed = 1600;
    ElapsedTime time;

    private static final int tolerance = 3;

    public void init()
    {
        time = new ElapsedTime();
        auto = new autoTemplate2(hardwareMap);
        auto.armInit("arm1","arm2","claw");
        auto.wheelInit("fr","br","bl","fl");
        auto.createTargets(70,70);
    }
    public void loop()
    {
        auto.moveArm(LEVEL_2);
        if(time.seconds()<2) {
            auto.drive(speed, 6, 6);
        }
        telemetry.addData("current",auto.current());
        telemetry.addData("target",auto.target());
        stop();
    }
//    public void turn(boolean right)
//    {
//        int turn = 33;
//        if(right)
//        {
//            auto.drive(400);
//        }
//        else
//        {
//            auto.drive(400);
//        }
//    }


}
