package org.firstinspires.ftc.teamcode.IntoTheDeep24_25.utils;

import java.util.HashMap;
import java.util.Map;

public class TaskToCoordinateMapper {
    private final Map<String, Node> taskToCoordinates;

    public TaskToCoordinateMapper() {
        taskToCoordinates = new HashMap<>();

        taskToCoordinates.put("High Basket", new Node(4, 5));
        taskToCoordinates.put("Low Basket", new Node(2, 3));
        taskToCoordinates.put("Observation Zone", new Node(5, 1));
        taskToCoordinates.put("Net Zone", new Node(3, 2));
        // Add all relevant tasks and their coordinates
    }

    public Node getCoordinates(String taskName) {
        return taskToCoordinates.getOrDefault(taskName, null);
    }
}