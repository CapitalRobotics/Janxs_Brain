package org.firstinspires.ftc.teamcode.IntoTheDeep24_25;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
https://pidexplained.com/how-to-tune-a-pid-controller/
https://gm0.org/en/latest/docs/software/concepts/control-loops.html
https://www.ctrlaltftc.com/the-pid-controller
 */
@Disabled
public class PID2 {
    private int kp = 0;
    private int kd = 0;
    private int ki = 0;

    private double p;
    private double i;
    private double d;

    private int target;
    private int error;
    private int lastError;
    private int pastTime;

    public PID2(int target){
        this.target = target;
        i = 0;
        lastError = 0;
    }
    public void findVals(){

    }

    public double returnPower(int current, ElapsedTime time){
            lastError +=error;
            error = target - current;
            p = error;
            i += lastError * time.seconds();
            d = (error - lastError)/time.seconds();
            double power = p+d;
            if(power>0.5){
                power = 0.5;
            }
            else if(power<-0.5){
                power = -0.5;
            }
            return power;

    }
    public void reset(ElapsedTime currentTime){
        currentTime.reset();
        lastError = error;
    }
    public void setTarget(int t){
        target = t;
    }
    public double returnP(){
        return p;
    }
    public double returnD(){
        return d;
    }
    public double returnI(){
        return i;
    }
    /*
    while True:
   current_time = get_current_time()
   current_error = desire_position-current_position

   p = k_p * current_error

   i += k_i * (current_error * (current_time - previous_time))

   if i > max_i:
       i = max_i
   elif i < -max_i:
       i = -max_i

   D = k_d * (current_error - previous_error) / (current_time - previous_time)

   output = p + i + d

   previous_error = current_error
   previous_time = current_time
     */
    /*
    /*

* Proportional Integral Derivative Controller

*/
/*
    Kp = someValue;
    Ki = someValue;
    Kd = someValue;

    reference = someValue;

    integralSum = 0;

    lastError = 0;

    // Elapsed timer class from SDK, please use it, it's epic
    ElapsedTime timer = new ElapsedTime();

while (setPointIsNotReached) {


        // obtain the encoder position
        encoderPosition = armMotor.getPosition();
        // calculate the error
        error = reference - encoderPosition;

        // rate of change of the error
        derivative = (error - lastError) / timer.seconds();

        // sum of all error over time
        integralSum = integralSum + (error * timer.seconds());

        out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);

        armMotor.setPower(out);

        lastError = error;

        // reset the timer for next time
        timer.reset();

    }
     */
}
