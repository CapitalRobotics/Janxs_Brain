package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class pidMaybe {
    private double Kp;
    private double Ki;
    private double error;
    private double lastError = 0;
    private double tolerance = 4;

    private DcMotorEx motor;
    ElapsedTime time = ElapsedTime();
    public pidMaybe(double Kp, double Ki, DcMotorEx motor) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.motor = motor;
    }
    public double calculatePower(double target, double current) {
        if(target == current+tolerance||target == current-tolerance)
        {
            return 0;
        }
        error = target-current;
        double p = Kp*(error-lastError)*time.seconds();
        double i = Ki*(error-lastError)*(Math.pow(time.seconds(),2)/2);
        double d = Kp*error;

        lastError = error;
        return p+i+d;
    }


}
