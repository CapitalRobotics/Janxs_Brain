package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
//@config is a FTC dashboard thing for live edit. also the reason why everything is static
//even if it makes machnik roll in her metaphoric grave
//@Config
@TeleOp(name = "the one with the video")
public class arm5 extends OpMode{
    private PIDController controller;
    public static double p = 0.5, i = 0, d = 1;
    public static double f = 0;

    public static int target = 0;
    //number of ticks in rotation of core hex motor/180
    public final double ticksInDegrees = 1/180;
    private DcMotorEx arm;

    @Override
    public void init(){
        controller = new PIDController(p,i,d);
        arm = hardwareMap.get(DcMotorEx.class, "arm1");
        //telemetry = new MultipleTelemetry(telemetry,ftcDashboard.getInstance().getTelemetry());
    }
    @Override
    public void loop(){
        controller.setPID(p,i,d);
        int current = arm.getCurrentPosition();
        double pid = controller.calculate(current,target);
        double ff = Math.cos(Math.toRadians(target/ticksInDegrees)) * f;

        double power = pid * ff;
        arm.setPower(power);
        telemetry.addData("pos",current);
        telemetry.addData("target", target);
        telemetry.update();
    }

}
