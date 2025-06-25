package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.fin;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "long")
public class tester_2 extends LinearOpMode {
    private static final int ARM_DOWN_POSITION = 35;
    private static final int LEVEL_1 = 650;
    private static final int LEVEL_2 = 800;
    private DcMotorEx frontRight,backRight,backLeft,frontLeft;

    private DcMotorEx arm,extend;
    private Servo claw;

    private ElapsedTime runtime = new ElapsedTime();

    // Encoder constants for the drivetrain
    static final double COUNTS_PER_MOTOR_REV = 28; // Rev motor ticks per revolution
    static final double DRIVE_GEAR_REDUCTION = 20.0; // External gearing
    static final double WHEEL_DIAMETER_INCHES = 4.0; // Diameter of wheels
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    boolean done = false;
    @Override
    public void runOpMode() {
        initial();
        double open = 0.25;
        double close = 1;
        /**
         * forward
         * raise arm
         * extend arm
         * open claw
         * retract arm
         * lower arm
         * backwards
         */
        waitForStart();
        if (opModeIsActive()) {

            frontRight.setDirection(DcMotor.Direction.REVERSE);
            backRight.setDirection(DcMotor.Direction.REVERSE);
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
            backLeft.setDirection(DcMotor.Direction.FORWARD);


            while(opModeIsActive())
            {
                double time = 3;
                telemetry.addData("pos",frontLeft.getTargetPosition());
                telemetry.addData("cur",frontLeft.getCurrentPosition());
                telemetry.update();
                if(!done)
                {
                    runtime.reset();
                    //raise arm
                    while(runtime.seconds()<1)
                    {
                        arm.setTargetPosition(LEVEL_1);
                        arm.setTargetPositionTolerance(20);
                        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        arm.setPower(1);
                    }
                    runtime.reset();
                    //forwards
                    while(runtime.seconds()<1.8)
                    {
                        // Set drivetrain motor directions
                        int m = 5;
                        int[] target = setTarget(m,m);
                        frontRight.setTargetPosition(target[0]);
                        backRight.setTargetPosition(target[1]);
                        backLeft.setTargetPosition(target[2]);
                        frontLeft.setTargetPosition(target[3]);
                        runToPos();
                    }
                    runtime.reset();
                    //extend arm
                    while(runtime.seconds()<2)
                    {
                        extend.setTargetPosition(600);
                        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        extend.setPower(1);
                    }
                    runtime.reset();
                    //open claw
                    while(runtime.seconds()<3)
                    {
                        claw.setPosition(open);
                    }
                    runtime.reset();
                    //safety backup
                  while(runtime.seconds()<0.2)
                    {
                        int m = -5;
                        int[] target = setTarget(m,m);
                        frontRight.setTargetPosition(target[0]);
                        backRight.setTargetPosition(target[1]);
                        backLeft.setTargetPosition(target[2]);
                        frontLeft.setTargetPosition(target[3]);
                        runToPos();
                    }
                   runtime.reset();
                    //retract arm
                    while(runtime.seconds()<2)
                    {
                        extend.setTargetPosition(0);
                        extend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        extend.setPower(1);
                    }
                    runtime.reset();
                    //lower arm
                    while(runtime.seconds()<1)
                    {
                        arm.setTargetPosition(0);
                        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        arm.setPower(1);
                    }
                    runtime.reset();
                    //backwards
                    while(runtime.seconds()<3)
                    {
                        int m = -5;
                        int[] target = setTarget(m,m);
                        frontRight.setTargetPosition(target[0]);
                        backRight.setTargetPosition(target[1]);
                        backLeft.setTargetPosition(target[2]);
                        frontLeft.setTargetPosition(target[3]);
                        runToPos();
                    }
                    done = true;
                   // stop();
                }
            }
        }
    }
    public void setPower(int p)
    {
        frontRight.setPower(p);
        frontLeft.setPower(p);
        backRight.setPower(p);
        backLeft.setPower(p);
    }
    public int[] setTarget(int left, int right)
    {
        int[] array = new int[4];

         array[0] = frontRight.getCurrentPosition() + (int)(right * COUNTS_PER_INCH);
         array[1] = backRight.getCurrentPosition() + (int)(right * COUNTS_PER_INCH);
         array[2] = backLeft.getCurrentPosition() + (int)(left * COUNTS_PER_INCH);
         array[3] = frontLeft.getCurrentPosition() + (int)(left * COUNTS_PER_INCH);
         return array;
    }
    public void runToPos()
    {
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(0.5);
        frontLeft.setPower(0.5);
        backRight.setPower(0.5);
        backLeft.setPower(0.5);
    }
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {
            // Calculate new target positions
            newLeftTarget = backLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = backRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);

            // Set targets for all motors
            backLeft.setTargetPosition(newLeftTarget);
            frontLeft.setTargetPosition(newLeftTarget);
            backRight.setTargetPosition(newRightTarget);
            frontRight.setTargetPosition(newRightTarget);

            // Set motors to run to position
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Start motion
            runtime.reset();
            backLeft.setPower(Math.abs(speed));
            frontLeft.setPower(Math.abs(speed));
            backRight.setPower(Math.abs(speed));
            frontRight.setPower(Math.abs(speed));

            // Wait while motors are running or timeout occurs
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (backLeft.isBusy() && backRight.isBusy())) {
                telemetry.addData("Target L", newLeftTarget);
                telemetry.addData("Target R", newRightTarget);
                telemetry.addData("Current L", backLeft.getCurrentPosition());
                telemetry.addData("Current R", backRight.getCurrentPosition());
                telemetry.update();
            }
            while (opModeIsActive() && arm.isBusy()) {
                telemetry.addData("Raising Arm", "Current Position: %d", arm.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion
            backLeft.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            frontRight.setPower(0);

            // Reset motors to RUN_USING_ENCODER
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250); // Optional delay
        }
    }
    public void initial() {
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backRight = hardwareMap.get(DcMotorEx.class, "br");
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");

        arm = hardwareMap.get(DcMotorEx.class, "arm");
        extend = hardwareMap.get(DcMotorEx.class, "extend");
        claw = hardwareMap.get(Servo.class, "claw");

        // Set arm motor to reset encoders and brake mode
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(20);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set drivetrain motors to use encoders
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set brake behavior for all drivetrain motors
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Raise the arm immediately

        // Send telemetry message
        telemetry.addData("Initialization", "Complete");
        telemetry.update();
    }
}
