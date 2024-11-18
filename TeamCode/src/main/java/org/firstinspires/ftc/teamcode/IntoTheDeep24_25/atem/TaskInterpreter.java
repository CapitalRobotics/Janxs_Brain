package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.atem;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.Movement;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.Node;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.TaskToCoordinateMapper;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class TaskInterpreter {
    private final ByteBuffer robot;
    private final TaskToCoordinateMapper taskMapper;

    // Task-to-action map
    private final Map<String, Runnable> taskActions;

    public TaskInterpreter(ByteBuffer robot) {
        this.robot = robot;
        this.taskMapper = new TaskToCoordinateMapper();

        // Define actions for each task
        this.taskActions = new HashMap<>();
        taskActions.put("High Basket", this::performHighBasketTask);
        taskActions.put("Low Basket", this::performLowBasketTask);
        taskActions.put("Observation Zone", this::performObservationZoneTask);
        taskActions.put("Net Zone", this::performNetZoneTask);
    }

    // Interpret and execute the task
    public void interpretAndExecuteTask(String taskName) throws InterruptedException {
        Runnable action = taskActions.get(taskName);

        if (action != null) {
            System.out.println("Executing task: " + taskName);
            action.run();
        } else {
            System.err.println("Unknown task: " + taskName);
        }
    }

    // Actions for specific tasks
    private void performHighBasketTask() {
        // Example: Move to the "High Basket" location and perform the action
        Node highBasketCoordinates = taskMapper.getCoordinates("High Basket");
        if (highBasketCoordinates != null) {
            System.out.println("Navigating to High Basket...");
            // Trigger A* to move to the location and raise the arm
            //robot.moveTo(highBasketCoordinates, 0.5); // Example
            //robot.armUp();
        }
    }

    private void performLowBasketTask() {
        // Example: Move to the "Low Basket" location and perform the action
        Node lowBasketCoordinates = taskMapper.getCoordinates("Low Basket");
        if (lowBasketCoordinates != null) {
            System.out.println("Navigating to Low Basket...");
            //robot.moveTo(lowBasketCoordinates, 0.5); // Example
            //robot.armDown();
        }
    }

    private void performObservationZoneTask() {
        // Example: Move to the "Observation Zone" location
        Node observationZoneCoordinates = taskMapper.getCoordinates("Observation Zone");
        if (observationZoneCoordinates != null) {
            System.out.println("Navigating to Observation Zone...");
            //robot.moveTo(observationZoneCoordinates, 0.5); // Example
        }
    }

    private void performNetZoneTask() {
        // Example: Move to the "Net Zone" location
        Node netZoneCoordinates = taskMapper.getCoordinates("Net Zone");
        if (netZoneCoordinates != null) {
            System.out.println("Navigating to Net Zone...");
            //robot.moveTo(netZoneCoordinates, 0.5); // Example
        }
    }

    public void run(ByteBuffer inputBuffer, ByteBuffer outputBuffer) {
        // Ensure input and output buffers are not null
        if (inputBuffer == null || outputBuffer == null) {
            throw new IllegalArgumentException("Input or output buffer cannot be null");
        }

        // Reset positions of both buffers to ensure proper reading and writing
        inputBuffer.rewind();
        outputBuffer.rewind();

        // Example processing: Copy data from inputBuffer to outputBuffer
        while (inputBuffer.hasRemaining()) {
            byte inputByte = inputBuffer.get();

            // Perform some operation on the input data (example: doubling the value)
            byte outputByte = (byte) (inputByte * 2);

            // Write the processed value to the output buffer
            if (outputBuffer.hasRemaining()) {
                outputBuffer.put(outputByte);
            } else {
                throw new IllegalStateException("Output buffer overflow. Ensure it is large enough.");
            }
        }

        // Reset the position of the output buffer for subsequent reads
        outputBuffer.flip();
    }

}