package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.atem;

import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.AStarPathfinding;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.Movement;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.Node;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.TaskToCoordinateMapper;
import org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils.ATEMModelInterpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATEMTaskExecutor {
    private final Movement robot;
    private final ATEMModelInterpreter modelInterpreter; // Class that wraps ATEM model calls
    private final AStarPathfinding pathfinder; // A* implementation
    private final TaskToCoordinateMapper taskMapper;

    // Task-specific actions
    private final Map<String, Runnable> taskActions;

    // Current task state
    private String currentTask;

    public ATEMTaskExecutor(Movement robot, ATEMModelInterpreter modelInterpreter, AStarPathfinding pathfinder) {
        this.robot = robot;
        this.modelInterpreter = modelInterpreter;
        this.pathfinder = pathfinder;
        this.taskMapper = new TaskToCoordinateMapper();
        this.taskActions = new HashMap<>();

        // Define actions for each task
        taskActions.put("High Basket", this::performHighBasketTask);
        taskActions.put("Low Basket", this::performLowBasketTask);
        taskActions.put("Observation Zone", this::performObservationZoneTask);
        taskActions.put("Net Zone", this::performNetZoneTask);
    }

    // Main loop to execute tasks
    public void executeTasks() throws InterruptedException {
        while (true) {
            System.out.println("Predicting next task...");

            // Example: Replace this with actual sensor data
            Map<String, Double> sensorData = null;

            // Get the next task from the model
            String nextTask = modelInterpreter.predictNextTask(currentTask);

            if (nextTask == null) {
                System.out.println("No further tasks predicted. Execution complete.");
                break;
            }

            System.out.println("Next Task: " + nextTask);

            // Perform the task using A* and task-specific actions
            interpretAndExecuteTask(nextTask);

            // Update current task
            currentTask = nextTask;

            // Simulate task completion
            System.out.println("Task " + nextTask + " completed.\n");
        }
    }

    // Interpret and execute a task
    private void interpretAndExecuteTask(String taskName) throws InterruptedException {
        Node taskCoordinates = taskMapper.getCoordinates(taskName);

        if (taskCoordinates != null) {
            System.out.println("Navigating to " + taskName + "...");
            List<Node> path = pathfinder.findPath(robot.getCurrentPosition(), taskCoordinates);

            // Execute the A* path
            if (path != null && !path.isEmpty()) {
                robot.executePath(path);
            } else {
                System.err.println("Failed to find a path to " + taskName);
                return;
            }

            // Perform task-specific action
            Runnable action = taskActions.get(taskName);
            if (action != null) {
                action.run();
            } else {
                System.err.println("No action defined for task: " + taskName);
            }
        } else {
            System.err.println("Coordinates not found for task: " + taskName);
        }
    }

    // Task-specific actions
    private void performHighBasketTask() {
        System.out.println("Performing High Basket task...");
        robot.armUp();
    }

    private void performLowBasketTask() {
        System.out.println("Performing Low Basket task...");
        robot.armDown();
    }

    private void performObservationZoneTask() {
        System.out.println("Observing...");
        // Additional logic for observation
    }

    private void performNetZoneTask() {
        System.out.println("Interacting with Net Zone...");
        // Additional logic for Net Zone interaction
    }
}