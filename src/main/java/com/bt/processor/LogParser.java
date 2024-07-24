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

public class LogParser {
    private static final Pattern LOG_PATTERN = Pattern.compile("(\\d{2}:\\d{2}:\\d{2}) (\\w+) (Start|End)");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Map<String, SessionDetail>  parseLogFile(Path filePath) throws IOException {
        Map<String, SessionDetail> usersDuration = new HashMap<>();
        Map<String, Stack<LocalTime>> userSessionStartStackMap = new HashMap<>();
        LocalTime earliestTime = null;
        LocalTime latestTime = null;

        for (String line : Files.readAllLines(filePath)) {
            Matcher matcher = LOG_PATTERN.matcher(line);
            if (matcher.matches()) {
                LocalTime time = LocalTime.parse(matcher.group(1), TIME_FORMATTER);
                String userName = matcher.group(2);
                String action = matcher.group(3);

                if (earliestTime == null || time.isBefore(earliestTime)) {
                    earliestTime = time;
                }
                if (latestTime == null || time.isAfter(latestTime)) {
                    latestTime = time;
                }

                if (action.equals("End")) {
                    long duration;
                    LocalTime startTime = (userSessionStartStackMap.get(userName) == null || userSessionStartStackMap.get(userName).isEmpty()) ? earliestTime : userSessionStartStackMap.get(userName).pop();

                    duration = time.toSecondOfDay() - startTime.toSecondOfDay();
                    if (!usersDuration.containsKey(userName)) {
                        usersDuration.put(userName, new SessionDetail (1,duration));
                        }
                    else {
                        usersDuration.computeIfPresent(userName, (k, sessionDetail) -> sessionDetail.updateTotalSessionDuration(duration));
                    }
                } else {
                    if (userSessionStartStackMap.containsKey(userName)) {
                        userSessionStartStackMap.get(userName).push(time);
                    } else {
                        Stack<LocalTime> stack = new Stack<>();
                        stack.push(time);
                        userSessionStartStackMap.put(userName, stack);
                    }
                }
            }
        }

        for (Map.Entry<String, Stack<LocalTime>> userSessionStartStack : userSessionStartStackMap.entrySet()) {
            String userName = userSessionStartStack.getKey();
            Stack<LocalTime> stack = userSessionStartStack.getValue();
            usersDuration.putIfAbsent(userName, new SessionDetail (0,0L));
            SessionDetail sessionDetail = usersDuration.get(userName);
            while (!stack.isEmpty())
                usersDuration.put(userName, sessionDetail.updateTotalSessionDuration(latestTime.toSecondOfDay() - stack.pop().toSecondOfDay()));

        }
        return usersDuration;
    }
}