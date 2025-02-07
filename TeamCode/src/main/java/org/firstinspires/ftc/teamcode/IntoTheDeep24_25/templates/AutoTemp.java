package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.templates;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
public class AutoTemp {
    public DcMotorEx fr = null;
    public DcMotorEx br = null;
    public DcMotorEx fl = null;
    public DcMotorEx bl = null;

    HardwareMap hwMap = null;

    private final double DEFAULT_SPEED = 0.5;
    private final int COUNTS_PER_INCH = 100;

    public AutoTemp(HardwareMap hardwareMap) {
        hwMap = hardwareMap;
    }

    public void initDriveMotors(String frontRight, String backRight, String backLeft, String frontLeft) {
        fr = hwMap.get(DcMotorEx.class, frontRight);
        br = hwMap.get(DcMotorEx.class, backRight);
        fl = hwMap.get(DcMotorEx.class, frontLeft);
        bl = hwMap.get(DcMotorEx.class, backLeft);

        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();
    }

    public void resetEncoders() {
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void driveForward(double distanceInInches, double speed) {
        int targetPosition = (int) (distanceInInches * COUNTS_PER_INCH);

        setTargetPosition(targetPosition, targetPosition, targetPosition, targetPosition);
        setDrivePower(speed);

        waitForMotorsToReachTarget();
        stopMotors();
    }

    public void turn(double degrees, double speed) {
        double inchesToTurn = degrees / 360.0 * Math.PI * getTurningRadius();
        int targetPosition = (int) (inchesToTurn * COUNTS_PER_INCH);

        setTargetPosition(targetPosition, targetPosition, -targetPosition, -targetPosition);
        setDrivePower(speed);

        waitForMotorsToReachTarget();
        stopMotors();
    }

    public void strafe(double distanceInInches, double speed, boolean isLeft) {
        int targetPosition = (int) (distanceInInches * COUNTS_PER_INCH);

        if (isLeft) {
            setTargetPosition(-targetPosition, targetPosition, targetPosition, -targetPosition);
        } else {
            setTargetPosition(targetPosition, -targetPosition, -targetPosition, targetPosition);
        }
        setDrivePower(speed);

        waitForMotorsToReachTarget();
        stopMotors();
    }

    private void setTargetPosition(int frTarget, int brTarget, int flTarget, int blTarget) {
        fr.setTargetPosition(fr.getCurrentPosition() + frTarget);
        br.setTargetPosition(br.getCurrentPosition() + brTarget);
        fl.setTargetPosition(fl.getCurrentPosition() + flTarget);
        bl.setTargetPosition(bl.getCurrentPosition() + blTarget);

        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setDrivePower(double power) {
        fr.setPower(power);
        br.setPower(power);
        fl.setPower(power);
        bl.setPower(power);
    }

    private void waitForMotorsToReachTarget() {
        while (fr.isBusy() && br.isBusy() && fl.isBusy() && bl.isBusy()) {
        }
    }

    private void stopMotors() {
        fr.setPower(0);
        br.setPower(0);
        fl.setPower(0);
        bl.setPower(0);
    }

    private double getTurningRadius() {
        return 10.0;
    }
}