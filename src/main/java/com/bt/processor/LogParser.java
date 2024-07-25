package com.bt.processor;

import com.bt.model.SessionDetail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses log files and calculates session durations for users.
 */
public class LogParser {
    // Pattern to match log entries
    private static final Pattern LOG_PATTERN = Pattern.compile("(\\d{2}:\\d{2}:\\d{2}) (\\w+) (Start|End)");
    // Format for parsing time
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Parses a log file and calculates session details for each user.
     */
    public Map<String, SessionDetail> parseLogFile(Path filePath) throws IOException {
        // Store session details per each user
        Map<String, SessionDetail> usersDuration = new HashMap<>();
        // Store start times per each user
        Map<String, Stack<LocalTime>> userSessionStartStackMap = new HashMap<>();
        LocalTime earliestTime = null;
        LocalTime latestTime = null;

        // Read file line by line
        for (String line : Files.readAllLines(filePath)) {
            Matcher matcher = LOG_PATTERN.matcher(line);
            if (matcher.matches()) {
                // Extract infos from a line of log
                LocalTime time = LocalTime.parse(matcher.group(1), TIME_FORMATTER);
                String userName = matcher.group(2);
                String action = matcher.group(3);

                // Update earliest and latest times
                earliestTime = (earliestTime == null || time.isBefore(earliestTime)) ? time : earliestTime;
                latestTime = (latestTime == null || time.isAfter(latestTime)) ? time : latestTime;

                // Process the line
                processLogEntry(usersDuration, userSessionStartStackMap, earliestTime, time, userName, action);
            }
        }

        // Handle any remaining open sessions
        finalizeOpenSessions(usersDuration, userSessionStartStackMap, latestTime);
        return usersDuration;
    }

    // Process each log line
    private void processLogEntry(Map<String, SessionDetail> usersDuration,
                                 Map<String, Stack<LocalTime>> userSessionStartStackMap,
                                 LocalTime earliestTime, LocalTime time, String userName, String action) {
        if ("End".equals(action)) {
            processEndAction(usersDuration, userSessionStartStackMap, earliestTime, time, userName);
        } else {
            processStartAction(userSessionStartStackMap, time, userName);
        }
    }

    // Process "End" lines
    private void processEndAction(Map<String, SessionDetail> usersDuration,
                                  Map<String, Stack<LocalTime>> userSessionStartStackMap,
                                  LocalTime earliestTime, LocalTime endTime, String userName) {
        Stack<LocalTime> startStack = userSessionStartStackMap.get(userName);
        // Get current session start time, use earliest if no start found
        LocalTime startTime = (startStack == null || startStack.isEmpty()) ? earliestTime : startStack.pop();
        long duration = endTime.toSecondOfDay() - startTime.toSecondOfDay();

        // Update user's total sessions duration
        usersDuration.compute(userName, (k, v) -> v == null ? new SessionDetail(1, duration) : v.updateTotalSessionDuration(duration));
    }

    // Process "Start" lines
    private void processStartAction(Map<String, Stack<LocalTime>> userSessionStartStackMap, LocalTime time, String userName) {
        // Add start time to user's stack
        userSessionStartStackMap.computeIfAbsent(userName, k -> new Stack<>()).push(time);
    }

    // Handle sessions without end time, open sessions
    private void finalizeOpenSessions(Map<String, SessionDetail> usersDuration,
                                      Map<String, Stack<LocalTime>> userSessionStartStackMap,
                                      LocalTime latestTime) {
        userSessionStartStackMap.forEach((userName, stack) -> {
            // Ensure user has a session detail
            usersDuration.putIfAbsent(userName, new SessionDetail(0, 0L));
            SessionDetail sessionDetail = usersDuration.get(userName);
            // Process all open sessions and update user's total sessions duration
            while (!stack.isEmpty()) {
                long duration = latestTime.toSecondOfDay() - stack.pop().toSecondOfDay();
                usersDuration.put(userName, sessionDetail.updateTotalSessionDuration(duration));
            }
        });
    }
}