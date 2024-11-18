//package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.tests;
//
//import static com.qualcomm.robotcore.util.Range.clip;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//
//import org.firstinspires.ftc.teamcode.TemplateJanx;
//@Disabled
//@TeleOp(name = "teleop arm code")
//public class teleop_arm_code extends LinearOpMode {
//    //replace Template with your class name
//    //initiate motors
//    // private motorType nameOfMotor
//    private DcMotor leftMotor;
//    private DcMotor rightMotor;
//    @Override
//    public void runOpMode() {
//        TemplateJanx janx = new TemplateJanx(hardwareMap);
//        janx.basicArmInit("arm_left", "arm_right");
//        //link motors to config
//        waitForStart();
//        if (opModeIsActive()) {
//            while (opModeIsActive()) {
//                //call functions here
//            }
//        }
//    }
//    //write functions here
//    private void arm(){
//        if(gamepad2.left_stick_y > 0) {
//         leftMotor.setPower(1);
//         rightMotor.setPower(1);
//        }
//        else if(gamepad2.left_stick_y < 0){
//            leftMotor.setPower(-1);
//            rightMotor.setPower(-1);
//        }
//    }
//}