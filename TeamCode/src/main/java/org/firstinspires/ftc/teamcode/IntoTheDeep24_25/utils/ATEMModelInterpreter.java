package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils;

import java.net.CookieManager;
import java.nio.ByteBuffer;
import java.util.Map;
import java.nio.ByteBuffer;

public class ATEMModelInterpreter {
    private final String modelPath;

    public ATEMModelInterpreter(String modelPath) {
        if (modelPath == null || modelPath.isEmpty()) {
            throw new IllegalArgumentException("Model path cannot be null or empty");
        }
        this.modelPath = modelPath;

        // Initialize the model here (e.g., load the TensorFlow Lite model)
        System.out.println("Model initialized with path: " + modelPath);
    }

    public String predictNextTask(String currentTask) {
        // Example implementation: Replace with the actual model invocation logic
        System.out.println("Predicting next task using current task: " + currentTask);

        // Simulate a prediction result
        return "High Basket"; // Replace with model's actual output
    }
}

    /**
     * Predict the next task based on the current task and sensor data.
     *
     * @param currentTask The current task being executed.
     * @return The name of the predicted next task.
     */
    public String predictNextTask(String currentTask) {
        // Validate input
        if (!taskToIndex.containsKey(currentTask)) {
            throw new IllegalArgumentException("Unknown task: " + currentTask);
        }

        // Prepare model input
        int currentTaskIndex = taskToIndex.get(currentTask);
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(20); // Example size
        inputBuffer.putFloat(currentTaskIndex);
       // inputBuffer.putFloat(sensorData.getOrDefault("time_elapsed", 0.0));
       // inputBuffer.putFloat(sensorData.getOrDefault("distance_to_target", 0.0));
       // inputBuffer.putFloat(sensorData.getOrDefault("gyro_angle", 0.0));
       // inputBuffer.putFloat(sensorData.getOrDefault("battery_level", 0.0));
        inputBuffer.rewind();

        // Perform inference
        ByteBuffer outputBuffer = ByteBuffer.allocateDirect(12); // 3 output tasks
        outputBuffer.rewind();
        runInference(inputBuffer, outputBuffer);

        // Get the highest scoring task index
        outputBuffer.rewind();
        float[] scores = new float[3]; // Example size
        for (int i = 0; i < scores.length; i++) {
            scores[i] = outputBuffer.getFloat();
        }
        int bestIndex = getMaxIndex(scores);

        // Map the index back to the task name
        CookieManager indexToTask;
        return indexToTask.get(bestIndex);
    }

