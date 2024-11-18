package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.AStarPathfinding;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.Movement;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.ATEMModelInterpreter;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.atem.ATEMTaskExecutor;

@Autonomous(name = "ATEM Autonomous", group = "Competition")
public class ATEMAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Initialize hardware and utility classes
        Movement robot = new Movement(); // Replace with actual hardware map integration
        AStarPathfinding pathfinder = new AStarPathfinding();
        ATEMModelInterpreter modelInterpreter = new ATEMModelInterpreter("model_path.tflite"); // Path to your TFLite model

        ATEMTaskExecutor taskExecutor = new ATEMTaskExecutor(robot, modelInterpreter, pathfinder);

        telemetry.addLine("Initializing...");
        telemetry.update();

        // Wait for the start signal
        waitForStart();

        if (opModeIsActive()) {
            telemetry.addLine("Starting Autonomous...");
            telemetry.update();

            try {
                // Execute tasks
                taskExecutor.executeTasks();
            } catch (InterruptedException e) {
                telemetry.addLine("Autonomous interrupted: " + e.getMessage());
                telemetry.update();
            }

            telemetry.addLine("Autonomous Complete!");
            telemetry.update();
        }
    }
}