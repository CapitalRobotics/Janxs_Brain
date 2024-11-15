package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.teleop.arm;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class pidMaybe {
    private double Kp;
    private double Ki;
    private double error;
    private double lastError = 0;
    private double tolerance = 4;

    private DcMotorEx motor;
    ElapsedTime time = new ElapsedTime();
    public pidMaybe(double Kp, double Ki, DcMotorEx motor) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.motor = motor;
    }
    public double calculatePower(double target, double current) {
        if(target == current+tolerance||target == current-tolerance)
        {
            time.reset();
            return 0;
        }
        error = target-current;
        double p = Kp*(error-lastError)*time.seconds();
        double i = Ki*(error-lastError)*(Math.pow(time.seconds(),2)/2);
        double d = Kp*error;
        coeff(p,i,d);

        lastError = error;
        return p+i+d;
    }
    public int getTime(){
        return (int)time.seconds();
    }


    public String coeff(double p, double i, double d) {
        // Format the coefficients into a string
        String coeffString = String.format("P: %.2f, I: %.2f, D: %.2f", p, i, d);

        // Return the formatted string
        return coeffString;
    }
    }


