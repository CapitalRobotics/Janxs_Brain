package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous
public class arm_action extends OpMode {
    autoTemplate2 auto;
    private static final int ARM_DOWN_POSITION = 15;
    private static final int LEVEL_1 = 67;
    private static final int LEVEL_2 = 127;
    private static final int speed = 1600;
    ElapsedTime time;
    public void init()
    {
        time = new ElapsedTime();
        auto = new autoTemplate2(hardwareMap);
        auto.armInit("arm1","arm2","claw");
        auto.wheelInit("fr","br","bl","fl");
    }
    public void loop()
    {
       auto.moveArm(LEVEL_2);
       if(time.seconds()<2){
           auto.drive(speed,-40,-40);
       }
       stop();
       auto.open();
        telemetry.addData("time",time.seconds());
    }
//


}
