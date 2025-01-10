package org.firstinspires.ftc.teamcode.IntoTheDeep24_25;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class pidMaybe {
    private double Kp,Ki,Kd;
    private double error;
    private double lastError = 0;
    private double tolerance = 4;

    private DcMotorEx motor;
    ElapsedTime time = new ElapsedTime();
    public pidMaybe(double Kp,double Ki,DcMotorEx motor){
        this.Kp = Kp;
        this.Ki = Ki;
        this.motor = motor;
    }
    public pidMaybe(double Kp, double Ki,double Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
    }
    public double calculatePower(double target, double current) {
        if(target == current+tolerance||target == current-tolerance)
        {
            time.reset();
            return 0;
        }
        error = target-current;
        double d = Kd * (error-lastError)*time.seconds();
        double i = Ki * (error-lastError)*(Math.pow(time.seconds(),2)/2);
        double p = Kp * error;
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


