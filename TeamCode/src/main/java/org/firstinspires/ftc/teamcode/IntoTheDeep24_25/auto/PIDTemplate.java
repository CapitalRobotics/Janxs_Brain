package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto;

public class PIDTemplate {
    private double kP;  // Proportional gain
    private double kI;  // Integral gain
    private double kD;  // Derivative gain

    // Variables to store the state of the controller
    private double previousError = 0;    // Previous error value, used for calculating the derivative
    private double integralSum = 0;      // Sum of errors over time, used for the integral term
    private double setpoint = 0;         // Desired target value (position, speed, etc.)

    // Constructor to initialize PID coefficients
    public PIDTemplate(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    // Set the target value for the PID controller
    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    // Calculate PID output based on the current value (sensor feedback)
    public double calculate(double currentValue) {
        //Calculate the error (difference between setpoint and current value)
        double error = setpoint - currentValue;

        //Update the integral (sum of all errors) - helps correct small, steady-state errors
        integralSum += error;

        //Calculate the derivative (rate of change of error)
        double derivative = error - previousError;

        //Apply the PID formula: output = (kP * error) + (kI * integral) + (kD * derivative)
        double output = (kP * error) + (kI * integralSum) + (kD * derivative);

        //Update previousError with the current error for the next cycle
        previousError = error;

        // Return the calculated output, which can be used to set motor power
        return output;
    }


}
