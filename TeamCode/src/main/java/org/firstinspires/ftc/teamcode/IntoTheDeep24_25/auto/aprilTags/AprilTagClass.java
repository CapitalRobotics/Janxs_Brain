package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.auto.aprilTags;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.AprilTagController;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.RobotController;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;

@Disabled
@Autonomous(name = "AprilTag Detection Only", group = "Autonomous")
public class AprilTagClass extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;
    private static final int DESIRED_TAG_ID = -1;
    private static final int TAG_TIMEOUT_THRESHOLD = 100;
    private int tagTimeoutCounter = 0;

    @Override
    public void runOpMode() {
        RobotController robot = new RobotController(hardwareMap);
        AprilTagController aprilTagController = new AprilTagController(hardwareMap, USE_WEBCAM);

        if (USE_WEBCAM) {
            aprilTagController.setManualExposure(6, 250);
        }

        telemetry.addData("Status", "Waiting for start...");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            boolean targetFound = false;
            AprilTagDetection desiredTag = null;

            List<AprilTagDetection> currentDetections = aprilTagController.getDetections();
            for (AprilTagDetection detection : currentDetections) {
                if ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID)) {
                    targetFound = true;
                    desiredTag = detection;
                    break;
                }
            }

            if (targetFound) {
                tagTimeoutCounter = 0;
                telemetry.addData("AprilTag Detected", "ID: %d", desiredTag.id);
                telemetry.addData("Range", "%5.1f inches", desiredTag.ftcPose.range);
                telemetry.addData("Bearing", "%3.0f degrees", desiredTag.ftcPose.bearing);
                telemetry.addData("Yaw", "%3.0f degrees", desiredTag.ftcPose.yaw);

                if (desiredTag.ftcPose.range > 36) {
                    robot.moveForward(-1);
                } else {
                    robot.stop();
                }
            } else {
                tagTimeoutCounter++;
                telemetry.addData("Status", "No AprilTag detected. Searching...");
                if (tagTimeoutCounter > TAG_TIMEOUT_THRESHOLD) {
                    robot.stop();
                    telemetry.addData("Status", "No AprilTag detected. Robot stopped.");
                }
            }

            telemetry.update();
            sleep(10);
        }
    }
}
