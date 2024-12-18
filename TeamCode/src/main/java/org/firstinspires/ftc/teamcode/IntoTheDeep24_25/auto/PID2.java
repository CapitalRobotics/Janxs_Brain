package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto;

import com.qualcomm.robotcore.util.ElapsedTime;

/*
https://pidexplained.com/how-to-tune-a-pid-controller/
https://docs.ftclib.org/ftclib/features/controllers
https://www.ctrlaltftc.com/the-pid-controller
 */
public class PID2 {
    int Kp = 0;//Once the proportional value that causes the controller to oscillate is found, take this value and divide it in half. This will be the starting P value
    int Kd = 0;//Continue to change the set point and increase the derivative until the overshoot has been dampened to an acceptable level.
    int Ki = 0; //Once the controller is stable, and responding desirably… congrats! you now have a  working PI controller.

    int target = 0;
    int current = 0;

    int iSum = 0;
    int lastError = 0;

    ElapsedTime timer = new ElapsedTime();


    public void setPower(){
        
    }

//
//while (setPointIsNotReached) {
//
//
//        // obtain the encoder position
//        encoderPosition = armMotor.getPosition();
//        // calculate the error
//        error = reference - encoderPosition;
//
//        // rate of change of the error
//        derivative = (error - lastError) / timer.seconds();
//
//        // sum of all error over time
//        integralSum = integralSum + (error * timer.seconds());
//
//        out = (Kp * error) + (Ki * integralSum) + (Kd * derivative);
//
//        armMotor.setPower(out);
//
//        lastError = error;
//
//        // reset the timer for next time
//        timer.reset();
//
//    }
//     */
}
//port 0 - arm up/down
//port 2: extender/retractor