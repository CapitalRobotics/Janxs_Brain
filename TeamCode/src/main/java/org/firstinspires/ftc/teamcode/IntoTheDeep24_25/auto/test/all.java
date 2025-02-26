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
    public void loop()
    {
      janx.open();
      janx.close();

      janx.moveArm(LEVEL_2);
      janx.drive(2400,15,15, 3);
      janx.moveArm(LEVEL_1);
      janx.drive(2400,-15,-15, 3);
      janx.turn(true);
      janx.drive(2400,6,6, 3);
      janx.turn(false);
      janx.drive(2400,6,6, 3);
//      janx.
    }
    public void turn(boolean right)
    {
        int turn = 33;
        if(right)
        {
            janx.drive(400,-turn,turn,3);
        }
        else
        {
            janx.drive(400,turn,-turn,3);
        }
    }


}
