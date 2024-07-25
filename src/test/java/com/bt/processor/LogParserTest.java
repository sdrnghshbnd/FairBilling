package com.bt.processor;

import com.bt.model.SessionDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogParserTest {
    private LogParser logParser;

    @BeforeEach
    void setUp() {
        logParser = new LogParser();
    }

    @Test
    void testParseLogFile(@TempDir Path tempDir) throws Exception {
        Path logFile = tempDir.resolve("test.log");
        Files.write(logFile, (
                "14:02:03 ALICE99 Start\n" +
                        "14:02:05 CHARLIE End\n" +
                        "14:02:34 ALICE99 End\n" +
                        "14:02:58 ALICE99 Start\n" +
                        "14:03:02 CHARLIE Start\n" +
                        "14:03:33 ALICE99 Start\n" +
                        "14:03:35 ALICE99 End\n" +
                        "14:03:37 CHARLIE End\n" +
                        "14:04:05 ALICE99 End\n" +
                        "14:04:13 CHARLIE Start\n" +
                        "14:04:14 CHARLIE Start\n" +
                        "14:04:19 CHARLIE Start\n" +
                        "14:04:20 CHARLIE End\n" +
                        "DakjhaNSSRRGLKJAHSNRGLIJASHDFGLKJASFG\n" +
                        "14:04:21 CHARLIE End\n" +
                        "14:04:22 CHARLIE End\n" +
                        "14:04:23 SADRA Start\n" +
                        "14:04:23 CHARLIE Start\n" +
                        "14:04:23 DAVID End\n" +
                        "14:04:33 CHARLIE Start\n" +
                        "14:04:53 ALICE99 End"

        ).getBytes());

        Map<String, SessionDetail> usersSessionsDetail = logParser.parseLogFile(logFile);

        assertEquals(4, usersSessionsDetail.size());
        assertTrue(usersSessionsDetail.containsKey("ALICE99"));
        assertTrue(usersSessionsDetail.containsKey("CHARLIE"));
        assertTrue(usersSessionsDetail.containsKey("SADRA"));
        assertTrue(usersSessionsDetail.containsKey("DAVID"));

        assertEquals(4, usersSessionsDetail.get("ALICE99").getSessionCounter());
        assertEquals(270, usersSessionsDetail.get("ALICE99").getTotalSessionDuration());

        assertEquals(7, usersSessionsDetail.get("CHARLIE").getSessionCounter());
        assertEquals(104, usersSessionsDetail.get("CHARLIE").getTotalSessionDuration());

        assertEquals(1, usersSessionsDetail.get("SADRA").getSessionCounter());
        assertEquals(30, usersSessionsDetail.get("SADRA").getTotalSessionDuration());

        assertEquals(1, usersSessionsDetail.get("DAVID").getSessionCounter());
        assertEquals(140, usersSessionsDetail.get("DAVID").getTotalSessionDuration());
    }

    @Test
    void testParseLogFileWithMissingStart(@TempDir Path tempDir) throws Exception {
        Path logFile = tempDir.resolve("test.log");
        Files.write(logFile, (
                "14:02:05 ALICE99 End\n" +
                        "14:02:34 ALICE99 Start\n" +
                        "14:03:35 ALICE99 End"
        ).getBytes());

        Map<String, SessionDetail> result = logParser.parseLogFile(logFile);

        SessionDetail aliceDetail = result.get("ALICE99");
        assertEquals(2, aliceDetail.getSessionCounter());
        assertEquals(61, aliceDetail.getTotalSessionDuration());
    }

    @Test
    void testParseLogFileWithMissingEnd(@TempDir Path tempDir) throws Exception {
        Path logFile = tempDir.resolve("test.log");
        Files.write(logFile, (
                "14:02:03 ALICE99 Start\n" +
                        "14:02:34 ALICE99 End\n" +
                        "14:03:35 ALICE99 Start"
        ).getBytes());

        Map<String, SessionDetail> result = logParser.parseLogFile(logFile);

        SessionDetail aliceDetail = result.get("ALICE99");
        assertEquals(2, aliceDetail.getSessionCounter());
        assertEquals(31, aliceDetail.getTotalSessionDuration());
    }

    @Test
    void testParseLogFileWithInvalidLines(@TempDir Path tempDir) throws Exception {
        Path logFile = tempDir.resolve("test.log");
        Files.write(logFile, (
                "14:02:03 ALICE99 Start\n" +
                        "Invalid line\n" +
                        "14:02:34 ALICE99 End\n" +
                        "Another invalid line\n" +
                        "14:03:35 ALICE99 Start\n" +
                        "14:04:05 ALICE99 End"
        ).getBytes());

        Map<String, SessionDetail> result = logParser.parseLogFile(logFile);

        SessionDetail aliceDetail = result.get("ALICE99");
        assertEquals(2, aliceDetail.getSessionCounter());
        assertEquals(61, aliceDetail.getTotalSessionDuration());
    }
}